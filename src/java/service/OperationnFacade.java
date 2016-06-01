/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Caisse;
import bean.CaisseTTCaisse;
import bean.Operationn;
import controler.util.SessionUtil;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.sf.jasperreports.engine.JRException;
import pdfUtil.PdfUtil;

/**
 *
 * @author samia
 */
@Stateless
public class OperationnFacade extends AbstractFacade<Operationn> {

    @PersistenceContext(unitName = "projetsDernierePU")
    private EntityManager em;
    @EJB
    private CaisseFacade caisseFacade;
    @EJB
    private CaisseTTCaisseFacade caisseTTCaisseFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OperationnFacade() {
        super(Operationn.class);
    }

    public int ajouterOperation(Operationn operation) {
        System.out.println("haa operrationn" + operation);
        System.out.println("haa operrationn getCaisse" + operation.getCaisse());
        String req = " select c from Caisse c where c.id like '" + operation.getCaisse().getId() + "'";
        Caisse loaded = (Caisse) em.createQuery(req).getSingleResult();
        System.out.println("haa lcaisseeeeee" + loaded.getId());

        if (loaded.getId() != null) {
            System.out.println("haa typoeper" + operation.getTypeOperation());
            if (operation.getType().equals("revenue")) {
                loaded.setEntree(loaded.getEntree() + operation.getMontant());
                System.out.println("haaa typee" + loaded.getEntree());
                operation.setCaisse(loaded);
                operation.setEtatValide(1);
                operation.setEtatDete(1);
                create(operation);
                System.out.println("haa des op" + operation.getDescriptionCai().getDescription());
                operation.setDescriptioncaise(operation.getDescriptionCai().getDescription());
                edit(operation);
                caisseFacade.edit(loaded);
                return 1;
            } else if (operation.getType().equals("depense")) {
                if (loaded.getEntree() > operation.getMontant()) {
                    loaded.setEntree(loaded.getEntree() - operation.getMontant());
                    System.out.println("haaa typee" + loaded.getEntree());
                    loaded.setDepense(loaded.getDepense() + operation.getMontant());
                    System.out.println("haaa typee" + loaded.getDepense());
                    operation.setCaisse(loaded);
                    operation.setEtatValide(1);
                    operation.setEtatDete(1);
                    create(operation);
                    operation.setDescriptioncaise(operation.getDescriptionCai().getDescription());
                    edit(operation);
                    caisseFacade.edit(loaded);
                    return 2;
                }
            } else if (operation.getType().equals("dete")) {
                loaded.setDete(loaded.getDete() + operation.getMontant());
                operation.setCaisse(loaded);
                operation.setEtatValide(1);
                operation.setEtatDete(1);
                create(operation);
                operation.setDescriptioncaise(operation.getDescriptionCai().getDescription());
                edit(operation);
                caisseFacade.edit(loaded);
                return 3;
            }

        }
        return -1;
    }

    public List<Operationn> findByType() {
        String req = " select o from Operationn o where o.etatValide = 1 ";
        return em.createQuery(req).getResultList();
    }

    public List<Operationn> findOp(String typeOp) {
        String requette = " select o from Operationn o where o.type like '" + typeOp + "'";
        List<Operationn> list = em.createQuery(requette).getResultList();
        return list;
    }

    public void generatePdf(Operationn operationn) throws JRException, IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("date", new Date());
        params.put("relever", operationn.getNumReleve());
        params.put("operation", operationn.getDescriptionOper());
        params.put("type", operationn.getType());
        params.put("montant", operationn.getMontant());
        params.put("responsable", operationn.getResponsable());
        PdfUtil.generatePdf(findAll(), params, "information Operation" + operationn.getId(), "/report/Operationn.jasper");
    }

    public List<Operationn> rechercheByCritere(Operationn operation) {
        String req = " SELECT o FROM Operationn o WHERE 1=1";
        if (operation.getType() != null && !operation.getType().equals("")) {
            req += " AND o.type LIKE  '" + operation.getType() + "' ";
        }
        if (operation.getCaisse().getId() != null && !operation.getCaisse().getId().equals("") && operation.getCaisse() != null && !operation.getCaisse().equals("")) {
            req += " AND o.caisse.id LIKE '" + operation.getCaisse().getId() + "' ";
        }
        if (operation.getDate1() != null && !operation.getDate1().equals("")) {
            req += " AND o.date1 = '" + operation.convertUtilToSql((Date) operation.getDate1()) + "' ";
        }
        if (operation.getEtatValide() != 0) {
            req += " AND o.etatValide = '" + operation.getEtatValide() + "' ";
        }
        List<Operationn> lista = em.createQuery(req).getResultList();
      
        return lista;
    }

//    public void selectDate(Operationn operation) {
//        Date date = operation.getDate1();
//        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        int month = localDate.getMonthValue();
//        int year = localDate.getYear();
//        System.out.println("haa lmonth "+ month );
//        System.out.println("haa lmonth "+ year );
//
//    }

    
public List<Operationn> selectOperCaisse() {
        System.out.println("hahwwaa waasll lop");
        List<Operationn> newList1;
        newList1 = new ArrayList<>();
        CaisseTTCaisse caisseTTCaisse = SessionUtil.getCaisseTTCaisse();
        if (caisseTTCaisse != null) {
            CaisseTTCaisse loaded = caisseTTCaisseFacade.find(caisseTTCaisse.getId());
            if (loaded != null) {
                LocalDate localDate1 = loaded.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int month1 = localDate1.getMonthValue();
                int year1 = localDate1.getYear();
                   System.out.println("haa lmonth1"+month1);
                    System.out.println("haa lyear1"+year1);
                String req = " select o from Operationn o where o.caisse.id like '" + loaded.getTypeCaisse() + "' ";
                System.out.println("haa re quetta"+req);
                List<Operationn> list = em.createQuery(req).getResultList();
                for (int i = 0; i < list.size(); i++) {
                    Operationn get = list.get(i);
                    LocalDate localDate = get.getDate1().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    int month = localDate.getMonthValue();
                    int year = localDate.getYear();
                    System.out.println("haa lmonth"+month);
                    System.out.println("haa lyear"+year);
                    if (month == month1 && year == year1) {
                        newList1.add(get);
                        System.out.println("haaa lisst"+newList1);
                    }
                }
                return newList1;
            }
        }
        return null;
    }
    
    
}

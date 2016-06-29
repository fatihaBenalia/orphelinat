/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.ArchiveOperation;
import bean.Caisse;
import bean.CaisseMoisCaisse;
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
    @EJB
    private CaisseMoisCaisseFacade caisseMoisCaisseFacade;
    @EJB
    private ArchiveOperationFacade archiveOperationFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OperationnFacade() {
        super(Operationn.class);
    }

    public void createOperationArchive2(Operationn operation) {
        System.out.println("hahiaa tcreate op archive11");
        Date date = new Date();

        ArchiveOperation operationnArchive = new ArchiveOperation();

        operationnArchive.setDate1(operation.getDate1());
        operationnArchive.setId(operation.getId());
        operationnArchive.setDate2(operation.getDate2());
        operationnArchive.setDateModification(operation.getDate1());
        operationnArchive.setDescriptioncaise(operation.getCaisse().getId());
        operationnArchive.setFournisseur(operation.getFournisseur());
        operationnArchive.setMediataire(operation.getMediataire());
        operationnArchive.setMontant(operation.getMontant());
        operationnArchive.setNbrMois(operation.getNbrMois());
        operationnArchive.setNumCheque(operation.getNumCheque());
        operationnArchive.setNumFacture(operation.getNumFacture());
        operationnArchive.setResponsable(operation.getResponsable());
        operationnArchive.setNumReleve(operation.getNumReleve());
        operationnArchive.setType(operation.getType());
        operationnArchive.setTypeOperation(operation.getTypeOperation());
        archiveOperationFacade.create(operationnArchive);
        System.out.println("haa op Archiveww" + operationnArchive);
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
                loaded.setProfit(loaded.getProfit() + operation.getMontant());
                System.out.println("haaa typee" + loaded.getEntree());
                operation.setCaisse(loaded);
                operation.setEtatValide(1);
                operation.setEtatDete(1);
              
                operation.setPhoto(null);
                operation.setEtatModification(1);
                create(operation);
                createOperationArchive2(operation);
                System.out.println("haa des op" + operation.getDescriptionCai().getDescription());
                operation.setDescriptioncaise(operation.getDescriptionCai().getDescription());
                operation.setPhoto(null);
                edit(operation);
                caisseFacade.edit(loaded);
                System.out.println("haaa l op t editat");

                System.out.println("hahiaa tcreate op archive");
                return 1;
            } else if (operation.getType().equals("depense")) {
                if (loaded.getEntree() > operation.getMontant()) {
                    loaded.setEntree(loaded.getEntree() - operation.getMontant());
                    loaded.setProfit(loaded.getProfit() - operation.getMontant());
                    System.out.println("haaa typee" + loaded.getEntree());
                    loaded.setDepense(loaded.getDepense() + operation.getMontant());
                    System.out.println("haaa typee" + loaded.getDepense());
                    operation.setCaisse(loaded);
                    operation.setEtatValide(1);
                    operation.setEtatDete(1);
                    operation.setPhoto(null);
                    operation.setEtatModification(1);
                    create(operation);
                    createOperationArchive2(operation);
                    operation.setDescriptioncaise(operation.getDescriptionCai().getDescription());
                    operation.setPhoto(null);
                    edit(operation);
                    caisseFacade.edit(loaded);

                    System.out.println("hahiaa tcreate op archive");
                    return 2;
                }
            } else if (operation.getType().equals("dete")) {
                loaded.setDete(loaded.getDete() + operation.getMontant());
                operation.setCaisse(loaded);
                operation.setEtatValide(1);
                operation.setEtatDete(1);
                operation.setPhoto(null);
                operation.setEtatModification(1);
                create(operation);
                createOperationArchive2(operation);
                operation.setDescriptioncaise(operation.getDescriptionCai().getDescription());
                operation.setPhoto(null);

                System.out.println("hahiaa tcreate op archive");

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
                System.out.println("haa lmonth1" + month1);
                System.out.println("haa lyear1" + year1);
                String req = " select o from Operationn o where o.caisse.id like '" + loaded.getTypeCaisse() + "' ";
                System.out.println("haa re quetta" + req);
                List<Operationn> list = em.createQuery(req).getResultList();
                for (int i = 0; i < list.size(); i++) {
                    Operationn get = list.get(i);
                    LocalDate localDate = get.getDate1().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    int month = localDate.getMonthValue();
                    int year = localDate.getYear();
                    System.out.println("haa lmonth" + month);
                    System.out.println("haa lyear" + year);
                    if (month == month1 && year == year1) {
                        newList1.add(get);
                        System.out.println("haaa lisst" + newList1);
                    }
                }
                return newList1;
            }
        }
        return null;
    }

    public List<Caisse> findCaisse(String typeCaisse) {
        String req = "select c from Caisse c where c.type like  '" + typeCaisse + "' ";
        List<Caisse> lista = em.createQuery(req).getResultList();
        System.out.println("haa lista" + lista);
        return lista;
    }

    public void createOperationArchive(Operationn operation) {
        System.out.println("hahiaa tcreate op archive11");
        ArchiveOperation operationnArchive = new ArchiveOperation();
        operationnArchive.setDate1(operation.getDate1());
        operationnArchive.setDate2(operation.getDate2());
        operationnArchive.setDescriptioncaise(operation.getDescriptioncaise());
        operationnArchive.setFournisseur(operation.getFournisseur());
        operationnArchive.setMediataire(operation.getMediataire());
        operationnArchive.setMontant(operation.getMontant());
        operationnArchive.setNbrMois(operation.getNbrMois());
        operationnArchive.setNumCheque(operation.getNumCheque());
        operationnArchive.setNumFacture(operation.getNumFacture());
        operationnArchive.setResponsable(operation.getResponsable());
        operationnArchive.setNumReleve(operation.getNumReleve());
        operationnArchive.setType(operation.getType());
        operationnArchive.setTypeOperation(operation.getTypeOperation());
        archiveOperationFacade.create(operationnArchive);
        System.out.println("haa op Archive" + operationnArchive);
    }

    public void createOperationArchive1(Operationn operation, double mont) {
        Date date = new Date();
        ArchiveOperation operationnArchive = new ArchiveOperation();
        operationnArchive.setDate1(operation.getDate1());
        operationnArchive.setDate2(operation.getDate2());
        operationnArchive.setDescriptioncaise(operation.getDescriptioncaise());
        operationnArchive.setFournisseur(operation.getFournisseur());
        operationnArchive.setMediataire(operation.getMediataire());
        operationnArchive.setMontant(mont);
        operationnArchive.setNbrMois(operation.getNbrMois());
        operationnArchive.setNumCheque(operation.getNumCheque());
        operationnArchive.setNumFacture(operation.getNumFacture());
        operationnArchive.setResponsable(operation.getResponsable());
        operationnArchive.setNumReleve(operation.getNumReleve());
        operationnArchive.setType(operation.getType());
        operationnArchive.setTypeOperation(operation.getTypeOperation());
        operationnArchive.setDateModification(date);
        operationnArchive.setEtatModification(2);
        archiveOperationFacade.create(operationnArchive);
    }

    public java.util.Date convertUtilToSql(java.util.Date utilDate) {

        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        return sqlDate;
    }

    public void afficheer() {
        System.out.println("fiiink");
    }

    public int modifierMontOperation(Operationn operation) {
        Operationn loaded = find(operation.getId());
        double mont2;
        double mont3;
        System.out.println("haa loaded" + loaded);
        if (loaded != null) {
            loaded.setMontant(operation.getMontant());
            edit(loaded);

//            System.out.println("haaaaaaaaaaqqqq" + hada.getDate1());
//            LocalDate localDate1 = hada.getDate1().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//            int month1 = localDate1.getMonthValue();
//            int year1 = localDate1.getYear();
//            System.out.println("haaa nb1r" + month1);
//            System.out.println("haaa nb1r" + year1);
        }
        return 1;
    }

    public int aff() {
        System.out.println("haa l growll");
        return 1;
    }

    public int validerDete(Operationn operation) {
        Operationn loaded = find(operation.getId());
        Date date = new Date();
        if (loaded != null && loaded.getType().equals("dete")) {
            Caisse caisse = loaded.getCaisse();
            Caisse loadedcaisse = caisseFacade.find(caisse.getId());
            if (loadedcaisse != null) {
                if (loadedcaisse.getEntree() > loaded.getMontant()) {
                    loadedcaisse.setEntree(loadedcaisse.getEntree() - loaded.getMontant());
                    loadedcaisse.setProfit(loadedcaisse.getProfit() - loaded.getMontant());
                    loadedcaisse.setDepense(loadedcaisse.getDepense() + loaded.getMontant());
                    loaded.setEtatDete(2);
                    loaded.setDate1(date);
                    edit(loaded);
                    return 1;
                } else if (loadedcaisse.getEntree() < loaded.getMontant()) {
                    return -1;
                }
            }
        }
        return -2;
    }

    public int modifierMontOperationf(Operationn operation) {
        Operationn loaded = find(operation.getId());

        Date date = new Date();
        System.out.println("haaa date" + date);
        double mont2;
        double mont3;
        double montant;
        ArchiveOperation archiveOperation = new ArchiveOperation();
        System.out.println("haa loaded" + loaded);
        LocalDate localDate1 = loaded.getDate1().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month1 = localDate1.getMonthValue();
        int year1 = localDate1.getYear();
        Caisse caisse = loaded.getCaisse();
        String typeOp = loaded.getType();
        System.out.println("haa lyear lwal" + year1);
        System.out.println("haa lmonth lwal" + month1);
        System.out.println("haa lcaisse lwal" + caisse);
        System.out.println("haa typeOp lwal" + typeOp);
        if (loaded.getId() != null) {
            loaded.setMontant(operation.getMontant());
            loaded.setEtatModification(2);
            edit(loaded);
            String requette = " select o from ArchiveOperation o ";
            List<ArchiveOperation> listaq = em.createQuery(requette).getResultList();
            System.out.println("haaaaaaaaaaqqqq" + listaq);
            for (int i = 0; i < listaq.size(); i++) {
                ArchiveOperation get = listaq.get(0);
                LocalDate localDate4 = get.getDate1().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int month4 = localDate4.getMonthValue();
                int year4 = localDate4.getYear();
                System.out.println("haa datee rab3a" + month4);
                System.out.println("haa datee rab3a" + year4);
                if (get.getId().equals(loaded.getId())) {
                    archiveOperation = get;
                    System.out.println("haaaa l archive get" + archiveOperation);

                }
            }
            if (loaded.getType().equals("revenue")) {
                System.out.println("hahwaa l revenue");
                System.out.println("haa hwa lmnt loaded" + loaded.getMontant());
                System.out.println("haa hwa lmnt archive" + archiveOperation.getMontant());
                if (loaded.getMontant() > archiveOperation.getMontant()) {
                    double mont = loaded.getMontant() - archiveOperation.getMontant();
                    montant = archiveOperation.getMontant();
                    System.out.println("haa lmont li n9asnaa");
                    createOperationArchive1(loaded, loaded.getMontant());
                    editCaissettCaisseMoisCaisse(loaded, mont, montant);
                    return 1;
                } else if (loaded.getMontant() < archiveOperation.getMontant()) {
                    montant = archiveOperation.getMontant();
                    double mont = archiveOperation.getMontant() - loaded.getMontant();
                    createOperationArchive1(loaded, loaded.getMontant());
                    editCaissettCaisseMoisCaisse(loaded, mont, montant);
                    return 2;
                }
            } else if (loaded.getType().equals("depense")) {
                if (loaded.getMontant() > archiveOperation.getMontant()) {
                    montant = archiveOperation.getMontant();
                    double mont = loaded.getMontant() - archiveOperation.getMontant();
                    createOperationArchive1(loaded, loaded.getMontant());
                    editCaissettCaisseMoisCaisse(loaded, mont, montant);
                    return 3;
                } else if (loaded.getMontant() < archiveOperation.getMontant()) {
                    montant = archiveOperation.getMontant();
                    double mont = archiveOperation.getMontant() - loaded.getMontant();
                    createOperationArchive1(loaded, loaded.getMontant());
                    editCaissettCaisseMoisCaisse(loaded, mont, montant);
                    return 4;
                }

            }

        }
        return -1;
    }

   
    
    public void editCaissettCaisseMoisCaisse(Operationn operation, double mont, double montant) {
        LocalDate localDate1 = operation.getDate1().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month1 = localDate1.getMonthValue();
        int year1 = localDate1.getYear();

        String req = " select c from CaisseTTCaisse c";
        List<CaisseTTCaisse> lista = em.createQuery(req).getResultList();
        System.out.println("haa listaa dyal tt caisse" + lista);
        for (int i = 0; i < lista.size(); i++) {
            CaisseTTCaisse get = lista.get(i);
            LocalDate localDate2 = get.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int month2 = localDate2.getMonthValue();
            int year2 = localDate2.getYear();
            System.out.println("hha month tani" + month2);
            System.out.println("hha year tani" + year2);
            System.out.println("hha type type" + get.getType());
            System.out.println("hha typeCai typeCai" + get.getTypeCaisse());

            if (get.getTypeCaisse().equals(operation.getCaisse().getId()) && month2 == month1 && year1 == year2) {
                String reqt = " select m from CaisseMoisCaisse m";
                List<CaisseMoisCaisse> listaaa = em.createQuery(reqt).getResultList();
                System.out.println("haaa list mois caisse" + listaaa);
                if (operation.getType().equals("revenue")) {
                    System.out.println("hahwaa l revenue");
                    System.out.println("haa hwa lmnt loaded" + operation.getMontant());
                    if (operation.getMontant() > montant) {
                        get.setEntree(get.getEntree() + mont);
                        get.setProfit(get.getProfit() + mont);
                        caisseTTCaisseFacade.edit(get);
                        System.out.println("haaa l tt caisse t editaa" + get.getEntree());
                        System.out.println("haaa l tt caisse t editaa" + get.getProfit());
                        for (int j = 0; j < listaaa.size(); j++) {
                            CaisseMoisCaisse get1 = listaaa.get(j);
                            LocalDate localDate3 = get1.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                            int month3 = localDate3.getMonthValue();
                            int year3 = localDate3.getYear();
                            System.out.println("hha month talt" + month3);
                            System.out.println("hha year talt" + year3);
                            if (month2 == month3 && year2 == year3 && get.getType().equals(get1.getType())) {
                                get1.setEntree(get1.getEntree() + mont);
                                get1.setProfit(get1.getProfit() + mont);
                                caisseMoisCaisseFacade.edit(get1);
                                System.out.println("haa lnois caisse t editaa");
                            }
                        }
                    } else if (operation.getMontant() < montant) {
                        get.setEntree(get.getEntree() - mont);
                        get.setProfit(get.getProfit() - mont);
                        caisseTTCaisseFacade.edit(get);
                        System.out.println("haaa l tt caisse t editaa" + get.getEntree());
                        System.out.println("haaa l tt caisse t editaa" + get.getProfit());
                        for (int j = 0; j < listaaa.size(); j++) {
                            CaisseMoisCaisse get1 = listaaa.get(j);
                            LocalDate localDate3 = get1.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                            int month3 = localDate3.getMonthValue();
                            int year3 = localDate3.getYear();
                            System.out.println("hha month talt" + month3);
                            System.out.println("hha year talt" + year3);
                            if (month2 == month3 && year2 == year3 && get.getType().equals(get1.getType())) {
                                get1.setEntree(get1.getEntree() - mont);
                                get1.setProfit(get1.getProfit() - mont);
                                caisseMoisCaisseFacade.edit(get1);
                                System.out.println("haa lnois caisse t editaa");
                            }
                        }
                    }
                } else if (operation.getType().equals("depense")) {
                    if (operation.getMontant() > montant) {
                        get.setEntree(get.getEntree() - mont);
                        get.setProfit(get.getProfit() - mont);
                        get.setDepense(get.getDepense() + mont);
                        caisseTTCaisseFacade.edit(get);
                        for (int j = 0; j < listaaa.size(); j++) {
                            CaisseMoisCaisse get1 = listaaa.get(j);
                            LocalDate localDate3 = get1.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                            int month3 = localDate3.getMonthValue();
                            int year3 = localDate3.getYear();
                            if (month2 == month3 && year2 == year3 && get.getType().equals(get1.getType())) {
                                get1.setEntree(get1.getEntree() - mont);
                                get1.setProfit(get1.getProfit() - mont);
                                get1.setDepense(get1.getDepense() + mont);
                                caisseMoisCaisseFacade.edit(get1);
                            }
                        }

                    } else if (operation.getMontant() < montant) {
                        get.setEntree(get.getEntree() + mont);
                        get.setProfit(get.getProfit() + mont);
                        get.setDepense(get.getDepense() - mont);
                        caisseTTCaisseFacade.edit(get);
                        for (int j = 0; j < listaaa.size(); j++) {
                            CaisseMoisCaisse get1 = listaaa.get(j);
                            LocalDate localDate3 = get1.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                            int month3 = localDate3.getMonthValue();
                            int year3 = localDate3.getYear();
                            if (month2 == month3 && year2 == year3 && get.getType().equals(get1.getType())) {
                                get1.setEntree(get1.getEntree() + mont);
                                get1.setProfit(get1.getProfit() + mont);
                                get1.setDepense(get1.getDepense() - mont);
                                caisseMoisCaisseFacade.edit(get1);

                            }

                        }
                    }
                }

            }
        }
    }

    public List<ArchiveOperation> voirDetails(Operationn operation) {
        List<ArchiveOperation> newList = new ArrayList();
        Operationn loaded = find(operation.getId());
        if (loaded != null) {
            LocalDate localDate1 = loaded.getDate1().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int month1 = localDate1.getMonthValue();
            int year1 = localDate1.getYear();
            String req = " select a from ArchiveOperation a";
            List<ArchiveOperation> lista = em.createQuery(req).getResultList();
            System.out.println("haa lmonth1" + month1);
            System.out.println("haa lyear1" + year1);
            System.out.println("haa descpri" + loaded.getCaisse().getId());
            System.out.println("haa type" + loaded.getType());
            System.out.println("haa four" + loaded.getFournisseur());
            System.out.println("haa medi" + loaded.getMediataire());
            System.out.println("haa che" + loaded.getNumCheque());
            System.out.println("haa rel" + loaded.getResponsable());
            System.out.println("haa rel" + loaded.getMontant());
            for (int i = 0; i < lista.size(); i++) {
                ArchiveOperation get = lista.get(i);
                LocalDate localDate2 = get.getDate1().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int month2 = localDate2.getMonthValue();
                int year2 = localDate2.getYear();
                System.out.println("ha hwaa hnayaa");
                if (month2 == month1 && year2 == year1 && get.getDescriptioncaise().equals(loaded.getCaisse().getId()) && get.getType().equals(loaded.getType()) && get.getFournisseur().equals(loaded.getFournisseur()) && get.getMediataire().equals(loaded.getMediataire()) && get.getNumCheque().equals(loaded.getNumCheque()) && get.getNumReleve().equals(loaded.getNumReleve()) && get.getMontant() != loaded.getMontant()) {

                    System.out.println("haa lmonth1" + month1);
                    System.out.println("haa lyear1" + year1);
                    System.out.println("haa descpri" + get.getDescriptioncaise());
                    System.out.println("haa type" + get.getType());
                    System.out.println("haa four" + get.getFournisseur());
                    System.out.println("haa medi" + get.getMediataire());
                    System.out.println("haa che" + get.getNumCheque());
                    System.out.println("haa rel" + get.getResponsable());
                    System.out.println("haa rel" + get.getMontant());

                    newList.add(get);
                    System.out.println("haaa newList" + newList);
                    loaded.setEtatModification(1);
                    edit(loaded);
                    return newList;

                }
                System.out.println("mal9ach l archive");
            }

        }
        return null;
    }

    public int findOperation() {
        String req = " select o from Operationn o where o.etatModification = 2 ";
        List<Operationn> list = em.createQuery(req).getResultList();
        int res = list.size();
        return res;
    }

    public List<Operationn> listOperation() {
        String req = " select o from Operationn o where o.etatModification = 2 ";
        List<Operationn> list = em.createQuery(req).getResultList();
        return list;
    }

}

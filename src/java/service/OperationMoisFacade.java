/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Caisse;
import bean.CompteAssociationParrinage;
import bean.Dossier;
import bean.OperationMois;
import bean.Parametre;
import bean.Parrinage;
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
public class OperationMoisFacade extends AbstractFacade<OperationMois> {

    @PersistenceContext(unitName = "projetsDernierePU")
    private EntityManager em;
 @EJB
    private ParametreFacade parametreFacade;
    @EJB
    private CaisseFacade caisseFacade;
    @EJB
    private DossierFacade dossierFacade;
    @EJB
    private CompteAssociationParrinageFacade compteAssociationParrinage;
    @EJB
    private ParrinageFacade parrinageFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OperationMoisFacade() {
        super(OperationMois.class);
    }
//had service pour les parrinages d'un seule parrin et les parrinages de deux parrin

    public int addOperationDeMoi() {
        Parametre parametre;
        parametre = parametreFacade.chercher();
        String requette = " select d from Dossier d where d.etat = 3  or d.etat=2";
        List<Dossier> ds = em.createQuery(requette).getResultList();
        System.out.println("hani dawazt la requette==========>" + ds.toString());
        for (int i = 0; i < ds.size(); i++) {
            Dossier get = ds.get(i);
            CompteAssociationParrinage associationParrinage = compteAssociationParrinage.find(get.getId());
            System.out.println("haniiiiii bdiiiiiit ");
            String reString = "select p from Parrinage p where p.dossier.id =" + get.getId() + "";
            List<Parrinage> parrinage = em.createQuery(reString).getResultList();
            for (int j = 0; j < parrinage.size(); j++) {
                Parrinage get1 = parrinage.get(j);
                get1.setNbrjours1(get1.getNbrjours1() - 1);
                parrinageFacade.edit(get1);
            }
            System.out.println("voilaaaa hani kandawaz traitement ");
            if (associationParrinage.getMontant() >= parametre.getMontantComplete()) {
                associationParrinage.setMontant(associationParrinage.getMontant() - parametre.getMontantComplete());
                get.setMontantTotal(parametre.getMontantComplete());
                dossierFacade.edit(get);
                compteAssociationParrinage.edit(associationParrinage);
                Date date = new Date();
                OperationMois operationMois1 = new OperationMois();
                Caisse caisse1 = caisseFacade.find("approvisionnement");
                System.out.println("haniii f caisse1====>" + caisse1.toString());
                operationMois1.setCaisse(caisse1);
                operationMois1.setDossier(get);
                operationMois1.setDate(date);
                operationMois1.setMontant(parametre.getAlimentation());
                operationMois1.setDescription("approvisionnement");
                operationMois1.setEtat("debit");
                create(operationMois1);
                caisse1.setEntreeDossier(caisse1.getEntreeDossier() + parametre.getAlimentation());
                caisseFacade.edit(caisse1);
                System.out.println("haniiii saliiiiit traitement lawal");
                /**
                 * ************************************************************************
                 */
                OperationMois operationMois2 = new OperationMois();
                Caisse caisse2 = caisseFacade.find("sacrifice");
                System.out.println("haniii f caisse2====>" + caisse2.toString());
                operationMois2.setCaisse(caisse2);
                operationMois2.setDossier(get);
                operationMois2.setDate(date);
                operationMois2.setMontant(parametre.getSacrifice());
                operationMois2.setDescription("sacrifice");
                operationMois2.setEtat("debit");
                create(operationMois2);
                caisse2.setEntreeDossier(caisse2.getEntreeDossier() + parametre.getSacrifice());
                caisseFacade.edit(caisse2);
                System.out.println("haniiii saliiiiit traitement taniii");
                /**
                 * ****************************************************************************
                 */
                OperationMois operationMois3 = new OperationMois();
                Caisse caisse3 = caisseFacade.find("santé");
                System.out.println("haniii f caisse3====>" + caisse3.toString());
                operationMois3.setCaisse(caisse3);
                operationMois3.setDossier(get);
                operationMois3.setDate(date);
                operationMois3.setMontant(parametre.getSante());
                operationMois3.setDescription("santé");
                operationMois3.setEtat("debit");
                create(operationMois3);

                caisse3.setEntreeDossier(caisse3.getEntreeDossier() + parametre.getSante());
                caisseFacade.edit(caisse3);
                System.out.println("haniiii saliiiiit traitement taniii");
                /**
                 * ******************************************************************************
                 */
                OperationMois operationMois4 = new OperationMois();
                Caisse caisse4 = caisseFacade.find("scolarité");
                System.out.println("haniii f caisse4====>" + caisse4.toString());
                operationMois4.setCaisse(caisse4);
                operationMois4.setDossier(get);
                operationMois4.setDate(date);
                operationMois4.setMontant(parametre.getScolarité());
                operationMois4.setDescription("scolarité");
                operationMois4.setEtat("debit");
                create(operationMois4);
                caisse4.setEntreeDossier(caisse4.getEntreeDossier() + parametre.getScolarité());
                caisseFacade.edit(caisse4);
                System.out.println("haniiii saliiiiit traitement taniii");
                /**
                 * ******************************************************************************
                 */
                OperationMois operationMois5 = new OperationMois();
                Caisse caisse5 = caisseFacade.find("habillement");
                System.out.println("haniii f caisse4====>" + caisse5.toString());
                operationMois5.setCaisse(caisse5);
                operationMois5.setDossier(get);
                operationMois5.setDate(date);
                operationMois5.setMontant(parametre.getHabillement());
                operationMois5.setDescription("habillement");
                operationMois5.setEtat("debit");
                create(operationMois5);
                caisse5.setEntreeDossier(caisse5.getEntreeDossier() + parametre.getHabillement());
                caisseFacade.edit(caisse5);
                System.out.println("haniiii saliiiiit traitement taniii");
                /**
                 * ******************************************************************************
                 */
                OperationMois operationMois6 = new OperationMois();
                Caisse caisse6 = caisseFacade.find("matelasEtCouvertures");
                System.out.println("haniii f caisse6====>" + caisse6.toString());
                operationMois6.setCaisse(caisse6);
                operationMois6.setDossier(get);
                operationMois6.setDate(date);
                operationMois6.setMontant(parametre.getMatelasEtCouvertures());
                operationMois6.setDescription("matelasEtCouvertures");
                operationMois6.setEtat("debit");
                create(operationMois6);
                caisse6.setEntreeDossier(caisse6.getEntreeDossier() + parametre.getMatelasEtCouvertures());
                caisseFacade.edit(caisse6);
                System.out.println("haniiii saliiiiit traitement taniii");

            }

        }
        for (int i = 0; i < ds.size(); i++) {
            Dossier get = ds.get(i);
              String reString1 = "select p from Parrinage p where p.dossier.id =" + get.getId() + "";
            List<Parrinage> parrinagee = em.createQuery(reString1).getResultList();
            for (int j = 0; j < parrinagee.size(); j++) {
                Parrinage get1 = parrinagee.get(j);
                if (get1.getNbrjours1() == 0) {
                    get.setLaid(get1.getNbrjours1());
                    dossierFacade.edit(get);
                    parrinageFacade.edit(get1);

                }
            }
            
        }
        return 1;
    }

    public int DossierDeEtat2() {
        Parametre parametre;
        parametre = parametreFacade.chercher();
        String requette = " select d from Dossier d where d.etat = 1 ";
        List<Dossier> mm = em.createQuery(requette).getResultList();
        for (int i = 0; i < mm.size(); i++) {
            Dossier get = mm.get(i);
            CompteAssociationParrinage associationParrinage = compteAssociationParrinage.find(get.getId());
            System.out.println("haniiiiii bdiiiiiit ");
            String reString = "select p from Parrinage p where p.dossier.id =" + get.getId() + "";
            List<Parrinage> parrinage = em.createQuery(reString).getResultList();
            for (int j = 0; j < parrinage.size(); j++) {
                Parrinage get1 = parrinage.get(j);
                get1.setNbrjours1(get1.getNbrjours1() - 1);
                parrinageFacade.edit(get1);
            }
            System.out.println("voilaaaa hani kandawaz traitement ");
            if (associationParrinage.getMontant() >= parametre.getMontantPartielle()) {
                associationParrinage.setMontant(associationParrinage.getMontant() - parametre.getMontantPartielle());
                get.setMontantTotal(parametre.getMontantPartielle());
                dossierFacade.edit(get);
                compteAssociationParrinage.edit(associationParrinage);
                Date date = new Date();
                OperationMois operationMois1 = new OperationMois();
                Caisse caisse1 = caisseFacade.find("approvisionnement");
                System.out.println("haniii f caisse1====>" + caisse1.toString());
                operationMois1.setCaisse(caisse1);
                operationMois1.setDossier(get);
                operationMois1.setDate(date);
                operationMois1.setMontant(parametre.getAlimentation());
                operationMois1.setDescription("approvisionnement");
                operationMois1.setEtat("debit");
                create(operationMois1);
                caisse1.setEntreeDossier(caisse1.getEntreeDossier() + parametre.getAlimentation());
                caisseFacade.edit(caisse1);
                System.out.println("haniiii saliiiiit traitement lawal");
            }
        }
        return 1;

    }
//5asni nchouf kafala 3ama--------------

    public int dossier3() {
        Parametre parametre;
        parametre = parametreFacade.chercher();
        String requette = " select d from Dossier d where d.etat = 4 or d.etat=5 ";
        List<Dossier> mm = em.createQuery(requette).getResultList();
        for (int i = 0; i < mm.size(); i++) {
            Dossier get = mm.get(i);
            CompteAssociationParrinage associationParrinage = compteAssociationParrinage.find(get.getId());
            System.out.println("haniiiiii bdiiiiiit ");
            String reString = "select p from Parrinage p where p.dossier.id =" + get.getId() + "";
            List<Parrinage> parrinage = em.createQuery(reString).getResultList();
            for (int j = 0; j < parrinage.size(); j++) {
                Parrinage get1 = parrinage.get(j);
                get1.setNbrjours1(get1.getNbrjours1() - 1);
                parrinageFacade.edit(get1);
                
            }
            System.out.println("voilaaaa hani kandawaz traitement ");
            associationParrinage.setMontant(associationParrinage.getMontant() - parametre.getMontantPartielle());
            get.setMontantDepenseSanter(get.getMontantDepenseSanter() + parametre.getMontantPartielle());
            get.setLaid(get.getLaid()-1);
            dossierFacade.edit(get);
            compteAssociationParrinage.edit(associationParrinage);
            Date date = new Date();
            OperationMois operationMois1 = new OperationMois();
            Caisse caisse1 = caisseFacade.find("parrainageGlobale");
            System.out.println("haniii f caisse1====>" + caisse1.getId());
            operationMois1.setCaisse(caisse1);
            operationMois1.setDossier(get);
            operationMois1.setDate(date);
            operationMois1.setMontant(parametre.getAlimentation());
            operationMois1.setDescription("parrainageGlobale");
            operationMois1.setEtat("credit");
            create(operationMois1);
            caisse1.setEntree(caisse1.getEntree() - parametre.getAlimentation());
            caisse1.setDepense(caisse1.getDepense() + parametre.getAlimentation());
            caisseFacade.edit(caisse1);
            System.out.println("haniiii saliiiiit traitement dialiiii");

            return 1;
        }
        return -1;
    }
/////////////////////////////////pdf///////////////////
   
    public List<OperationMois> mesOperations() {
        List<OperationMois> newlist = new ArrayList<>();
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        String req = " select o from OperationMois o";
        List<OperationMois> listaa = em.createQuery(req).getResultList();
        for (int i = 0; i < listaa.size(); i++) {
            OperationMois get = listaa.get(i);
            Date date1 = get.getDate();
            LocalDate localDate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int month1 = localDate1.getMonthValue();
            int year1 = localDate1.getYear();
            if(month1 == month && year1 == year){
            newlist.add(get);
            
            }
        }
        return newlist;
    }

    public void generatePdf() throws JRException, IOException {
    List<OperationMois> list = mesOperations();
        Map<String, Object> params = new HashMap<>();
        PdfUtil.generatePdf(list, params, "information OperationMoi", "/report/ListOperationType1.jasper");
    }
   
    
}

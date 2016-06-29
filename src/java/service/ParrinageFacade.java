/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.CompteAssociationParrinage;
import bean.Dossier;
import bean.Operationn;
import bean.Parametre;
import bean.Parrain;
import bean.Parrinage;
import controler.util.SessionUtil;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ParrinageFacade extends AbstractFacade<Parrinage> {

    @PersistenceContext(unitName = "projetsDernierePU")
    private EntityManager em;
//    @EJB
//    private CompteParrainFacade compteParrainFacade;
    @EJB
    private CompteAssociationParrinageFacade compteAssociationParrinageFacade;
    @EJB
    private DossierFacade dossierFacade;
    @EJB
    private service.ParametreFacade parametreFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParrinageFacade() {
        super(Parrinage.class);
    }

//  public int ajouterNouveauParrinage(Parrinage parrinage){
//        Dossier dossier=SessionUtil.getDossier();
//        dossier.setEtat(1);
//        dossierFacade.edit(dossier);
//        System.out.println("ha dossier"+dossier.getId());
//        Parrain  parrain=SessionUtil.getParrin();
//         System.out.println("ha parrain"+parrain.getId());
//        parrinage.setDossier(dossier);
//        parrinage.setParrain(parrain);
//        parrinage.setType("total");
//        CompteParrain compteParrain=compteParrainFacade.find(parrain.getCompteParrain().getId());
//        compteParrain.setMontantFamille(compteParrain.getMontantFamille()+parrinage.getMontant());
//        create(parrinage);
//        System.out.println("ha lparrinage"+parrinage);
//        compteParrainFacade.edit(compteParrain);
//        return 1;
//    }
    public int ajouterNouveauParrinage(Parrinage parrinage) {
        Parrain parrain = SessionUtil.getParrin();
        Dossier dossier = SessionUtil.getDossier();
        parrinage.setDossier(dossier);
        parrinage.setParrain(parrain);
        CompteAssociationParrinage compteAssociationParrinage = compteAssociationParrinageFacade.find(dossier.getCompteAssociationParrinage().getId());
        compteAssociationParrinage.setMontant(compteAssociationParrinage.getMontant() + (parrinage.getNbrjours1() * parrinage.getMontant()));
        System.out.println("haa lcompte" + compteAssociationParrinage.getMontant());
        String requette = " select p from Parrinage p where p.dossier.id = '" + dossier.getId() + "' and p.parrain.id = '" + parrain.getId() + "' ";
        System.out.println("haa req" + requette);
        List<Parrinage> lista = em.createQuery(requette).getResultList();
        System.out.println("haa listaa" + lista);
        if (!lista.isEmpty()) {
            for (int j = 0; j < lista.size(); j++) {
                Parrinage get1 = lista.get(j);
                System.out.println("haa lget1" + get1);
                if (get1.getNbrjours1() == 0) {
                    System.out.println("haa l get1 nbrjr dyalo" + get1.getNbrjours1());
                    get1.setNbrjours1(get1.getNbrjours1() - 1);
                    get1.setEtatArchive(2);
                    dossier.setLaid(-1);
                    edit(get1);
                    dossierFacade.edit(dossier);

                }
            }
        }
        parrinage.setTotMontant(parrinage.getNbrjours1() * parrinage.getMontant());
        parrinage.setEtatArchive(1);
        super.create(parrinage);
        compteAssociationParrinageFacade.edit(compteAssociationParrinage);
        return 1;
    }

    public List<Parrinage> selectParrinage1() {
        String req = " select p from Parrinage p where p.etatArchive = 1 ";
        return em.createQuery(req).getResultList();
    }

  

    public List<Parrinage> rechercheByCritere(Parrinage parrinage1) {

        String req = " SELECT p FROM Parrinage p WHERE 1=1";
        if (parrinage1.getParrain().getNom() != null && !parrinage1.getParrain().getNom().equals("")) {
            req += " and p.parrain.nom like '" + parrinage1.getParrain().getNom() + "' and p.dossier.id  = '" + SessionUtil.getDossier().getId() + "' ";
        }
       
        System.out.println("ha requetaaa " + req);

        List<Parrinage> lista = em.createQuery(req).getResultList();
               
        System.out.println("haaa listaa" + lista);

        return lista;
    }

    public int nbrMonth(List<Parrinage> parrinages) {
        Date date = new Date();
        int resMonth = 0;
        
        if (!parrinages.isEmpty()) {
            System.out.println("laa liste dyalnaa"+parrinages);
            long nbr = parrinages.size();
            System.out.println("haa nbr dyalna" + nbr);
            Parrinage parrinage = super.find(nbr);
            System.out.println("hahiaa molati lparrinage"+parrinage);
            System.out.println("haanii jiiit");
            int nbrjr = parrinage.getNbrjours1();
            Date date1 = parrinage.getDebutPar();
            System.out.println("ha nbr jr "+nbrjr);
            System.out.println("ha date jr "+date1);
            LocalDate localDate = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int month = localDate.getMonthValue();
            int year = localDate.getYear();

            LocalDate localDate1 = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int month1 = localDate1.getMonthValue();
            int year1 = localDate1.getYear();
            if (year1 == year && month1 >= month) {
                resMonth = month1 - month;
            } else if (year1 > year && month1 > month) {
                int res = year1 - year;
                resMonth = ((res * 12) + month1) - month;
            } else if (year1 > year && month1 == month) {
                resMonth = (year1 - year) * 12;
            } else if (year1 > year && month1 < month) {
                int res = (year1 - year) * 12;
                int res1 = month - month1;
                resMonth = res - res1;
            }
        }
        System.out.println("haaa lnbr d lmonth" + resMonth);
        return resMonth;
    }

    public double moisNonPaye(int nbr) {
         Parametre parametre =parametreFacade.chercher();
        double mois = -(nbr * parametre.getMontantPartielle());
        return mois;
    }

    public String getNomParrain(String nom) {
        System.out.println("hahnaa dkhlnaa lmethode parrain");
        String prenom;
        String nomPrenom = null;
        String req = " select p from Parrain p where p.nom like '" + nom + "' ";
        List<Parrain> parrains = em.createQuery(req).getResultList();
        System.out.println("haa req parrain"+req);
        if (!parrains.isEmpty()) {
            System.out.println("haaa ;istaa parrain"+parrains);
            Parrain parrain = parrains.get(0);
            System.out.println("haaa lparrain dyalna"+parrain.getNom());
            prenom = parrain.getPrenom();
            nomPrenom = nom +" "+prenom;
            System.out.println("haa prenom"+prenom);
            System.out.println("haa syaad"+nomPrenom);
        }
        return nomPrenom;
    }

    public Date getDate(){
        Date date = new Date();
        System.out.println("haa date "+ date);
        return date;
    }
    
      public void generatePdf(Parrinage parrinage) throws JRException, IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("p1", new Date());
        params.put("p2", parrinage.getDossier().getNomFamille());
        params.put("p3", parrinage.getParrain().getNom());
        params.put("p4", parrinage.getParrain().getPrenom());
        params.put("p5", parrinage.getMontant());
        params.put("p6", parrinage.getParrain().getMediataire().getNom());
        params.put("p7", parrinage.getParrain().getMediataire().getPrenom());
        params.put("p8", parrinage.getTypePaiement());
        params.put("p9", parrinage.getNumCheque());
        PdfUtil.generatePdf(findAll(), params, "information Parrinage" + parrinage.getId(), "/report/Parrinage.jasper");
    }
      
      
}


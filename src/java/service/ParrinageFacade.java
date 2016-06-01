/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;
import bean.CompteAssociationParrinage;
import bean.Dossier;
import bean.Operationn;
import bean.Parrain;
import bean.Parrinage;
import controler.util.SessionUtil;
import java.io.IOException;
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
public class ParrinageFacade extends AbstractFacade<Parrinage> {
    @PersistenceContext(unitName = "projetsDernierePU")
    private EntityManager em;
//    @EJB
//    private CompteParrainFacade compteParrainFacade;
    @EJB
    private CompteAssociationParrinageFacade compteAssociationParrinageFacade;
    @EJB
    private DossierFacade dossierFacade;
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
        compteAssociationParrinage.setMontant(compteAssociationParrinage.getMontant() + parrinage.getMontant());
         System.out.println("haa lcompte"+compteAssociationParrinage.getMontant());
        String requette= " select p from Parrinage p where p.dossier.id = '" + dossier.getId() + "' and p.parrain.id = '" + parrain.getId() + "' ";
         System.out.println("haa req"+requette);
        List<Parrinage> lista= em.createQuery(requette).getResultList();
         System.out.println("haa listaa"+lista);
         if(!lista.isEmpty()){
          for (int j = 0; j < lista.size(); j++) {
                Parrinage get1 = lista.get(j);
                System.out.println("haa lget1"+get1);
                if(get1.getNbrjours1() == 0){
                    System.out.println("haa l get1 nbrjr dyalo"+get1.getNbrjours1());
                    get1.setNbrjours1(get1.getNbrjours1()-1);
                    edit(get1);
                    dossierFacade.edit(dossier);
                    
                }
          }
         }
        super.create(parrinage);
        compteAssociationParrinageFacade.edit(compteAssociationParrinage);
        return 1;
    }
  
        public void generatePdf(Parrinage parrinage) throws JRException, IOException{
        Map<String,Object> params=new HashMap<>();
        params.put("date", new Date());
        params.put("nomfamille", parrinage.getDossier().getNomFamille());
        params.put("nom", parrinage.getParrain().getNom());
        params.put("prenom", parrinage.getParrain().getPrenom());
        params.put("montant",parrinage.getMontant());
        params.put("nommedia",parrinage.getParrain().getMediataire().getNom());
        params.put("prenommedia",parrinage.getParrain().getMediataire().getPrenom());
        PdfUtil.generatePdf(findAll(), params, "information Parrinage"+parrinage.getId(), "/report/Parrinage.jasper");
    }
  
}

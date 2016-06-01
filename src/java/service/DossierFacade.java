/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Caisse;
import bean.Dossier;
import bean.OperationMois;
import bean.Parametre;
import bean.Parrinage;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ASUS
 */
@Stateless
public class DossierFacade extends AbstractFacade<Dossier> {

    @PersistenceContext(unitName = "projetsDernierePU")
    private EntityManager em;
    @EJB
    private OperationMoisFacade operationMoisFacade;
    @EJB
    private CaisseFacade caisseFacade;
    @EJB
    private ParametreFacade parametreFacade;
    @EJB
    private ParrinageFacade parrinageFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

//    public int indiceOfParametre(){
//        List<Parametre> maList=parametreFacade.findAll();
//        int res=maList.size();
//        return res;
//    }
//    Parametre parametre=parametreFacade.find(indiceOfParametre());
    public DossierFacade() {
        super(Dossier.class);
    }

    public List<Dossier> mesDossiers() {
        String requette = " select d from Dossier d where d.etat > 0 ";
        List<Dossier> list = em.createQuery(requette).getResultList();
        for (int i = 0; i < list.size(); i++) {
            Dossier get = list.get(i);
            System.out.println("haa doss lwal" + get.getLaid());

            String req = " select p from Parrinage p where p.dossier.id =" + get.getId();
            List<Parrinage> lista = em.createQuery(req).getResultList();
            for (int j = 0; j < lista.size(); j++) {
                Parrinage get1 = lista.get(j);
                System.out.println("haa parr lwl" + get1.getNbrjours1());
                System.out.println("haa parr lwl" + get1.getNbrjours1());
                System.out.println("haa parr lwl" + get1.getNbrjours1());
                if (get1.getNbrjours1() == 0) {
                    get.setLaid(get1.getNbrjours1());
                    edit(get);
                    parrinageFacade.edit(get1);
                    System.out.println("haa dossier" + get.getLaid());

                }
            }

        }
        return list;
    }

    public int findAllParrinage1(Dossier dossier) {

        String requette = " select p from Parrinage p where p.dossier.id =" + dossier.getId();
        List<Parrinage> lista = em.createQuery(requette).getResultList();
        for (int i = 0; i < lista.size(); i++) {
            Parrinage get1 = lista.get(i);
            if (get1.getNbrjours1() != 0) {
                return 1;
            }
        }
        return -1;

    }

    public void operationCaisse(Dossier get, Caisse newCaisse) {
        Date date = new Date();
        Parametre parametre = parametreFacade.parametreEffectuer();
        OperationMois operationMois = new OperationMois();
        operationMois.setDossier(get);
        operationMois.setMontant(parametre.getSacrifice());
        operationMois.setDate(date);
        Caisse caisse = caisseFacade.find(newCaisse.getId());
        operationMois.setCaisse(caisse);
        edit(get);
        operationMoisFacade.create(operationMois);
        caisse.setEntree(caisse.getEntree() + parametre.getSacrifice());

    }
//    public int  validerParrinageMoi(){
//        
//        String requette="select d from Dossier d where d.etat=1";
//        List<Dossier> mesDossier=em.createQuery(requette).getResultList();
//        for (int i = 0; i < mesDossier.size(); i++) {
//            Dossier get = mesDossier.get(i);
//            
//            if(get.getCompteAssociationParrinage().getMontant()>parametre.getMontantComplete()){
//                get.setMontantTotal(parametre.getMontantComplete());
//                get.getCompteAssociationParrinage().setMontant(get.getCompteAssociationParrinage().getMontant()-parametre.getMontantComplete());
//                Date date=new Date();//convert to sql
//                OperationMois operationMois1=new OperationMois();
//                operationMois1.setDossier(get);
//                operationMois1.setMontant(parametre.getSacrifice());
//                operationMois1.setDate(date);
//                Caisse caisse=caisseFacade.find(1); //sacrifice
//                operationMois1.setCaisse(caisse);
//                edit(get);
//                operationMoisFacade.create(operationMois1);
//                caisse.setMontant(caisse.getMontant()+parametre.getSacrifice());
//                caisse.setType(1);//hatina
//                caisseFacade.edit(caisse);
//                            /**************************************/
//                OperationMois operationMois2=new OperationMois();
//                operationMois2.setDossier(get);
//                operationMois2.setMontant(parametre.getSante());
//                operationMois2.setDate(date);
//                Caisse caisse2=caisseFacade.find(2);//sante
//                operationMois2.setCaisse(caisse2);
//                edit(get);
//                operationMoisFacade.create(operationMois2);
//                caisse.setMontant(caisse2.getMontant()+parametre.getSante());
//                caisse.setType(2);//sante
//                caisseFacade.edit(caisse2);
//                /****************************************************************/
//                 OperationMois operationMois3=new OperationMois();
//                operationMois3.setDossier(get);
//                operationMois3.setMontant(parametre.getAlimentation());
//                operationMois3.setDate(date);
//                Caisse caisse3=caisseFacade.find(3);//alimentation
//                operationMois3.setCaisse(caisse3);
//                edit(get);
//                operationMoisFacade.create(operationMois3);
//                caisse.setMontant(caisse3.getMontant()+parametre.getAlimentation());
//                caisse.setType(3);//sante
//                caisseFacade.edit(caisse3);
//                    /****************************************************************/
//                 OperationMois operationMois4=new OperationMois();
//                operationMois4.setDossier(get);
//                operationMois4.setMontant(parametre.getHabillement());
//                operationMois4.setDate(date);
//                Caisse caisse4=caisseFacade.find(4);//habillement
//                operationMois4.setCaisse(caisse4);
//                edit(get);
//                operationMoisFacade.create(operationMois4);
//                caisse.setMontant(caisse4.getMontant()+parametre.getHabillement());
//                caisse.setType(4);//sante
//                caisseFacade.edit(caisse4);
//                /****************************************************************/
//                 OperationMois operationMois5=new OperationMois();
//                operationMois5.setDossier(get);
//                operationMois5.setMontant(parametre.getMatelasEtCouvertures());
//                operationMois5.setDate(date);
//                Caisse caisse5=caisseFacade.find(5);//habillement
//                operationMois5.setCaisse(caisse5);
//                edit(get);
//                operationMoisFacade.create(operationMois4);
//                caisse.setMontant(caisse5.getMontant()+parametre.getMatelasEtCouvertures());
//                caisse.setType(5);//sante
//                caisseFacade.edit(caisse5);
//                 /****************************************************************/
//                 OperationMois operationMois6=new OperationMois();
//                operationMois6.setDossier(get);
//                operationMois6.setMontant(parametre.getScolarité());
//                operationMois6.setDate(date);
//                Caisse caisse6=caisseFacade.find(6);//habillement
//                operationMois6.setCaisse(caisse6);
//                edit(get);
//                operationMoisFacade.create(operationMois6);
//                caisse.setMontant(caisse6.getMontant()+parametre.getScolarité());
//                caisse.setType(6);//scolarite
//                caisseFacade.edit(caisse6);
//            }
//           
//        }
//        
//        return 0;
//        
//    }

    public List<Dossier> findDosiier(int etatt) {
        String requette = " SELECT d FROM Dossier d WHERE d.etat = " + etatt;
        List<Dossier> lista = em.createQuery(requette).getResultList();
        System.out.println("haa listaa" + lista);
        return lista;
    }

    public List<Parrinage> findAllParrinage(Dossier dossier) {
        String requette = " select p from Parrinage p where p.dossier.id = '" + dossier.getId() + "' order by p.debutPar ASC";
        List<Parrinage> lista = em.createQuery(requette).getResultList();
        return lista;
    }

}

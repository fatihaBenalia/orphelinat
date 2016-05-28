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

            String req = " select p from Parrinage p where p.dossier.id =" + get.getId();
            List<Parrinage> lista = em.createQuery(req).getResultList();
            for (int j = 0; j < lista.size(); j++) {
                Parrinage get1 = lista.get(j);
              
                if (get1.getNbrjours1() == 0) {
                    get.setLaid(get1.getNbrjours1());
                    edit(get);
                    parrinageFacade.edit(get1);

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

    public List<Dossier> rechercheByCritere(Dossier dossier) {
    String req = " select d from Dossier d where 1=1";
    
    if(dossier.getNumDossier() != null && !dossier.getNumDossier().equals("")){
        req += " and d.numDossier like '" + dossier.getNumDossier() + "' ";
    }
    if(dossier.getEtat() != 0){
        req += " and d.etat like '" + dossier.getEtat()+ "' ";
    }
    
    if(dossier.getNomFamille() != null && !dossier.getNomFamille().equals("")){
        req += " and d.nomFamille like '" + dossier.getNomFamille()+ "' ";
    }
    if(dossier.getRegion()!= null && !dossier.getRegion().equals("")){
        req += " and d.region like '" + dossier.getRegion()+ "' ";
    }
    List<Dossier> lista = em.createQuery(req).getResultList();
        System.out.println("haa req"+req);
        System.out.println("haa newLista "+lista);
    return lista;
    
    }

    

}

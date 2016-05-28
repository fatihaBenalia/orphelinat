/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Parrain;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author samia
 */
@Stateless
public class ParrainFacade extends AbstractFacade<Parrain> {

    @PersistenceContext(unitName = "projetsDernierePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParrainFacade() {
        super(Parrain.class);
    }


 public List<Parrain> rechercher(Parrain parrainn1) {
    String requette= " SELECT p FROM Parrain p WHERE 1=1";
    if(parrainn1.getNom() != null && !parrainn1.getNom().equals("")){
        requette += " AND p.nom like '" + parrainn1.getNom() + "' ";
    }
    if(parrainn1.getPrenom() != null && !parrainn1.getPrenom().equals("")){
        requette += " AND p.prenom like '" + parrainn1.getPrenom() + "' ";
    }
     System.out.println("haa requ"+requette);
    List<Parrain> lista= em.createQuery(requette).getResultList();
    return lista;
    }
    /* Apres la modification de la conception */
    

    public int createParrain(Parrain parrain) {
       String req = " select p from Parrain p where p.nom like '" + parrain.getNom() + "' and p.prenom like '" + parrain.getPrenom() + "' ";
       List<Parrain> lista = em.createQuery(req).getResultList();
       if(!lista.isEmpty()){
           return -1;
       }
       super.create(parrain);
        return 1;
  
    }
    
}

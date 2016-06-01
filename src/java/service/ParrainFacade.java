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


 public List<Parrain> rechercher(String nom,String prenom) {
    String requette= " SELECT p FROM Parrain p WHERE 1=1";
    if(nom != null ){
        requette += " AND p.nom like '" + nom + "' ";
    }
    if(prenom != null){
        requette += " AND p.prenom like '" + prenom + "' ";
    }
     System.out.println("haa requ"+requette);
    List<Parrain> lista= em.createQuery(requette).getResultList();
    return lista;
    }
    /* Apres la modification de la conception */
    

    
}

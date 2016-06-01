/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Caisse;
import bean.Description;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author samia
 */
@Stateless
public class DescriptionFacade extends AbstractFacade<Description> {

    @PersistenceContext(unitName = "projetsDernierePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DescriptionFacade() {
        super(Description.class);
    }
    
//    public List<Description> findByCaise(String id){
//        String requette= "select d from  Description d where d.idCaise like ' " + id + "' ";
//        System.out.println("ha req"+requette);
//        List<Description> mm =em.createQuery(id)
//        System.out.println("ha moulati list====>"+mm);
//        return mm;
//    }
    
    public List<Description> findTypeCaisse(Caisse caisse){
        String req = " SELECT d From Description d WHERE d.idCaise LIKE  '" + caisse.getId() + "' ";
        System.out.println("haa req"+req);
        List<Description> lista = em.createQuery(req).getResultList();
        System.out.println("haa lista"+ lista);
        return lista;
    }
}

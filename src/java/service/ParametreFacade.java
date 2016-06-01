/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Parametre;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author samia
 */
@Stateless
public class ParametreFacade extends AbstractFacade<Parametre> {

    @PersistenceContext(unitName = "projetsDernierePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParametreFacade() {
        super(Parametre.class);
    }
  
    
      public Parametre parametreEffectuer() {
          Date date = new Date();
        List<Parametre> list = em.createQuery(" select p form  Parametre p where p.dateApplication <= '" + date + "' order by p.dateApplication DESC").setMaxResults(1).getResultList();
        if (list != null && list.isEmpty() && list.get(0) != null) {
            return list.get(0);
        }
        return new Parametre();
    }
}

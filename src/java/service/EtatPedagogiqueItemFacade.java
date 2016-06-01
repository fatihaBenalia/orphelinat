/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.EtatPedagogiqueItem;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author samia
 */
@Stateless
public class EtatPedagogiqueItemFacade extends AbstractFacade<EtatPedagogiqueItem> {

    @PersistenceContext(unitName = "projetsDernierePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EtatPedagogiqueItemFacade() {
        super(EtatPedagogiqueItem.class);
    }
    
}

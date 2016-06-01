/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.CaisseMoisCaisse;
import java.util.Arrays;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author samia
 */
@Stateless
public class CaisseMoisCaisseFacade extends AbstractFacade<CaisseMoisCaisse> {

    @PersistenceContext(unitName = "projetsDernierePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CaisseMoisCaisseFacade() {
        super(CaisseMoisCaisse.class);
    }
 
}

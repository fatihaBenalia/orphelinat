/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Cliquer;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author samia
 */
@Stateless
public class CliquerFacade extends AbstractFacade<Cliquer> {

    @PersistenceContext(unitName = "projetsDernierePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CliquerFacade() {
        super(Cliquer.class);
    }
      @Schedule(dayOfMonth = "13", hour = "15", minute = "01", second = "0")
    public void modifier() {

        System.out.println("haaa hwaa res");
        Cliquer cliquer = find(1);
        cliquer.setRes(2);
        cliquer.setRes3(2);
        cliquer.setRes2(2);
        cliquer.setTest(1);
        System.out.println("haaa hwaa res" + cliquer.getRes());
       edit(cliquer);
    }
         
}

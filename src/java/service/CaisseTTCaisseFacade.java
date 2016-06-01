/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.CaisseMoisCaisse;
import bean.CaisseTTCaisse;
import bean.Operationn;
import controler.util.SessionUtil;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author samia
 */
@Stateless
public class CaisseTTCaisseFacade extends AbstractFacade<CaisseTTCaisse> {

    @PersistenceContext(unitName = "projetsDernierePU")
    private EntityManager em;
    @EJB
    private service.CaisseMoisCaisseFacade caisseMoisCaisseFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CaisseTTCaisseFacade() {
        super(CaisseTTCaisse.class);
    }

    public List<CaisseTTCaisse> selectCaisse() {
        CaisseMoisCaisse caisseMoisCaisse = SessionUtil.getCaisseMoisCaisse();
        List<CaisseTTCaisse> newList = new ArrayList<>();
        if (caisseMoisCaisse != null) {
            CaisseMoisCaisse loaded = caisseMoisCaisseFacade.find(caisseMoisCaisse.getId());
            if (loaded != null) {
                String type = loaded.getType();
                Date date = loaded.getDate();
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int month = localDate.getMonthValue();
                int year = localDate.getYear();
                if (type.equals("sociale")) {
                    String requette = " select c from CaisseTTCaisse c where c.type like 'sociale' ";
                    List<CaisseTTCaisse> lista = em.createQuery(requette).getResultList();
                    for (int i = 0; i < lista.size(); i++) {
                        CaisseTTCaisse get = lista.get(i);
                        Date date1 = get.getDate();
                        LocalDate localDate1 = get.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        int month1 = localDate1.getMonthValue();
                        int year1 = localDate1.getYear();
                        if (month == month1 && year == year1) {
                            newList.add(get);
                        }
                    }
                } else if (type.equals("gestion")) {
                    String requette = " select c from CaisseTTCaisse c where c.type like 'gestion' ";
                    List<CaisseTTCaisse> lista = em.createQuery(requette).getResultList();
                    for (int i = 0; i < lista.size(); i++) {
                        CaisseTTCaisse get = lista.get(i);
                        Date date1 = get.getDate();
                        LocalDate localDate1 = get.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        int month1 = localDate1.getMonthValue();
                        int year1 = localDate1.getYear();
                        if (month == month1 && year == year1) {
                            newList.add(get);
                        }
                    }
                }
                return newList;
            }
        }
        return null;
    }

    
}

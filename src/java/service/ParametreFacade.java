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
      

    public Parametre chercher() {
        long res = findAll().size();
        System.out.println("voilaaaaaaa le resultat======>" + res);
        Parametre parametre = find(res);
        System.out.println("hahoooooowa" + parametre.toString());
        System.out.println("hahooooooooowa=============>" + parametre.getHabillement());
        return parametre;
    }

    public List<Parametre> selectParametre() {
    long res = findAll().size();
    String req = " select p from Parametre p where p.id = '" + res + "' ";
    List<Parametre> lista = em.createQuery(req).getResultList();
    return lista;
    }

    public int modParametre(Parametre parametre) {
        Date date = new Date();
        Parametre loaded = find(parametre.getId());
        if(loaded != null){
            loaded.setAlimentation(parametre.getAlimentation());
            loaded.setHabillement(parametre.getHabillement());
            loaded.setMatelasEtCouvertures(parametre.getMatelasEtCouvertures());
            loaded.setMontantComplete(parametre.getMontantComplete());
            loaded.setMontantPartielle(parametre.getMontantPartielle());
            loaded.setSacrifice(parametre.getSacrifice());
            loaded.setSante(parametre.getSante());
            loaded.setScolarité(parametre.getScolarité());
            loaded.setDateApplication(date);
            edit(loaded);
            return 1;
        }
        return -1;
    }
    
    public int createPar(Parametre parametre){
        Date date = new Date();
        parametre.setDateApplication(date);
        create(parametre);
        return 1;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Dossier;
import bean.DossierArchive;
import controler.util.SessionUtil;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author samia
 */
@Stateless
public class DossierArchiveFacade extends AbstractFacade<DossierArchive> {

    @PersistenceContext(unitName = "projetsDernierePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DossierArchiveFacade() {
        super(DossierArchive.class);
    }

    public List<DossierArchive> findArchives() {
        Dossier dossier = SessionUtil.getDossier();
        System.out.println("haa doss"+dossier);
        if(dossier != null){
        String req = " select d from DossierArchive d where d.numero like '" + dossier.getNumero() + "'  and d.nomFamille like '" + dossier.getNomFamille() + "' ";
        List<DossierArchive> lista = em.createQuery(req).getResultList();
            System.out.println("ha req "+req);
        System.out.println("haa listaa dyl les archives" + lista);
        return lista;
    }
        return null;
    }

}

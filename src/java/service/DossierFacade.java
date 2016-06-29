/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Caisse;
import bean.Dossier;
import bean.DossierArchive;
import bean.OperationMois;
import bean.Parametre;
import bean.Parrinage;
import controler.util.SessionUtil;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ASUS
 */
@Stateless
public class DossierFacade extends AbstractFacade<Dossier> {

    @PersistenceContext(unitName = "projetsDernierePU")
    private EntityManager em;
    @EJB
    private OperationMoisFacade operationMoisFacade;
    @EJB
    private CaisseFacade caisseFacade;
    @EJB
    private ParametreFacade parametreFacade;
    @EJB
    private ParrinageFacade parrinageFacade;
    @EJB
    private DossierArchiveFacade dossierArchiveFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

//    public int indiceOfParametre(){
//        List<Parametre> maList=parametreFacade.findAll();
//        int res=maList.size();
//        return res;
//    }
//    Parametre parametre=parametreFacade.find(indiceOfParametre());
    public DossierFacade() {
        super(Dossier.class);
    }

    public List<Dossier> mesDossiers() {
        String requette = " select d from Dossier d where d.etat > 0 ";
        List<Dossier> list = em.createQuery(requette).getResultList();
        for (int i = 0; i < list.size(); i++) {
            Dossier get = list.get(i);

            String req = " select p from Parrinage p where p.dossier.id =" + get.getId();
            List<Parrinage> lista = em.createQuery(req).getResultList();
            for (int j = 0; j < lista.size(); j++) {
                Parrinage get1 = lista.get(j);

                if (get1.getNbrjours1() == 0) {
                    get.setLaid(get1.getNbrjours1());
                    edit(get);
                    parrinageFacade.edit(get1);

                }
            }

        }
        return list;
    }

    public int findAllParrinage1(Dossier dossier) {

        String requette = " select p from Parrinage p where p.dossier.id =" + dossier.getId();
        List<Parrinage> lista = em.createQuery(requette).getResultList();
        for (int i = 0; i < lista.size(); i++) {
            Parrinage get1 = lista.get(i);
            if (get1.getNbrjours1() != 0) {
                return 1;
            }
        }
        return -1;

    }

    public void operationCaisse(Dossier get, Caisse newCaisse) {
        Date date = new Date();
        Parametre parametre = parametreFacade.parametreEffectuer();
        OperationMois operationMois = new OperationMois();
        operationMois.setDossier(get);
        operationMois.setMontant(parametre.getSacrifice());
        operationMois.setDate(date);
        Caisse caisse = caisseFacade.find(newCaisse.getId());
        operationMois.setCaisse(caisse);
        edit(get);
        operationMoisFacade.create(operationMois);
        caisse.setEntree(caisse.getEntree() + parametre.getSacrifice());

    }

    public List<Dossier> findDosiier(int etatt) {
        String requette = " SELECT d FROM Dossier d WHERE d.etat = " + etatt;
        List<Dossier> lista = em.createQuery(requette).getResultList();
        System.out.println("haa listaa" + lista);
        return lista;
    }

    public List<Parrinage> findAllParrinage(Dossier dossier) {
        String requette = " select p from Parrinage p where p.dossier.id = '" + dossier.getId() + "'  and p.etatArchive = 1";
        List<Parrinage> lista = em.createQuery(requette).getResultList();
        return lista;
    }

    public List<Dossier> rechercheByCritere(Dossier dossier) {
        String req = " select d from Dossier d where 1=1";

        if (dossier.getNumDossier() != null && !dossier.getNumDossier().equals("")) {
            req += " and d.numDossier like '" + dossier.getNumDossier() + "' ";
        }
        if (dossier.getEtat() != 0) {
            req += " and d.etat like '" + dossier.getEtat() + "' ";
        }

        if (dossier.getNomFamille() != null && !dossier.getNomFamille().equals("")) {
            req += " and d.nomFamille like '" + dossier.getNomFamille() + "' ";
        }
        if (dossier.getRegion() != null && !dossier.getRegion().equals("")) {
            req += " and d.region like '" + dossier.getRegion() + "' ";
        }
        List<Dossier> lista = em.createQuery(req).getResultList();
        System.out.println("haa req" + req);
        System.out.println("haa newLista " + lista);
        return lista;

    }

    public void editDosssier() {
        List<Dossier> lista = findAll();
        for (int i = 0; i < lista.size(); i++) {
            Dossier get = lista.get(i);
            edit(get);
            String req = " select p from Parrinage p where p.dossier.id ='" + get.getId() + "' ";
            List<Parrinage> listaaa = em.createQuery(req).getResultList();
            for (int j = 0; j < listaaa.size(); j++) {
                Parrinage get1 = listaaa.get(j);
                parrinageFacade.edit(get1);

            }
        }
    }

    public void createNvDossier(Dossier dossier) {
        System.out.println("haaa hnaayaaa f archive");
        Date date = new Date();
        DossierArchive dossierArchive = new DossierArchive();
        dossierArchive.setAdresseActuelle(dossier.getAdresseActuelle());
//        dossierArchive.setAssistanteSociale1(dossier.getAssistanteSociale1().getNom() + " " + dossier.getAssistanteSociale1().getPrenom());
        dossierArchive.setDate(date);
        dossierArchive.setDateDossier(dossier.getDateDossier());
        int etatt = dossier.getEtat();
        if (etatt == 1) {
            dossierArchive.setEtat("partielle");
        }
        if (etatt == 2) {
            dossierArchive.setEtat("complete Par Deux Parrains");
        }
        if (etatt == 3) {
            dossierArchive.setEtat("complete Par Un Seul Parrain");
        }
        if (etatt == 4) {
            dossierArchive.setEtat("abandonnée");
        }
        if (etatt == 5) {
            dossierArchive.setEtat("parrinée par la caisse");
        }
//        dossierArchive.setMere(dossier.getMere().getNom() + " " + dossier.getMere().getPrenom());
//        dossierArchive.setPere(dossier.getPere().getNom() + " " + dossier.getPere().getPrenom());
//        dossierArchive.setNbrMembre(dossier.getNbrMembre());
        dossierArchive.setNbrOrphelin(dossier.getNbrOrphelin());
        dossierArchive.setRegion(dossier.getRegion());
        dossierArchive.setNumero(dossier.getNumero());
        dossierArchive.setNomFamille(dossier.getNomFamille());
        dossierArchiveFacade.create(dossierArchive);
        System.out.println("haa archive t creaa");
    }

    public void createNvDossier1(Dossier dossier, Object oldValue) {
        Date date = new Date();
        DossierArchive dossierArchive = new DossierArchive();
        dossierArchive.setAdresseActuelle(dossier.getAdresseActuelle());
//        dossierArchive.setAssistanteSociale1(dossier.getAssistanteSociale1().getNom() + " " + dossier.getAssistanteSociale1().getPrenom());
        dossierArchive.setDate(dossier.getDateDossier());
        dossierArchive.setDateDossier(dossier.getDateDossier());
        int etatt = new Integer(oldValue.toString());
      if (etatt == 1) {
            dossierArchive.setEtat("partielle");
        }
        if (etatt == 2) {
            dossierArchive.setEtat("complete Par Deux Parrains");
        }
        if (etatt == 3) {
            dossierArchive.setEtat("complete Par Un Seul Parrain");
        }
        if (etatt == 4) {
            dossierArchive.setEtat("abandonnée");
        }
        if (etatt == 5) {
            dossierArchive.setEtat("parrinée par la caisse");
        }
       

//        dossierArchive.setMere(dossier.getMere().getNom() + " " + dossier.getMere().getPrenom());
//        dossierArchive.setPere(dossier.getPere().getNom() + " " + dossier.getPere().getPrenom());
//        dossierArchive.setNbrMembre(dossier.getNbrMembre());
        dossierArchive.setNbrOrphelin(dossier.getNbrOrphelin());
        dossierArchive.setRegion(dossier.getRegion());
        dossierArchive.setNumero(dossier.getNumero());
        dossierArchive.setNomFamille(dossier.getNomFamille());
        dossierArchiveFacade.create(dossierArchive);
    }

    public void editerSelected(Dossier dossier, Object oldValue) {
        System.out.println("haa dossier" + dossier.getId());
        System.out.println("haa lvalue" + oldValue);
        Dossier loaded = find(dossier.getId());
        if (loaded != null) {
            if(loaded.getEtat() == -1){
                loaded.setEtat(-1);
                edit(loaded);
            }
            if (loaded.getEtat() == 4 || loaded.getEtat() == 5) {

                System.out.println("etat dyal selected taniaa" + loaded.getEtat());
                String req = " select a from DossierArchive a where a.numero like '" + loaded.getNumero() + "' ";
                List<DossierArchive> lista = em.createQuery(req).getResultList();

              
                if (!lista.isEmpty()) {
                    DossierArchive get = lista.get(0);
                    if (get.getNumero().equals(dossier.getNumero())) {
                        System.out.println("hnaa ilaa kan 4 w 5");
                        System.out.println("haa lget1" + get.getNumero());
                        System.out.println("haa dyal doss1" + get.getNumero());
                        loaded.setEtat(dossier.getEtat());
                        loaded.setLaid(-1);
                        edit(loaded);
                        createNvDossier(loaded);
                    } else if (!get.getNumero().equals(dossier.getNumero())) {
                        System.out.println("hnaa ilaa kan 4 w 5");
                        System.out.println("haa lget2" + get.getNumero());
                        System.out.println("haa dyal doss2" + get.getNumero());
                        createNvDossier1(dossier, oldValue);

                        loaded.setEtat(dossier.getEtat());
                        loaded.setLaid(-1);
                        edit(loaded);

                        createNvDossier(loaded);
                    }

                } else if (lista.isEmpty()) {
                    System.out.println("hnaa ilaa kan 4 w 5");
                    System.out.println("hnaa lista khawiaa");
                    createNvDossier1(dossier, oldValue);

                    loaded.setEtat(dossier.getEtat());
                    loaded.setLaid(-1);
                    edit(loaded);

                    createNvDossier(loaded);
                }
            } else if (loaded.getEtat() != 4 || loaded.getEtat() != 5) {
                String req = " select a from DossierArchive a where a.numero like '" + loaded.getNumero() + "' ";
                List<DossierArchive> lista = em.createQuery(req).getResultList();
                String requette = " select p from Parrinage p where p.dossier.id = '" + loaded.getId() + "' ";
                System.out.println("haa req" + requette);
                List<Parrinage> listaa = em.createQuery(requette).getResultList();
                System.out.println("haaaa listww"+listaa);
                for (int i = 0; i < listaa.size(); i++) {
                    Parrinage get1 = listaa.get(i);
                    get1.setEtatArchive(2);
                    parrinageFacade.edit(get1);
                    if (!lista.isEmpty()) {
                        DossierArchive get = lista.get(0);
                        if (get.getNumero().equals(dossier.getNumero())) {
                            System.out.println("hnaa ilaa makanch 4 w 5");
                            System.out.println("haa lget3" + get.getNumero());
                            System.out.println("haa dyal doss3" + get.getNumero());
                            loaded.setEtat(dossier.getEtat());
                            loaded.setLaid(-1);
                            edit(loaded);
                            createNvDossier(loaded);
                        } else if (!get.getNumero().equals(dossier.getNumero())) {
                            System.out.println("hnaa ilaa makanch 4 w 5");
                            System.out.println("haa lget4" + get.getNumero());
                            System.out.println("haa dyal doss4" + get.getNumero());
                            createNvDossier1(dossier, oldValue);
                            loaded.setLaid(-1);
                            loaded.setEtat(dossier.getEtat());
                            edit(loaded);

                            createNvDossier(loaded);
                        }
                    
                    } else if (lista.isEmpty()) {
                        System.out.println("haaa hwaa dkhaal lhnaa");
                        System.out.println("hnaa ilaa makanch 4 w 5");
                        System.out.println("hadii tanii list khawiaa");
                        createNvDossier1(dossier, oldValue);
                        loaded.setLaid(-1);
                        loaded.setEtat(dossier.getEtat());
                        edit(loaded);

                        createNvDossier(loaded);
                    }

                }
            }
        }

    }

    public List<DossierArchive> voirDossierArchive(Dossier dossier) {
  
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


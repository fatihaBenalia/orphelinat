/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Caisse;
import bean.CaisseMoisCaisse;
import bean.CaisseTTCaisse;
import bean.Operationn;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author samia
 */
@Stateless
public class CaisseFacade extends AbstractFacade<Caisse> {

    @PersistenceContext(unitName = "projetsDernierePU")
    private EntityManager em;
    @EJB
    private service.CaisseMoisCaisseFacade caisseMoisFacade;
    @EJB
    private service.CaisseTTCaisseFacade caisseTTCaisseFacade;
    @EJB
    private service.CaisseMoisCaisseFacade caisseMoisCaisseFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CaisseFacade() {
        super(Caisse.class);
    }

    public int create1(Caisse caisse) {
        Caisse loaded = find(caisse.getId());
        if (loaded == null) {
            create(caisse);
            return 1;
        }
        return -1;
    }
///////////////////////////// sociale actuel ://///////////////////////////////////////////////////////////

    public double calculMontEntree() {
        double mont1 = 0;
        String req = " select c from Caisse c where c.type like 'sociale' ";
        List<Caisse> lista = em.createQuery(req).getResultList();
        for (int i = 0; i < lista.size(); i++) {
            Caisse get = lista.get(i);
            mont1 += get.getEntree();

        }
        return mont1;
    }

    public double calculMontDete() {
        double mont1 = 0;
        String req = " select c from Caisse c where c.type like 'sociale' ";
        List<Caisse> lista = em.createQuery(req).getResultList();
        for (int i = 0; i < lista.size(); i++) {
            Caisse get = lista.get(i);
            double mont = get.getDete();
            mont1 += mont;

        }
        return mont1;
    }

    public double calculMontDete1() {
        double mont1 = 0;
        String req = " select c from Caisse c where c.type like 'sociale' ";
        List<Caisse> lista = em.createQuery(req).getResultList();
        for (int i = 0; i < lista.size(); i++) {
            Caisse get = lista.get(i);  //// mlii tcrea operation tkon 3ndha l etadete 1 hta tvalida bli tkhlsate o nbdlo l etat l 0
            String requette = " select o from Operationn o where o.etatDete = 1 and o.type like 'dete' and o.caisse.id = '" + get.getId() + "'";
            List<Operationn> listaa = em.createQuery(requette).getResultList();
            for (int j = 0; j < listaa.size(); j++) {
                Operationn get1 = listaa.get(j);
                double mont2 = get1.getMontant();
                mont1 += mont2;
            }

        }
        return mont1;
    }

    public double calculMontProfit() {
        double mont1 = calculMontDete1();
        double mont2 = calculMontDepense();
        double mont3 = calculMontEntree();
        double mont4 = (mont3 - mont2) - mont1;
        return mont4;

    }

    public double calculMontDepense() {
        double mont1 = 0;
        String req = " select c from Caisse c where c.type like 'sociale' ";
        List<Caisse> lista = em.createQuery(req).getResultList();
        for (int i = 0; i < lista.size(); i++) {
            Caisse get = lista.get(i);
            double mont = get.getDepense();
            mont1 += mont;

        }
        return mont1;
    }

    //////////////////////////////////////////////////////////Gestion actuel:////////////////////////////////////////////
    public double calculMontEntree1() {
        double mont1 = 0;
        String req = " select c from Caisse c where c.type like 'gestion' ";
        List<Caisse> lista = em.createQuery(req).getResultList();
        for (int i = 0; i < lista.size(); i++) {
            Caisse get = lista.get(i);
            mont1 += get.getEntree();

        }
        return mont1;
    }

    public double calculMontDeteGestion() {
        double mont1 = 0;
        String req = " select c from Caisse c where c.type like 'gestion' ";
        List<Caisse> lista = em.createQuery(req).getResultList();
        for (int i = 0; i < lista.size(); i++) {
            Caisse get = lista.get(i);
            double mont = get.getDete();
            mont1 += mont;

        }
        return mont1;
    }

    public double calculMontDete1Gestion() {
        double mont1 = 0;
        String req = " select c from Caisse c where c.type like 'gestion' ";
        List<Caisse> lista = em.createQuery(req).getResultList();
        for (int i = 0; i < lista.size(); i++) {
            Caisse get = lista.get(i);  //// mlii tcrea operation tkon 3ndha l etadete 1 hta tvalida bli tkhlsate o nbdlo l etat l 0
            String requette = " select o from Operationn o where o.etatDete = 1 and o.type like 'dete' and o.caisse.id = '" + get.getId() + "'";
            List<Operationn> listaa = em.createQuery(requette).getResultList();
            for (int j = 0; j < listaa.size(); j++) {
                Operationn get1 = listaa.get(j);
                double mont2 = get1.getMontant();
                mont1 += mont2;
            }

        }
        return mont1;
    }

    public double calculMontProfit1() {
        double mont1 = calculMontDete1Gestion();
        double mont2 = calculMontDepense1();
        double mont3 = calculMontEntree1();
        double mont4 = (mont3 - mont2) - mont1;
        return mont4;
    }

    public double calculMontDepense1() {
        double mont1 = 0;
        String req = " select c from Caisse c where c.type like 'gestion' ";
        List<Caisse> lista = em.createQuery(req).getResultList();
        for (int i = 0; i < lista.size(); i++) {
            Caisse get = lista.get(i);
            double mont = get.getDepense();
            mont1 += mont;

        }
        return mont1;
    }
///////////////////////////////// partie sociale//////////////////////////////////////////////////////////////////
/////////////////////// calcul de la somme des caisses pour chaque mois !! (sociale) //////////////////////////////////

    public void calculMontChaquCaisse(Caisse caisse, double rev, double det, double dep) {
        Date date = new Date();
        System.out.println("hahomaa lrev" + rev);
        System.out.println("hahomaa det" + det);
        System.out.println("hahomaa dep" + dep);
        double prof;
        if (rev - dep > 0) {
            prof = rev - dep;
        } else {
            prof = rev - dep;
        }
        CaisseTTCaisse caisseTTCCaisse = new CaisseTTCaisse();
        caisseTTCCaisse.setDepense(dep);
        caisseTTCCaisse.setDete(det);
        caisseTTCCaisse.setEntree(rev);
        caisseTTCCaisse.setProfit(prof);
        caisseTTCCaisse.setDate(date);
        caisseTTCCaisse.setTypeCaisse(caisse.getId());
        caisseTTCCaisse.setType("sociale");
        caisseTTCaisseFacade.create(caisseTTCCaisse);

    }

//////////////// pour le mois fevrier !!
    @Schedule(dayOfMonth = "29", second = "0", minute = "0", hour = "23")
    public void calSommeCaisse() {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        int nbr = localDate.lengthOfMonth();
        System.out.println("haa lmonth " + month);
        System.out.println("haa lmonth " + year);
        if (month == 2 && nbr == 29) {
            String req = " select c from Caisse c where c.type like 'sociale' ";
            List<Caisse> lista = em.createQuery(req).getResultList();
            for (int i = 0; i < lista.size(); i++) {
                Caisse get = lista.get(i);
                double rev1 = 0;
                double det1 = 0;
                double dep1 = 0;
                double prof1 = 0;
                String requette = " select o from Operationn o where o.caisse.id like '" + get.getId() + "' ";
                List<Operationn> lista1 = em.createQuery(requette).getResultList();
                for (int j = 0; j < lista1.size(); j++) {
                    Operationn get1 = lista1.get(j);
                    LocalDate localDate1 = get1.getDate1().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    int month1 = localDate1.getMonthValue();
                    int year1 = localDate1.getYear();
                    System.out.println("haa lmonth1 " + month1);
                    System.out.println("haa lmonth1 " + year1);
                    if (month1 == month && year1 == year) {
                        if (get1.getType().equals("revenue")) {
                            rev1 += get1.getMontant();
                        } else if (get1.getType().equals("depense")) {
                            dep1 += get1.getMontant();
                        } else if (get1.getType().equals("dete")) {
                            det1 += get1.getMontant();
                        }
                    }
                }
                calculMontChaquCaisse(get, rev1, det1, dep1);
            }
        }
    }

    @Schedule(dayOfMonth = "28", second = "0", minute = "0", hour = "23")
    public void calSommeCaisse3() {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        int nbr = localDate.lengthOfMonth();
        System.out.println("haa lmonth " + month);
        System.out.println("haa lmonth " + year);
        if (month == 2 && nbr == 28) {
            String req = " select c from Caisse c where c.type like 'sociale' ";
            List<Caisse> lista = em.createQuery(req).getResultList();
            for (int i = 0; i < lista.size(); i++) {
                Caisse get = lista.get(i);
                double rev1 = 0;
                double det1 = 0;
                double dep1 = 0;
                double prof1 = 0;
                String requette = " select o from Operationn o where o.caisse.id like '" + get.getId() + "' ";
                List<Operationn> lista1 = em.createQuery(requette).getResultList();
                for (int j = 0; j < lista1.size(); j++) {
                    Operationn get1 = lista1.get(j);
                    LocalDate localDate1 = get1.getDate1().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    int month1 = localDate1.getMonthValue();
                    int year1 = localDate1.getYear();
                    System.out.println("haa lmonth1 " + month1);
                    System.out.println("haa lmonth1 " + year1);
                    if (month1 == month && year1 == year) {
                        if (get1.getType().equals("revenue")) {
                            rev1 += get1.getMontant();
                        } else if (get1.getType().equals("depense")) {
                            dep1 += get1.getMontant();
                        } else if (get1.getType().equals("dete")) {
                            det1 += get1.getMontant();
                        }
                    }
                }
                calculMontChaquCaisse(get, rev1, det1, dep1);
            }
        }
    }

    @Schedule(dayOfMonth = "26", second = "0", minute = "00", hour = "16")
    public void test() {
        System.out.println("haa test");
    }

//    }
    ////////////////// les mois li li 3ndhom 31 jours   
   @Schedule(dayOfMonth = "17", second = "0", minute = "41", hour = "13")
    public void calSommeCaisse1() {
        System.out.println("ha 7na hnaaaa");
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        System.out.println("haa lmonth " + month);
        System.out.println("haa lmonth " + year);
        if (month == 3 || month == 5 || month == 1 || month == 7 || month == 8 || month == 10 || month == 12) {
            String req = " select c from Caisse c where c.type like 'sociale' ";
            List<Caisse> lista = em.createQuery(req).getResultList();
            System.out.println("haa listaa dyal les caisses !! " + lista);
            for (int i = 0; i < lista.size(); i++) {
                Caisse get = lista.get(i);
                double rev1 = 0;
                double det1 = 0;
                double dep1 = 0;
                double prof1 = 0;
                String requette = " select o from Operationn o where o.caisse.id like '" + get.getId() + "' ";
                List<Operationn> lista1 = em.createQuery(requette).getResultList();
                for (int j = 0; j < lista1.size(); j++) {
                    Operationn get1 = lista1.get(j);
                    LocalDate localDate1 = get1.getDate1().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    int month1 = localDate1.getMonthValue();
                    int year1 = localDate1.getYear();
                    System.out.println("haa lmonth1 " + month1);
                    System.out.println("haa lmonth1 " + year1);
                    if (month1 == month && year1 == year) {
                        if (get1.getType().equals("revenue")) {
                            rev1 += get1.getMontant();
                        } else if (get1.getType().equals("depense")) {
                            dep1 += get1.getMontant();
                        } else if (get1.getType().equals("dete")) {
                            det1 += get1.getMontant();
                        }
                    }
                }
                calculMontChaquCaisse(get, rev1, det1, dep1);
            }
        }
    }

    ////////////////// les mois qui contient 30 jours
    @Schedule(dayOfMonth = "17", second = "0", minute = "52", hour = "13")
    public void calSommeCaisse2() {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        System.out.println("haa lmonth " + month);
        System.out.println("haa lmonth " + year);
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            String req = " select c from Caisse c where c.type like 'sociale' ";
            List<Caisse> lista = em.createQuery(req).getResultList();
            System.out.println("haa listra " + lista);
            for (int i = 0; i < lista.size(); i++) {
                Caisse get = lista.get(i);
                double rev1 = 0;
                double det1 = 0;
                double dep1 = 0;
                double prof1 = 0;
                String requette = " select o from Operationn o where o.caisse.id like '" + get.getId() + "' ";
                List<Operationn> lista1 = em.createQuery(requette).getResultList();
                System.out.println("haa listraww" + lista1);
                for (int j = 0; j < lista1.size(); j++) {
                    Operationn get1 = lista1.get(j);
                    LocalDate localDate1 = get1.getDate1().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    int month1 = localDate1.getMonthValue();
                    int year1 = localDate1.getYear();
                    System.out.println("haa lmonth1 " + month1);
                    System.out.println("haa lmonth1 " + year1);
                    if (month1 == month && year1 == year) {
                        if (get1.getType().equals("revenue")) {
                            rev1 += get1.getMontant();
                        } else if (get1.getType().equals("depense")) {
                            dep1 += get1.getMontant();
                        } else if (get1.getType().equals("dete")) {
                            det1 += get1.getMontant();
                        }
                    }
                }
                calculMontChaquCaisse(get, rev1, det1, dep1);
            }
        }
    }
///////////////////////////////////////////////////////////////////////////////

/////////////////////////// calcul la somme de tout les caisse pour chaque mois:(les Caisses Socials)/////////////////////////////////////////
    public void calculMontTTCaisse(double rev, double det, double dep, double profit) {
        Date date = new Date();
        System.out.println("haa lreeev" + rev);
        System.out.println("haa lproooooooof" + profit);
        System.out.println("haa ldepppp" + dep);
        System.out.println("haa ldeeet" + det);
        CaisseMoisCaisse caisseMoisCaisse = new CaisseMoisCaisse();
        caisseMoisCaisse.setDepense(dep);
        caisseMoisCaisse.setDete(det);
        caisseMoisCaisse.setEntree(rev);
        caisseMoisCaisse.setProfit(profit);
        caisseMoisCaisse.setDate(date);
        caisseMoisCaisse.setType("sociale");
        caisseMoisCaisseFacade.create(caisseMoisCaisse);

    }

////////////// les mois qui contient 30 jours :
     @Schedule(dayOfMonth = "17", second = "0", minute = "55", hour = "13")
    public void calSommeCaissePourTTCaisse() {
        double rev1 = 0;
        double det1 = 0;
        double dep1 = 0;
        double prof1 = 0;
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            String requette = " select c from CaisseTTCaisse c where c.type like 'sociale' ";
            List<CaisseTTCaisse> lista = em.createQuery(requette).getResultList();
            for (int i = 0; i < lista.size(); i++) {
                CaisseTTCaisse get = lista.get(i);
                LocalDate localDate1 = get.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int month1 = localDate1.getMonthValue();
                int year1 = localDate1.getYear();
                if (month1 == month && year1 == year) {
                    rev1 += get.getEntree();
                    det1 += get.getDete();
                    dep1 += get.getDepense();
                    prof1 += get.getProfit();
                }
            }
            calculMontTTCaisse(rev1, det1, dep1, prof1);
        }
    }
//////////////////////////////// les mois qui contient 31 jours :

   @Schedule(dayOfMonth = "17", second = "0", minute = "43", hour = "13")
    public void calSommeCaissePourTTCaisse1() {
        double rev1 = 0;
        double det1 = 0;
        double dep1 = 0;
        double prof1 = 0;
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        if (month == 3 || month == 5 || month == 1 || month == 7 || month == 8 || month == 10 || month == 12) {
            String requette = " select c from CaisseTTCaisse c where c.type like 'sociale' ";
            List<CaisseTTCaisse> lista = em.createQuery(requette).getResultList();
            for (int i = 0; i < lista.size(); i++) {
                CaisseTTCaisse get = lista.get(i);
                LocalDate localDate1 = get.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int month1 = localDate1.getMonthValue();
                int year1 = localDate1.getYear();
                if (month1 == month && year1 == year) {
                    rev1 += get.getEntree();
                    det1 += get.getDete();
                    dep1 += get.getDepense();
                    prof1 += get.getProfit();
                }
            }
            calculMontTTCaisse(rev1, det1, dep1, prof1);
        }
    }

/////////////////////////////// le mois fevrier qui contient 28 jours : 
    @Schedule(dayOfMonth = "28", second = "0", minute = "30", hour = "23")
    public void calSommeCaissePourTTCaisse2() {
        double rev1 = 0;
        double det1 = 0;
        double dep1 = 0;
        double prof1 = 0;
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        int nbr = localDate.lengthOfMonth();
        if (month == 2 && nbr == 28) {
            String requette = " select c from CaisseTTCaisse c where c.type like 'sociale' ";
            List<CaisseTTCaisse> lista = em.createQuery(requette).getResultList();
            for (int i = 0; i < lista.size(); i++) {
                CaisseTTCaisse get = lista.get(i);
                LocalDate localDate1 = get.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int month1 = localDate1.getMonthValue();
                int year1 = localDate1.getYear();
                if (month1 == month && year1 == year) {
                    rev1 += get.getEntree();
                    det1 += get.getDete();
                    dep1 += get.getDepense();
                    prof1 += get.getProfit();
                }
            }
            calculMontTTCaisse(rev1, det1, dep1, prof1);
        }
    }

    /////////////////////////////// fevrier 29 jours :
    @Schedule(dayOfMonth = "29", second = "0", minute = "30", hour = "23")
    public void calSommeCaissePourTTCaisse3() {
        double rev1 = 0;
        double det1 = 0;
        double dep1 = 0;
        double prof1 = 0;
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        int nbr = localDate.lengthOfMonth();
        if (month == 2 && nbr == 29) {
            String requette = " select c from CaisseTTCaisse c where c.type like 'sociale' ";
            List<CaisseTTCaisse> lista = em.createQuery(requette).getResultList();
            for (int i = 0; i < lista.size(); i++) {
                CaisseTTCaisse get = lista.get(i);
                LocalDate localDate1 = get.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int month1 = localDate1.getMonthValue();
                int year1 = localDate1.getYear();
                if (month1 == month && year1 == year) {
                    rev1 += get.getEntree();
                    det1 += get.getDete();
                    dep1 += get.getDepense();
                    prof1 += get.getProfit();
                }
            }
            calculMontTTCaisse(rev1, det1, dep1, prof1);
        }
    }
/////////////////////////////////////////////partie gestion /////////////////////////////////////////////////////

/////////////////////// calcul de la somme des caisses pour chaque mois !! (gestion) //////////////////////////////////
    public void calculMontChaquCaisseGestion(Caisse caisse, double rev, double det, double dep) {
        Date date = new Date();
        double prof;
        if (rev - dep > 0) {
            prof = rev - dep;
        } else {
            prof = rev - dep;
        }
        CaisseTTCaisse caisseTTCCaisse = new CaisseTTCaisse();
        caisseTTCCaisse.setDepense(dep);
        caisseTTCCaisse.setDete(det);
        caisseTTCCaisse.setEntree(rev);
        caisseTTCCaisse.setProfit(prof);
        caisseTTCCaisse.setDate(date);
        caisseTTCCaisse.setTypeCaisse(caisse.getId());
        caisseTTCCaisse.setType("gestion");
        caisseTTCaisseFacade.create(caisseTTCCaisse);

    }

//////////////// pour le mois fevrier !!
    @Schedule(dayOfMonth = "29", second = "0", minute = "10", hour = "23")
    public void calSommeCaisseGestion() {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        int nbr = localDate.lengthOfMonth();
        System.out.println("haa lmonth " + month);
        System.out.println("haa lmonth " + year);
        if (month == 2 && nbr == 29) {
            String req = " select c from Caisse c where c.type like 'gestion' ";
            List<Caisse> lista = em.createQuery(req).getResultList();
            for (int i = 0; i < lista.size(); i++) {
                Caisse get = lista.get(i);
                double rev1 = 0;
                double det1 = 0;
                double dep1 = 0;
                double prof1 = 0;
                String requette = " select o from Operationn o where o.caisse.id like '" + get.getId() + "' ";
                List<Operationn> lista1 = em.createQuery(requette).getResultList();
                for (int j = 0; j < lista1.size(); j++) {
                    Operationn get1 = lista1.get(j);
                    LocalDate localDate1 = get1.getDate1().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    int month1 = localDate1.getMonthValue();
                    int year1 = localDate1.getYear();
                    System.out.println("haa lmonth1 " + month1);
                    System.out.println("haa lmonth1 " + year1);
                    if (month1 == month && year1 == year) {
                        if (get1.getType().equals("revenue")) {
                            rev1 += get1.getMontant();
                        } else if (get1.getType().equals("depense")) {
                            dep1 += get1.getMontant();
                        } else if (get1.getType().equals("dete")) {
                            det1 += get1.getMontant();
                        }
                    }
                }
                calculMontChaquCaisseGestion(get, rev1, det1, dep1);
            }
        }
    }

    @Schedule(dayOfMonth = "28", second = "0", minute = "10", hour = "23")
    public void calSommeCaisseGestionnn() {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        int nbr = localDate.lengthOfMonth();
        System.out.println("haa lmonth " + month);
        System.out.println("haa lmonth " + year);
        if (month == 2 && nbr == 28) {
            String req = " select c from Caisse c where c.type like 'gestion' ";
            List<Caisse> lista = em.createQuery(req).getResultList();
            for (int i = 0; i < lista.size(); i++) {
                Caisse get = lista.get(i);
                double rev1 = 0;
                double det1 = 0;
                double dep1 = 0;
                double prof1 = 0;
                String requette = " select o from Operationn o where o.caisse.id like '" + get.getId() + "' ";
                List<Operationn> lista1 = em.createQuery(requette).getResultList();
                for (int j = 0; j < lista1.size(); j++) {
                    Operationn get1 = lista1.get(j);
                    LocalDate localDate1 = get1.getDate1().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    int month1 = localDate1.getMonthValue();
                    int year1 = localDate1.getYear();
                    System.out.println("haa lmonth1 " + month1);
                    System.out.println("haa lmonth1 " + year1);
                    if (month1 == month && year1 == year) {
                        if (get1.getType().equals("revenue")) {
                            rev1 += get1.getMontant();
                        } else if (get1.getType().equals("depense")) {
                            dep1 += get1.getMontant();
                        } else if (get1.getType().equals("dete")) {
                            det1 += get1.getMontant();
                        }
                    }
                }
                calculMontChaquCaisseGestion(get, rev1, det1, dep1);
            }
        }
    }

//    }
    ////////////////// les mois li li 3ndhom 31 jours   
   @Schedule(dayOfMonth = "17", second = "0", minute = "41", hour = "13")
    public void calSommeCaisseGestionn() {
        System.out.println("ha 7na hnaaaa");
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        System.out.println("haa lmonth " + month);
        System.out.println("haa lmonth " + year);
        if (month == 3 || month == 5 || month == 1 || month == 7 || month == 8 || month == 10 || month == 12) {
            String req = " select c from Caisse c where c.type like 'gestion' ";
            List<Caisse> lista = em.createQuery(req).getResultList();
            for (int i = 0; i < lista.size(); i++) {
                Caisse get = lista.get(i);
                double rev1 = 0;
                double det1 = 0;
                double dep1 = 0;
                double prof1 = 0;
                String requette = " select o from Operationn o where o.caisse.id like '" + get.getId() + "' ";
                List<Operationn> lista1 = em.createQuery(requette).getResultList();
                for (int j = 0; j < lista1.size(); j++) {
                    Operationn get1 = lista1.get(j);
                    LocalDate localDate1 = get1.getDate1().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    int month1 = localDate1.getMonthValue();
                    int year1 = localDate1.getYear();
                    System.out.println("haa lmonth1 " + month1);
                    System.out.println("haa lmonth1 " + year1);
                    if (month1 == month && year1 == year) {
                        if (get1.getType().equals("revenue")) {
                            rev1 += get1.getMontant();
                        } else if (get1.getType().equals("depense")) {
                            dep1 += get1.getMontant();
                        } else if (get1.getType().equals("dete")) {
                            det1 += get1.getMontant();
                        }
                    }
                }
                calculMontChaquCaisseGestion(get, rev1, det1, dep1);
            }
        }
    }

    ////////////////// les mois qui contient 30 jours
    @Schedule(dayOfMonth = "17", second = "0", minute = "52", hour = "13")
    public void calSommeCaisseGestionnnm() {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        System.out.println("haa lmonth " + month);
        System.out.println("haa lmonth " + year);
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            String req = " select c from Caisse c where c.type like 'gestion' ";
            List<Caisse> lista = em.createQuery(req).getResultList();
            for (int i = 0; i < lista.size(); i++) {
                Caisse get = lista.get(i);
                double rev1 = 0;
                double det1 = 0;
                double dep1 = 0;
                double prof1 = 0;
                String requette = " select o from Operationn o where o.caisse.id like '" + get.getId() + "' ";
                List<Operationn> lista1 = em.createQuery(requette).getResultList();
                for (int j = 0; j < lista1.size(); j++) {
                    Operationn get1 = lista1.get(j);
                    LocalDate localDate1 = get1.getDate1().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    int month1 = localDate1.getMonthValue();
                    int year1 = localDate1.getYear();
                    System.out.println("haa lmonth1 " + month1);
                    System.out.println("haa lmonth1 " + year1);
                    if (month1 == month && year1 == year) {
                        if (get1.getType().equals("revenue")) {
                            rev1 += get1.getMontant();
                        } else if (get1.getType().equals("depense")) {
                            dep1 += get1.getMontant();
                        } else if (get1.getType().equals("dete")) {
                            det1 += get1.getMontant();
                        }
                    }
                }
                calculMontChaquCaisseGestion(get, rev1, det1, dep1);
            }
        }
    }
///////////////////////////////////////////////////////////////////////////////

/////////////////////////// calcul la somme de tout les caisse pour chaque mois:(les Caisses gestions)/////////////////////////////////////////
    public void calculMontTTCaisseGestion(double rev, double det, double dep, double profit) {
        Date date = new Date();

        CaisseMoisCaisse caisseMoisCaisse = new CaisseMoisCaisse();
        caisseMoisCaisse.setDepense(dep);
        caisseMoisCaisse.setDete(det);
        caisseMoisCaisse.setEntree(rev);
        caisseMoisCaisse.setProfit(profit);
        caisseMoisCaisse.setDate(date);
        caisseMoisCaisse.setType("gestion");
        caisseMoisCaisseFacade.create(caisseMoisCaisse);

    }

////////////// les mois qui contient 30 jours :
    @Schedule(dayOfMonth = "17", second = "0", minute = "55", hour = "13")
    public void calSommeCaissePourTTCaisseGestion() {
        double rev1 = 0;
        double det1 = 0;
        double dep1 = 0;
        double prof1 = 0;
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            String requette = " select c from CaisseTTCaisse c where c.type like 'gestion' ";
            List<CaisseTTCaisse> lista = em.createQuery(requette).getResultList();
            for (int i = 0; i < lista.size(); i++) {
                CaisseTTCaisse get = lista.get(i);
                LocalDate localDate1 = get.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int month1 = localDate1.getMonthValue();
                int year1 = localDate1.getYear();
                if (month1 == month && year1 == year) {
                    rev1 += get.getEntree();
                    det1 += get.getDete();
                    dep1 += get.getDepense();
                    prof1 += get.getProfit();
                }
            }
            calculMontTTCaisseGestion(rev1, det1, dep1, prof1);
        }
    }
//////////////////////////////// les mois qui contient 31 jours :

   @Schedule(dayOfMonth = "17", second = "0", minute = "43", hour = "13")
    public void calSommeCaissePourTTCaisseGestionn() {
        double rev1 = 0;
        double det1 = 0;
        double dep1 = 0;
        double prof1 = 0;
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        if (month == 3 || month == 5 || month == 1 || month == 7 || month == 8 || month == 10 || month == 12) {
            String requette = " select c from CaisseTTCaisse c where c.type like 'gestion' ";
            List<CaisseTTCaisse> lista = em.createQuery(requette).getResultList();
            for (int i = 0; i < lista.size(); i++) {
                CaisseTTCaisse get = lista.get(i);
                LocalDate localDate1 = get.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int month1 = localDate1.getMonthValue();
                int year1 = localDate1.getYear();
                if (month1 == month && year1 == year) {
                    rev1 += get.getEntree();
                    det1 += get.getDete();
                    dep1 += get.getDepense();
                    prof1 += get.getProfit();
                }
            }
            calculMontTTCaisseGestion(rev1, det1, dep1, prof1);
        }
    }

/////////////////////////////// le mois fevrier qui contient 28 jours : 
    @Schedule(dayOfMonth = "28", second = "0", minute = "37", hour = "23")
    public void calSommeCaissePourTTCaisseGestionnn() {
        double rev1 = 0;
        double det1 = 0;
        double dep1 = 0;
        double prof1 = 0;
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        int nbr = localDate.lengthOfMonth();
        if (month == 2 && nbr == 28) {
            String requette = " select c from CaisseTTCaisse c where c.type like 'gestion' ";
            List<CaisseTTCaisse> lista = em.createQuery(requette).getResultList();
            for (int i = 0; i < lista.size(); i++) {
                CaisseTTCaisse get = lista.get(i);
                LocalDate localDate1 = get.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int month1 = localDate1.getMonthValue();
                int year1 = localDate1.getYear();
                if (month1 == month && year1 == year) {
                    rev1 += get.getEntree();
                    det1 += get.getDete();
                    dep1 += get.getDepense();
                    prof1 += get.getProfit();
                }
            }
            calculMontTTCaisseGestion(rev1, det1, dep1, prof1);
        }
    }

    /////////////////////////////// fevrier 29 jours :
    @Schedule(dayOfMonth = "29", second = "0", minute = "37", hour = "23")
    public void calSommeCaissePourTTCaisseGestionnnm() {
        double rev1 = 0;
        double det1 = 0;
        double dep1 = 0;
        double prof1 = 0;
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        int nbr = localDate.lengthOfMonth();
        if (month == 2 && nbr == 29) {
            String requette = " select c from CaisseTTCaisse c where c.type like 'gestion' ";
            List<CaisseTTCaisse> lista = em.createQuery(requette).getResultList();
            for (int i = 0; i < lista.size(); i++) {
                CaisseTTCaisse get = lista.get(i);
                LocalDate localDate1 = get.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int month1 = localDate1.getMonthValue();
                int year1 = localDate1.getYear();
                if (month1 == month && year1 == year) {
                    rev1 += get.getEntree();
                    det1 += get.getDete();
                    dep1 += get.getDepense();
                    prof1 += get.getProfit();
                }
            }
            calculMontTTCaisseGestion(rev1, det1, dep1, prof1);
        }
    }

    public double getCaisse(Caisse caisse) {
        if (caisse != null) {
            String req = " select c from Caisse c where c.id like '" + caisse.getId() + "' ";
            List<Caisse> list = em.createQuery(req).getResultList();
            if (!list.isEmpty()) {
                Caisse loaded = list.get(0);
                double res = loaded.getDepense();
                return res;
            }

        }
        return 0;
    }

    public double getCaisse1(Caisse caisse) {
        if (caisse != null) {
            String req = " select c from Caisse c where c.id like '" + caisse.getId() + "' ";
            List<Caisse> list = em.createQuery(req).getResultList();
            if (!list.isEmpty()) {
                Caisse loaded = list.get(0);
                double res = loaded.getDete();
                return res;
            }

        }
        return 0;
    }

    public double getCaisse2(Caisse caisse) {
        if (caisse != null) {
            String req = " select c from Caisse c where c.id like '" + caisse.getId() + "' ";
            List<Caisse> list = em.createQuery(req).getResultList();
            if (!list.isEmpty()) {
                Caisse loaded = list.get(0);
                double res = loaded.getProfit();
                return res;
            }

        }
        return 0;
    }

    public double getCaisse3(Caisse caisse) {
        if (caisse != null) {
            String req = " select c from Caisse c where c.id like '" + caisse.getId() + "' ";
            List<Caisse> list = em.createQuery(req).getResultList();
            if (!list.isEmpty()) {
                Caisse loaded = list.get(0);
                double res = loaded.getEntree();
                return res;
            }

        }
        return 0;
    }

    public List<Caisse> findCaisse(String typeCaisse) {
        String req = "select c from Caisse c where c.type like  '" + typeCaisse + "' ";
        List<Caisse> lista = em.createQuery(req).getResultList();
        System.out.println("haa lista" + lista);
        return lista;
    }

    @Schedule(dayOfMonth = "17", second = "0", minute = "47", hour = "13")
    public void hanii() {
        System.out.println("saii khdmaat 3la slama");
    }

}

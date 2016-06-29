/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.User;
import controler.util.SessionUtil;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author samia
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "projetsDernierePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }

//    public int seConnecter(User user) {
//        System.out.println("haani ylh dkhlt");
//        User lodedUser = find(user.getId());
//        System.out.println("haa syad" + lodedUser.getId());
//        if (lodedUser.getId().equals(user.getId())) {
//            if (lodedUser.getPassword().equals(user.getPassword())) {
//                if (lodedUser.getType().equals("admin")) {
//                    return 1;
//                } else if (lodedUser.getType().equals("comptable")) {
//                    System.out.println("haani wast lmethode");
//                    return 2;
//                }
//
//            }
//            return -2;
//        }
//        return -3;
//    }
    public int affiche(String id, String password) {
           System.out.println("haanii dkhalt lservice");
        User loaded = find(id);
        System.out.println("hahwaaa l9aaa syaad ");
        if (loaded != null) {
            if (loaded.getPassword().equals(password)) {
                 SessionUtil.registerUser(loaded);
                if (loaded.getType().equals("comptable")) {
                    System.out.println("hahwaaa loaded" + loaded.getType());
                   
                    System.out.println("hahwaa loadedd "+ loaded.getType());
                    return 2;
                } else if (loaded.getType().equals("admin")) {
                    return 1;
                }
            }
        }
        System.out.println("haa login mahwach");
        return -3;
    }

    public int seConnnecter(String id, String password) {
        System.out.println("haanii dkhalt lservice");
        User loaded = find(id);
        System.out.println("hahwaaa l9aaa syaad ");
        if (loaded != null) {
            if (loaded.getPassword().equals(password)) {
                if (loaded.getType().equals("comptable")) {
                    System.out.println("hahwaaa loaded" + loaded.getType());
                    return 2;
                } else if (loaded.getType().equals("admin")) {
                    return 1;
                }
            }
        }
        System.out.println("haa login mahwach");
        return -3;
    }

}

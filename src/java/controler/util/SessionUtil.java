package controler.util;


import bean.CaisseMoisCaisse;
import bean.CaisseTTCaisse;
import bean.Dossier;
import bean.Parrain;
import bean.Parrinage;
import bean.User;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class SessionUtil {
  
    private static final SessionUtil instance = new SessionUtil();
    
   
    private SessionUtil() {
        super();
    }

      public static void registerParrain(Parrain parrain){
          setAttribute("pp", parrain);
    }
     public static Parrain getParrin(){
       return  (Parrain) getAttribute("pp");
    }
      public static void registerUser(User user){
          setAttribute("pp1", user);
    }
     public static User getUser(){
       return  (User) getAttribute("pp1");
    }
     
        public static void registerDossier(Dossier dossier){
          setAttribute("dd", dossier);
    }
     public static Dossier getDossier(){
       return  (Dossier) getAttribute("dd");
    }
     
     
     
        public static void registerCaisseMoisCaisse(CaisseMoisCaisse caisseMoisCaisse){
          setAttribute("cc", caisseMoisCaisse);
    }
     public static CaisseMoisCaisse getCaisseMoisCaisse(){
       return  (CaisseMoisCaisse) getAttribute("cc");
    }
     
        public static void registerCaisseTTCaisse(CaisseTTCaisse caisseTTCaisse){
          setAttribute("cc1", caisseTTCaisse);
    }
     public static CaisseTTCaisse getCaisseTTCaisse(){
       return  (CaisseTTCaisse) getAttribute("cc1");
    }
     
     
     
     
        public static void registerParrinage(Parrinage parrinage){
          setAttribute("aq", parrinage);
    }
     public static Parrinage getParrinage(){
       return  (Parrinage) getAttribute("aq");
    }
     
     
     
    
    public static SessionUtil getInstance() {
        return instance;
    }
    private static boolean isContextOk(FacesContext fc) {
        boolean res = (fc != null
                && fc.getExternalContext() != null
                && fc.getExternalContext().getSession(false) != null);
        return res;
    }

    private static HttpSession getSession(FacesContext fc) {
        return (HttpSession) fc.getExternalContext().getSession(false);
    }

    public static Object getAttribute(String cle) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Object res = null;
        if (isContextOk(fc)) {
            res = getSession(fc).getAttribute(cle);
        }
        return res;
    }

   
    public static void setAttribute(String cle, Object valeur) {
        FacesContext fc = FacesContext.getCurrentInstance();
        if (fc != null && fc.getExternalContext() != null) {
            getSession(fc).setAttribute(cle, valeur);
        }
    }
    
    
    public static void removeAttribute(String cle) {
        FacesContext fc = FacesContext.getCurrentInstance();
        if (fc != null && fc.getExternalContext() != null) {
            if (getAttribute(cle) != null) {
                getSession(fc).removeAttribute(cle);
            }
        }
    }
    
    
}

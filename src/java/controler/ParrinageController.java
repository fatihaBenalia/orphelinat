package controler;

import bean.Dossier;
import bean.Parrain;
import bean.Parrinage;
import controler.util.JsfUtil;
import controler.util.JsfUtil.PersistAction;
import controler.util.Mail;
import controler.util.SessionUtil;
import java.io.IOException;
import service.ParrinageFacade;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import net.sf.jasperreports.engine.JRException;
import pdfUtil.PdfUtil;

@Named("parrinageController")
@SessionScoped
public class ParrinageController implements Serializable {

    @EJB
    private service.ParrinageFacade ejbFacade;
 
    private List<Parrinage> items = null;
    private Parrinage selected;
    private Parrinage parrinage1;
    private String message;
    private String objet;
    private String email;
    private int nbrMonth;
    private double moisNonPaye;
    private String nomPrenom;
    private Date date;

    public Date getDate() {
        return date = new Date();
    }

    public void setDate(Date date) {
        this.date = date;
    }
    

    
    public String getNomPrenom() {
        return nomPrenom;
    }

    public void setNomPrenom(String nomPrenom) {
        this.nomPrenom = nomPrenom;
    }
    
    
    public double getMoisNonPaye() {
        return moisNonPaye;
    }

    public void setMoisNonPaye(double moisNonPaye) {
        this.moisNonPaye = moisNonPaye;
    }
    
    
    public int getNbrMonth() {
        return nbrMonth;
    }

    public void setNbrMonth(int nbrMonth) {
        this.nbrMonth = nbrMonth;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Parrinage getParrinage1() {
        if (parrinage1 == null) {
            parrinage1 = new Parrinage();
        }
        return parrinage1;
    }

    public void setParrinage1(Parrinage parrinage1) {
        this.parrinage1 = parrinage1;
    }

    public ParrinageController() {
    }

    public Parrinage getSelected() {
        if (selected == null) {
            SessionUtil.registerParrinage(selected);
            selected = new Parrinage();
        }
        return selected;
    }

    public void enregisterSelected() {
        System.out.println("haaa lparrinage" + selected.getId());
        SessionUtil.registerParrinage(selected);

    }

    public void affiche1() {
        System.out.println("hanii dkhalt lcontroller");
    }

    public Parrinage getParrinage() {
        return SessionUtil.getParrinage();
    }

    public String goPage() {
        enregisterSelected();
        return "/parrinage/EnvoyerEmail1.xhtml";
    }
   

    public String goPage1() {
        enregisterSelected();
        return "/parrinage/EnvoyerEmail2.xhtml";
    }

    public void setSelected(Parrinage selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ParrinageFacade getFacade() {
        return ejbFacade;
    }

    public Parrinage prepareCreate() {
        selected = new Parrinage();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ParrinageCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ParrinageUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ParrinageDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Parrinage> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Parrinage getParrinage(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Parrinage> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Parrinage> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Parrinage.class)
    public static class ParrinageControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ParrinageController controller = (ParrinageController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "parrinageController");
            return controller.getParrinage(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Parrinage) {
                Parrinage o = (Parrinage) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Parrinage.class.getName()});
                return null;
            }
        }

    }

    public Parrain getParrain1() {
        Parrain par = SessionUtil.getParrin();
        return par;
    }

    public Parrain getParrain() {
        Parrain parrain = SessionUtil.getParrin();
        System.out.println("haa lparrain" + parrain.getNom());
        return parrain;
    }

   
//    
//    public void ajouterParrinagee(){
//       int res= ejbFacade.ajouterNouveauParrinage(selected);
//        System.out.println("haaa "+selected);
//       if(res>0){
//           JsfUtil.addSuccessMessage(" La Parrinage Est Ajouté Avec Sucess");
//       }else{
//           JsfUtil.addErrorMessage("error!");
//       }
//       selected=null;
//    }

    public Dossier getDossierrr() {
        Dossier dos = SessionUtil.getDossier();
        return dos;
    }

    public void affiche() {
        System.out.println("haniiiiiiiiiiiiiiii");
    }

    public void ajouterParrinageeee() throws JRException, IOException {
        System.out.println("ha 7naahnaa");
        int res = ejbFacade.ajouterNouveauParrinage(selected);

        if (res > 0) {
//            ejbFacade.generatePdf(selected);
//            FacesContext.getCurrentInstance().getResponseComplete();
            JsfUtil.addSuccessMessage(" La Parrinage Est Ajouté Avec Sucess");
        } else {
            JsfUtil.addErrorMessage("error!");
        }
    }

     public String getParrain2() {
        if (parrinage1.getParrain().getNom() != null && !parrinage1.getParrain().getNom().equals("")) {
        String prenom = ejbFacade.getNomParrain(parrinage1.getParrain().getNom());
        String parrain1 = parrinage1.getParrain().getNom()+" "+ prenom;
         return parrain1;  
        } else {
        return null;
            
        }

    }
    
    public void rechercheByCritere() {
        System.out.println("haniiii haniiii haniiii");
        items = ejbFacade.rechercheByCritere(parrinage1);
        nomPrenom = ejbFacade.getNomParrain(parrinage1.getParrain().getNom());
        if (!items.isEmpty()) {
            nbrMonth = ejbFacade.nbrMonth(items);
            moisNonPaye = ejbFacade.moisNonPaye(nbrMonth);
            System.out.println("hanii f controller" + nbrMonth);
            System.out.println("haaa howa tamaan li makhlasch"+moisNonPaye);
            date = getDate();
        }

    }

    public void envoyerMessage() {
        System.out.println("haaa l objet"+objet);
        Mail.sendMail("fatihaabenaliaa@gmail.com", "fatiha123456", email, objet, message);
        JsfUtil.addSuccessMessage("Votre Message Est Envoyer Avec Succes");
    }
     public void generatePdf() throws JRException, IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("p5", date);
        params.put("p1", nomPrenom);
        params.put("p2", getDossierrr().getNomFamille());
        params.put("p3",nbrMonth);
        params.put("p4",moisNonPaye );
        PdfUtil.generatePdf(items, params, "Bilan Copmte", "/report/TestBilan.jasper");
    }
     public void imprimer() throws JRException, IOException{
         generatePdf();
          FacesContext.getCurrentInstance().getResponseComplete();
     }
     

}

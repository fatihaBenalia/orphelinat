package controler;

import bean.Dossier;
import bean.DossierArchive;
import controler.util.JsfUtil;
import controler.util.JsfUtil.PersistAction;
import controler.util.SessionUtil;
import service.DossierFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.event.CellEditEvent;

@Named("dossierController")
@SessionScoped
public class DossierController implements Serializable {

    @EJB
    private service.DossierFacade ejbFacade;
    private List<Dossier> items = null;
    private List<DossierArchive> archivees = null;
    private Dossier selected;
    private int etatt;
    private int nbr;
    private String msg;
    private String destination;
    private Dossier nesDossier;

    public List<DossierArchive> getArchivees() {
        if(archivees == null){
            archivees = new ArrayList();
        }
        return archivees;
    }

    public void setArchivees(List<DossierArchive> archivees) {
        this.archivees = archivees;
    }

   

  
    
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Dossier getNesDossier() {
        if(nesDossier == null){
            nesDossier = new Dossier();
        }
        return nesDossier;
    }

    public void setNesDossier(Dossier nesDossier) {
        this.nesDossier = nesDossier;
    }
   
    

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    
    
    public int getNbr() {
        return nbr = findAllParrinage1(selected);
    }

    public void setNbr(int nbr) {
        this.nbr = nbr;
    }

    public int getEtatt() {
        return etatt;
    }

    public void setEtatt(int etatt) {
        this.etatt = etatt;
    }

    public DossierController() {
    }

    public Dossier getSelected() {
        if (selected == null) {
            selected = new Dossier();

        }
        return selected;
    }

    public void setSelected(Dossier selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private DossierFacade getFacade() {
        return ejbFacade;
    }

    public Dossier prepareCreate() {
        selected = new Dossier();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("DossierCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("DossierUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("DossierDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Dossier> getItems() {
        
        if (items == null) {
            items = ejbFacade.mesDossiers();
        }
        return items;
    }
    public void getItems1() {
        
  
            items = ejbFacade.mesDossiers();
     
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

    public Dossier getDossier(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Dossier> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Dossier> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Dossier.class)
    public static class DossierControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DossierController controller = (DossierController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "dossierController");
            return controller.getDossier(getKey(value));
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
            if (object instanceof Dossier) {
                Dossier o = (Dossier) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Dossier.class.getName()});
                return null;
            }
        }

    }

    /////////////////////
    
    
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
            System.out.println("haa lwla"+oldValue);
            System.out.println("haa tania"+newValue);
            System.out.println("etat dyal selected lwlaaa"+selected.getEtat());
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "L'etat Est Changer Avec Sucess", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            ejbFacade.editerSelected(selected,oldValue);
            
        }
    } 
    public void findDosiier() {
        items = ejbFacade.findDosiier(etatt);
    }

    public void findAllll() {

        items = ejbFacade.mesDossiers();

    }

   
    public void selectParrinage(Dossier dossier) {
        selected.setParrinages(ejbFacade.findAllParrinage(dossier));
    }

    public int findAllParrinage1(Dossier dossier) {
        int res = ejbFacade.findAllParrinage1(dossier);
        return res;
    }

    public String registerDossier() {
        SessionUtil.registerDossier(selected);

        return "/parrinage/AjouterParrinage.xhtml";
    }

    public Dossier getDossier() {
        Dossier dos = SessionUtil.getDossier();
        return dos;
    }

    public void rechercheByCritere(){
        System.out.println("haa dossier"+nesDossier);
        items = ejbFacade.rechercheByCritere(nesDossier);
    }
   public void editerDossier(){
     ejbFacade.editDosssier();
   } 
   
   public String registerDossierPourParrain(){
       System.out.println("haaa dossier dyaalnaa"+selected.getId());
       SessionUtil.registerDossier(selected);
       return "/parrinage/ParrinageDunParrain.xhtml";
   }
   public void afficher(){
       System.out.println("haaniniiiiii");
   }
   
   public void voirArchiveDossier(){
       System.out.println("hahwaa dkhaal lhnaas");

        archivees = ejbFacade.voirDossierArchive(selected);
   }
   
}

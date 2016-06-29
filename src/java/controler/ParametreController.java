package controler;

import bean.Parametre;
import controler.util.JsfUtil;
import controler.util.JsfUtil.PersistAction;
import service.ParametreFacade;

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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("parametreController")
@SessionScoped
public class ParametreController implements Serializable {

    @EJB
    private service.ParametreFacade ejbFacade;
    private List<Parametre> items = null;
    private List<Parametre> parametres = null;
    private Parametre selected;

    public List<Parametre> getParametres() {
        if(parametres == null){
            parametres = ejbFacade.selectParametre();
        }
        return parametres;
    }
    public void actualiser() {
       
            parametres = ejbFacade.selectParametre();
            selected = null;
       
    }
    
       
 public void modifierAnn(){

    int res=ejbFacade.modParametre(selected);
    selected= null;
    if(res >0 ){
             JsfUtil.addSuccessMessage(" Le Parametre Est Modifié Avec Sucess");
    }else{
        JsfUtil.addErrorMessage("error!");
     
 }
 }
public void createParam(){
  int res=ejbFacade.createPar(selected);
    selected= null;
    if(res >0 ){
             JsfUtil.addSuccessMessage(" Le Parametre Est crée Avec Sucess");
    }else{
        JsfUtil.addErrorMessage("error!");
     
 }  
}
    public void setParametres(List<Parametre> parametres) {
        this.parametres = parametres;
    }

    
    
    
    public ParametreController() {
    }
    
    

    public Parametre getSelected() {
        if(selected == null){
            selected = new Parametre();
        }
        return selected;
    }
 public void updateParametre(Parametre parametre) {
        this.selected = parametre;
    

    }
    
    public void setSelected(Parametre selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ParametreFacade getFacade() {
        return ejbFacade;
    }

    public Parametre prepareCreate() {
        selected = new Parametre();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ParametreCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ParametreUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ParametreDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Parametre> getItems() {
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

    public Parametre getParametre(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Parametre> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Parametre> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Parametre.class)
    public static class ParametreControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ParametreController controller = (ParametreController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "parametreController");
            return controller.getParametre(getKey(value));
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
            if (object instanceof Parametre) {
                Parametre o = (Parametre) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Parametre.class.getName()});
                return null;
            }
        }

    }

}

package controler;

import bean.DossierArchive;
import controler.util.JsfUtil;
import controler.util.JsfUtil.PersistAction;
import service.DossierArchiveFacade;

import java.io.Serializable;
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

@Named("dossierArchiveController")
@SessionScoped
public class DossierArchiveController implements Serializable {

    @EJB
    private service.DossierArchiveFacade ejbFacade;
    @EJB
    private service.DossierFacade dossierFacade;
    private List<DossierArchive> items = null;
    private DossierArchive selected;
    
   

    
    
    public DossierArchiveController() {
    }

    public DossierArchive getSelected() {
        if(selected == null){
            selected = new DossierArchive();
        }
        return selected;
    }

    public void setSelected(DossierArchive selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private DossierArchiveFacade getFacade() {
        return ejbFacade;
    }

    public DossierArchive prepareCreate() {
        selected = new DossierArchive();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("DossierArchiveCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("DossierArchiveUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("DossierArchiveDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<DossierArchive> getItems() {
        if (items == null) {
           
            items = getFacade().findAll() ;
             System.out.println("haa listaa"+items);
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

    public DossierArchive getDossierArchive(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<DossierArchive> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<DossierArchive> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = DossierArchive.class)
    public static class DossierArchiveControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DossierArchiveController controller = (DossierArchiveController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "dossierArchiveController");
            return controller.getDossierArchive(getKey(value));
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
            if (object instanceof DossierArchive) {
                DossierArchive o = (DossierArchive) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), DossierArchive.class.getName()});
                return null;
            }
        }

    }

    
    
}

package controler;

import bean.CaisseMoisCaisse;
import controler.util.JsfUtil;
import controler.util.JsfUtil.PersistAction;
import controler.util.SessionUtil;
import service.CaisseMoisCaisseFacade;

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

@Named("caisseMoisCaisseController")
@SessionScoped
public class CaisseMoisCaisseController implements Serializable {

    @EJB
    private service.CaisseMoisCaisseFacade ejbFacade;
    @EJB
    private service.CaisseTTCaisseFacade caisseTTCaisseFacade;
    private List<CaisseMoisCaisse> items = null;
    private List<CaisseMoisCaisse> filterCaisses = null;
    private CaisseMoisCaisse selected;

    public CaisseMoisCaisseController() {
    }

    public List<CaisseMoisCaisse> getFilterCaisses() {
        return filterCaisses;
    }

    public void setFilterCaisses(List<CaisseMoisCaisse> filterCaisses) {
        this.filterCaisses = filterCaisses;
    }

    
    public CaisseMoisCaisse getSelected() {
        if (selected == null) {
            selected = new CaisseMoisCaisse();
        }
        return selected;
    }

    public void setSelected(CaisseMoisCaisse selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CaisseMoisCaisseFacade getFacade() {
        return ejbFacade;
    }

    public CaisseMoisCaisse prepareCreate() {
        selected = new CaisseMoisCaisse();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CaisseMoisCaisseCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CaisseMoisCaisseUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CaisseMoisCaisseDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<CaisseMoisCaisse> getItems() {
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

    public CaisseMoisCaisse getCaisseMoisCaisse(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<CaisseMoisCaisse> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<CaisseMoisCaisse> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = CaisseMoisCaisse.class)
    public static class CaisseMoisCaisseControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CaisseMoisCaisseController controller = (CaisseMoisCaisseController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "caisseMoisCaisseController");
            return controller.getCaisseMoisCaisse(getKey(value));
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
            if (object instanceof CaisseMoisCaisse) {
                CaisseMoisCaisse o = (CaisseMoisCaisse) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), CaisseMoisCaisse.class.getName()});
                return null;
            }
        }

    }
//////////////////////

    public void selectCaisse() {
        SessionUtil.registerCaisseMoisCaisse(selected);
        caisseTTCaisseFacade.selectCaisse();
    }
    
  
    
}


package controler;

import bean.CaisseTTCaisse;
import controler.util.JsfUtil;
import controler.util.JsfUtil.PersistAction;
import controler.util.SessionUtil;
import service.CaisseTTCaisseFacade;

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

@Named("caisseTTCaisseController")
@SessionScoped
public class CaisseTTCaisseController implements Serializable {

    @EJB
    private service.CaisseTTCaisseFacade ejbFacade;
    @EJB
    private service.OperationnFacade operationnFacade;
    private List<CaisseTTCaisse> items = null;
    private CaisseTTCaisse selected;

    public CaisseTTCaisseController() {
    }

    public CaisseTTCaisse getSelected() {
        if (selected == null) {
            selected = new CaisseTTCaisse();
        }
        return selected;
    }

    public void setSelected(CaisseTTCaisse selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CaisseTTCaisseFacade getFacade() {
        return ejbFacade;
    }

    public CaisseTTCaisse prepareCreate() {
        selected = new CaisseTTCaisse();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CaisseTTCaisseCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CaisseTTCaisseUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CaisseTTCaisseDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<CaisseTTCaisse> getItems() {
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

    public CaisseTTCaisse getCaisseTTCaisse(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<CaisseTTCaisse> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<CaisseTTCaisse> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = CaisseTTCaisse.class)
    public static class CaisseTTCaisseControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CaisseTTCaisseController controller = (CaisseTTCaisseController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "caisseTTCaisseController");
            return controller.getCaisseTTCaisse(getKey(value));
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
            if (object instanceof CaisseTTCaisse) {
                CaisseTTCaisse o = (CaisseTTCaisse) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), CaisseTTCaisse.class.getName()});
                return null;
            }
        }

    }
////////////////////////////

    public List<CaisseTTCaisse> getCaisse() {
        if (items == null) {
            return items = new ArrayList<>();
        } else {
            List<CaisseTTCaisse> lista = ejbFacade.selectCaisse();
            if (lista != null) {
                return items = lista;
            }
        }
        return null;
    }

    public void selectOperationCaisse() {
        System.out.println("haaanii haniii hna");
        SessionUtil.registerCaisseTTCaisse(selected);
        operationnFacade.selectOperCaisse();
    }

}

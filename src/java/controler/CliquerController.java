package controler;

import bean.Cliquer;
import controler.util.JsfUtil;
import controler.util.JsfUtil.PersistAction;
import service.CliquerFacade;

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

@Named("cliquerController")
@SessionScoped
public class CliquerController implements Serializable {

    @EJB
    private service.CliquerFacade ejbFacade;
    private List<Cliquer> items = null;
    private Cliquer selected;
 public int clicker1() {
        Cliquer cliquer = ejbFacade.find(1);
        if (cliquer.getRes()== 1) {
            return 1;
        } else {
            return -1;
        }
    }
       public int clicker2() {
        Cliquer cliquer = ejbFacade.find(1);
        if (cliquer.getRes2()== 1) {
            return 1;
        } else {
            return -1;
        }
    }
       public int tester() {
        Cliquer cliquer = ejbFacade.find(1);
        if (cliquer.getTest()== 1) {
            return 1;
        } else {
            return -1;
        }
    }
          public int clicker3() {
        Cliquer cliquer = ejbFacade.find(1);
        if (cliquer.getRes3()== 1) {
            return 1;
        } else {
            return -1;
        }
    }

   

    public void actionButton() {
        System.out.println("voila l'ation");
        Cliquer cliquer = ejbFacade.find(1);
        System.out.println("hahwaa "+cliquer);
        cliquer.setRes(1);
        cliquer.setRes2(1);
        cliquer.setRes3(1);
        ejbFacade.edit(cliquer);
        System.out.println("ha howa "+cliquer);
    }

    public CliquerController() {
    }

    public Cliquer getSelected() {
        if(selected==null){
            selected=new Cliquer();
        }
        return selected;
    }

    public void setSelected(Cliquer selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CliquerFacade getFacade() {
        return ejbFacade;
    }

    public Cliquer prepareCreate() {
        selected = new Cliquer();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CliquerCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CliquerUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CliquerDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Cliquer> getItems() {
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

    public Cliquer getCliquer(int id) {
        return getFacade().find(id);
    }

    public List<Cliquer> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Cliquer> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Cliquer.class)
    public static class CliquerControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CliquerController controller = (CliquerController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "cliquerController");
            return controller.getCliquer(getKey(value));
        }

        int getKey(String value) {
            int key;
            key = Integer.parseInt(value);
            return key;
        }

        String getStringKey(int value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Cliquer) {
                Cliquer o = (Cliquer) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Cliquer.class.getName()});
                return null;
            }
        }

    }

}

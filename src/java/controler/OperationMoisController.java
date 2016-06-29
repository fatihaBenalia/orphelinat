package controler;

import bean.Cliquer;
import bean.OperationMois;
import bean.Parametre;
import controler.util.JsfUtil;
import controler.util.JsfUtil.PersistAction;
import java.io.IOException;
import service.OperationMoisFacade;

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
import net.sf.jasperreports.engine.JRException;
import service.CliquerFacade;
import service.ParametreFacade;

@Named("operationMoisController")
@SessionScoped
public class OperationMoisController implements Serializable {

    @EJB
    private service.OperationMoisFacade ejbFacade;
    private List<OperationMois> items = null;
    private OperationMois selected;
 @EJB
    private CliquerFacade cliquerFacade;
 @EJB
 private ParametreFacade parametreFacade;

    public void afficher() {
        long res = parametreFacade.findAll().size();
        System.out.println("voilaaaaaaa le resultat======>" + res);
        Parametre parametre = parametreFacade.find(res);
        System.out.println("hahoooooowa" + parametre.toString());
    }

    
    public void affichww(){
        System.out.println("haaa dkhaaaaal");
    }
    
    public void action1() throws JRException, IOException {
        int res = ejbFacade.addOperationDeMoi();
        Cliquer cliquer = cliquerFacade.find(1);
        if (res > 0) {
            System.out.println("hahwaa rjj3o 1");
            cliquer.setRes(1);
            cliquer.setTest(2);
            cliquerFacade.edit(cliquer);
            System.out.println("hahwaa rjj3o 1");
//            cliquerFacade.edit(cliquer);
//          ejbFacade.generatePdf();
//        FacesContext.getCurrentInstance().getResponseComplete();
            JsfUtil.addSuccessMessage("L'alimentation pour les dossier parrainée completement est Bien validé");
        } else {
            JsfUtil.addErrorMessage("Error !! ");
        }
    }

    public void action2() throws JRException, IOException {
        System.out.println("hwhaaa dkhal");
        int res = ejbFacade.DossierDeEtat2();
        Cliquer cliquer = cliquerFacade.find(1);
        if (res > 0) {
            cliquer.setRes2(1);
            cliquer.setTest(2);
            cliquerFacade.edit(cliquer);
            JsfUtil.addSuccessMessage("L'alimentation pour les dossier parrainée partielement est Bien validé");
        } else {
            JsfUtil.addErrorMessage("Error !!");
        }
    }

    public void action3() throws JRException, IOException {
        int res = ejbFacade.dossier3();
        Cliquer cliquer = cliquerFacade.find(1);
        if (res > 0) {
            cliquer.setRes3(1);
            cliquer.setTest(2);
            cliquerFacade.edit(cliquer);
            JsfUtil.addSuccessMessage("L'alimentation pour les dossier abandonnée est Bien validé");

        } else {
            JsfUtil.addErrorMessage("Error !!");
        }
    }

    public int clicker() {
        Cliquer cliquer = cliquerFacade.find(1);
        if (cliquer.getRes() == 1) {
            return 1;
        } else {
            return -1;
        }
    }
    public OperationMoisController() {
    }

    public OperationMois getSelected() {
        if(selected==null){
            selected=new OperationMois();
        }
        return selected;
    }

    public void setSelected(OperationMois selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private OperationMoisFacade getFacade() {
        return ejbFacade;
    }

    public OperationMois prepareCreate() {
        selected = new OperationMois();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("OperationMoisCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("OperationMoisUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("OperationMoisDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<OperationMois> getItems() {
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

    public OperationMois getOperationMois(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<OperationMois> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<OperationMois> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = OperationMois.class)
    public static class OperationMoisControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            OperationMoisController controller = (OperationMoisController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "operationMoisController");
            return controller.getOperationMois(getKey(value));
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
            if (object instanceof OperationMois) {
                OperationMois o = (OperationMois) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), OperationMois.class.getName()});
                return null;
            }
        }

    }

}

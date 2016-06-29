package controler;

import bean.ArchiveOperation;
import bean.Caisse;
import bean.Description;
import bean.Operationn;
import controler.util.JsfUtil;
import controler.util.JsfUtil.PersistAction;
import controler.util.ServerConfigUtil;
import java.io.IOException;
import service.OperationnFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import org.primefaces.event.FileUploadEvent;

@Named("operationnController")
@SessionScoped
public class OperationnController implements Serializable {

    @EJB
    private service.OperationnFacade ejbFacade;
    @EJB
    private service.DescriptionFacade descriptionFacade;
    @EJB
    private service.CaisseTTCaisseFacade caisseTTCaisseFacade;
    private List<Operationn> items = null;
    private List<Description> description = null;
    private Operationn selected;
    private String typeCaisse;
    private Caisse caisse;
    private Operationn operation;
    private String typeOp;
    private String typeCaiss;
    private List<Caisse> caissses;
    private List<ArchiveOperation> archives;
    private List<Operationn> operationns;
    private static int nbrOp;

    public List<Operationn> getOperationns() {
        if(operationns == null){
            operationns = ejbFacade.listOperation();
        }
        return operationns;
    }
    public void getOperationns1() {
        System.out.println("haniiii hnaa f op1");
            operationns = ejbFacade.listOperation();
            archives = new ArrayList();
}

    public void setOperationns(List<Operationn> operationns) {
        this.operationns = operationns;
    }
    
    
    

    public int getNbrOp() {
        int nbr;
        nbr = ejbFacade.findOperation();
        return nbrOp = nbr;
    }

    public List<ArchiveOperation> getArchives() {
        if (archives == null) {
            archives = new ArrayList();
        }
        return archives;
    }

    public void setArchives(List<ArchiveOperation> archives) {
        this.archives = archives;
    }

    public List<Caisse> getCaissses() {
        if (caissses == null) {
            caissses = new ArrayList();
        }
        return caissses;
    }

    public void setCaissses(List<Caisse> caissses) {
        this.caissses = caissses;
    }

    public String getTypeCaiss() {
        return typeCaiss;
    }

    public void setTypeCaiss(String typeCaiss) {
        this.typeCaiss = typeCaiss;
    }

    public String goPageCreate() {
        return "/operationn/List_1.xhtml";
    }

    public void trouverCaisse() {
        System.out.println("haa type dyal lcaisse " + typeCaiss);
//        if(selected != null){
//        selected.setTypeOperation(typeCaiss);
//        ejbFacade.edit(selected);
//        }
        caissses = ejbFacade.findCaisse(typeCaiss);

    }

    public String getTypeOp() {
        return typeOp;
    }

    public void setTypeOp(String typeOp) {
        this.typeOp = typeOp;
    }

    public Operationn getOperation() {
        if (operation == null) {
            operation = new Operationn();
        }
        return operation;
    }

    public void setOperation(Operationn operation) {
        this.operation = operation;
    }

    public void findByCaise() {
        description = descriptionFacade.findTypeCaisse(selected.getCaisse());
        System.out.println("haa lista" + description);
    }

    public List<Description> getDescription() {
        if (description == null) {
            description = new ArrayList();

        }
        return description;
    }

    public void setDescription(List<Description> description) {
        this.description = description;
    }

    public Caisse getCaisse() {
        if (caisse == null) {
            caisse = new Caisse();
        }
        return caisse;
    }

    public void setCaisse(Caisse caisse) {
        this.caisse = caisse;
    }

    public String getTypeCaisse() {
        return typeCaisse;
    }

    public OperationnController() {
    }

    public Operationn getSelected() {
        if (selected == null) {
            selected = new Operationn();
        }
        return selected;
    }

    public void setSelected(Operationn selected) {
        System.out.println("hanniiiiiiiiiiiiiii" + selected);
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private OperationnFacade getFacade() {
        return ejbFacade;
    }

    public Operationn prepareCreate() {
        selected = new Operationn();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("OperationnCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("OperationnUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("OperationnDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Operationn> getItems() {
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

    public Operationn getOperationn(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Operationn> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Operationn> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Operationn.class)
    public static class OperationnControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            OperationnController controller = (OperationnController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "operationnController");
            return controller.getOperationn(getKey(value));
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
            if (object instanceof Operationn) {
                Operationn o = (Operationn) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Operationn.class.getName()});
                return null;
            }
        }

    }
    //////////////////////

    public void createOperation() throws JRException, IOException {
        int res = ejbFacade.ajouterOperation(selected);
//        Mail.sendMail("fatihaabenaliaa@gmail.com","fatiha123456", "azzazisamia123@gmail.com", "hhh", "testtest");
        if (res > 0) {
//            ejbFacade.generatePdf(selected);
//            FacesContext.getCurrentInstance().getResponseComplete();
            JsfUtil.addSuccessMessage("l'Operation Est Ajoutee Avec Succes");
            selected = null;
        } else {
            JsfUtil.addErrorMessage("Verifier Le Solde De Votre Caisse !!");
        }
    }

    public void generatePdf() throws JRException, IOException {
        ejbFacade.generatePdf(selected);
        FacesContext.getCurrentInstance().getResponseComplete();
    }

    public void search() {
        items = ejbFacade.findByType();
    }

    public void findAll() {
        items = ejbFacade.findAll();
    }

    public void rechercheByCritere() {
        System.out.println("hahnaa");
        items = ejbFacade.rechercheByCritere(operation);
    }

    public void affiche() {
        System.out.println("affiiiiche");
    }

    public void findByType() {
        System.out.println("haaaaanii");
        ejbFacade.findOp(typeOp);

    }
/////////////////////////////////

    public void upload(FileUploadEvent event) throws Exception {
        Date date = new Date();
        double x = System.currentTimeMillis();
        ServerConfigUtil.upload(event.getFile(), ServerConfigUtil.getEtudiantFilePath(true), x + ".pdf");
        String path = ServerConfigUtil.getEtudiantFilePath(true) + "\\" + x + ".pdf";
        System.out.println(path);
        selected.setPhoto(x + ".pdf");
        selected.setEtatDete(2);
        selected.setDate2(date);
        ejbFacade.edit(selected);
    }

    public int resultat(Operationn op) {
        if (op.getPhoto() == null) {

            return 1;
        } else {
            return -1;
        }
    }

    public List<Operationn> getOperCaisse() {
        if (items == null) {
            return items = new ArrayList<>();
        } else {
            List<Operationn> lista = ejbFacade.selectOperCaisse();
            if (lista != null) {
                System.out.println("haaaaaa lista op" + lista);
                return items = lista;
            }
        }
        return null;
    }

    public String goPaqge2() {
        return "/operationn/List_1.xhtml";
    }

    public void afficheer() {
        int res = ejbFacade.modifierMontOperationf(selected);
        System.out.println("haaaaaaaaaa res" + res);
        if (res > 0) {
            JsfUtil.addSuccessMessage("le montant d'operation est modifier avec success");
        } else {
            JsfUtil.addErrorMessage("error !! ");
        }
    }

    public void valideDete() {
        int res = ejbFacade.validerDete(selected);
        System.out.println("haaaaaaaaaa res" + res);
        if (res > 0) {
            JsfUtil.addSuccessMessage("le dette est validé avec sucess");
        } else {
            JsfUtil.addErrorMessage("vous avez pas l'argent assez pour valider ce dette !! ");
        }
    }

    public void afficheer1() {
        ejbFacade.modifierMontOperation(selected);

    }

    public void voirDetails() {
        System.out.println("haani details");
        archives = ejbFacade.voirDetails(selected);
    }

   
}

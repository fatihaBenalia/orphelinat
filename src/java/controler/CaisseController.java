package controler;

import bean.Caisse;
import controler.util.JsfUtil;
import controler.util.JsfUtil.PersistAction;
import service.CaisseFacade;

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
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

@Named("caisseController")
@SessionScoped
public class CaisseController implements Serializable {

    @EJB
    private service.CaisseFacade ejbFacade;
    private List<Caisse> items = null;
    private Caisse selected;
    private static double montdepense;
    private static double montDete;
    private static double montProfit;
    private static double montEntree;
    private static double montdepense1;
    private static double montDete1;
    private static double montProfit1;
    private static double montEntree1;
    private Caisse caisse;
    private BarChartModel barChartModel;
    private List<Caisse> caissees;
    private String typeCaisse;

    public String getTypeCaisse() {
        return typeCaisse;
    }

    public void setTypeCaisse(String typeCaisse) {
        this.typeCaisse = typeCaisse;
    }
    
    public void creerCaisse(){
        int res = ejbFacade.create1(selected);
        if(res >0){
            JsfUtil.addSuccessMessage("Caisse Est Crée Qvec Succes ");
        }else{
            JsfUtil.addErrorMessage("Error !!");
        }
            
            
    }

    
     public void findByCaise() {
         System.out.println("haa type dyal lcaisse "+typeCaisse);
        caissees = ejbFacade.findCaisse(typeCaisse);
     
    }
    
    public List<Caisse> getCaissees() {
        if(caissees == null){
            caissees = new ArrayList();
        }
        return caissees;
    }

    public void setCaissees(List<Caisse> caissees) {
        this.caissees = caissees;
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

    public BarChartModel getBarChartModel() {
        if (barChartModel == null) {
            barChartModel = new BarChartModel();
        }
        createBarModel();
        return barChartModel;
    }

    public void setBarChartModel(BarChartModel barChartModel) {
        this.barChartModel = barChartModel;
    }

    public void createBarModel() {

        barChartModel = new BarChartModel();
 
        ChartSeries p = new ChartSeries();
        barChartModel.addSeries(p);
        p.setLabel("Depense");
        p.set("__", (Number) ejbFacade.getCaisse(caisse));
        
        ChartSeries p1 = new ChartSeries();
        barChartModel.addSeries(p1);
        p1.setLabel("Dete");
        p1.set("__", (Number) ejbFacade.getCaisse1(caisse));
        
        ChartSeries p2 = new ChartSeries();
        barChartModel.addSeries(p2);
        p2.setLabel("Entree");
        p2.set("__", (Number) ejbFacade.getCaisse3(caisse));
        
        ChartSeries p3 = new ChartSeries();
        barChartModel.addSeries(p3);
        p3.setLabel("Profit");
        p3.set("__", (Number) ejbFacade.getCaisse2(caisse));
 
        barChartModel.setTitle("Statistique Pour La Caisse"+"   "+caisse.getId());
        barChartModel.setLegendPosition("ne");
     
      

    }

    public double getMontDete() {
        double mont = ejbFacade.calculMontDete();
        montDete = mont;
        return montDete;
    }

    public double getMontProfit() {
        double mont = ejbFacade.calculMontProfit();
        montProfit = mont;
        return montProfit;
    }

    public double getMontEntree() {
        double mont = ejbFacade.calculMontEntree();
        System.out.println("haa lmontentree" + mont);
        return montEntree = mont;
    }

    public double getMontdepense() {
        double mont = ejbFacade.calculMontDepense();
        montdepense = mont;
        return montdepense;
    }

    public double getMontDete1() {
        double mont = ejbFacade.calculMontDeteGestion();
        montDete1 = mont;
        return montDete1;
    }

    public double getMontProfit1() {
        double mont = ejbFacade.calculMontProfit1();
        montProfit1 = mont;
        return montProfit1;
    }

    public double getMontEntree1() {
        double mont = ejbFacade.calculMontEntree1();
        System.out.println("haa lmontentree" + mont);
        return montEntree1 = mont;
    }

    public double getMontdepense1() {
        double mont = ejbFacade.calculMontDepense1();
        montdepense1 = mont;
        return montdepense1;
    }

    public CaisseController() {
    }

    public Caisse getSelected() {
        if (selected == null) {
            selected = new Caisse();
        }
        return selected;
    }

    public void setSelected(Caisse selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CaisseFacade getFacade() {
        return ejbFacade;
    }

    public Caisse prepareCreate() {
        selected = new Caisse();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CaisseCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CaisseUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CaisseDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Caisse> getItems() {
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

    public Caisse getCaisse(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<Caisse> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Caisse> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Caisse.class)
    public static class CaisseControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CaisseController controller = (CaisseController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "caisseController");
            return controller.getCaisse(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Caisse) {
                Caisse o = (Caisse) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Caisse.class.getName()});
                return null;
            }
        }

    }

}

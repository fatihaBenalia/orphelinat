/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


/**
 *
 * @author HASNA
 */
@Entity
public class Caisse implements Serializable {
    @OneToMany(mappedBy = "caisse")
    private List<Operationn> operationns;

    
    @OneToMany(mappedBy = "caisse")
    private List<OperationMois> operationMoiss;
  
    private static final long serialVersionUID = 1L;
    @Id
    
    private String id;
    private double dete;
    private double depense;
    private double profit;
    private double entree;
    private double entreeDossier;

    public double getEntreeDossier() {
        return entreeDossier;
    }

    public void setEntreeDossier(double entreeDossier) {
        this.entreeDossier = entreeDossier;
    }
    
    
    private String type; /// social ou gestion 

    public List<Operationn> getOperationns() {
        if(operationns == null){
            operationns = new ArrayList<>();
        }
        return operationns;
    }

    public void setOperationns(List<Operationn> operationns) {
        this.operationns = operationns;
    }

    public double getDete() {
        return dete;
    }

    public void setDete(double dete) {
        this.dete = dete;
    }

    public double getDepense() {
        return depense;
    }

    public void setDepense(double depense) {
        this.depense = depense;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getEntree() {
        return entree;
    }

    public void setEntree(double entree) {
        this.entree = entree;
    }

    
    
    
    public List<OperationMois> getOperationMoiss() {
        if(operationMoiss == null){
            operationMoiss = new ArrayList<>();
        }
        return operationMoiss;
    }

    public void setOperationMoiss(List<OperationMois> operationMoiss) {
        this.operationMoiss = operationMoiss;
    }
    
   
    
   

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

 
   
    @Override
    public String toString() {
        return id +"" ;
    }


}

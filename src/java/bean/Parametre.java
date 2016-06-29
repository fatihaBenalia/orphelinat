/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 *
 * @author ASUS
 */
@Entity
public class Parametre implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double montantComplete;//600
    private double montantPartielle;//300
    private double alimentation;//300
    private double habillement;//50
    private double MatelasEtCouvertures;//50
    private double sante;//50
    private double scolarité;//50
    private double sacrifice;//100
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateApplication;
    private int ress;

    public int getRess() {
        return ress;
    }

    public void setRess(int ress) {
        this.ress = ress;
    }
    

    public Date getDateApplication() {
        return dateApplication;
    }

    public void setDateApplication(Date dateApplication) {
        this.dateApplication = dateApplication;
    }


   
    public double getMontantComplete() {
        return montantComplete;
    }

    public void setMontantComplete(double montantComplete) {
        this.montantComplete = montantComplete;
    }

    public double getMontantPartielle() {
        return montantPartielle;
    }

    public void setMontantPartielle(double montantPartielle) {
        this.montantPartielle = montantPartielle;
    }

    public double getAlimentation() {
        return alimentation;
    }

    public void setAlimentation(double alimentation) {
        this.alimentation = alimentation;
    }

    public double getHabillement() {
        return habillement;
    }

    public void setHabillement(double habillement) {
        this.habillement = habillement;
    }

    public double getMatelasEtCouvertures() {
        return MatelasEtCouvertures;
    }

    public void setMatelasEtCouvertures(double MatelasEtCouvertures) {
        this.MatelasEtCouvertures = MatelasEtCouvertures;
    }

    public double getSante() {
        return sante;
    }

    public void setSante(double sante) {
        this.sante = sante;
    }

    public double getScolarité() {
        return scolarité;
    }

    public void setScolarité(double scolarité) {
        this.scolarité = scolarité;
    }

    public double getSacrifice() {
        return sacrifice;
    }

    public void setSacrifice(double sacrifice) {
        this.sacrifice = sacrifice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parametre)) {
            return false;
        }
        Parametre other = (Parametre) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.Parametre[ id=" + id + " ]";
    }

}

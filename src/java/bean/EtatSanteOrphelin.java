/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author HASNA
 */
@Entity
public class EtatSanteOrphelin implements Serializable {
    @ManyToOne
    private EtatSante etatSante;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
        private String typeHandicape;
    private int etatHandicape;
    private String maladie;
    private String suivieMaladieChronique;
    private String suivieHandicape;
    private String suivieMaladieRepetitif;
    private double depense;
    private String besoin;
    @OneToOne
    private Orphelin orphelin;

    public EtatSante getEtatSante() {
        return etatSante;
    }

    public void setEtatSante(EtatSante etatSante) {
        this.etatSante = etatSante;
    }

    public String getTypeHandicape() {
        return typeHandicape;
    }

    public void setTypeHandicape(String typeHandicape) {
        this.typeHandicape = typeHandicape;
    }

    public int getEtatHandicape() {
        return etatHandicape;
    }

    public void setEtatHandicape(int etatHandicape) {
        this.etatHandicape = etatHandicape;
    }

    public String getMaladie() {
        return maladie;
    }

    public void setMaladie(String maladie) {
        this.maladie = maladie;
    }

    public String getSuivieMaladieChronique() {
        return suivieMaladieChronique;
    }

    public void setSuivieMaladieChronique(String suivieMaladieChronique) {
        this.suivieMaladieChronique = suivieMaladieChronique;
    }

    public String getSuivieHandicape() {
        return suivieHandicape;
    }

    public void setSuivieHandicape(String suivieHandicape) {
        this.suivieHandicape = suivieHandicape;
    }

    public String getSuivieMaladieRepetitif() {
        return suivieMaladieRepetitif;
    }

    public void setSuivieMaladieRepetitif(String suivieMaladieRepetitif) {
        this.suivieMaladieRepetitif = suivieMaladieRepetitif;
    }

    public double getDepense() {
        return depense;
    }

    public void setDepense(double depense) {
        this.depense = depense;
    }

    public String getBesoin() {
        return besoin;
    }

    public void setBesoin(String besoin) {
        this.besoin = besoin;
    }

    public Orphelin getOrphelin() {
        return orphelin;
    }

    public void setOrphelin(Orphelin orphelin) {
        this.orphelin = orphelin;
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
        if (!(object instanceof EtatSanteOrphelin)) {
            return false;
        }
        EtatSanteOrphelin other = (EtatSanteOrphelin) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.EtatSanteOrphelin[ id=" + id + " ]";
    }
    
}

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
 * @author samia
 */
@Entity
public class ArchiveOperation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date1;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date2;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateModification;
    private int nbrMois;
    private String type; // masarif / madakhil / dayn
    private String responsable;
    private String fournisseur;
    private String mediataire;
    private double montant;
    private String numCheque;
    private String numFacture;
    private String numReleve;
    private String descriptioncaise;
    private int etatModification;
    private String typeOperation;

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    public int getNbrMois() {
        return nbrMois;
    }

    public void setNbrMois(int nbrMois) {
        this.nbrMois = nbrMois;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    public String getMediataire() {
        return mediataire;
    }

    public void setMediataire(String mediataire) {
        this.mediataire = mediataire;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getNumCheque() {
        return numCheque;
    }

    public void setNumCheque(String numCheque) {
        this.numCheque = numCheque;
    }

    public String getNumFacture() {
        return numFacture;
    }

    public void setNumFacture(String numFacture) {
        this.numFacture = numFacture;
    }

    public String getNumReleve() {
        return numReleve;
    }

    public void setNumReleve(String numReleve) {
        this.numReleve = numReleve;
    }

    public String getDescriptioncaise() {
        return descriptioncaise;
    }

    public void setDescriptioncaise(String descriptioncaise) {
        this.descriptioncaise = descriptioncaise;
    }

    public int getEtatModification() {
        return etatModification;
    }

    public void setEtatModification(int etatModification) {
        this.etatModification = etatModification;
    }

    public String getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(String typeOperation) {
        this.typeOperation = typeOperation;
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
        if (!(object instanceof ArchiveOperation)) {
            return false;
        }
        ArchiveOperation other = (ArchiveOperation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.ArchiveOperation[ id=" + id + " ]";
    }

}

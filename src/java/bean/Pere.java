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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author User
 */
@Entity
public class Pere extends Personne {
    @OneToOne(mappedBy = "pere")
    private Dossier dossier;

    private String travailavantDeces;
    private String recruteur;
    private int cnss;
    @OneToOne
    private Avocat avocat;
    private String numCnss;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateNaissance;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDeces;
    private String raisonDeces;

    public Dossier getDossier() {
        return dossier;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
    }

    public String getTravailavantDeces() {
        return travailavantDeces;
    }

    public void setTravailavantDeces(String travailavantDeces) {
        this.travailavantDeces = travailavantDeces;
    }

    public String getRecruteur() {
        return recruteur;
    }

    public void setRecruteur(String recruteur) {
        this.recruteur = recruteur;
    }

    public int getCnss() {
        return cnss;
    }

    public void setCnss(int cnss) {
        this.cnss = cnss;
    }

    public Avocat getAvocat() {
        return avocat;
    }

    public void setAvocat(Avocat avocat) {
        this.avocat = avocat;
    }

    public String getNumCnss() {
        return numCnss;
    }

    public void setNumCnss(String numCnss) {
        this.numCnss = numCnss;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Date getDateDeces() {
        return dateDeces;
    }

    public void setDateDeces(Date dateDeces) {
        this.dateDeces = dateDeces;
    }

    public String getRaisonDeces() {
        return raisonDeces;
    }

    public void setRaisonDeces(String raisonDeces) {
        this.raisonDeces = raisonDeces;
    }
    
    
    
}

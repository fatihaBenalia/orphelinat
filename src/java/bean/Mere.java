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
public class Mere extends Personne {
    @OneToOne(mappedBy = "mere")
    private EtatSanteMere etatSanteMere;
    @OneToOne(mappedBy = "mere")
    private EtatEthique etatEthique;
    @OneToOne(mappedBy = "mere")
    private Dossier dossier;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDeces;
    private String raisonDeces;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateNaissance;

    public Dossier getDossier() {
        return dossier;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
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

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    
}

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
public class DossierArchive implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;//
    private String nomFamille;//
    private String region;//
    private String assistanteSociale1;//
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDossier;
    private String adresseActuelle;
    private int nbrMembre;
    private int nbrOrphelin;
    private String etat;
    private String mere;
    private String pere;
    private String ParrainResp;

    public String getParrainResp() {
        return ParrainResp;
    }

    public void setParrainResp(String ParrainResp) {
        this.ParrainResp = ParrainResp;
    }
    
    
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNomFamille() {
        return nomFamille;
    }

    public void setNomFamille(String nomFamille) {
        this.nomFamille = nomFamille;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAssistanteSociale1() {
        return assistanteSociale1;
    }

    public void setAssistanteSociale1(String assistanteSociale1) {
        this.assistanteSociale1 = assistanteSociale1;
    }

   

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDateDossier() {
        return dateDossier;
    }

    public void setDateDossier(Date dateDossier) {
        this.dateDossier = dateDossier;
    }

    public String getAdresseActuelle() {
        return adresseActuelle;
    }

    public void setAdresseActuelle(String adresseActuelle) {
        this.adresseActuelle = adresseActuelle;
    }

    public int getNbrMembre() {
        return nbrMembre;
    }

    public void setNbrMembre(int nbrMembre) {
        this.nbrMembre = nbrMembre;
    }

    public int getNbrOrphelin() {
        return nbrOrphelin;
    }

    public void setNbrOrphelin(int nbrOrphelin) {
        this.nbrOrphelin = nbrOrphelin;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getMere() {
        return mere;
    }

    public void setMere(String mere) {
        this.mere = mere;
    }

    public String getPere() {
        return pere;
    }

    public void setPere(String pere) {
        this.pere = pere;
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
        if (!(object instanceof DossierArchive)) {
            return false;
        }
        DossierArchive other = (DossierArchive) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.DossierArchive[ id=" + id + " ]";
    }

}

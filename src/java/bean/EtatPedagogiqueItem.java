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
public class EtatPedagogiqueItem implements Serializable {
    @ManyToOne
    private EtatPedagogique etatPedagogique;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    private Orphelin orphelin;
    private String relationMere;
    private String relationFrere;
    private String relationVoision;
    private String moeur;
    private String habillement;
    private String interetScolaire;
    private String commentaire;

    public EtatPedagogique getEtatPedagogique() {
        return etatPedagogique;
    }

    public void setEtatPedagogique(EtatPedagogique etatPedagogique) {
        this.etatPedagogique = etatPedagogique;
    }

    public Orphelin getOrphelin() {
        return orphelin;
    }

    public void setOrphelin(Orphelin orphelin) {
        this.orphelin = orphelin;
    }

    public String getRelationMere() {
        return relationMere;
    }

    public void setRelationMere(String relationMere) {
        this.relationMere = relationMere;
    }

    public String getRelationFrere() {
        return relationFrere;
    }

    public void setRelationFrere(String relationFrere) {
        this.relationFrere = relationFrere;
    }

    public String getRelationVoision() {
        return relationVoision;
    }

    public void setRelationVoision(String relationVoision) {
        this.relationVoision = relationVoision;
    }

    public String getMoeur() {
        return moeur;
    }

    public void setMoeur(String moeur) {
        this.moeur = moeur;
    }

    public String getHabillement() {
        return habillement;
    }

    public void setHabillement(String habillement) {
        this.habillement = habillement;
    }

    public String getInteretScolaire() {
        return interetScolaire;
    }

    public void setInteretScolaire(String interetScolaire) {
        this.interetScolaire = interetScolaire;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
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
        if (!(object instanceof EtatPedagogiqueItem)) {
            return false;
        }
        EtatPedagogiqueItem other = (EtatPedagogiqueItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.EtatPedagogiqueItem[ id=" + id + " ]";
    }
    
}

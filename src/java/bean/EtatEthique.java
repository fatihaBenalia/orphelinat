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
import javax.persistence.OneToOne;

/**
 *
 * @author User
 */
@Entity
public class EtatEthique implements Serializable {
    @OneToOne(mappedBy = "etatEthique")
    private Dossier dossier;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String SoutienScolaire;
    private String nourritureOrphelin;
    private String propreteHabille;
    private String propreteLogement;
    private String relationFamille;
    private String relationVoision;
    private String habillement;
    private String interetScolaire;
    private String commentaire;
    @OneToOne
    private Mere mere;

    public Dossier getDossier() {
        return dossier;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSoutienScolaire() {
        return SoutienScolaire;
    }

    public void setSoutienScolaire(String SoutienScolaire) {
        this.SoutienScolaire = SoutienScolaire;
    }

    public String getNourritureOrphelin() {
        return nourritureOrphelin;
    }

    public void setNourritureOrphelin(String nourritureOrphelin) {
        this.nourritureOrphelin = nourritureOrphelin;
    }

    public String getPropreteHabille() {
        return propreteHabille;
    }

    public void setPropreteHabille(String propreteHabille) {
        this.propreteHabille = propreteHabille;
    }

    public String getPropreteLogement() {
        return propreteLogement;
    }

    public void setPropreteLogement(String propreteLogement) {
        this.propreteLogement = propreteLogement;
    }

    public String getRelationFamille() {
        return relationFamille;
    }

    public void setRelationFamille(String relationFamille) {
        this.relationFamille = relationFamille;
    }

    public String getRelationVoision() {
        return relationVoision;
    }

    public void setRelationVoision(String relationVoision) {
        this.relationVoision = relationVoision;
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

    public Mere getMere() {
        return mere;
    }

    public void setMere(Mere mere) {
        this.mere = mere;
    }

    
    
    
}

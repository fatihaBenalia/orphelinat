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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author User
 */
@Entity

public class Orphelin implements Serializable {

  

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
      @OneToOne(mappedBy = "orphelin")
    private EtatSanteOrphelin etatSanteOrphelin;
    @OneToOne(mappedBy = "orphelin")
    private EtatPedagogiqueItem etatPedagogiqueItem;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateNaissance;
    @ManyToOne
    private Dossier dossier;
    private String niveauScolaire;
    private String ecole;
    private int nbrAnneeRedouble;
    private String raisonRedoublement;
    private int aideScolaire;
    private String matiere;
    private String blemeScolaire;
    private String futurPerspective;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDisconScolaire;
    private String raisonDisconScolaire;
    private String etablissement;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDebutFormation;
    private int dureeFormation;
    private String typeFormation;
    private String blemeFormation;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDiscon;
    private String Raisondiscontinuite;
    private String capacite;
    private String raisonNonTravail;
    private String dureeChomage;
    private String perspective;
    private int type;
    private String sexe;
// si cultive=1 si FormeProfessionellement=2 si NonForme=3 

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public EtatSanteOrphelin getEtatSanteOrphelin() {
        return etatSanteOrphelin;
    }

    public void setEtatSanteOrphelin(EtatSanteOrphelin etatSanteOrphelin) {
        this.etatSanteOrphelin = etatSanteOrphelin;
    }

    public EtatPedagogiqueItem getEtatPedagogiqueItem() {
        return etatPedagogiqueItem;
    }

    public void setEtatPedagogiqueItem(EtatPedagogiqueItem etatPedagogiqueItem) {
        this.etatPedagogiqueItem = etatPedagogiqueItem;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Dossier getDossier() {
        return dossier;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
    }

    public String getNiveauScolaire() {
        return niveauScolaire;
    }

    public void setNiveauScolaire(String niveauScolaire) {
        this.niveauScolaire = niveauScolaire;
    }

    public String getEcole() {
        return ecole;
    }

    public void setEcole(String ecole) {
        this.ecole = ecole;
    }

    public int getNbrAnneeRedouble() {
        return nbrAnneeRedouble;
    }

    public void setNbrAnneeRedouble(int nbrAnneeRedouble) {
        this.nbrAnneeRedouble = nbrAnneeRedouble;
    }

    public String getRaisonRedoublement() {
        return raisonRedoublement;
    }

    public void setRaisonRedoublement(String raisonRedoublement) {
        this.raisonRedoublement = raisonRedoublement;
    }

    public int getAideScolaire() {
        return aideScolaire;
    }

    public void setAideScolaire(int aideScolaire) {
        this.aideScolaire = aideScolaire;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getBlemeScolaire() {
        return blemeScolaire;
    }

    public void setBlemeScolaire(String blemeScolaire) {
        this.blemeScolaire = blemeScolaire;
    }

    public String getFuturPerspective() {
        return futurPerspective;
    }

    public void setFuturPerspective(String futurPerspective) {
        this.futurPerspective = futurPerspective;
    }

    public Date getDateDisconScolaire() {
        return dateDisconScolaire;
    }

    public void setDateDisconScolaire(Date dateDisconScolaire) {
        this.dateDisconScolaire = dateDisconScolaire;
    }

    public String getRaisonDisconScolaire() {
        return raisonDisconScolaire;
    }

    public void setRaisonDisconScolaire(String raisonDisconScolaire) {
        this.raisonDisconScolaire = raisonDisconScolaire;
    }

    public String getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(String etablissement) {
        this.etablissement = etablissement;
    }

    public Date getDateDebutFormation() {
        return dateDebutFormation;
    }

    public void setDateDebutFormation(Date dateDebutFormation) {
        this.dateDebutFormation = dateDebutFormation;
    }

    public int getDureeFormation() {
        return dureeFormation;
    }

    public void setDureeFormation(int dureeFormation) {
        this.dureeFormation = dureeFormation;
    }

    public String getTypeFormation() {
        return typeFormation;
    }

    public void setTypeFormation(String typeFormation) {
        this.typeFormation = typeFormation;
    }

    public String getBlemeFormation() {
        return blemeFormation;
    }

    public void setBlemeFormation(String blemeFormation) {
        this.blemeFormation = blemeFormation;
    }

    public Date getDateDiscon() {
        return dateDiscon;
    }

    public void setDateDiscon(Date dateDiscon) {
        this.dateDiscon = dateDiscon;
    }

    public String getRaisondiscontinuite() {
        return Raisondiscontinuite;
    }

    public void setRaisondiscontinuite(String Raisondiscontinuite) {
        this.Raisondiscontinuite = Raisondiscontinuite;
    }

    public String getCapacite() {
        return capacite;
    }

    public void setCapacite(String capacite) {
        this.capacite = capacite;
    }

    public String getRaisonNonTravail() {
        return raisonNonTravail;
    }

    public void setRaisonNonTravail(String raisonNonTravail) {
        this.raisonNonTravail = raisonNonTravail;
    }

    public String getDureeChomage() {
        return dureeChomage;
    }

    public void setDureeChomage(String dureeChomage) {
        this.dureeChomage = dureeChomage;
    }

    public String getPerspective() {
        return perspective;
    }

    public void setPerspective(String perspective) {
        this.perspective = perspective;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    
    
    
    
}

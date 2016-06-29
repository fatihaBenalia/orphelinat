/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
/**
 *
 * @author User
 */
@Entity
public class Dossier implements Serializable {

    @OneToMany(mappedBy = "dossier")
    private List<OperationMois> operationMoiss;

    @OneToMany(mappedBy = "dossier")
    private List<Parrinage> parrinages;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDossier;
    @OneToOne
    private Mere mere;
    @OneToOne
    private Pere pere;
    @OneToOne
    private ResponsableFamille responsableFamille;//
    private String numDossier;
    private String numero;//
    private String nomFamille;//
    private String region;//
    @ManyToOne
    private ResponsableRegion responsableRegion;
    private String adresseActuelle;
    @OneToMany(mappedBy = "dossier")
    private List<Membre> membres;
    @OneToMany(mappedBy = "dossier")
    private List<Orphelin> orphelins;
    @OneToOne
    private EtatPedagogique etatPedagogique;
    @OneToOne
    private EtatEthique etatEthique;
    @OneToOne
    private EtatSante etatSante;
    @OneToOne
    private Logement logement;
    @OneToMany(mappedBy = "dossier")
    private List<Materiel> materiels;
    @OneToOne
    private Activite activite;
    @OneToOne
    private Aide aide;
    @ManyToOne
    private AssistanteSociale assistanteSociale1;//
    @ManyToOne
    private AssistanteSociale assistanteSociale2;//
    private String remarque;
    private String proposition;
    private double montantTotal;
    private float indice;
    private int nbrMembre;
    private int nbrOrphelin;
    private int etat;
    private double montantDepenseSanter;
    private int laid;

    
    @OneToOne
    CompteAssociationParrinage compteAssociationParrinage;

 
    
    

    public List<OperationMois> getOperationMoiss() {
        return operationMoiss;
    }

    public void setOperationMoiss(List<OperationMois> operationMoiss) {
        this.operationMoiss = operationMoiss;
    }

    public double getMontantDepenseSanter() {
        return montantDepenseSanter;
    }

    public void setMontantDepenseSanter(double montantDepenseSanter) {
        this.montantDepenseSanter = montantDepenseSanter;
    }

    public int getLaid() {
        return laid;
    }

    public void setLaid(int laid) {
        this.laid = laid;
    }

    public CompteAssociationParrinage getCompteAssociationParrinage() {
        return compteAssociationParrinage;
    }

    public void setCompteAssociationParrinage(CompteAssociationParrinage compteAssociationParrinage) {
        this.compteAssociationParrinage = compteAssociationParrinage;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public List<Parrinage> getParrinages() {
        return parrinages;
    }

    public void setParrinages(List<Parrinage> parrinages) {
        this.parrinages = parrinages;
    }

    public int getNbrOrphelin() {
        return nbrOrphelin;
    }

    public void setNbrOrphelin(int nbrOrphelin) {
        this.nbrOrphelin = nbrOrphelin;
    }

    public int getNbrMembre() {
        return nbrMembre;
    }

    public void setNbrMembre(int nbrMembre) {
        this.nbrMembre = nbrMembre;
    }

    public float getIndice() {
        return indice;
    }

    public void setIndice(float indice) {
        this.indice = indice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateDossier() {
        return dateDossier;
    }

    public void setDateDossier(Date dateDossier) {
        this.dateDossier = dateDossier;
    }

    public Mere getMere() {
        return mere;
    }

    public void setMere(Mere mere) {
        this.mere = mere;
    }

    public Pere getPere() {
        return pere;
    }

    public void setPere(Pere pere) {
        this.pere = pere;
    }

    public ResponsableFamille getResponsableFamille() {
        return responsableFamille;
    }

    public void setResponsableFamille(ResponsableFamille responsableFamille) {
        this.responsableFamille = responsableFamille;
    }

    public String getNumDossier() {
        return numDossier;
    }

    public void setNumDossier(String numDossier) {
        this.numDossier = numDossier;
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

    public ResponsableRegion getResponsableRegion() {
        return responsableRegion;
    }

    public void setResponsableRegion(ResponsableRegion responsableRegion) {
        this.responsableRegion = responsableRegion;
    }

    public String getAdresseActuelle() {
        return adresseActuelle;
    }

    public void setAdresseActuelle(String adresseActuelle) {
        this.adresseActuelle = adresseActuelle;
    }

    public List<Membre> getMembres() {
        return membres;
    }

    public void setMembres(List<Membre> membres) {
        this.membres = membres;
    }

    public List<Orphelin> getOrphelins() {
        return orphelins;
    }

    public void setOrphelins(List<Orphelin> orphelins) {
        this.orphelins = orphelins;
    }

    public EtatPedagogique getEtatPedagogique() {
        return etatPedagogique;
    }

    public void setEtatPedagogique(EtatPedagogique etatPedagogique) {
        this.etatPedagogique = etatPedagogique;
    }

    public EtatEthique getEtatEthique() {
        return etatEthique;
    }

    public void setEtatEthique(EtatEthique etatEthique) {
        this.etatEthique = etatEthique;
    }

    public EtatSante getEtatSante() {
        return etatSante;
    }

    public void setEtatSante(EtatSante etatSante) {
        this.etatSante = etatSante;
    }

    public Logement getLogement() {
        return logement;
    }

    public void setLogement(Logement logement) {
        this.logement = logement;
    }

    public List<Materiel> getMateriels() {
        return materiels;
    }

    public void setMateriels(List<Materiel> materiels) {
        this.materiels = materiels;
    }

    public Activite getActivite() {
        return activite;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }

    public Aide getAide() {
        return aide;
    }

    public void setAide(Aide aide) {
        this.aide = aide;
    }

    public AssistanteSociale getAssistanteSociale1() {
        return assistanteSociale1;
    }

    public void setAssistanteSociale1(AssistanteSociale assistanteSociale1) {
        this.assistanteSociale1 = assistanteSociale1;
    }

    public AssistanteSociale getAssistanteSociale2() {
        return assistanteSociale2;
    }

    public void setAssistanteSociale2(AssistanteSociale assistanteSociale2) {
        this.assistanteSociale2 = assistanteSociale2;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public String getProposition() {
        return proposition;
    }

    public void setProposition(String proposition) {
        this.proposition = proposition;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }


    
}

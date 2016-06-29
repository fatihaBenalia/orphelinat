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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author samia
 */
@Entity
public class Operationn implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date1;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date2;
    private int nbrMois;
    private String type; // masarif / madakhil / dayn
    @ManyToOne
    private Caisse caisse;//combobox
    @OneToOne
    private Description descriptionCai;
    private String descriptionOper;
    private String responsable;
    private String fournisseur;
    private String mediataire;
    private String debit;
    private String credit;
    private double montant;
    private String numCheque;
    private String numFacture;
    private String numReleve;
    private String photo;
    private int etat; // na9is fatoura wla ada2
    private int etatValide;
    private String typeOperation; /// gestion source ou social
    private int etatDete;
    private String descriptioncaise;
    private int etatModification;
    private String typePaiement;

    public String getTypePaiement() {
        return typePaiement;
    }

    public void setTypePaiement(String typePaiement) {
        this.typePaiement = typePaiement;
    }
    
    

    public int getEtatModification() {
        return etatModification;
    }

    public void setEtatModification(int etatModification) {
        this.etatModification = etatModification;
    }
    
    
    
    
    
      public java.util.Date convertUtilToSql(java.util.Date utilDate) {

        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        return sqlDate;
    }
    
    public String getDescriptioncaise() {
        return descriptioncaise;
    }

    public void setDescriptioncaise(String descriptioncaise) {
        this.descriptioncaise = descriptioncaise;
    }
    

    public int getEtatDete() {
        return etatDete;
    }

    public void setEtatDete(int etatDete) {
        this.etatDete = etatDete;
    }
    
    
    public String getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(String typeOperation) {
        this.typeOperation = typeOperation;
    }
    
    

    public int getEtatValide() {
        return etatValide;
    }

    public void setEtatValide(int etatValide) {
        this.etatValide = etatValide;
    }
    
    
    
    
   
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

    public Caisse getCaisse() {
        if(caisse == null){
        caisse = new Caisse();
        }
        return caisse;
    }

    public void setCaisse(Caisse caisse) {
        this.caisse = caisse;
    }

    public Description getDescriptionCai() {
        if(descriptionCai ==null){
            descriptionCai=new Description();
        }
        return descriptionCai;
    }

    public void setDescriptionCai(Description descriptionCai) {
        this.descriptionCai = descriptionCai;
    }

  

    public String getDescriptionOper() {
        return descriptionOper;
    }

    public void setDescriptionOper(String descriptionOper) {
        this.descriptionOper = descriptionOper;
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

    public String getDebit() {
        return debit;
    }

    public void setDebit(String debit) {
        this.debit = debit;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
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

    public String getPhoto() {
        System.out.println("ha hwaaa "+photo);
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
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
        if (!(object instanceof Operationn)) {
            return false;
        }
        Operationn other = (Operationn) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.Operation[ id=" + id + " ]";
    }
    
}

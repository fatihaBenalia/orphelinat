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
import javax.persistence.Temporal;

/**
 *
 * @author HASNA
 */
@Entity
public class Parrinage implements Serializable {
  
  
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne 
    private Parrain parrain;
    @ManyToOne
    private Dossier dossier;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date debutPar;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date finPar;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date priseDossier;
    private String type;
    private double montant;
    private int nbrjours1;
    private int etatArchive;
    private double totMontant;
    private String numCheque;
    private String typePaiement;
    private String remarque;

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

    public String getTypePaiement() {
        return typePaiement;
    }

    public void setTypePaiement(String typePaiement) {
        this.typePaiement = typePaiement;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }
    
    

    public double getTotMontant() {
        return totMontant;
    }
    public void setTotMontant(double totMontant) {
        this.totMontant = totMontant;
    }

    public int getEtatArchive() {
        return etatArchive;
    }

    public void setEtatArchive(int etatArchive) {
        this.etatArchive = etatArchive;
    }

    public int getNbrjours1() {
        return nbrjours1;
    }

    public void setNbrjours1(int nbrjours1) {
        this.nbrjours1 = nbrjours1;
    }

    public Parrain getParrain() {
        if(parrain == null){
            parrain= new Parrain();
        }
        return parrain;
    }

    public void setParrain(Parrain parrain) {
        this.parrain = parrain;
    }

   
    public Dossier getDossier() {
        if(dossier == null){
            dossier= new Dossier();
        }
        return dossier;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
    }

    public Date getDebutPar() {
        return debutPar;
    }

    public void setDebutPar(Date debutPar) {
        this.debutPar = debutPar;
    }

    public Date getFinPar() {
        return finPar;
    }

    public void setFinPar(Date finPar) {
        this.finPar = finPar;
    }

    public Date getPriseDossier() {
        return priseDossier;
    }

    public void setPriseDossier(Date priseDossier) {
        this.priseDossier = priseDossier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        if (!(object instanceof Parrinage)) {
            return false;
        }
        Parrinage other = (Parrinage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  "id";
    }
    
}

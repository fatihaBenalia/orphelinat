/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author ASUS
 */
 @Entity
public class CompteAssociationParrinage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double  montant;
    int nbr1;
    int nbr2;
    @OneToMany
    private List<Parrinage> parrinages;//hadou li3ndhoum type de kafala takmiliya
    //POUR CHERCHER HAD LISTE parrinage ou dossier c'est le dossier actuelle et les parrinage de type partielle 

    public int getNbr1() {
        return nbr1;
    }

    public void setNbr1(int nbr1) {
        this.nbr1 = nbr1;
    }

    public int getNbr2() {
        return nbr2;
    }

    public void setNbr2(int nbr2) {
        this.nbr2 = nbr2;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public List<Parrinage> getParrinages() {
        return parrinages;
    }

    public void setParrinages(List<Parrinage> parrinages) {
        this.parrinages = parrinages;
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
        if (!(object instanceof CompteAssociationParrinage)) {
            return false;
        }
        CompteAssociationParrinage other = (CompteAssociationParrinage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.CompteAssociationParrinage[ id=" + id + " ]";
    }
    
}

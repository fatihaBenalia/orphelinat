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
import javax.persistence.OneToOne;

/**
 *
 * @author User
 */
@Entity
public class EtatPedagogique implements Serializable {
    @OneToOne(mappedBy = "etatPedagogique")
    private Dossier dossier;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long id;
    @OneToMany(mappedBy = "etatPedagogique")
   private List<EtatPedagogiqueItem> etatPedagogiqueItems;

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

    public List<EtatPedagogiqueItem> getEtatPedagogiqueItems() {
        return etatPedagogiqueItems;
    }

    public void setEtatPedagogiqueItems(List<EtatPedagogiqueItem> etatPedagogiqueItems) {
        this.etatPedagogiqueItems = etatPedagogiqueItems;
    }
  
    
    
}

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
public class EtatSante implements Serializable {
    @OneToOne(mappedBy = "etatSante")
    private Dossier dossier;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "etatSante")
    private List<EtatSanteOrphelin> etatSanteOrphelins;
    @OneToOne
    private EtatSanteMere etatSanteMere;

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

    public List<EtatSanteOrphelin> getEtatSanteOrphelins() {
        return etatSanteOrphelins;
    }

    public void setEtatSanteOrphelins(List<EtatSanteOrphelin> etatSanteOrphelins) {
        this.etatSanteOrphelins = etatSanteOrphelins;
    }

    public EtatSanteMere getEtatSanteMere() {
        return etatSanteMere;
    }

    public void setEtatSanteMere(EtatSanteMere etatSanteMere) {
        this.etatSanteMere = etatSanteMere;
    }

    
    
    
}

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

/**
 *
 * @author User
 */
@Entity
public class Materiel implements Serializable {
    @ManyToOne
    private Dossier dossier;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private int etatMateriel;
    private String libelleMateriel;

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

    public int getEtatMateriel() {
        return etatMateriel;
    }

    public void setEtatMateriel(int etatMateriel) {
        this.etatMateriel = etatMateriel;
    }

    public String getLibelleMateriel() {
        return libelleMateriel;
    }

    public void setLibelleMateriel(String libelleMateriel) {
        this.libelleMateriel = libelleMateriel;
    }
    
    
    
}

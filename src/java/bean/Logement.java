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
public class Logement implements Serializable {
    @OneToOne(mappedBy = "logement")
    private Dossier dossier;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String residenseAvantDeces;
    private String raisonChangementLog;
    private int typeLog;
    private int nbrChambre;
    private String surface;
    private int typePossession;
    private double montantGage;
    private double montantLocation;
    private String autreType;
    private int eau;
    private int elec;
    private int eauEgout;
    private double montantEau;
    private double montantElec;

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

    public String getResidenseAvantDeces() {
        return residenseAvantDeces;
    }

    public void setResidenseAvantDeces(String residenseAvantDeces) {
        this.residenseAvantDeces = residenseAvantDeces;
    }

    public String getRaisonChangementLog() {
        return raisonChangementLog;
    }

    public void setRaisonChangementLog(String raisonChangementLog) {
        this.raisonChangementLog = raisonChangementLog;
    }

    public int getTypeLog() {
        return typeLog;
    }

    public void setTypeLog(int typeLog) {
        this.typeLog = typeLog;
    }

    public int getNbrChambre() {
        return nbrChambre;
    }

    public void setNbrChambre(int nbrChambre) {
        this.nbrChambre = nbrChambre;
    }

    public String getSurface() {
        return surface;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    public int getTypePossession() {
        return typePossession;
    }

    public void setTypePossession(int typePossession) {
        this.typePossession = typePossession;
    }

    public double getMontantGage() {
        return montantGage;
    }

    public void setMontantGage(double montantGage) {
        this.montantGage = montantGage;
    }

    public double getMontantLocation() {
        return montantLocation;
    }

    public void setMontantLocation(double montantLocation) {
        this.montantLocation = montantLocation;
    }

    public String getAutreType() {
        return autreType;
    }

    public void setAutreType(String autreType) {
        this.autreType = autreType;
    }

    public int getEau() {
        return eau;
    }

    public void setEau(int eau) {
        this.eau = eau;
    }

    public int getElec() {
        return elec;
    }

    public void setElec(int elec) {
        this.elec = elec;
    }

    public int getEauEgout() {
        return eauEgout;
    }

    public void setEauEgout(int eauEgout) {
        this.eauEgout = eauEgout;
    }

    public double getMontantEau() {
        return montantEau;
    }

    public void setMontantEau(double montantEau) {
        this.montantEau = montantEau;
    }

    public double getMontantElec() {
        return montantElec;
    }

    public void setMontantElec(double montantElec) {
        this.montantElec = montantElec;
    }
           
    
    
    
}

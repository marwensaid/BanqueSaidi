/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author marwen
 */
@Entity
public class CompteEpargne extends CompteBancaire implements Serializable {

    public CompteEpargne(String nom, double solde, double tauxEpargne) {
        super(nom, solde);
        this.desciption = "Epargne";
        this.tauxEpargne = tauxEpargne;
    }

    private double tauxEpargne;

    public CompteEpargne() {
    }

    public double getTauxEpargne() {
        return tauxEpargne;
    }

    public void setTauxEpargne(double tauxEpargne) {
        this.tauxEpargne = tauxEpargne;
    }

    public void appliquerTaux() {
        solde = (int) (solde * (1 + tauxEpargne));
    }

    @Override
    public String toString() {
        return "entities.CompteEpargne[ id=" + id + " ]";
    }

}

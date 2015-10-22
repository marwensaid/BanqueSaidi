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
public class CompteCourant extends CompteBancaire implements Serializable {

    public CompteCourant() {
    }

    public CompteCourant(String nom, double solde) {
        super(nom, solde);
        this.desciption = "Courant";
    }

    @Override
    public String toString() {
        return "entities.CompteCourant[ id=" + id + " ]";
    }

}

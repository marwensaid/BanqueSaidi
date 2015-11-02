/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.CompteBancaire;
import entities.OperationBancaire;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import javax.persistence.Query;
import org.primefaces.model.LazyDataModel;
import session.GestionnaireCompteBancaire;


/**
 *
 * @author marwen
 */
@Named(value = "operationBancaireMBean")
@RequestScoped
public class OperationBancaireMBean implements Serializable{

    @EJB
    private GestionnaireCompteBancaire gc;
    private int idCompte;
    private LazyDataModel<OperationBancaire> modele;

    public LazyDataModel<OperationBancaire> getModele() {
        return modele;
    }

    public void setModele(LazyDataModel<OperationBancaire> modele) {
        this.modele = modele;
    }

    public GestionnaireCompteBancaire getGc() {
        return gc;
    }

    public void setGc(GestionnaireCompteBancaire gc) {
        this.gc = gc;
    }

    public int getIdCompte() {
        System.out.println("GET ID COMPTE : " + idCompte);
        return idCompte;
    }

    public void setIdCompte(int idCompte) {
        System.out.println("SET ID COMPTE : " + idCompte);
        this.idCompte = idCompte;
    }
    

    /**
     * Creates a new instance of OperationBancaireMBean
     */
    public OperationBancaireMBean() {
        System.out.println("NEW OperationsBancairesMBean");
        

    }

    public List<OperationBancaire> getOperationsBancaires() {
        return gc.getOperationsBancaires(idCompte);
    }

}

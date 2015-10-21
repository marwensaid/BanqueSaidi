/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.CompteBancaire;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import session.GestionnaireCompteBancaire;

/**
 *
 * @author marwen
 */
@ManagedBean
@RequestScoped
public class CompteBancaireMBean {

    @EJB
    private GestionnaireCompteBancaire gc;
    private int idCompteACrediter;
    private double montantACrediter;
    private int idCompteADebiter;
    private double montantADebiter;
    private int id1;
    private int id2;
    private double montantTransfert;
    private String message;
    private LazyDataModel<CompteBancaire> modele;

    /**
     * Creates a new instance of CompteBancaireMBean
     */
    public CompteBancaireMBean() {
        modele = new LazyDataModel<CompteBancaire>() {

            @Override
            public List load(int i, int i1, String string, SortOrder so, Map map) {
                List<CompteBancaire> comptes = new ArrayList<CompteBancaire>();
                comptes = gc.getLazyComptes(i, i1);
                return comptes;
            }

            @Override
            public int getRowCount() {
                return gc.getNBComptes();
            }
        };
    }

    public GestionnaireCompteBancaire getGc() {
        return gc;
    }

    public void setGc(GestionnaireCompteBancaire gc) {
        this.gc = gc;
    }

    public LazyDataModel<CompteBancaire> getModele() {
        return modele;
    }

    public void setModele(LazyDataModel<CompteBancaire> modele) {
        this.modele = modele;
    }

    public String getMessage() {
        return message;
    }

    /**
     * Get the value of id2
     *
     * @return the value of id2
     */
    public int getId2() {
        return id2;
    }

    /**
     * Set the value of id2
     *
     * @param id2 new value of id2
     */
    public void setId2(int id2) {
        this.id2 = id2;
    }

    /**
     * Get the value of montantTransfert
     *
     * @return the value of montantTransfert
     */
    public double getMontantTransfert() {
        return montantTransfert;
    }

    /**
     * Set the value of montantTransfert
     *
     * @param montantTransfert new value of montantTransfert
     */
    public void setMontantTransfert(double montantTransfert) {
        this.montantTransfert = montantTransfert;
    }

    /**
     * Get the value of id1
     *
     * @return the value of id1
     */
    public int getId1() {
        return id1;
    }

    /**
     * Set the value of id1
     *
     * @param id1 new value of id1
     */
    public void setId1(int id1) {
        this.id1 = id1;
    }

    /**
     * Get the value of montantADebiter
     *
     * @return the value of montantADebiter
     */
    public double getMontantADebiter() {
        return montantADebiter;
    }

    /**
     * Set the value of montantADebiter
     *
     * @param montantADebiter new value of montantADebiter
     */
    public void setMontantADebiter(double montantADebiter) {
        this.montantADebiter = montantADebiter;
    }

    /**
     * Get the value of idCompteADebiter
     *
     * @return the value of idCompteADebiter
     */
    public int getIdCompteADebiter() {
        return idCompteADebiter;
    }

    /**
     * Set the value of idCompteADebiter
     *
     * @param idCompteADebiter new value of idCompteADebiter
     */
    public void setIdCompteADebiter(int idCompteADebiter) {
        this.idCompteADebiter = idCompteADebiter;
    }

    /**
     * Get the value of montantACrediter
     *
     * @return the value of montantACrediter
     */
    public double getMontantACrediter() {
        return montantACrediter;
    }

    /**
     * Set the value of montantACrediter
     *
     * @param montantACrediter new value of montantACrediter
     */
    public void setMontantACrediter(double montantACrediter) {
        this.montantACrediter = montantACrediter;
    }

    /**
     * Get the value of idCompteACrediter
     *
     * @return the value of idCompteACrediter
     */
    public int getIdCompteACrediter() {
        return idCompteACrediter;
    }

    /**
     * Set the value of idCompteACrediter
     *
     * @param idCompteACrediter new value of idCompteACrediter
     */
    public void setIdCompteACrediter(int idCompteACrediter) {
        this.idCompteACrediter = idCompteACrediter;
    }

    // MODELES / PROPRIETES
    // Pour affichage dans la datatable
    public List<CompteBancaire> getComptesBancaires() {
        return gc.findAll();
    }

    // METHODES D'ACTION
    public void creerComptesDeTest() {
        System.out.println("### COMPTES CREES ###");
        gc.creerComptesTest();
    }

    public void crediterUnCompte() {
        gc.crediterUnCompte(idCompteACrediter, montantACrediter);
    }

    public void debiterUnCompte() {
        gc.debiterUnCompte(idCompteADebiter, montantADebiter);
    }

    public void transferer() {
        // MAUVAIS : DEUX TRANSACTIONS ICI !!!
        //gc.debiterUnCompte(id1, montantTransfert);
        //gc.crediterUnCompte(id2, montantTransfert);
        try {
            gc.transferer(id1, id2, montantTransfert);
        } catch (Exception e) {
            message = "Transfert impossible, pas assez d'argent";
            System.out.println("### PAS ASSEZ d'argent");
        }
    }

}

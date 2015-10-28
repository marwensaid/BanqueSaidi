/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.CompteBancaire;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import session.GestionnaireCompteBancaire;

/**
 *
 * @author marwen
 */
@Named(value = "compteBancaireMBean")
@SessionScoped
public class CompteBancaireMBean implements Serializable {

    @EJB
    private GestionnaireCompteBancaire gc;
    private CompteBancaire compteBancaire;
    private List<CompteBancaire> listCompteBancaire;
    private Long idCompteACrediter;
    private double montantACrediter;
    private Long idCompteADebiter;
    private double montantADebiter;
    private Long id1;
    private Long id2;
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

    public List<CompteBancaire> getListCompteBancaire() {
        return listCompteBancaire;
    }

    public void setListCompteBancaire(List<CompteBancaire> listCompteBancaire) {
        this.listCompteBancaire = listCompteBancaire;
    }

    public CompteBancaire getCompteBancaire() {
        return compteBancaire;
    }

    public void setCompteBancaire(CompteBancaire compteBancaire) {
        this.compteBancaire = compteBancaire;
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
    public Long getId2() {
        return id2;
    }

    /**
     * Set the value of id2
     *
     * @param id2 new value of id2
     */
    public void setId2(Long id2) {
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
    public Long getId1() {
        return id1;
    }

    /**
     * Set the value of id1
     *
     * @param id1 new value of id1
     */
    public void setId1(Long id1) {
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
    public Long getIdCompteADebiter() {
        return idCompteADebiter;
    }

    /**
     * Set the value of idCompteADebiter
     *
     * @param idCompteADebiter new value of idCompteADebiter
     */
    public void setIdCompteADebiter(Long idCompteADebiter) {
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
    public Long getIdCompteACrediter() {
        return idCompteACrediter;
    }

    /**
     * Set the value of idCompteACrediter
     *
     * @param idCompteACrediter new value of idCompteACrediter
     */
    public void setIdCompteACrediter(Long idCompteACrediter) {
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

    public String suppression() {

        gc.delete(this.compteBancaire);

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Suppression réussie ", "suppression OK");

        FacesContext.getCurrentInstance().addMessage(null, message);
        return "listeComptes";
    }

    public void suppress() {
        gc.delete(this.compteBancaire);
    }

    public void transferer() {

        try {
            gc.transferer(id1, id2, montantTransfert);
        } catch (Exception e) {
            message = "Transfert impossible, pas assez d'argent";
            System.out.println("### PAS ASSEZ d'argent ###");
        }
    }

    public String detailCompte(CompteBancaire compteBancaire) {
        this.compteBancaire = compteBancaire;
        return "historiqueCompte?faces-redirect=true";
    }

    public String operationBancaire(CompteBancaire compteBancaire) {
        this.compteBancaire = compteBancaire;
        return "operation?faces-redirect=true";
    }

    public List<String> autoCompletion(String query) {
        List<String> varAutoComp = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            varAutoComp.add(query + i);
        }
        return varAutoComp;
    }

}

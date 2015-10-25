/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import session.GestionnaireDeClient;

/**
 *
 * @author marwen
 */
@Named(value = "clientMBean")
@SessionScoped
public class ClientMBean implements Serializable {

    @EJB
    private GestionnaireDeClient clientManager;

    private String nom;
    private String prenom;
    private Date dateNaiss;
    private String adresse;
    private String telephone;
    private String mail;
    private float solde;

    public ClientMBean() {
    }

    public GestionnaireDeClient getClientManager() {
        return clientManager;
    }

    public void setClientManager(GestionnaireDeClient clientManager) {
        this.clientManager = clientManager;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateNaiss() {
        return dateNaiss;
    }

    public void setDateNaiss(Date dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public float getSolde() {
        return solde;
    }

    public void setSolde(float solde) {
        this.solde = solde;
    }

    public String creerUnClient() {
        clientManager.creerClient(nom, prenom, dateNaiss, adresse, telephone, mail, solde);

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Création réussie !", "Le client a été ajoutée.");

        FacesContext.getCurrentInstance().addMessage(null, message);
        return "listeComptes";

    }

}

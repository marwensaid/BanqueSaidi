/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.ClientAuthentification;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import session.GestionnaireDeClient;

/**
 *
 * @author marwen
 */
@ManagedBean(name = "ClientAuthenticationMBean")
@SessionScoped
public class ClientAuthentificationMBean implements Serializable {

    @EJB
    private GestionnaireDeClient gestionnaireDeClient;
    private String login;
    private String pwd;
    private boolean status;

    public ClientAuthentificationMBean() {
    }

    public GestionnaireDeClient getGestionnaireDeClient() {
        return gestionnaireDeClient;
    }

    public void setGestionnaireDeClient(GestionnaireDeClient gestionnaireDeClient) {
        this.gestionnaireDeClient = gestionnaireDeClient;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void connecte() {
        ClientAuthentification clientAuthentification = this.gestionnaireDeClient.connecter(this.login, this.pwd);
        if (clientAuthentification != null) {
            this.status = true;
            System.out.println("Bonjour " + this.login);
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("faces/index.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(CompteBancaireMBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Vérifier vos paramétre de connexion", ""));

    }

    public void seDeconnecter() {
        this.login = "";
        this.pwd = "";
        this.status = false;
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("faces/clientAuthentification.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(CompteBancaireMBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author marwen
 */
@Entity
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nom;
    private String prenom;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateNaissance;
    private String adresse;
    private String telephone;
    private String mail;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<CompteBancaire> listeComptesBancaire = new ArrayList();

    public Client() {
    }

    public Client(String nom, String prenom, List<CompteBancaire> listeComptesBancaire) {
        this.nom = nom;
        this.prenom = prenom;
        this.listeComptesBancaire = listeComptesBancaire;
    }

    public Client(String nom, String prenom, Date dateDeNaissance, String adresse, String telephone, String mail) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateDeNaissance;
        this.adresse = adresse;
        this.telephone = telephone;
        this.mail = mail;
    }

    public Client(String nom, String prenom, Date dateDeNaissance, String adresse, String telephone, String mail, List<CompteBancaire> listeComptesBancaire) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateDeNaissance;
        this.adresse = adresse;
        this.telephone = telephone;
        this.mail = mail;
        this.listeComptesBancaire = listeComptesBancaire;
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

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
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

    public List<CompteBancaire> getListeComptesBancaire() {
        return listeComptesBancaire;
    }

    public void setListeComptesBancaire(List<CompteBancaire> listeComptesBancaire) {
        this.listeComptesBancaire = listeComptesBancaire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Client[ id=" + id + " ]";
    }

    public Object getListeComptes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

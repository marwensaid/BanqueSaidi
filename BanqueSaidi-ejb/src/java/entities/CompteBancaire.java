/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author marwen
 */
@Entity
@Table(name = "CompteBancaire")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CompteBancaire.count", query = "SELECT count(c) FROM CompteBancaire c "),
    @NamedQuery(name = "CompteBancaire.findAll", query = "SELECT c FROM CompteBancaire c order by c.id ASC")

})
abstract public class CompteBancaire implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;
    protected double solde;
    protected String nom;
    protected String desciption;
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    protected Client client;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    protected List<OperationBancaire> listOperations = new ArrayList<>();

    public CompteBancaire() {
        client = new Client();
    }

    public CompteBancaire(String nom, double solde) {
        this.nom = nom;
        this.solde = solde;
        addOperation("creation compte", solde);

    }

    public List<OperationBancaire> getOperations() {
        return listOperations;
    }

    public void setOperations(List<OperationBancaire> operations) {
        this.listOperations = operations;
    }

    public void addOperationBancaire(OperationBancaire operationBancaire) {
        listOperations.add(operationBancaire);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
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
        if (!(object instanceof CompteBancaire)) {
            return false;
        }
        CompteBancaire other = (CompteBancaire) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.CompteBancaire[ id=" + id + " ]";
    }

    public double consultation() {
        return solde;
    }

    public void crediter(double montant) {
        solde += montant;
        addOperation("créditer", montant);
    }

    public void debiter(double montant) {
        if (solde < montant) {
            throw new EJBException();
        } else {
            solde -= montant;
        }
        addOperation("débiter", montant);
    }

    private void addOperation(String description, double montant) {
        OperationBancaire operationBancaire = new OperationBancaire(description, montant);
        listOperations.add(operationBancaire);
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<OperationBancaire> getListeOperations() {
        return listOperations;
    }

}

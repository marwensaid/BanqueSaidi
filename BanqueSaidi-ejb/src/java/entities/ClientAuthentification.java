/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author marwen
 */
@Entity
@Table(name="ClientAuthentification")

public class ClientAuthentification implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name="clientLogin")
    private String clientLogin;
    @Column(name="clientPwd")
    private String clientPwd;

    public ClientAuthentification() {
    }

    public ClientAuthentification(int id, String clientLogin, String clientPwd) {
        this.id = id;
        this.clientLogin = clientLogin;
        this.clientPwd = clientPwd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientLogin() {
        return clientLogin;
    }

    public void setClientLogin(String clientLogin) {
        this.clientLogin = clientLogin;
    }

    public String getClientPwd() {
        return clientPwd;
    }

    public void setClientPwd(String mdp) {
        this.clientPwd = mdp;
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
        if (!(object instanceof ClientAuthentification)) {
            return false;
        }
        ClientAuthentification other = (ClientAuthentification) object;
        if ((this.clientLogin == null && other.clientLogin != null) || (this.clientLogin != null && !this.clientLogin.equals(other.clientLogin))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.ClientAuthentification[ id=" + id + " ]";
    }
    
}

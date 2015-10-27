/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;

/**
 *
 * @author marwen
 */
@Singleton
@LocalBean
public class InitDB {

    @EJB
    private GestionnaireCompteBancaire gc;

    @PostConstruct
    public void InitDB() {
        System.out.println("#### BD REMPLIE ###");
        gc.creerBcpComptes();
        gc.genererBcpOperations();

    }
}
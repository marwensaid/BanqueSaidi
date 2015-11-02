/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.ScheduleExpression;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.TimerService;

/**
 *
 * @author marwen
 */
@Startup
@Singleton
@LocalBean
public class InitDB {

    @EJB
    private GestionnaireCompteBancaire gc;
    @Resource
    TimerService timerService;
    @PostConstruct
    public void InitDB() {
        System.out.println("#### Base,de donnée générée  ###");
       // gc.creerBcpComptes();
        gc.creerBcpComptesCourant();
        gc.creerBcpComptesEpargnes();
        //gc.genererBcpOperations();
        ScheduleExpression scheduleExp = new ScheduleExpression().second("*/20").minute("*").hour("*");
       this.timerService.createCalendarTimer(scheduleExp);
       System.out.println("###Création Timer terminée###");

    }
    @Timeout
    public void executerTraitement() {
        System.out.println("###Timer déclanché###");
        this.gc.appliquerTaux();
    }
}

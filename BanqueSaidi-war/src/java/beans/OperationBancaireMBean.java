/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.CompteBancaire;
import entities.OperationBancaire;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import session.GestionnaireCompteBancaire;
import session.GestionnaireOperations;


/**
 *
 * @author marwen
 */
@Named(value = "operationBancaireMBean")
@RequestScoped
public class OperationBancaireMBean implements Serializable{


    @EJB
    private GestionnaireOperations go;
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

    public GestionnaireOperations getGc() {
        return go;
    }

    public void setGc(GestionnaireOperations gc) {
        this.go = go;
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
        modele = new LazyDataModel<OperationBancaire>() {

            @Override
            public List load(int i, int i1, String string, SortOrder so, Map map) {
                List<OperationBancaire> operations = new ArrayList<OperationBancaire>();
                operations = go.getLazyOperations(i, i1);
                return operations;
            }

            @Override
            public int getRowCount() {
                return go.getNBOperations(); //To change body of generated methods, choose Tools | Templates.
            }

        };
        

    }

    public List<OperationBancaire> getOperationsBancaires() {
        return gc.getOperationsBancaires(idCompte);
    }
    
}

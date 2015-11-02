/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entities.OperationBancaire;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jimy zak
 */
@Stateless
@LocalBean
public class GestionnaireOperations {

    @PersistenceContext
    EntityManager em;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public List<OperationBancaire> getLazyOperations(int start, int nbComptes) {
        Query query = em.createNamedQuery("Operations.findAll");
        query.setFirstResult(start);
        query.setMaxResults(nbComptes);

        return query.getResultList();
    }
    public int getNBOperations() {
        Query query = em.createNamedQuery("Operations.count");
        return ((Long) query.getSingleResult()).intValue();
    }

}

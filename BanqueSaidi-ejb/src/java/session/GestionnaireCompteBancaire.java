/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entities.CompteBancaire;
import entities.OperationBancaire;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author marwen
 */
@Stateless
@LocalBean
public class GestionnaireCompteBancaire {

    @PersistenceContext
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public GestionnaireCompteBancaire() {

    }

    // Fait un insert d'un compte bancaire (entité) dans la base
    public void creerCompte(CompteBancaire c) {
        em.persist(c);
    }

    public CompteBancaire creerCompte(String nom, double solde) {
        CompteBancaire compte = new CompteBancaire(nom, solde) {
        };
        creerOperation(compte, "Création du compte", solde);
        persist(compte);

        return compte;
    }

    public List<CompteBancaire> findAll() {
        Query q = em.createQuery("select c from CompteBancaire c");
        return q.getResultList();
    }

    public void crediterUnCompte(int id, double montant) {
        // On va chercher un compte dans la base, il est connecté
        CompteBancaire c = em.find(CompteBancaire.class, id);
        // On update juste en faisant solde+=montant
        c.crediter(montant);
    }

    public void debiterUnCompte(int id, double montant) {
        // On va chercher un compte dans la base, il est connecté
        CompteBancaire c = em.find(CompteBancaire.class, id);
        // On update juste en faisant solde-=montant
        c.debiter(montant);
    }

    public void transferer(int id1, int id2, double montant) {
        debiterUnCompte(id1, montant);
        crediterUnCompte(id2, montant);
    }

    public List<OperationBancaire> getOperationsBancaires(int compteId) {
        CompteBancaire c = em.find(CompteBancaire.class, compteId);
        return c.getListeOperations();
    }

    public void persist(Object object) {
        em.persist(object);
    }

    private void creerOperation(CompteBancaire compte, String description, double montant) {
        OperationBancaire op = new OperationBancaire(description, montant);
        compte.getOperations().add(op);
    }

    public void creerComptesTest() {

        creerCompte("test1", 150020);
        creerCompte("test2", 950001);
        creerCompte("test3", 20030);
        creerCompte("test4", 100050);
    }

    public List<CompteBancaire> getLazyComptes(int start, int nbComptes) {
        Query query = em.createNamedQuery("CompteBanquaire.findAll");
        query.setFirstResult(start);
        query.setMaxResults(nbComptes);

        return query.getResultList();
    }

    public int getNBComptes() {
        Query query = em.createNamedQuery("CompteBanquaire.nbComptes");
        return ((Long) query.getSingleResult()).intValue();
    }

    /**
     *
     * @param idCompte
     * @return
     */
    public List<OperationBancaire> getOperationBancaire(int idCompte) {
        CompteBancaire c = em.find(CompteBancaire.class, idCompte);
        return c.getListeOperations();
    }

    public void delete(CompteBancaire compteBancaire) {
        em.remove(em.merge(compteBancaire));
    }
}

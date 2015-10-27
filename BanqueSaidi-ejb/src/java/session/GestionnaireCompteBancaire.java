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
    public static String[] TABNOM = {"Totti", "Totto", "Zidane", "Messi", "Ronaldo", "Saidi", "Jimy", "Deco", "Pow", "James", "Boom", "Miaou", "Than", "Low"};
    public static String[] TABPRENOMS = {"Halil", "Tural", "Youri", "Koman", "Peter", "Romac", "Lima", "Carlos", "Carles", "Rojo", "Momo", "Marwen", "Zak", "Zabaleta", "Rio",};

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

    public void crediterUnCompte(Long id, double montant) {
        // On va chercher un compte dans la base, il est connecté
        CompteBancaire c = em.find(CompteBancaire.class, id);
        // On update juste en faisant solde+=montant
        c.crediter(montant);
    }

    public void debiterUnCompte(Long id, double montant) {
        // On va chercher un compte dans la base, il est connecté
        CompteBancaire c = em.find(CompteBancaire.class, id);
        // On update juste en faisant solde-=montant
        c.debiter(montant);
    }

    public void transferer(long id1, Long id2, double montant) {
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

    void creerBcpComptes() {
        for (int i = 0; i < 2000; i++) {
            int indice1 = (int) (Math.random() * TABNOM.length - 1);
            int indice2 = (int) (Math.random() * TABPRENOMS.length - 1);
            this.creerCompte(new CompteBancaire(TABNOM[indice1] + " " + TABPRENOMS[indice2], (int) (Math.random() * 10000)) {
            });
        }
    }

    void genererBcpOperations() {
        int nbCompte = ((Long) this.em.createNamedQuery("CompteBancaire.count").getSingleResult()).intValue();
        System.out.println("### Opération de Test ###");
        for (int i = 0; i < 10000; i++) {
            if (i % 1000 == 0) {
                System.out.println(i + "opérations Ok");
            }
            int indice1 = (int) (Math.random() * nbCompte) + 1;
            int indice2 = indice1;
            //pour ne pas avoir le meme indice
            while (indice1 == indice2) {
                indice2 = (int) (Math.random() * nbCompte) + 1;
            }
            this.creerOperationsComptes(new Long(indice1), new Long(indice2));
        }
        System.out.println("### Opérations créées ###");
        System.out.println("### End of Operation ###");
    }

    private void creerOperationsComptes(Long id1, Long id2) {
        double rand = (int) (Math.random() * 1000 + 1);
        rand += ((int) (Math.random() * 2)) * 0.5;
        this.transferer(id1, id2, rand);
    }
}

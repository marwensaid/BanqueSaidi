/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import com.sun.corba.se.impl.orbutil.closure.Constant;
import entities.CompteBancaire;
import entities.CompteCourant;
import entities.CompteEpargne;
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
    private float taux = (float) 0.03;

    public GestionnaireCompteBancaire() {

    }

    public void creerCompte(CompteBancaire c) {
        em.persist(c);
    }

    public CompteBancaire creerCompteBancaire(String nom, double solde) {
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

        creerCompteBancaire("test1", 150020);
        creerCompteBancaire("test2", 950001);
        creerCompteBancaire("test3", 20030);
        creerCompteBancaire("test4", 100050);
    }

    public List<CompteBancaire> getLazyComptes(int start, int nbComptes) {
        Query query = em.createNamedQuery("CompteBancaire.findAll");
        query.setFirstResult(start);
        query.setMaxResults(nbComptes);

        return query.getResultList();
    }

    public int getNBComptes() {
        Query query = em.createNamedQuery("CompteBancaire.count");
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

    public void creerBcpComptes() {
        for (int i = 0; i < 2000; i++) {
            int indice1 = (int) (Math.random() * TABNOM.length - 1);
            int indice2 = (int) (Math.random() * TABPRENOMS.length - 1);
            this.creerCompte(new CompteBancaire(TABNOM[indice1] + " " + TABPRENOMS[indice2], (int) (Math.random() * 10000)) {
            });
        }
    }

    public void creerBcpComptesCourant() {
        for (int i = 0; i < 2000; i++) {
            int indice1 = (int) (Math.random() * TABNOM.length - 1);
            int indice2 = (int) (Math.random() * TABPRENOMS.length - 1);
            this.creerCompte(new CompteCourant(TABNOM[indice1] + " " + TABPRENOMS[indice2], (int) (Math.random() * 10000)));
        }
    }

    public void creerBcpComptesEpargnes() {
        for (int i = 0; i < 2000; i++) {
            int indice1 = (int) (Math.random() * TABNOM.length - 1);
            int indice2 = (int) (Math.random() * TABPRENOMS.length - 1);
            this.creerCompte(new CompteEpargne(TABNOM[indice1] + " " + TABPRENOMS[indice2], (int) (Math.random() * 10000), taux));
        }
    }

    public void genererBcpOperations() {
        int nbCompte = ((Long) this.em.createNamedQuery("CompteBancaire.count").getSingleResult()).intValue();
        System.out.println("### Opération de Test ###");
        for (int i = 0; i < 10000; i++) {
            if (i % 1000 == 0) {
                System.out.println(i + "opérations Ok");
            }
            int indice1 = (int) (Math.random() * nbCompte) + 1;
            int indice2 = indice1;
            while (indice1 == indice2) {
                indice2 = (int) (Math.random() * nbCompte) + 1;
            }
            this.creerOperationsComptes( indice1,  indice2);
        }
        System.out.println("### Opérations créées ###");
        System.out.println("### End of Operation ###");
    }

    public void creerOperationsComptes(int id1, int id2) {
        double rand = (int) (Math.random() * 1000 + 1);
        rand += ((int) (Math.random() * 2)) * 0.5;
        this.transferer(id1, id2, rand);
    }

    public List<CompteBancaire> getComptesTrie(String champ, String order, int depart, int nb) {

        String orderValue = "";

        if (order.equals("ASCENDING")) {
            orderValue = "ASC";
        } else {
            orderValue = "DESC";
        }
        String r = "";
        if (champ.equals("nom")) {
            r = "select c from CompteBancaire c order by c.nom "
                    + orderValue;
            System.out.println("Trier les comptes par nom: " + r);
        } else if (champ.equals("id")) {
            r = "select c from CompteBancaire c order by c.id "
                    + orderValue;
            System.out.println("Trier les comptes par Id: " + r);
        } else if (champ.equals("solde")) {
            r = "select c from CompteBancaire c order by c.solde "
                    + orderValue;
            System.out.println("Trier les comptes par solde: " + r);
        }

        Query q = em.createQuery(r);
        q.setFirstResult(depart);
        q.setMaxResults(nb);
        return q.getResultList();
    }

    public int getAllOperations(Long id) {
        String r = "select count(o) from CompteBancaire as c, in(c.operations) as o where c.id=" + id;
        System.out.println(r);
        Query q = this.em.createQuery(r);
        return ((Long) q.getSingleResult()).intValue();
    }
}

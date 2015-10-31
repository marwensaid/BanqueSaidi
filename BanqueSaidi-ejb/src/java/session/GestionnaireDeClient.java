/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entities.Client;
import entities.ClientAuthentification;
import entities.CompteBancaire;
import entities.OperationBancaire;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author marwen
 */
@Stateless
@LocalBean
public class GestionnaireDeClient {

    @PersistenceContext
    private EntityManager em;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

    @EJB
    private GestionnaireDeClient gestionnaireDeClient;

    public void persist(Object object) {
        em.persist(object);
    }

    public List<Client> getAllClient() {
        Query query = em.createQuery("SELECT c FROM Client c");
        return query.getResultList();
    }

    public void creerClient(String nom, String prenom, Date date, String adresse, String telephone, String mail, double solde) {
        Client client = new Client(nom, prenom, date, adresse, telephone, mail);

        CompteBancaire compte = new CompteBancaire(nom, solde) {
        };

        OperationBancaire operation = new OperationBancaire("Création du compte", solde);
        compte.getOperations().add(operation);

        compte.setClient(client);
        client.getListeComptesBancaire().add(compte);

        em.persist(client);
        em.persist(compte);
    }

    public void addClient(Client client) {
        em.persist(client);
    }

    public Client findClientByID(Long id) {
        Query query = em.createQuery("SELECT c FROM Client c WHERE c.id =: id");
        query.setParameter("id", id);
        return (Client) query.getSingleResult();
    }

    public Client findClientByName(String nom) {
        Query query = em.createQuery("SELECT c FROM client c WHERE c.nom =:nom");
        query.setParameter("nom", nom);
        try {
            return (Client) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException en) {
            List<Client> clients = (List<Client>) query.getResultList();

            return clients.get(0);
        }
    }

    /**
     * Creer des client de test
     *
     * @throws java.text.ParseException
     */
    public void creerClientTest() throws ParseException {
        creerClient("John", "Lennon", simpleDateFormat.parse("1940/10/09"), "Nice", "06200200", "john@beatles.com", 150000);
        creerClient("Paul", "Mac Cartney", simpleDateFormat.parse("1942/09/18"), "Paris", "07800800", "paul@beatles.com", 950000);
        creerClient("Ringo", "Starr", simpleDateFormat.parse("1940/07/07"), "Londre", "06333980", "ringo@beatles.com", 20000);
        creerClient("Georges", "Harrisson", simpleDateFormat.parse("1943/02/25"), "Paris", "07966098", "georges@beatles.com", 100000);

    }

    public ClientAuthentification connecter(String login, String pwd) {
        String client = "select u from ClientAuthentification c where c.ClientLogin='"+login+"' and c.ClientPwd='"+pwd+"'";
        Query query = this.em.createQuery(client);
        query.setMaxResults(1);
        if(query.getResultList().isEmpty()){
            return null;
        }
        ClientAuthentification clientResult = (ClientAuthentification) query.getSingleResult();
        return clientResult;
    }

}

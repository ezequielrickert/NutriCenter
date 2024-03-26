package org.example.service.client;

import org.example.model.Client;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.example.persistence.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


public class UserRepositoryImp implements UserRepository {

  EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UserPU");

  @Override
  public void createUser(String username, String email) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();

    Client client = new Client();
    client.setClientName(username);
    client.setClientEmail(email);

    entityManager.persist(client);

    entityManager.getTransaction().commit();
//    try (Session session = HibernateUtil.getSessionFactory().getCurrentSession();) {
//      transaction = session.beginTransaction();
//
//      Client client = new Client();
//      client.setClientName(username);
//      client.setClientEmail(email);
//
//      session.save(client);
//      transaction.commit();
//    } catch (Exception ex) {
//      if (transaction != null) {
//        transaction.rollback();
//      }
//      ex.printStackTrace();
//    }
  }

  @Override
  public Client readUser(Long clientId) {
    Transaction transaction = null;
    Client client = null;
//    try (Session session = new Configuration().configure().buildSessionFactory().openSession()) {
//      transaction = session.beginTransaction();
//
//      client = session.get(Client.class, clientId);
//
//      transaction.commit();
//    } catch (Exception ex) {
//      if (transaction != null) {
//        transaction.rollback();
//      }
//      ex.printStackTrace();
//    }
    return client;
  }

  @Override
  public void updateUser(Long clientId, String username, String email) {
    Transaction transaction = null;
//    try (Session session = new Configuration().configure().buildSessionFactory().openSession()) {
//      transaction = session.beginTransaction();
//
//      Client client = session.get(Client.class, clientId);
//      if (client != null) {
//        client.setClientName(username);
//        client.setClientEmail(email);
//      }
//
//      session.update(client);
//      transaction.commit();
//    } catch (Exception ex) {
//      if (transaction != null) {
//        transaction.rollback();
//      }
//      ex.printStackTrace();
//    }
  }

  @Override
  public void deleteUser(Long clientId) {
    Transaction transaction = null;
//    try (Session session = new Configuration().configure().buildSessionFactory().openSession()) {
//      transaction = session.beginTransaction();
//
//      Client client = session.get(Client.class, clientId);
//      if (client != null) {
//        session.delete(client);
//      }
//
//      transaction.commit();
//    } catch (Exception ex) {
//      if (transaction != null) {
//        transaction.rollback();
//      }
//      ex.printStackTrace();
//    }
  }
}

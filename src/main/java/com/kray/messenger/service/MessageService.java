package com.kray.messenger.service;

import com.kray.messenger.model.Message;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.kray.messenger.repositories.MessageRepository;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

@Singleton
public class MessageService {

    private EntityManager em;

    public MessageService() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.kray.messenger.PersistenceUnit");
        em = emf.createEntityManager();
    }

    public List<Message> getAllMessages() {
        em.getTransaction().begin();
        List<Message> messages = em.createQuery("FROM Message", Message.class).getResultList();
        em.getTransaction().commit();
        em.close();
        return messages;
    }

    public List<Message> getMessagesPaginated(int start, int size, int year) {
        em.getTransaction().begin();
        String hql = "FROM Message";
        Query query = em.createQuery(hql, Message.class);
        if (year > 0) {
            hql = "FROM Message m WHERE YEAR(m.creationDatetime) = :year";
            query = em.createQuery(hql, Message.class).setParameter("year", year);
        }
        query.setFirstResult(start);
        query.setMaxResults(size);
        List<Message> messages = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return messages;
    }

    public List<Message> getMessagesByYear(int year) {
        em.getTransaction().begin();
        String hql = "FROM Message m WHERE YEAR(m.creationDatetime) = :year";
        Query query = em.createQuery(hql, Message.class).setParameter("year", year);
        List<Message> messages = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return messages;
    }

    public Message getMessageById(Long id) {
        em.getTransaction().begin();
        Message message = em.find(Message.class, id);
        em.getTransaction().commit();
        em.close();
        return message;
    }

    public Message addMessage(Message message) {
        em.getTransaction().begin();
        message.setCreationDatetime(Calendar.getInstance().getTime());
        em.persist(message);
        Message entity = em.find(Message.class, message.getId());
        em.getTransaction().commit();
        em.close();
        return entity;
    }

    public Message updateMessage(Message message) {
        em.getTransaction().begin();
        em.merge(message);
        em.getTransaction().commit();
        em.close();
        return message;
    }

    public boolean deleteMessage(Long id) {
        boolean wasRemoved = false;

        em.getTransaction().begin();
        Message entity = em.find(Message.class, id);
        em.remove(entity);

        if (!em.contains(entity)) wasRemoved = true;

        em.getTransaction().commit();
        em.close();

        return wasRemoved;
    }

}

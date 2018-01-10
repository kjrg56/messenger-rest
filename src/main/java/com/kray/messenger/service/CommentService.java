package com.kray.messenger.service;

import com.kray.messenger.model.Comment;
import com.kray.messenger.model.Message;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.Calendar;
import java.util.List;

@Singleton
public class CommentService {

    private EntityManager em;

    public CommentService() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.kray.messenger.PersistenceUnit");
        em = emf.createEntityManager();
    }

    public Comment createComment(Comment comment, long messageId) {
        em.getTransaction().begin();
        Message parentEntity = em.find(Message.class, messageId);
        comment.setCreationDatetime(Calendar.getInstance().getTime());
        comment.setMessage(parentEntity);
        em.persist(comment);
        em.getTransaction().commit();
        em.close();
        return comment;
    }

    public List<Comment> getAllComments(long messageId) {
        em.getTransaction().begin();
        Query query = em.createQuery("FROM Comment c WHERE c.message.id = :messageId", Comment.class);
        query.setParameter("messageId", messageId);
        List<Comment> comments = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return comments;
    }

    public Comment updateComment(Comment comment, long messageId) {
        em.getTransaction().begin();
        Message parentEntity = em.find(Message.class, messageId);
        comment.setMessage(parentEntity);
        comment.setCreationDatetime(Calendar.getInstance().getTime());
        Comment entity = em.merge(comment);
        em.getTransaction().commit();
        em.close();
        return entity;
    }

    public boolean deleteComment(long commentId) {
        em.getTransaction().begin();
        Comment entity = em.find(Comment.class, commentId);
        em.remove(entity);
        if (em.contains(entity)) return false;
        em.getTransaction().commit();
        em.close();
        return true;
    }

}

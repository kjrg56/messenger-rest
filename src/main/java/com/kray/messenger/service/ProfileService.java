package com.kray.messenger.service;

import com.kray.messenger.model.Profile;
import com.kray.messenger.repositories.ProfileRepository;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Singleton
public class ProfileService {

    private EntityManager em;

    public ProfileService() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.kray.messenger.PersistenceUnit");
        em = emf.createEntityManager();
    }

    public List<Profile> getAllProfiles() {
        em.getTransaction().begin();
        List<Profile> profiles = em.createQuery("FROM Profile", Profile.class).getResultList();
        em.getTransaction().commit();
        em.close();
        return profiles;
    }

    public List<Profile> getProfilesByYear(int year) {
        em.getTransaction().begin();
        String hql = "FROM Profile e WHERE YEAR(e.creationDatetime) = :year";
        Query query = em.createQuery(hql, Profile.class).setParameter("year", year);
        List<Profile> profiles = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return profiles;
    }

    public List<Profile> getProfilesPaginated(int start, int size, int year) {
        em.getTransaction().begin();
        String hql = "FROM Profile";
        Query query = em.createQuery(hql, Profile.class);
        if (year > 0) {
            hql = "FROM Profile e WHERE YEAR(e.creationDatetime) = :year";
            query = em.createQuery(hql, Profile.class);
            query.setParameter("year", year);
        }
        query.setFirstResult(start);
        query.setMaxResults(size);
        List<Profile> profiles = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return profiles;
    }

    public Profile getProfileByUsername(String username) {
        em.getTransaction().begin();
        Profile entity = em.find(Profile.class, username);
        em.getTransaction().commit();
        em.close();
        return entity;
    }

    public Profile addProfile(Profile profile) {
        em.getTransaction().begin();
        profile.setCreationDatetime(Calendar.getInstance().getTime());
        em.persist(profile);
        Profile entity = em.find(Profile.class, profile.getUsername());
        em.getTransaction().commit();
        em.close();
        return entity;
    }

    public Profile updateProfile(Profile profile) {
        em.getTransaction().begin();
        profile.setCreationDatetime(Calendar.getInstance().getTime());
        em.merge(profile);
        em.getTransaction().commit();
        em.close();
        return profile;
    }

    public boolean deleteProfile(String username) {
        boolean wasRemoved = false;

        em.getTransaction().begin();
        Profile entity = em.find(Profile.class, username);
        em.remove(entity);

        if (!em.contains(entity)) wasRemoved = true;

        em.getTransaction().commit();
        em.close();

        return wasRemoved;
    }
}

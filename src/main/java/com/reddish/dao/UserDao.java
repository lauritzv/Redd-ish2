package com.reddish.dao;

import com.reddish.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;

public class UserDao {

    public static ReddishUser getUserbyUsername(EntityManager em, String username) {
        try {
            return em.createQuery("FROM ReddishUser WHERE username like :username", ReddishUser.class)
                    .setParameter("username", username).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }


    public static ReddishUser registerUser(EntityManager em, String username, String email, String password, String password2) {
        ReddishUser user = new ReddishUser(username, email, password, password2, 0L, new ArrayList<Comment>(), new ArrayList<Post>(), new ArrayList<Subreddit>());
        if (user.validate() == null) {
            persist(em, user);
            return user;
        }
        return null;
    }

    public static boolean subscribeUserTo(String subredditname, ReddishUser user, EntityManager em) {
        Subreddit sub = SubRedditDao.getReddit(em, subredditname);
        boolean subscribed = user.subscribe(sub);
        if (subscribed) {
            mergeUser(em, user);
        }
        return subscribed;
    }

    public static boolean unsubscribeUserFrom(String subredditname, ReddishUser user, EntityManager em) {
        Subreddit sub = SubRedditDao.getReddit(em, subredditname);
        boolean unsubscribed = user.unsubscribe(sub);
        if (unsubscribed) {
            mergeUser(em, user);
        }
        return unsubscribed;
    }

    public static void mergeUser(EntityManager em, ReddishUser user) {
        merge(em, user);
    }

    public static void persistVote(EntityManager em, VoteModel vote) {
        persist(em, vote);
    }

    private static void merge(EntityManager em, Object obj) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(obj);
        em.flush();
        transaction.commit();
    }

    private static void persist(EntityManager em, Object obj) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(obj);
        em.flush();
        transaction.commit();
    }
}

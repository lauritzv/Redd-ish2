package com.reddish.dao;

import com.reddish.model.Comment;
import com.reddish.model.Post;
import com.reddish.model.ReddishUser;
import com.reddish.model.Subreddit;

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
        if (username == null || username.isEmpty() || email == null || email.isEmpty()
                || password == null || password.isEmpty()
                || password2 == null || password2.isEmpty()
                || !password.equals(password2)) {
            return null;
        } else {
            ReddishUser user = new ReddishUser(username, email, password, password2, 0L, new ArrayList<Comment>(), new ArrayList<Post>(), new ArrayList<Subreddit>());
            mergeUser(em, user);
            return user;
        }
    }

    public static boolean subscribeUserTo(String subredditname, ReddishUser user, EntityManager em) {
       Subreddit sub = SubRedditDao.getReddit(em, subredditname);
       boolean subscribed = user.subscribe(sub);
       if(subscribed){
           mergeUser(em, user);
       }
       return subscribed;
    }

    public static boolean unsubscribeUserFrom(String subredditname, ReddishUser user, EntityManager em) {
        return false;
    }

    public static void mergeUser(EntityManager em, ReddishUser user){
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(user);
        em.flush();
        transaction.commit();
    }
}

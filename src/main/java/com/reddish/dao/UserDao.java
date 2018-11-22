package com.reddish.dao;

import com.reddish.model.Comment;
import com.reddish.model.Post;
import com.reddish.model.ReddishUser;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;

public class UserDao {

    public static ReddishUser getUserbyUsername(EntityManager em, String username) {
        try {
            return em.createQuery("FROM ReddishUser WHERE username like '" + username + "'", ReddishUser.class).getSingleResult();
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
            ReddishUser user = new ReddishUser(username, email, password, password2, 0L, new ArrayList<Comment>(), new ArrayList<Post>());
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(user);
            em.flush();
            transaction.commit();
            return user;
        }
    }
}

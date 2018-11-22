package com.reddish.dao;

import com.reddish.model.ReddishUser;

import javax.persistence.EntityManager;

public class UserDao {

    public static ReddishUser getUserbyUsername(EntityManager em, String username) {
        try {
            return em.createQuery("FROM ReddishUser WHERE username like '" + username + "'", ReddishUser.class).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }


    void registerUser(ReddishUser user) {

    }
}

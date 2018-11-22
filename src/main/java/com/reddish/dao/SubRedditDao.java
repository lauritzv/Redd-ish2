package com.reddish.dao;

import com.reddish.model.Subreddit;

import javax.persistence.EntityManager;

public class SubRedditDao {

    public static Subreddit getReddit(EntityManager em, String name){
        try {
            return em.createQuery("FROM Subreddit WHERE name like '" + name + "'", Subreddit.class).getSingleResult();
        } catch(Exception e){
            return null;
        }
    }
}

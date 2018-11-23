package com.reddish.dao;

import com.reddish.model.Post;
import com.reddish.model.Subreddit;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;

public class SubRedditDao {

    public static Subreddit getReddit(EntityManager em, String name){
        try {
            return em.createQuery("FROM Subreddit WHERE name like :name", Subreddit.class).
                    setParameter("name", name).getSingleResult();
        } catch(Exception e){
            return null;
        }
    }

    public static Subreddit createReddit(EntityManager em, String name){
        if(name == null || name.isEmpty())
            return null;
        if(getReddit(em, name) != null)
            return null;
        Subreddit subreddit = new Subreddit(name, null, new ArrayList<Post>());
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(subreddit);
        transaction.commit();
        return subreddit;
    }
}

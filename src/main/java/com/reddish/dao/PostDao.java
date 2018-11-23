package com.reddish.dao;

import com.reddish.model.Comment;
import com.reddish.model.Post;
import com.reddish.model.ReddishUser;
import com.reddish.model.Subreddit;
import freemarker.template.utility.HtmlEscape;
import org.apache.commons.text.StringEscapeUtils;
import org.w3c.dom.Entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class PostDao {

    public static void deletePost(EntityManager em, Post post){
        //Todo without breaking the database entries - I attempted and failed
    }

    public static void mergePost(EntityManager em, Post post)
    {
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.merge(post);
        em.flush();
        et.commit();
    }

    public static List<Post> getPosts(EntityManager em) {
        return em.createQuery("FROM Post", Post.class).getResultList();
    }

    public static Post findPost(EntityManager em, Long id) {
        return em.createQuery("FROM Post where id = :id", Post.class).setParameter("id", (int) (long) id).getSingleResult();
    }

    public static List<Post> getPosts(EntityManager em, String subreddit) {
        return em.createQuery("FROM Post where subreddit.name like :subreddit", Post.class)
                .setParameter("subreddit", subreddit).getResultList();
    }

    public static void postLink(EntityManager em, ReddishUser poster, String title, String link, Subreddit subreddit) {
        Post post = poster.addPost(System.currentTimeMillis(), 1L, title, link, subreddit, true, null);
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(post);
        em.merge(poster);
        em.flush();
        et.commit();
    }

    public static void postSelf(EntityManager em, ReddishUser poster, String title, String content, Subreddit subreddit) {
        Post post = poster.addPost(System.currentTimeMillis(), 1L, title, "", subreddit, false, content);
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(post);
        em.merge(poster);
        em.flush();
        et.commit();
        post.setLink("/r/" + post.getSubreddit().getName() + "/" + post.getId() + "/comments");
        et.begin();
        em.merge(post);
        em.flush();
        et.commit();
    }
}

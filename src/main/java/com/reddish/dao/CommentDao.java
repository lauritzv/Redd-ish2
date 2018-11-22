package com.reddish.dao;

import com.reddish.model.Comment;
import com.reddish.model.Post;
import com.reddish.model.ReddishUser;
import com.reddish.model.Subreddit;
import freemarker.template.utility.HtmlEscape;
import org.apache.commons.text.StringEscapeUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class CommentDao {

    public static List<Comment> getComments(EntityManager em, int postid) {
        return em.createQuery("FROM Comment where post = " + postid).getResultList();
    }

    public static void addComment(EntityManager em, String content, ReddishUser commenter, Post post) {
        Comment comment = new Comment(1L, System.currentTimeMillis(), content, commenter, post);
        post.addComment(comment);
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(comment);
        em.merge(post);
        em.flush();
        et.commit();
    }

}

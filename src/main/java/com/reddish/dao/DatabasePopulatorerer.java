package com.reddish.dao;

import com.reddish.model.Comment;
import com.reddish.model.Post;
import com.reddish.model.ReddishUser;
import com.reddish.model.Subreddit;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;

public class DatabasePopulatorerer {

    public void populate(EntityManager em) {
        if (!em.createQuery("FROM ReddishUser").getResultList().isEmpty()) {
            return;
        }
        List<ReddishUser> users = new ArrayList<>();
        users.add(zorrix());
        users.add(gumman());
        users.add(lauritz());
        users.add(fhqwhgads());
        Subreddit cats = createCats(users);
        Subreddit dank = createDank(users);
        Subreddit wavy = createWavy(users);
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.joinTransaction();
        em.persist(users.get(0));
        em.persist(users.get(1));
        em.persist(users.get(2));
        em.persist(users.get(3));
        em.persist(cats);
        em.persist(dank);
        em.persist(wavy);
        em.flush();
        et.commit();
    }

    private static Subreddit createCats(List<ReddishUser> users) {
        List<Post> posts = new ArrayList<Post>();
        Subreddit cats = new Subreddit("cats", new ArrayList<>(), posts);
        cats.setPosts(createCatPosts(users, cats));

        return cats;
    }

    private static Subreddit createDank(List<ReddishUser> users) {
        List<Post> posts = new ArrayList<Post>();
        Subreddit dank = new Subreddit("Dank", new ArrayList<>(), posts);
        dank.setPosts(createDankPosts(users, dank));

        return dank;
    }

    private static Subreddit createWavy(List<ReddishUser> users) {
        List<Post> posts = new ArrayList<Post>();
        Subreddit wavy = new Subreddit("Wavy", new ArrayList<>(), posts);
        wavy.setPosts(createWavyPosts(users, wavy));

        return wavy;
    }

    private static List<Post> createCatPosts(List<ReddishUser> users, Subreddit cats) {
        List<Post> catPosts = new ArrayList<>();
        catPosts.add(new Post(users.get(0),
                System.currentTimeMillis(), 15L,
                "sad cat", new ArrayList<Comment>(), "https://i.redd.it/e2q6mwjvryw11.jpg", cats, true, null));
        catPosts.add(new Post(users.get(3),
                System.currentTimeMillis(), -1L,
                "upvote money cat", new ArrayList<Comment>(), "http://i.imgur.com/xjaMIKB.png", cats, true, null));
        catPosts.get(1).addComment(new Comment(1L, System.currentTimeMillis() + 30000, "I clicked to upvote and broke my mouse, 0/10 meme", users.get(0), catPosts.get(1)));
        catPosts.add(new Post(users.get(2),
                System.currentTimeMillis(), 1L,
                "Cat.", new ArrayList<Comment>(), "https://i.redd.it/wsjlkvc0b5s11.jpg", cats, true, null));
        catPosts.add(new Post(users.get(1),
                System.currentTimeMillis(), 1L,
                "TIL That you can get dishonorably discharged from the Navy for boarding the wrong vessel just once.", new ArrayList<Comment>(), "", cats, false, "Whoops, wrong sub!"));
        catPosts.add(new Post(users.get(0),
                System.currentTimeMillis(), 1L,
                "kake of the year 2018", new ArrayList<Comment>(), "http://www.cakerush.co.uk/photos/cf018.jpg", cats, true, null));
        return catPosts;
    }

    private static List<Post> createDankPosts(List<ReddishUser> users, Subreddit dank) {
        List<Post> dankPosts = new ArrayList<>();
        dankPosts.add(new Post(users.get(2),
                System.currentTimeMillis(), 1L,
                "we will miss you robert", new ArrayList<Comment>(), "https://cdn.dopl3r.com/memes_files/before-after-in-only-2-weeks-we-lost-robert-JnEDB.jpg", dank, true, null));
        dankPosts.add(new Post(users.get(0),
                System.currentTimeMillis(), 1L,
                "rate my security", new ArrayList<Comment>(), "http://i.imgur.com/NSmFQgg.gif", dank, true, null));
        return dankPosts;
    }

    private static List<Post> createWavyPosts(List<ReddishUser> users, Subreddit wavy) {
        List<Post> wavyPosts = new ArrayList<>();
        wavyPosts.add(new Post(users.get(3),
                System.currentTimeMillis(), 1L,
                "~~~", new ArrayList<Comment>(), "https://i.imgur.com/buqoQ0Q.jpg", wavy, true, null));
        wavyPosts.add(new Post(users.get(1),
                System.currentTimeMillis(), 1L,
                "【ＧＥＯＲＧＥ】", new ArrayList<Comment>(), "http://vaporwave.co/wp-content/uploads/2016/04/vaporwave-seinfeld-windows95.jpg", wavy, true, null));
        return wavyPosts;
    }


    private static ReddishUser zorrix() {
        return new ReddishUser("Zorrix",
                "zorrix@fake.lol",
                "passord",
                "passord",
                69L,
                new ArrayList<Comment>(),
                new ArrayList<Post>(),
                new ArrayList<Subreddit>());
    }

    private static ReddishUser gumman() {
        return new ReddishUser("Gumman",
                "gumman@fake.lol",
                "passord",
                "passord",
                9001L,
                new ArrayList<Comment>(),
                new ArrayList<Post>(),
                new ArrayList<Subreddit>());
    }

    private static ReddishUser lauritz() {
        return new ReddishUser("lauritz",
                "lauritz@fake.lol",
                "passord",
                "passord",
                999L,
                new ArrayList<Comment>(),
                new ArrayList<Post>(),
                new ArrayList<Subreddit>());
    }

    private static ReddishUser fhqwhgads() {
        return new ReddishUser("fhqwhgads",
                "fhqwhgads@fake.lol",
                "passord",
                "passord",
                1337L,
                new ArrayList<Comment>(),
                new ArrayList<Post>(),
                new ArrayList<Subreddit>());
    }


}

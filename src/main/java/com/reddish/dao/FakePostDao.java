package com.reddish.dao;

import com.reddish.model.Post;
import com.reddish.model.ReddishUser;

import java.util.ArrayList;
import java.util.List;

public class FakePostDao {


    final static int NUMBER_OF_POSTS = 10;

    public static List<Post> getPosts(){
        List<Post> posts = new ArrayList<Post>();

        for(int i=0; i < NUMBER_OF_POSTS; i++)
        {
            //public ReddishUser(String username, String email, String password, String password2, Long karma,
            // List<Comment> comments, List<Post> posts) {
            ReddishUser poster = new ReddishUser("user"+i, "email.com", "", "", new Long(5), null, null, null);

            posts.add(new Post(
                    poster,
                    System.currentTimeMillis() - (long) Math.floor(Math.random() * 500000),
                    (long) Math.floor(Math.random() * 50000),
                    "post title " + i,
                    null,
                    "https://gumman.one/pics/gumman.jpg",
                    null,
                    (Math.random()>0.5),
                    "this is content"));
        }

        return posts;
    }
}

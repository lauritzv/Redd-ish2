package com.reddish.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Subreddit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "subreddit",cascade = CascadeType.ALL)
    private List<Post> posts;


    public Subreddit() {
    }

    public Subreddit(String name, List<ReddishUser> moderators, List<Post> posts) {
        this.name = name;
        this.posts = posts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }


}

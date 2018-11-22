package com.reddish.model;

import javax.persistence.*;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Long votes;
    private Long date;
    @Lob
    private String content;
    @ManyToOne
    private ReddishUser commenter;
    @ManyToOne(cascade = CascadeType.ALL)
    private Post post;

    public Comment() {
    }

    public Comment(Long votes, Long date, String content, ReddishUser commenter, Post post) {
        this.votes = votes;
        this.date = date;
        this.content = content;
        this.commenter = commenter;
        this.post = post;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getVotes() {
        return votes;
    }

    public void setVotes(Long votes) {
        this.votes = votes;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ReddishUser getCommenter() {
        return commenter;
    }

    public void setCommenter(ReddishUser commenter) {
        this.commenter = commenter;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}

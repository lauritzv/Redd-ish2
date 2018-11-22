package com.reddish.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private ReddishUser poster;
    private Long date;
    private Long votes;
    private String title;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "post",fetch = FetchType.EAGER)
    private List<Comment> comments;
    private String link;
    @ManyToOne(cascade = CascadeType.ALL)
    private Subreddit subreddit;
    private Boolean linkPost;
    @Lob
    private String content;

    public Post() {
    }

    public Post(ReddishUser poster, Long date, Long votes, String title, List<Comment> comments, String link, Subreddit subreddit, Boolean linkPost, String content) {
        this.poster = poster;
        this.date = date;
        this.votes = votes;
        this.title = title;
        this.comments = comments;
        this.link = link;
        this.subreddit = subreddit;
        this.linkPost = linkPost;
        this.content = content;

    }

    public synchronized void upvote()
    {
        this.votes++;
    }

    public void downvote()
    {
        this.votes--;
    }

    public void addComment (Comment comment) {
        comments.add(comment);
    }

    public int commentcount() {
        return comments.size();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ReddishUser getPoster() {
        return poster;
    }

    public void setPoster(ReddishUser poster) {
        this.poster = poster;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Long getVotes() {
        return votes;
    }

    public void setVotes(Long votes) {
        this.votes = votes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Subreddit getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(Subreddit subreddit) {
        this.subreddit = subreddit;
    }

    public Boolean getLinkPost() {
        return linkPost;
    }

    public void setLinkPost(Boolean linkPost) {
        this.linkPost = linkPost;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

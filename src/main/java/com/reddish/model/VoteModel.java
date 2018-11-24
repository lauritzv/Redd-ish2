package com.reddish.model;

import javax.persistence.*;

@Entity
public class VoteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int postId;
    @ManyToOne
    private ReddishUser voter;
    private boolean vote;

    public VoteModel(int postId, boolean vote, ReddishUser voter) {
        this.vote = vote;
        this.postId = postId;
        this.voter = voter;
    }

    public VoteModel(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isVote() {
        return vote;
    }

    public void setVote(boolean vote) {
        this.vote = vote;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public ReddishUser getVoter() {
        return voter;
    }

    public void setVoter(ReddishUser voter) {
        this.voter = voter;
    }
}

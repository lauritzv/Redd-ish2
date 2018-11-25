package com.reddish.REST;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.reddish.dao.PostDao;
import com.reddish.dao.UserDao;
import com.reddish.model.Post;
import com.reddish.model.ReddishUser;
import com.reddish.model.Vote;
import com.reddish.model.VoteModel;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;

import static com.reddish.util.ConstantsUtils.JSON_INAUTHENTICATED_MESSAGE;
import static com.reddish.util.ConstantsUtils.JSON_POST_DOES_NOT_EXIST_MESSAGE;
import static spark.Spark.get;
import static spark.Spark.put;


public class VoteRest {

    public static String REST_PATH = "/rest";
    public static String POST_PATH = REST_PATH + "/post";


    /**
     * setup vote rest, not set upvote rest
     */
    public static void setupVoteRest(SessionFactory sf) {

        put(POST_PATH + "/:postid", (req, res) -> {
            String body = req.body();
            Gson gson = new GsonBuilder().create();

            Vote vote = gson.fromJson(body, Vote.class);
            String username = vote.getUsername();

            if (username.equals("not_logged_in")) {
                return JSON_INAUTHENTICATED_MESSAGE;
            } else {
                EntityManager em = sf.createEntityManager();
                ReddishUser user = UserDao.getUserbyUsername(em, username);
                if (user == null || !user.getPassword().equals(vote.getPassword())) {
                    em.close();
                    return JSON_INAUTHENTICATED_MESSAGE;
                }
                Long post_id = null;
                try {
                    post_id = Long.parseLong(req.params("postid"));
                } catch (Exception e) {
                    em.close();
                    return JSON_POST_DOES_NOT_EXIST_MESSAGE;
                }

                Post post = PostDao.findPost(em, post_id);
                if (post == null) {
                    em.close();
                    return JSON_POST_DOES_NOT_EXIST_MESSAGE;
                }

                VoteModel voteModel = null;
                if (vote.getUp() && user.hasVoted(post, true)) {
                    post.upvote();
                    voteModel = user.vote(post, vote.getUp());
                } else if(!vote.getUp() && user.hasVoted(post, false)) {
                    post.downvote();
                    voteModel = user.vote(post, vote.getUp());
                }

                UserDao.persistVote(em, voteModel);
                UserDao.mergeUser(em, user);
                PostDao.mergePost(em, post);

                em.close();

                VoteReply voteReply = new VoteReply();
                voteReply.setPost_id(post.getId());
                voteReply.setVotes(post.getVotes());

                return gson.toJson(voteReply);
            }
        });

        get(POST_PATH + "/:postid", (req, res) -> {
            Long post_id = null;
            try {
                post_id = Long.parseLong(req.params("postid"));
            } catch (Exception e) {
                return JSON_POST_DOES_NOT_EXIST_MESSAGE;
            }

            EntityManager em = sf.createEntityManager();
            Post post = null;
            try {
                post = PostDao.findPost(em, post_id);
            } catch (Exception e) {
                em.close();
                return JSON_POST_DOES_NOT_EXIST_MESSAGE;
            }

            em.close();

            Gson gson = new GsonBuilder().create();
            return gson.toJson(post.getTitle());
        });
    }


    private static class VoteReply {

        private long post_id;
        private long votes;

        private VoteReply() {
        }

        public long getPost_id() {
            return post_id;
        }

        public void setPost_id(long post_id) {
            this.post_id = post_id;
        }

        public long getVotes() {
            return votes;
        }

        public void setVotes(long votes) {
            this.votes = votes;
        }
    }
}


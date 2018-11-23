package com.reddish;

import com.reddish.dao.*;
import com.reddish.model.HttpException;
import com.reddish.model.Post;
import com.reddish.model.ReddishUser;
import com.reddish.model.Subreddit;
import com.reddish.util.LoginUtil;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import spark.*;
import spark.template.freemarker.FreeMarkerEngine;
import sun.rmi.runtime.Log;


import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.reddish.REST.VoteRest.setupVoteRest;
import static com.reddish.util.ConstantsUtils.*;
import static spark.Spark.*;


public class App {

    private static boolean hasDatabase = false;

    public static void main(String[] args) {
        secure("src/main/resources/keystore.jks", "password", null, null);
        SessionFactory sf = new Configuration().configure().buildSessionFactory();

        if (!hasDatabase) {

            DatabasePopulatorerer db = new DatabasePopulatorerer();
            EntityManager emaids = sf.createEntityManager();
            db.populate(emaids);
            emaids.close();
            hasDatabase = true;
        }

        staticFileLocation(STATIC_FILE_LOCATION);

        get(FRONTPAGE_PATH, (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put(USER, req.attribute(USER));
            EntityManager emaids = sf.createEntityManager();
            List<Post> posts = PostDao.getPosts(emaids);
            emaids.close();
            posts.sort((p1, p2) -> {
                return (int) (p2.getVotes() - p1.getVotes());
            });
            model.put(POSTS_PARAM, posts);

            return new FreeMarkerEngine().render(
                    new ModelAndView(model, FRONTPAGE_VIEW)
            );
        });

        get(SUBREDDIT_PATH, (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            EntityManager emaids = sf.createEntityManager();
            ReddishUser user = LoginUtil.getAttachedUser(emaids, req.session());
            model.put(USER, user);
            String subredditname = req.params(SUBREDDIT_PARAM);
            model.put(SUBREDDIT_PARAM, subredditname);
            if (subredditname == null) {
                model.put(SUBREDDIT_PARAM, EMPTY_STRING);
            }
            if (SubRedditDao.getReddit(sf.createEntityManager(), subredditname) == null) {
                List<Subreddit> similarNamedSubreddishes = SubRedditDao.getAllReddits(emaids).stream().filter(sub -> sub.getName().equalsIgnoreCase(subredditname)).collect(Collectors.toList());
                model.put("similarnamedsubreddishes", similarNamedSubreddishes);
                emaids.close();
                return new FreeMarkerEngine().render(
                        new ModelAndView(model, SUBREDDIT_DOES_NOT_EXIST_VIEW)
                );
            }
            List<Post> posts = PostDao.getPosts(emaids, subredditname);
            posts.sort((p1, p2) -> {
                return (int) (p2.getVotes() - p1.getVotes());
            });
            model.put(POSTS_PARAM, posts);


            if (user != null) {
                if (user.getSubscriptions().stream().filter(sub -> sub.getName().equals(subredditname)).findAny().isPresent()) {
                    model.put("subscribed", true);
                }
            }
            String s = new FreeMarkerEngine().render(
                    new ModelAndView(model, FRONTPAGE_VIEW)
            );
            emaids.close();
            return s;
        });

        post(SUBSCRIBE_PATH, (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            EntityManager emaids = sf.createEntityManager();
            String subredditname = req.params(SUBREDDIT_PARAM);
            ReddishUser user = LoginUtil.getAttachedUser(emaids, req.session());


            //subscribe if you're subscribed, unsubscribe if not
            if (!user.getSubscriptions().stream().filter(sub -> sub.getName().equals(subredditname)).findAny().isPresent()) {
                UserDao.subscribeUserTo(subredditname, user, emaids);
            } else {
                UserDao.unsubscribeUserFrom(subredditname, user, emaids);
            }
            emaids.close();
            res.redirect("/r/" + subredditname);
            return "";
        });

        get("/test/:subreddit", (req, res) -> {
            EntityManager emaids = sf.createEntityManager();
            String subredditname = req.params(SUBREDDIT_PARAM);
            ReddishUser user = LoginUtil.getAttachedUser(emaids, req.session());

            ReddishUser freshuser = UserDao.getUserbyUsername(emaids, user.getUsername());
            freshuser.getSubscriptions();
            emaids.close();

            return user.getSubscriptions().stream().filter(sub -> sub.getName().equals(subredditname)).findAny().isPresent()
                    + " | " + freshuser.getSubscriptions().stream().filter(sub -> sub.getName().equals(subredditname)).findAny().isPresent();
        });

        get(LOGIN_PATH, (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new FreeMarkerEngine().render(
                    new ModelAndView(model, LOGIN_VIEW)
            );
        });

        post(LOGIN_PATH, (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Session session = req.session();
            EntityManager emaids = sf.createEntityManager();
            boolean loggedIn = LoginUtil.login(emaids, session, req.queryParams(USERNAME_PARAM), req.queryParams(PASSWORD_PARAM));
            emaids.close();
            if (loggedIn) {
                res.redirect(FRONTPAGE_PATH);
                halt();
            } else {
                res.redirect(LOGIN_PATH);
            }
            return new ModelAndView(model, LOGIN_VIEW);
        });

        get(REGISTER_PATH, (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put(USER, req.attribute(USER));
            return new FreeMarkerEngine().render(
                    new ModelAndView(model, REGISTER_VIEW)
            );
        });

        post(REGISTER_PATH, (req, res) -> {
            EntityManager emaids = sf.createEntityManager();
            String email = req.queryParams("email");
            String username = req.queryParams("username");
            String password = req.queryParams("password");
            String passwordrepeat = req.queryParams("passwordrepeat");
            ReddishUser user = UserDao.registerUser(emaids, username, email, password, passwordrepeat);
            if (user == null) {
                Map<String, Object> model = new HashMap<>();
                res.redirect(REGISTER_PATH);
            } else {
                LoginUtil.login(emaids, req.session(), username, password);
                res.redirect(FRONTPAGE_PATH);
            }
            emaids.close();
            return "";
        });

        get(REGISTER_SUBREDDIT_PATH, (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put(USER, req.attribute(USER));
            return new FreeMarkerEngine().render(
                    new ModelAndView(model, REGISTER_SUBREDDIT_VIEW)
            );
        });

        post(REGISTER_SUBREDDIT_PATH, (req, res) -> {
            EntityManager emaids = sf.createEntityManager();
            String name = req.queryParams("name");
            Subreddit subreddit = SubRedditDao.createReddit(emaids, name);
            emaids.close();
            if (subreddit == null)
                res.redirect(REGISTER_SUBREDDIT_PATH);
            else
                res.redirect("/r/" + name);
            return "";
        });

        get(POST_PATH, (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put(USER, req.attribute(USER));
            return new FreeMarkerEngine().render(
                    new ModelAndView(model, POST_VIEW));
        });

        post(POST_PATH, (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String title = req.queryParams("title");
            String subreddit = req.queryParams("subreddit");
            String type = req.queryParams("type");
            EntityManager emaids = sf.createEntityManager();
            Subreddit sub = SubRedditDao.getReddit(emaids, subreddit);
            if (type.equals("link")) {
                String link = req.queryParams("link");
                PostDao.postLink(emaids, ((ReddishUser) req.session().attribute(USER)), title, link, sub);
            } else {
                String content = req.queryParams("content");
                PostDao.postSelf(emaids, ((ReddishUser) req.session().attribute(USER)), title, content, sub);
            }
            emaids.close();
            res.redirect("/r/" + subreddit);
            return "";
        });

        get("/hello", (req, res) -> "hello world");

        get("/user", (req, res) -> {
            EntityManager emaids = sf.createEntityManager();
            List<ReddishUser> users = emaids.createQuery("FROM ReddishUser").getResultList();
            emaids.close();
            String s = "";
            for (ReddishUser u : users) {
                s = s + u.getUsername();
            }
            return s;
        });

        get("/user/:username", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put(USER, req.attribute(USER));
            EntityManager emaids = sf.createEntityManager();
            List<ReddishUser> users = emaids.createQuery("FROM ReddishUser").getResultList();
            emaids.close();

            Optional<ReddishUser> optionalUser = users.stream().filter(u -> u.getUsername().equals(req.params("username"))).findAny();
            if (!optionalUser.isPresent()) {
                throw new HttpException(404, NO_SUCH_USER);
            }

            model.put("pageuser", optionalUser.get());
            return new FreeMarkerEngine().render(
                    new ModelAndView(model, USER_VIEW)
            );
        });

        get("/logout", (req, res) -> {
            Map<String, Object> model = new HashMap<>();

            LoginUtil.logOut(req.session(true));

            return new FreeMarkerEngine().render(
                    new ModelAndView(model, LOGOUT_VIEW)
            );
        });

        get(COMMENTS_PATH, (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String subredditname = req.params(SUBREDDIT_PARAM);
            model.put(SUBREDDIT_PARAM, subredditname);
            if (subredditname == null) {
                model.put(SUBREDDIT_PARAM, EMPTY_STRING);
            }
            model.put(USER, req.attribute(USER));
            EntityManager emaids = sf.createEntityManager();
            Post post = PostDao.findPost(emaids, Long.parseLong((req.params(POSTID_PARAM))));
            emaids.close();
            model.put(POST_PARAM, post);
            return new FreeMarkerEngine().render(
                    new ModelAndView(model, COMMENTS_VIEW)
            );
        });

        post(COMMENTS_PATH, (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String content = req.queryParams("commentcontent");
            EntityManager emaids = sf.createEntityManager();
            CommentDao.addComment(emaids, content, (ReddishUser) req.session().attribute(USER), PostDao.findPost(emaids, Long.parseLong(req.params(POSTID_PARAM))));
            res.redirect("/r/" + req.params(SUBREDDIT_PARAM) + "/" + req.params(POSTID_PARAM) + "/comments");
            emaids.close();
            return new FreeMarkerEngine().render(
                    new ModelAndView(model, COMMENTS_VIEW));

        });

        post(DELETE_POST_PATH, (req, res) -> {
            EntityManager emaids = sf.createEntityManager();
            ReddishUser user = LoginUtil.getAttachedUser(emaids, req.session());
            Post post = PostDao.findPost(emaids, Long.parseLong(req.params(POSTID_PARAM)));
            /*if(user.getId() == post.getPoster().getId()){
                PostDao.deletePost(emaids, post);
            }*/


            emaids.close();

            //res.redirect("/user/" + post.getPoster().getUsername());

            return "deleting doesn't work yet";
        });

        before((request, response) -> {
            if (request.session(false) != null)
                request.attribute(USER, request.session(false).attribute(USER));
        });

        before(POST_PATH, (request, response) -> {
            boolean authenticated = LoginUtil.isLoggedIn(request.session());
            if (!authenticated) {
                throw new HttpException(401, NOT_PERMITTED);
            }
        });

        before(SUBSCRIBE_PATH, (request, response) -> {
            boolean authenticated = LoginUtil.isLoggedIn(request.session());
            if (!authenticated) {
                throw new HttpException(401, NOT_PERMITTED);
            }
        });

        exception(HttpException.class, (exception, request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put(EXCEPTION_ARGS, exception);
            notFound(new FreeMarkerEngine().render(
                    new ModelAndView(model, EXCEPTION_VIEW)));
        });

        setupVoteRest(sf);

    }
}

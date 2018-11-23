package com.reddish.util;

public class ConstantsUtils {

    public final static String USER = "user";
    public final static String FRONTPAGE_VIEW = "subreddit.ftl";
    public final static String FRONTPAGE_PATH = "/";
    public final static String LOGIN_VIEW = "login.ftl";
    public final static String LOGIN_PATH = "/login";
    public final static String SUBREDDIT_PATH = "/r/:subreddit";
    public final static String SUBSCRIBE_PATH = "/r/:subreddit/subscribe";
    public final static String SUBREDDIT_PARAM = "subreddit";
    public final static String REGISTER_VIEW = "register.ftl";
    public final static String REGISTER_PATH = "/register";
    public final static String POSTS_PARAM = "posts";
    public final static String EMPTY_STRING = "";
    public final static String STATIC_FILE_LOCATION = "/public";
    public final static String USERNAME_PARAM = "username";
    public final static String PASSWORD_PARAM = "password";

    public final static String COMMENTS_VIEW = "comments.ftl";
    public final static String COMMENTS_PATH = SUBREDDIT_PATH + "/:postid/comments";
    public final static String DELETE_POST_PATH = SUBREDDIT_PATH + "/:postid/delete";
    public final static String POST_PARAM = "post";
    public final static String POSTID_PARAM = "postid";
    public static final String REGISTER_SUBREDDIT_PATH = "registersubreddit";
    public static final String REGISTER_SUBREDDIT_VIEW = "registersubreddit.ftl";

    public static final String SUBREDDIT_DOES_NOT_EXIST_VIEW = "subreddit_does_not_exist.ftl";

    public static final String POST_VIEW = "post.ftl";
    public static final String POST_PATH = "/post";
    public static final String NO_SUCH_SUBREDDISH = "Subreddish does not exist";
    public static final String NO_SUCH_USER = "User does not exist";
    public static final String EXCEPTION_VIEW = "exception.ftl";
    public static final String EXCEPTION_ARGS = "exception" ;
    public static final String NOT_PERMITTED = "You need to be logged in to access this resource";

    public static final String USER_VIEW = "user.ftl";
    public static final String LOGOUT_VIEW = "logout.ftl";

    public static final String JSON_INAUTHENTICATED_MESSAGE = "{ \"error\": \"you must be logged in to vote\" }";
    public static final String JSON_POST_DOES_NOT_EXIST_MESSAGE = "{ \"error\": \"that post does not exist!\" }";
}

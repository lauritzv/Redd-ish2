package com.reddish.util;

import com.reddish.dao.UserDao;
import com.reddish.model.ReddishUser;
import spark.Session;

import javax.persistence.EntityManager;

public class LoginUtil {



    public static Boolean login(EntityManager em, Session session, String username, String password){
        ReddishUser user = UserDao.getUserbyUsername(em, username);
        if (user == null || !(user.getPassword().equals(password) || PasswordUtil.verifyPassword(password, user.getPassword())) )
            return false;
        session.attribute(ConstantsUtils.USER, user);
        return true;
    }

    public static void logOut(Session session){
        session.invalidate();
    }

    public static boolean isLoggedIn(Session session){
        Object obj = session.attribute(ConstantsUtils.USER);
        if(obj == null)
            return false;
        else
            return true;
    }

    public static ReddishUser getAttachedUser(EntityManager em, Session session){
        if(!isLoggedIn(session))
            return null;
        ReddishUser user = session.attribute(ConstantsUtils.USER);
        UserDao.mergeUser(em, user);
        return UserDao.getUserbyUsername(em, user.getUsername());
    }
}

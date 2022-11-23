package org.menegment.service;

import org.menegment.dao.UserDao;
import org.menegment.interfeses.SERVICE;
import org.menegment.models.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class UserService implements SERVICE<User> {


    private boolean isTrue;

    private UserDao userDao = new UserDao();

    private int i = 0;

    public boolean isEntity(User user) {
        isTrue = false;
        List<User> users = userDao.findAllEntity();
        if (users != null) {
            for (User u : users) {
                if (u.getLogin().equals(user.getLogin()) && u.getPassword().equals(user.getPassword())) {
                    isTrue = true;
                    return isTrue;
                }
            }
        }
        return isTrue;
    }

    public boolean isEntityToOne(User user) {
        isTrue = false;
        List<User> users = userDao.findAllEntity();
        if (users != null) {
            for (User u : users) {
                if (u.getLogin().equals(user.getLogin())) {
                    isTrue = true;
                    break;
                }
            }
        }
        return isTrue;
    }

    public User correctFindEntityString(User user) {
        User user1 = null;
        try {
            user1 = userDao.findEntity(user.getLogin());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user1;
    }

    public boolean correctSaveEntity(User user) {
        isTrue = false;
        try {
            isTrue = userDao.saveEntity(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTrue;
    }

    public ArrayList<User> correctAllEntity() {
        ArrayList<User> users = new ArrayList<>();
        try {
            users = (ArrayList<User>) userDao.findAllEntity();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean correctDeleteEntity(User user) {
        isTrue = false;
        try {
            isTrue = userDao.deleteEntityId(user.getUser_id());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTrue;
    }

    public boolean correctUpdateEntity(User user) {
        isTrue = false;
        try {
            isTrue = userDao.updateEntity(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTrue;
    }

    public boolean correctUpdateEntityString(User user) {
        isTrue = false;
        try {
            isTrue = userDao.updateEntityString(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTrue;
    }

    public User poisk(User user) {
        ArrayList<User> users;
        users = correctAllEntity();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getLogin().equals(user.getLogin())) {
                user.setUser_id(i);
                return user;
            }
        }
        return null;
    }

}


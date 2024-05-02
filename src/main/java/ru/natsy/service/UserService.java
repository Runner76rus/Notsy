package ru.natsy.service;

import ru.natsy.dao.UserDao;
import ru.natsy.model.User;
import ru.natsy.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserService {

    private final UserDao userDao = UserDao.getInstance();

    public User get(long id) {
        return userDao.findById(id);
    }

    public List<User> getAll() {
        return userDao.findAll();
    }

    public void add(User user) {
        userDao.save(user);
    }

    public boolean update(User user) {
        return userDao.update(user);
    }

    public boolean remove(long id) {
        return userDao.delete(id);
    }

}

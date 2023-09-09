package Dao.custom.impl;

import Dao.custom.UserDAO;
import entity.User;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {
    @Override
    public ArrayList<User> getAll(Session session) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(User entity, Session session) throws SQLException, ClassNotFoundException {
        session.save(entity);
        return true;
    }

    @Override
    public boolean update(User entity, Session session) throws SQLException, ClassNotFoundException {
        session.update(entity);
        return true;
    }

    @Override
    public boolean exits(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s, Session session) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewId(Session session) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public User search(String id, Session session) throws SQLException, ClassNotFoundException {
        try (session) {
            return session.get(User.class, id);
        }
    }
}

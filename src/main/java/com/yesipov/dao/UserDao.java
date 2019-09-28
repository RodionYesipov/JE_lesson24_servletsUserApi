package com.yesipov.dao;

import com.yesipov.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;


public class UserDao {
    private SessionFactory sessionFactory;

    public UserDao() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public User addUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        }
        return user;
    }

    public void updateUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        }
    }

    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("from User", User.class)
                    .list();
        }
    }

    public User getUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            User result = session
                    .createQuery("from User where id = :id", User.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return session
                    .createQuery("from User where id = :id", User.class)
                    .setParameter("id", id)
                    .getSingleResult();
        }
    }

    public String deleteUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(getUserById(id));
            transaction.commit();
        }
        return "user id = " + id + " deleted successfully";
    }

    public void close() {
        sessionFactory.close();
    }
}

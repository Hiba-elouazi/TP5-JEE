package dao;

import entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

public class UserDAOImpl implements UserDAO {

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            Query<User> query = session.createQuery(
                "FROM User u WHERE u.username = :username AND u.password = :password",
                User.class
            );
            query.setParameter("username", username);
            query.setParameter("password", password);
            return query.uniqueResult();
        } catch (Exception e) {
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public void addUser(User user) {
        Session session = null;
        Transaction tx  = null;
        try {
            session = HibernateUtil.getSession();
            tx      = session.beginTransaction();
            session.persist(user);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Erreur lors de l'ajout de l'utilisateur : " + e.getMessage(), e);
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            Query<Long> query = session.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.username = :username", Long.class
            );
            query.setParameter("username", username);
            Long count = query.uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            return false;
        } finally {
            if (session != null) session.close();
        }
    }
}
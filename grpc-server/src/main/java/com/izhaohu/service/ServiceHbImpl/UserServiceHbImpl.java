package com.izhaohu.service.ServiceHbImpl;

import com.izhaohu.db.HibernateUtils;
import com.izhaohu.model.User;
import com.izhaohu.service.UserService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserServiceHbImpl implements UserService {

    public int addUser(User user) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtils.newSession();
            tx = session.beginTransaction();

            session.save(user);
            int id = user.getId();
            tx.commit();
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();;
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return 0;
    }

    public int updateUser(User user, int id) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtils.newSession();
            tx = session.beginTransaction();
            User exists = session.get(User.class, id);
            exists.setAge(user.getAge());
            exists.setEmail(user.getEmail());
            session.update(exists);
            tx.commit();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return 0;
    }

    public int deleteUser(int id) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtils.newSession();
            tx = session.beginTransaction();
            User exists = new User();
            exists.setId(id);
            session.delete(exists);
            tx.commit();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return 0;
    }

    public User getUserById(int id) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtils.newSession();
            tx = session.beginTransaction();
            User exists = session.get(User.class,id );
            tx.commit();
            return exists;
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    public List<User> getUsers() {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtils.newSession();
            tx = session.beginTransaction();
            Query<User> query = session.createQuery("FROM USER", User.class);
            List<User> users = query.list();
            tx.commit();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }
}

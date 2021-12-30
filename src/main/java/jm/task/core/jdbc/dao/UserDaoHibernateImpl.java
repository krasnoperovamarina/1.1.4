package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }
    SessionFactory sessionFactory = Util.getSessionFactory();


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
        String sql = "CREATE TABLE IF NOT EXISTS users " +
                "(id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(20), lastName VARCHAR(20), " +
                "age INT)";
        session.createSQLQuery(sql).executeUpdate();
        transaction.commit();
        System.out.println("Таблица успешно создана");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS users";
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
            System.out.println("Таблица успешно удалена");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User us = new User(name, lastName, age);
            session.save(us);
            transaction.commit();
            System.out.println("Пользователь " + name + " успешно добавлен");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
            System.out.println("Пользователь " + id + " успешно удален");
    } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> usAll = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            usAll = session.createQuery("from User").getResultList();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usAll;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User ").executeUpdate();
            transaction.commit();
            System.out.println("Все записи из таблицы удалены");
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}

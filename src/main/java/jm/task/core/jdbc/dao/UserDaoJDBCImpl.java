package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static javax.swing.DropMode.INSERT;
import static org.hibernate.hql.internal.antlr.HqlTokenTypes.INTO;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    Connection connection = Util.getConnection();

    public void createUsersTable() {
        String table = "CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20), lastName VARCHAR(20), age INT)";
        try (PreparedStatement statement = connection.prepareStatement(table)) {
            statement.executeUpdate();
            System.out.println("Таблица успешно создана");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String table = "DROP TABLE IF EXISTS users";
        try (PreparedStatement statement = connection.prepareStatement(table)) {
            statement.executeUpdate();
            System.out.println("Таблица успешно удалена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastName, age) VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            System.out.println("Пользователь " + name + " успешно добавлен.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String delete = "DELETE FROM users WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(delete)) {
            preparedStatement.setInt(1, (int) id);
            preparedStatement.executeUpdate();
            System.out.println("Пользователь " + id + " успешно удален.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        String getAll = "SELECT * FROM users";
        List<User> allUsers = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(getAll); ResultSet rez = preparedStatement.executeQuery()) {
            while (rez.next()) {
                Long id = rez.getLong("id");
                String name = rez.getString("name");
                String lastName = rez.getString("lastName");
                byte age = rez.getByte("age");
                allUsers.add(new User(id, name, lastName, age));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        String cleanUsers = "TRUNCATE TABLE users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(cleanUsers)) {
            preparedStatement.executeUpdate();
            System.out.println("Все записи из таблицы удалены");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}


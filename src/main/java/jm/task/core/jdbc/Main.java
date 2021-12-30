package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        UserDaoHibernateImpl userService = new UserDaoHibernateImpl();
        userService.dropUsersTable();
        userService.createUsersTable();
        userService.saveUser("Artem", "Artemov", (byte) 15);
        userService.saveUser("Den", "Artemov", (byte) 36);
        userService.saveUser("Oleg", "Ivanov", (byte) 26);
        userService.saveUser("Yulia", "Mozer", (byte) 41);
        userService.removeUserById(1);
        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
    }
}

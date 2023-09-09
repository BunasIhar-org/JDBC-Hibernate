package jdbc;

import jdbc.model.*;
import jdbc.service.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Ivan", "Ivanov", (byte) 25);
        userService.saveUser("Petr", "Petrov", (byte) 35);
        userService.saveUser("Sidor", "Sidorov", (byte) 45);
        userService.saveUser("Dima", "Dmitriev", (byte) 15);


        List<User> list = userService.getAllUsers();
        list.forEach(System.out::println);

        userService.cleanUsersTable();

        userService.dropUsersTable();

    }
}

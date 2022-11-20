package kg.megacom.service.impl;

import kg.megacom.dao.UserRep;
import kg.megacom.models.User;
import kg.megacom.models.enums.UserStatus;
import kg.megacom.service.UserService;

import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

public class UserServiceImpl implements UserService {
    UserRep userRep = UserRep.INSTANCE;
    Scanner scanner = new Scanner(System.in);

    @Override
    public User createUser() {
        System.out.println("        Регистрация");
        System.out.print("Введите имя: ");
        String name = scanner.nextLine();
        System.out.print("Введите возраст: ");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.print("Введите описание: ");
        String definition = scanner.nextLine();
        System.out.print("Введите почту: ");
        String email = scanner.nextLine();
        User user = new User(name, age, definition, email, UserStatus.NEW);
        try {
            userRep.saveUser(user);
            System.out.println("Пользователь успешно сохранен!");
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось сохранить пользователя");
        }
        return user;
    }

    @Override
    public User userAuth() {
        System.out.println("        Авторизация");
        System.out.print("Введите почту: ");
        String email = scanner.nextLine();
        User user = findUserByEmail(email);
        if (user.getId() == 0){
            return userAuth();
        }
        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        User user = null;
        try {
            user = userRep.getUserByEmail(email);
        } catch (SQLException e) {
            throw new RuntimeException("Пользователь не найден");
        }
        return user;
    }

    @Override
    public List<User> getListOfUsersToRequest(User user) {
        List<User> users = null;
        try {
            users = userRep.listOfUsersWhomNotRequest(user);
        } catch (SQLException e) {
            throw new RuntimeException("Нет новых пользователей");
        }
        return users;
    }

    @Override
    public void changeUserStatusToActive(User user) {
        try {
            userRep.updateUserStatus(user);
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось обновить статус пользователя");
        }
    }


}

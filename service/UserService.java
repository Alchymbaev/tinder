package kg.megacom.service;

import kg.megacom.models.User;
import kg.megacom.service.impl.UserServiceImpl;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    UserService INSTANCE = new UserServiceImpl();

    User createUser();

    User userAuth();

    User findUserByEmail(String email);

    List<User> getListOfUsersToRequest(User user);

    void changeUserStatusToActive(User user);
}

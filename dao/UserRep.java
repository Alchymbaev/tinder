package kg.megacom.dao;

import kg.megacom.dao.impl.UserRepImpl;
import kg.megacom.models.User;

import java.sql.SQLException;
import java.util.List;

public interface UserRep {
    UserRep INSTANCE = new UserRepImpl();

    void saveUser(User user) throws SQLException;

    User getUserByEmail(String email) throws SQLException;

    User getUserById(int id) throws SQLException;

    List<User> listOfUsersWhomNotRequest(User user) throws SQLException;

    void updateUserStatus(User user) throws SQLException;
}

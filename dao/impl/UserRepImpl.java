package kg.megacom.dao.impl;

import kg.megacom.dao.DbHelperRep;
import kg.megacom.dao.UserRep;
import kg.megacom.models.User;
import kg.megacom.models.enums.UserStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepImpl implements UserRep {
    DbHelperRep dbHelperRep = DbHelperRep.INSTANCE;

    @Override
    public void saveUser(User user) throws SQLException {
        try (PreparedStatement preparedStatement = dbHelperRep.connect().prepareStatement("INSERT INTO users " +
                "(name, age, definition, email, status) VALUES (?, ?, ?, ?, ?)")){
            preparedStatement.setString(1, user.getName());
            preparedStatement.setInt(2, user.getAge());
            preparedStatement.setString(3, user.getDefinition());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, String.valueOf(user.getStatus()));

            preparedStatement.execute();
        }
    }

    @Override
    public User getUserByEmail(String email) throws SQLException {
        User user = new User();
        try (PreparedStatement preparedStatement = dbHelperRep.connect().prepareStatement("SELECT " +
                "id, name, age, definition, email, status from users where email = ?")) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setAge(resultSet.getInt("age"));
                user.setEmail(resultSet.getString("email"));
                user.setDefinition(resultSet.getString("definition"));
                user.setStatus(UserStatus.valueOf(resultSet.getString("status")));
            }
        }
        return user;
    }

    @Override
    public User getUserById(int id) throws SQLException {
        User user = new User();
        try (PreparedStatement preparedStatement = dbHelperRep.connect().prepareStatement("SELECT " +
                "id, name, age, definition, email, status from users where id = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setAge(resultSet.getInt("age"));
                user.setEmail(resultSet.getString("email"));
                user.setDefinition(resultSet.getString("definition"));
                user.setStatus(UserStatus.valueOf(resultSet.getString("status")));
            }
        }
        return user;
    }

    @Override
    public List<User> listOfUsersWhomNotRequest(User user) throws SQLException {
        List<User> users = new ArrayList<>();
        try(PreparedStatement preparedStatement = dbHelperRep.connect().prepareStatement("select * from users " +
                "where id <> ? and id not in (select recipient_id from orders where user_id = ?)")){
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                User usr = new User();
                usr.setId(resultSet.getInt("id"));
                usr.setName(resultSet.getString("name"));
                usr.setAge(resultSet.getInt("age"));
                usr.setEmail(resultSet.getString("email"));
                usr.setDefinition(resultSet.getString("definition"));
                usr.setStatus(UserStatus.valueOf(resultSet.getString("status")));
                users.add(usr);
            }
        }
        return users;
    }

    @Override
    public void updateUserStatus(User user) throws SQLException {
        try(PreparedStatement preparedStatement = dbHelperRep.connect().prepareStatement("update users set status = 'ACTIVE' where id = ?")){
            preparedStatement.setInt(1, user.getId());
            preparedStatement.execute();
        }
    }
}

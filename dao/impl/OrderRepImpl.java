package kg.megacom.dao.impl;

import com.sun.org.apache.xpath.internal.operations.Or;
import kg.megacom.dao.DbHelperRep;
import kg.megacom.dao.OrderRep;
import kg.megacom.dao.UserRep;
import kg.megacom.models.Order;
import kg.megacom.models.User;
import kg.megacom.models.enums.OrderStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderRepImpl implements OrderRep {
    DbHelperRep dbHelperRep = DbHelperRep.INSTANCE;
    UserRep userRep = UserRep.INSTANCE;

    @Override
    public void saveOrder(Order order) throws SQLException {
        try(PreparedStatement preparedStatement = dbHelperRep.connect().prepareStatement("INSERT INTO orders " +
                "(user_id, recipient_id, status, match, message) VALUES (?, ?, ?, ?, ?)")){
            preparedStatement.setInt(1, order.getUserId().getId());
            preparedStatement.setInt(2, order.getRecipientId().getId());
            preparedStatement.setString(3, String.valueOf(order.getStatus()));
            preparedStatement.setInt(4, 0);  // 0-noMatch, 1-isMatch
            preparedStatement.setString(5, order.getMessage());
            preparedStatement.execute();
        }
    }

    @Override
    public List<Order> selectAllUserRequests(User user) throws SQLException {
        List<Order> orders = new ArrayList<>();
        try(PreparedStatement preparedStatement = dbHelperRep.connect().prepareStatement("SELECT * FROM orders " +
                "where user_id = ?")) {
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Order order = new Order();
                boolean isMatch = false;
                if (resultSet.getInt("match") == 1){
                    isMatch = true;
                }
                order.setUserId(user);
                order.setRecipientId(userRep.getUserById(resultSet.getInt("recipient_id")));
                order.setStatus(OrderStatus.valueOf(resultSet.getString("status")));
                order.setMatch(isMatch);
                order.setMessage(resultSet.getString("message"));
                orders.add(order);
            }
        }
        return  orders;
    }

    @Override
    public Order selectOrderByUserAndRecipientUser(User user, User recipientUser) throws SQLException {
        Order order = new Order();
        try(PreparedStatement preparedStatement = dbHelperRep.connect().prepareStatement("SELECT * FROM orders where user_id = ? and recipient_id = ?")) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, recipientUser.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                boolean isMatch = false;
                if (resultSet.getInt("match") == 1){
                    isMatch = true;
                }
                order.setId(resultSet.getInt("id"));
                order.setUserId(userRep.getUserById(resultSet.getInt("user_id")));
                order.setRecipientId(userRep.getUserById(resultSet.getInt("recipient_id")));
                order.setStatus(OrderStatus.valueOf(resultSet.getString("status")));
                order.setMatch(isMatch);
                order.setMessage(resultSet.getString("message"));
            }
        }
        return order;
    }

    @Override
    public void updateMatch(Order order) throws SQLException {
        try(PreparedStatement preparedStatement = dbHelperRep.connect().prepareStatement("UPDATE orders SET match = 1 where id = ?")) {
            preparedStatement.setInt(1, order.getId());
            preparedStatement.execute();
        }
    }
}

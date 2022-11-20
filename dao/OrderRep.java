package kg.megacom.dao;

import kg.megacom.dao.impl.OrderRepImpl;
import kg.megacom.models.Order;
import kg.megacom.models.User;

import java.sql.SQLException;
import java.util.List;

public interface OrderRep {
    OrderRep INSTANCE = new OrderRepImpl();

    void saveOrder(Order order) throws SQLException;

    List<Order> selectAllUserRequests(User user) throws SQLException;

    Order selectOrderByUserAndRecipientUser(User user, User recipientUser) throws SQLException;

    void updateMatch(Order order) throws SQLException;
}

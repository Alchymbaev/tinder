package kg.megacom.service;

import kg.megacom.models.Order;
import kg.megacom.models.User;
import kg.megacom.service.impl.OrderServiceImpl;

import java.util.List;

public interface OrderService {
    OrderService INSTANCE = new OrderServiceImpl();

    Order createOrder(User user);

    List<Order> mySentRequests(User user);

    Order checkOrders(User user, User recipientUser);

    void updateOrder(Order order);
}

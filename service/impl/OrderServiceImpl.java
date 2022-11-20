package kg.megacom.service.impl;

import com.sun.org.apache.xpath.internal.operations.Or;
import kg.megacom.dao.OrderRep;
import kg.megacom.models.Order;
import kg.megacom.models.User;
import kg.megacom.models.enums.OrderStatus;
import kg.megacom.service.OrderService;
import kg.megacom.service.UserService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderServiceImpl implements OrderService {
    UserService userService = UserService.INSTANCE;
    OrderRep orderRep = OrderRep.INSTANCE;
    Scanner scanner = new Scanner(System.in);
    @Override
    public Order createOrder(User user) {
        Order order = new Order();
        User curUser = userService.findUserByEmail(user.getEmail());
        List<User> users = userService.getListOfUsersToRequest(curUser);
        AtomicInteger counter = new AtomicInteger(1);
        int scannedNum;
        System.out.println("Выберите пользователя из списка: ");
        users.forEach(x -> {
            System.out.print(counter.get());
            System.out.println(": Имя - " + x.getName() + ", Возраст - " + x.getAge() + ", Описание - " + x.getDefinition());
            counter.getAndIncrement();
        });
        System.out.print("Пользователь: ");
        scannedNum = Integer.parseInt(scanner.nextLine());
        User recipientUser = users.get(scannedNum - 1);
        System.out.print("Ваше сообщение: ");
        String message = scanner.nextLine();

        order.setUserId(user);
        order.setRecipientId(recipientUser);
        order.setStatus(OrderStatus.EXPECT_AN_ANSWER);
        order.setMatch(false);
        order.setMessage(message);
        try {
            orderRep.saveOrder(order);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return order;
    }

    @Override
    public List<Order> mySentRequests(User user) {
        List<Order> myOrders = null;
        try {
            myOrders = orderRep.selectAllUserRequests(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return myOrders;
    }

    @Override
    public Order checkOrders(User user, User recipientUser) {
        Order order;
        try {
            order = orderRep.selectOrderByUserAndRecipientUser(user, recipientUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  order;
    }

    @Override
    public void updateOrder(Order order) {
        try {
            orderRep.updateMatch(order);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}

package kg.megacom.service.impl;

import com.sun.org.apache.xpath.internal.operations.Or;
import kg.megacom.models.Order;
import kg.megacom.models.User;
import kg.megacom.service.OrderService;
import kg.megacom.service.UserService;

import java.sql.SQLOutput;
import java.util.Scanner;

public class Operation {
    Scanner scanner = new Scanner(System.in);
    User user = new User();
    Order order = new Order();
    Order recipientOrder = new Order();
    UserService userService = UserService.INSTANCE;
    OrderService orderService = OrderService.INSTANCE;
    public void firstStep(){
        System.out.println("    Добро пожаловать в Tinder!!!");
        System.out.println("Выберите категорию: 1-регистрация, 2-авторизация ");
        System.out.print("Категория: ");
        int value = Integer.valueOf(scanner.nextLine());
        if (value == 1){
            user = userService.createUser();
        }
        else if (value == 2) {
            user = userService.userAuth();
            userService.changeUserStatusToActive(user);
        }
        else {
            System.out.println("Попробуйте снова");
            firstStep();
        }
    }

    public void secondStep(){
        System.out.println("    Выберите категорию: 1-отправить запрос, 2-мои отправленные запросы");
        System.out.print("Категория: ");
        int value = Integer.valueOf(scanner.nextLine());
        if (value == 1){
            order = orderService.createOrder(user);
            thirdStep();
        } else if (value == 2) {
            orderService.mySentRequests(user).forEach(x -> {
                System.out.println("Пользователь: " + x.getRecipientId().getName() +
                        " , Статус:" + x.getStatus() + " , письмо: " + x.getMessage());
            });
        } else {
            System.out.println("Попробуйте снова");
            secondStep();
        }
    }

    public void thirdStep(){
        recipientOrder = orderService.checkOrders(order.getRecipientId(), user);
        if (recipientOrder.getId() == 0){
            System.out.println("Ваша запрос успешно отправлен!");
        } else {
            System.out.println("У вас взаимная симпатия!!!");
            System.out.println("Сообщение от " + recipientOrder.getUserId().getName() + ": " + recipientOrder.getMessage());
            orderService.updateOrder(recipientOrder);
            orderService.updateOrder(orderService.checkOrders(order.getUserId(), order.getRecipientId()));
        }
    }
}

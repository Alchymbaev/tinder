package kg.megacom.models;

import kg.megacom.models.enums.OrderStatus;

public class Order {
    private int id;
    private User userId;
    private User recipientId;
    private OrderStatus status;
    private boolean match;
    private String message;

    public Order(int id, User userId, User recipientId, OrderStatus status, boolean match, String message) {
        this.id = id;
        this.userId = userId;
        this.recipientId = recipientId;
        this.status = status;
        this.match = match;
        this.message = message;
    }

    public Order(User userId, User recipientId, OrderStatus status, boolean match, String message) {
        this.userId = userId;
        this.recipientId = recipientId;
        this.status = status;
        this.match = match;
        this.message = message;
    }

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public User getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(User recipientId) {
        this.recipientId = recipientId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public boolean isMatch() {
        return match;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", recipientId=" + recipientId +
                ", status=" + status +
                ", match=" + match +
                ", message='" + message + '\'' +
                '}';
    }
}

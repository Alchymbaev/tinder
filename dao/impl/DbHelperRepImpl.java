package kg.megacom.dao.impl;

import kg.megacom.dao.DbHelperRep;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbHelperRepImpl implements DbHelperRep {
    private final String url = "jdbc:postgresql://localhost/tinder_db";
    private final String user = "postgres";
    private final String password = "uhyhuqape";

    @Override
    public Connection connect() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось подключиться к базе");
        }
        return connection;
    }
}

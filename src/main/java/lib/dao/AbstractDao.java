package lib.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractDao<T> implements CommonDao<T> {
    protected Connection connection;

    public AbstractDao() {
        String url = "jdbc:mysql://localhost:3306/my_library";
        String login = "root";
        String pass = "root";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, login, pass);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected Connection getConnection() {
        return connection;
    }
}

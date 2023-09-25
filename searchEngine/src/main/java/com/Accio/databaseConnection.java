package com.Accio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public  class databaseConnection {
    static Connection connection = null;//Static Connection Object: You have a static Connection object named connection, which will store the database connection once it's established. This ensures that you reuse the same connection throughout the application rather than creating a new one each time.

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        }
        String db = "searchengineapp";
        String user = "root";
        String pwd = "root";
        return getConnection(db, user, pwd);
    }

    //getConnection() Method: This is a public method that returns a database connection. It first checks if the connection object is not null (i.e., if a connection has already been established). If it's not null, it returns the existing connection. If it's null, it calls the private getConnection(String db, String user, String pwd) method to create a new database connection.
    private static Connection getConnection(String db, String user, String pwd) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/" + db + "?user=" + user + "&password=" + pwd);
        } catch (SQLException | ClassNotFoundException sqlException) {
            sqlException.printStackTrace();
        }
        return connection;
    }
}
    //getConnection(String db, String user, String pwd) Method: This is a private method that creates and returns a new database connection using the provided database name (db), username (user), and password (pwd). It loads the MySQL JDBC driver and uses the DriverManager to establish the connection.
    //Your updated code is valid and effectively handles both SQLException and ClassNotFoundException exceptions in a single catch block. This is known as a multi-catch block and is a feature introduced in Java 7 to simplify exception handling


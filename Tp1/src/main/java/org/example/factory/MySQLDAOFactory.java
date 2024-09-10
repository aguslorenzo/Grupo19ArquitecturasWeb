package org.example.factory;


import org.example.dao.MySQLClienteDAO;
import org.example.dao.MySQLFacturaDAO;
import org.example.dao.MySQLProductoDAO;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDAOFactory extends AbstractFactory{
    private static MySQLDAOFactory instance = null;

    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String uri = "jdbc:mysql://localhost:3306/db-tpe";
    public static Connection conn;

    private MySQLDAOFactory() {
    }

    public static synchronized MySQLDAOFactory getInstance() {
        if (instance == null) {
            instance = new MySQLDAOFactory();
        }
        return instance;
    }

    public static Connection createConnection() {
        if (conn != null) {
            return conn;
        }
        String driver = DRIVER;
        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                 | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            conn = DriverManager.getConnection(uri, "usuario", "password");
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    @Override
    public MySQLClienteDAO getClienteDAO() {
        return new MySQLClienteDAO(createConnection());
    }

    @Override
    public MySQLFacturaDAO getFacturaDAO() {
        return new MySQLFacturaDAO(createConnection());
    }

    @Override
    public MySQLProductoDAO getProductoDAO() {
        return new MySQLProductoDAO(createConnection());
    }


}

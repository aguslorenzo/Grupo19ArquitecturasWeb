package arquitectura.grupo19.factory;


import arquitectura.grupo19.dao.FacturaDAOImpl;
import arquitectura.grupo19.dao.ProductoDAOMySQL;
import arquitectura.grupo19.dao.ClienteDAOImpl;
import arquitectura.grupo19.dao.ProductoDAOImpl;

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
    public ClienteDAOImpl getClienteDAO() {
        return new ClienteDAOImpl(createConnection());
    }

    @Override
    public FacturaDAOImpl getFacturaDAO() {
        return new FacturaDAOImpl(createConnection());
    }

    @Override
    public ProductoDAOImpl getProductoDAO() {
        return new ProductoDAOMySQL(createConnection());
    }


}

package arquitectura.grupo19.factory;

import arquitectura.grupo19.dao.FacturaDAOImpl;
import arquitectura.grupo19.dao.ProductoDAOImplDerby;
import arquitectura.grupo19.dao.ClienteDAOImpl;
import arquitectura.grupo19.dao.ProductoDAOImplSQL;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DerbyDAOFactory extends AbstractFactory{

    private static DerbyDAOFactory instance = null;

    public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    public static final String URI = "jdbc:derby:MyDerbyDb; create=true";
    public static Connection conn;

    public static synchronized DerbyDAOFactory getInstance(){ //DATO patron singleton
        if(instance == null){
            instance = new DerbyDAOFactory();
        }
        return instance;
    }

    public static Connection createConnection(){
        if (conn != null) {
            return conn;
        }
        try {
            Class.forName(DRIVER).getDeclaredConstructor().newInstance();
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        try {
            conn = DriverManager.getConnection(URI);
            /*createTables(conn);
            conn.close();*/
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
    public ProductoDAOImplSQL getProductoDAO() {
        return new ProductoDAOImplDerby(createConnection());
    }
}

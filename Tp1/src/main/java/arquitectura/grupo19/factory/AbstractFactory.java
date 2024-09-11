package arquitectura.grupo19.factory;

import arquitectura.grupo19.dao.FacturaDAOImpl;
import arquitectura.grupo19.dao.ClienteDAOImpl;
import arquitectura.grupo19.dao.ProductoDAOImpl;

public abstract class AbstractFactory {

    public static final int MYSQL_JDBC = 1;
    public static final int DERBY_JDBC = 2;

    public abstract ClienteDAOImpl getClienteDAO();
    public abstract FacturaDAOImpl getFacturaDAO();
    public abstract ProductoDAOImpl getProductoDAO();

    public static AbstractFactory getDAOFactory(int whichFactory) {
        switch (whichFactory) {
            case MYSQL_JDBC: {
                return MySQLDAOFactory.getInstance();
            }
            case DERBY_JDBC:
                return DerbyDAOFactory.getInstance();
            default:
                return null;
        }
    }
}

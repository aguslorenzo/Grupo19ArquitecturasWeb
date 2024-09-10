package org.example.factory;

import org.example.dao.MySQLClienteDAO;
import org.example.dao.MySQLFacturaDAO;
import org.example.dao.MySQLProductoDAO;

public abstract class AbstractFactory {

    public static final int MYSQL_JDBC = 1;
    public static final int DERBY_JDBC = 2;

    public abstract MySQLClienteDAO getClienteDAO();
    public abstract MySQLFacturaDAO getFacturaDAO();
    public abstract MySQLProductoDAO getProductoDAO();

    public static AbstractFactory getDAOFactory(int whichFactory) {
        switch (whichFactory) {
            case MYSQL_JDBC: {
                return MySQLDAOFactory.getInstance();
            }
            /*case DERBY_JDBC:
                return DerbyDaoFactory.getInstance();*/
            default:
                return null;
        }
    }
}

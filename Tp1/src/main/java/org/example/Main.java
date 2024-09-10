package org.example;

import org.example.dao.ProductoDAO;
import org.example.factory.MySQLDAOFactory;
import org.example.utils.HelperMySQL;

public class Main {
    public static void main(String[] args) throws Exception{
        HelperMySQL dbMySQL = new HelperMySQL();
        //dbMySQL.dropTables();
        //dbMySQL.createTables();
        //dbMySQL.populateDB();
        ProductoDAO productoDAO = new ProductoDAO(MySQLDAOFactory.createConnection());
        System.out.println(productoDAO.obtenerProductoMasRecaudado());
    }
}
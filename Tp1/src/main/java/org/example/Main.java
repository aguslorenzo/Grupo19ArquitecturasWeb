package org.example;

import org.example.dao.MySQLProductoDAO;
import org.example.factory.AbstractFactory;
import org.example.factory.MySQLDAOFactory;
import org.example.utils.HelperMySQL;
import org.example.dao.ProductoDAO;

public class Main {
    public static void main(String[] args) throws Exception{
        HelperMySQL dbMySQL = new HelperMySQL();
        dbMySQL.dropTables();
        dbMySQL.createTables();
        dbMySQL.populateDB();

        AbstractFactory factoryMysql = AbstractFactory.getDAOFactory(1); //MySQL
        MySQLProductoDAO product = factoryMysql.getProductoDAO();
        System.out.println(product.obtenerProductoMasRecaudado());

    }
}
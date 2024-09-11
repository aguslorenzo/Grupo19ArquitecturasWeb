package org.example;

import org.example.dao.ClienteDAO;
import org.example.dao.ProductoDAO;
import org.example.dao.ProductoDAOImpl;
import org.example.dao.ProductoDAOMySQL;
import org.example.entities.Cliente;
import org.example.factory.AbstractFactory;
import org.example.utils.HelperDerby;
import org.example.utils.HelperMySQL;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception{
        HelperMySQL dbMySQL = new HelperMySQL();
        dbMySQL.dropTables();
        dbMySQL.createTables();
        dbMySQL.populateDB();

        AbstractFactory factoryMysql = AbstractFactory.getDAOFactory(1); //MySQL
        ProductoDAO product = factoryMysql.getProductoDAO();
        System.out.println(product.obtenerProductoMasRecaudado());

        ClienteDAO clienteMySql = factoryMysql.getClienteDAO();
        List<Cliente> clientesMySql = clienteMySql.obtenerFacturacionClientes();
        for(Cliente c: clientesMySql){
            System.out.println(c);
        }

        HelperDerby dbDerby = new HelperDerby();
        dbDerby.dropTables();
        dbDerby.createTables();
        dbDerby.populateDB();

        System.out.println("----------------inicio derby-----------------");

        AbstractFactory factoryDerby = AbstractFactory.getDAOFactory(2); //Derby
        ProductoDAOImpl productDerby = factoryDerby.getProductoDAO();
        System.out.println(productDerby.obtenerProductoMasRecaudado());

        ClienteDAO clienteDerby = factoryDerby.getClienteDAO();
        List<Cliente> clientesDerby = clienteDerby.obtenerFacturacionClientes();
        for(Cliente c: clientesDerby){
            System.out.println(c);
        }
    }
}
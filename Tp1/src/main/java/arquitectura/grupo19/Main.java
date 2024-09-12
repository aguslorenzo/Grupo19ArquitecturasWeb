package arquitectura.grupo19;

import arquitectura.grupo19.dao.ProductoDAO;
import arquitectura.grupo19.dao.ClienteDAO;
import arquitectura.grupo19.dao.ProductoDAOImplSQL;
import arquitectura.grupo19.entities.Cliente;
import arquitectura.grupo19.factory.AbstractFactory;
import arquitectura.grupo19.utils.HelperDerby;
import arquitectura.grupo19.utils.HelperMySQL;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception{

        System.out.println("----------------inicio mysql-----------------");

        HelperMySQL dbMySQL = new HelperMySQL();
        dbMySQL.dropTables();
        dbMySQL.createTables();
        dbMySQL.populateDB();
        dbMySQL.closeConnection();

        AbstractFactory factoryMysql = AbstractFactory.getDAOFactory(1); //MySQL
        ProductoDAO product = factoryMysql.getProductoDAO();
        System.out.println(product.obtenerProductoMasRecaudado());

        ClienteDAO clienteMySql = factoryMysql.getClienteDAO();
        List<Cliente> clientesMySql = clienteMySql.obtenerFacturacionClientes();
        for(Cliente c: clientesMySql){
            System.out.println(c);
        }

        System.out.println("----------------inicio derby-----------------");

        HelperDerby dbDerby = new HelperDerby();
        dbDerby.dropTables();
        dbDerby.createTables();
        dbDerby.populateDB();
        dbDerby.closeConnection();

        AbstractFactory factoryDerby = AbstractFactory.getDAOFactory(2); //Derby
        ProductoDAOImplSQL productDerby = factoryDerby.getProductoDAO();
        System.out.println(productDerby.obtenerProductoMasRecaudado());

        ClienteDAO clienteDerby = factoryDerby.getClienteDAO();
        List<Cliente> clientesDerby = clienteDerby.obtenerFacturacionClientes();
        for(Cliente c: clientesDerby){
            System.out.println(c);
        }



    }
}
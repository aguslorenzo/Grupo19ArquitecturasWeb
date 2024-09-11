package arquitectura.grupo19;

import arquitectura.grupo19.dao.ProductoDAO;
import arquitectura.grupo19.dao.ClienteDAO;
import arquitectura.grupo19.dao.ProductoDAOImpl;
import arquitectura.grupo19.entities.Cliente;
import arquitectura.grupo19.factory.AbstractFactory;
import arquitectura.grupo19.utils.HelperDerby;
import arquitectura.grupo19.utils.HelperMySQL;

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
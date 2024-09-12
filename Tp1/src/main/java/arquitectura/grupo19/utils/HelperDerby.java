package arquitectura.grupo19.utils;

import arquitectura.grupo19.dao.*;
import arquitectura.grupo19.entities.Cliente;
import arquitectura.grupo19.entities.Factura;
import arquitectura.grupo19.entities.FacturaProducto;
import arquitectura.grupo19.entities.Producto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class HelperDerby {

    private Connection conn = null;

    public HelperDerby(){
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        String uri = "jdbc:derby:MyDerbyDb; create=true";
        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        try {
            conn = DriverManager.getConnection(uri);
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() {
        if (conn != null){
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void dropTables() throws SQLException {
        dropTable("facturas_productos");
        dropTable("facturas");
        dropTable("clientes");
        dropTable("productos");
        this.conn.commit();
        System.out.println("Tablas eliminadas");
    }

    private void dropTable(String tableName) throws SQLException {

        if (conn == null || conn.isClosed()) {
            throw new SQLException("La conexión a la base de datos no está abierta.");
        }

        if(tableExists(tableName)){
            String dropSQL = "DROP TABLE "+ tableName;

            try (PreparedStatement stmt = conn.prepareStatement(dropSQL)){
                stmt.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void createTables() throws SQLException {
        if (!tableExists("clientes")){
            createTable("CREATE TABLE clientes(" +
                    "idCliente INT NOT NULL, " +
                    "nombre VARCHAR(500), " +
                    "email VARCHAR(150), " +
                    "CONSTRAINT clientes_pk PRIMARY KEY (idCliente))");
        }
        if (!tableExists("productos")){
            createTable("CREATE TABLE productos(" +
                    "idProducto INT NOT NULL, " +
                    "nombre VARCHAR(45), " +
                    "valor FLOAT NOT NULL, " +
                    "CONSTRAINT productos_pk PRIMARY KEY (idProducto))");
        }
        if (!tableExists("facturas")){
            createTable("CREATE TABLE facturas(" +
                    "idFactura INT NOT NULL, " +
                    "idCliente INT NOT NULL, " +
                    "CONSTRAINT facturas_pk PRIMARY KEY (idFactura), "+
                    "CONSTRAINT FK_idCliente FOREIGN KEY (idCliente) REFERENCES clientes (idCliente))");
        }

        if(!tableExists("facturas_productos")){
            createTable("CREATE TABLE facturas_productos(" +
                    "idFactura INT NOT NULL, " +
                    "idProducto INT NOT NULL, " +
                    "cantidad INT NOT NULL, " +
                    "CONSTRAINT facturas_productos_pk PRIMARY KEY (idFactura, idProducto), " +
                    "CONSTRAINT FK_idFactura FOREIGN KEY (idFactura) REFERENCES facturas (idFactura), " +
                    "CONSTRAINT FK_idProducto FOREIGN KEY (idProducto) REFERENCES productos (idProducto))");
        }

        this.conn.commit();
        System.out.println("Tablas creadas");
    }

    private void createTable(String sql) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("La conexión a la base de datos no está abierta.");
        }

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void populateDB() throws Exception {

        try {
            System.out.println("Populating DB...");
            conn.setAutoCommit(false); // Desactiva autocommit para manejar las transacciones manualmente
            processCSV("src\\main\\resources\\clientes.csv", "Cliente");
            processCSV("src\\main\\resources\\productos.csv", "Producto");
            processCSV("src\\main\\resources\\facturas.csv", "Factura");
            processCSV("src\\main\\resources\\facturas-productos.csv", "FacturaProducto");
            System.out.println("Datos insertados correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al insertar datos en la base de datos: " + e.getMessage());
        }
    }

    private void processCSV(String filePath, String entity){
        System.out.println("Insertando "+ entity.toLowerCase() + "s...");

        try (CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new
                FileReader(filePath))){
            switch (entity) {
                case "Cliente" -> processClientes(parser);
                case "Producto" -> processProductos(parser);
                case "Factura" -> processFacturas(parser);
                case "FacturaProducto" -> processFacturasProductos(parser);
                default -> System.err.println("Datos inválidos en el archivo CSV: " + filePath);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void processClientes(CSVParser parser){
        ClienteDAOImpl clienteDAO = new ClienteDAOImpl(this.conn);

        for(CSVRecord row : parser) {
            if(row.size() >= 3) {
                String idString = row.get(0);
                String nombre = row.get(1);
                String email = row.get(2);
                if(isValidNumber(idString) && !nombre.isEmpty() && !email.isEmpty()) {
                    try {
                        int id = Integer.parseInt(idString);
                        Cliente cliente = new Cliente(id, nombre, email);
                        clienteDAO.insert(cliente);
                    } catch (Exception e) {
                        System.err.println("Error al persistir el cliente: " + e.getMessage());
                    }
                }
            }
        }
        System.out.println("Clientes insertados");

    }

    private void processFacturas(CSVParser parser) {
        FacturaDAOImpl facturaDAO = new FacturaDAOImpl(this.conn);

        for(CSVRecord row : parser) {
            if (row.size() >= 2) {
                String idString = row.get(0);
                String idClienteString = row.get(1);

                if (isValidNumber(idString) && isValidNumber(idClienteString)) {
                    try {
                        int id = Integer.parseInt(idString);
                        int idCliente = Integer.parseInt(idClienteString);

                        Factura factura = new Factura(id, idCliente);
                        facturaDAO.insert(factura);
                    } catch (Exception e) {
                        System.err.println("Error al persistir la factura: " + e.getMessage());
                    }
                }
            }
        }
        System.out.println("Facturas insertadas");

    }

    private void processProductos(CSVParser parser) {
        ProductoDAOImplSQL productoDAO = new ProductoDAOImplDerby(this.conn);

        for(CSVRecord row : parser) {
            if (row.size() >= 3) {
                String idString = row.get(0);
                String nombre = row.get(1);
                String valorString = row.get(2);

                if (isValidNumber(idString) && !nombre.isEmpty() && isValidNumber(valorString)) {
                    try {
                        int id = Integer.parseInt(idString);
                        float valor = Float.parseFloat(valorString);

                        Producto producto = new Producto(id, nombre, valor);
                        productoDAO.insert(producto);
                    } catch (Exception e) {
                        System.err.println("Error al persistir el producto: " + e.getMessage());
                    }
                }
            }
        }
        System.out.println("Productos insertados");

    }

    private void processFacturasProductos(CSVParser parser){
        FacturaProductoDAOImpl facturaProductoDAO = new FacturaProductoDAOImpl(this.conn);

        for(CSVRecord row : parser) {
            if (row.size() >= 3) {
                String idFacturaString = row.get(0);
                String idProductoString = row.get(1);
                String cantidadString = row.get(2);

                if (isValidNumber(idFacturaString) && isValidNumber(idProductoString) && isValidNumber(cantidadString)) {
                    try {
                        int idFactura = Integer.parseInt(idFacturaString);
                        int idProducto = Integer.parseInt(idProductoString);
                        int cantidad = Integer.parseInt(cantidadString);

                        FacturaProducto facturaProducto = new FacturaProducto(idFactura, idProducto, cantidad);
                        facturaProductoDAO.insert(facturaProducto);
                    } catch (Exception e) {
                        System.err.println("Error al persistir la venta: " + e.getMessage());
                    }
                }
            }
        }
        System.out.println("Ventas insertadas");
    }

    private boolean isValidNumber(String str) {

        if (str == null || str.isEmpty()) return false;

        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean tableExists(String tableName) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        try (ResultSet resultSet = meta.getTables(null, null, tableName.toUpperCase(), null)) {
            return resultSet.next(); // Si hay resultados, la tabla existe
        }
    }

}

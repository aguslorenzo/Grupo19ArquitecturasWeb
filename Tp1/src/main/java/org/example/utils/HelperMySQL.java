package org.example.utils;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HelperMySQL {
    private Connection conn = null;

    public HelperMySQL() {//Constructor
        String driver = "com.mysql.cj.jdbc.Driver";
        String uri = "jdbc:mysql://localhost:3306/db-tpe";

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
        } catch (Exception e) {
            e.printStackTrace();
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
        dropTable("facturas_productos"); // La factura-producto se debe eliminar antes que las tablas factura y producto
        dropTable("facturas"); // La factura se debe eliminar antes que el cliente
        dropTable("clientes");
        dropTable("productos");
        this.conn.commit();
        System.out.println("Tablas eliminadas");
    }

    private void dropTable(String tableName) throws SQLException {

        if (conn == null || conn.isClosed()) {
            throw new SQLException("La conexión a la base de datos no está abierta.");
        }

        String dropSQL = "DROP TABLE IF EXISTS "+ tableName;

        try (PreparedStatement stmt = conn.prepareStatement(dropSQL)){
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createTables() throws SQLException {
        createTable("CREATE TABLE IF NOT EXISTS clientes(" +
                    "idCliente INT NOT NULL, " +
                    "nombre VARCHAR(500), " +
                    "email VARCHAR(150), " +
                    "CONSTRAINT clientes_pk PRIMARY KEY (idCliente));");

        createTable("CREATE TABLE IF NOT EXISTS productos(" +
                    "idProducto INT NOT NULL, " +
                    "nombre VARCHAR(45), " +
                    "valor FLOAT(10,2) NOT NULL, " +
                    "CONSTRAINT productos_pk PRIMARY KEY (idProducto));");

        createTable("CREATE TABLE IF NOT EXISTS facturas(" +
                    "idFactura INT NOT NULL, " +
                    "idCliente INT NOT NULL, " +
                    "CONSTRAINT facturas_pk PRIMARY KEY (idFactura), "+
                    "CONSTRAINT FK_idCliente FOREIGN KEY (idCliente) REFERENCES clientes (idCliente));");

        createTable("CREATE TABLE IF NOT EXISTS facturas_productos(" +
                    "idFactura INT NOT NULL, " +
                    "idProducto INT NOT NULL, " +
                    "cantidad INT NOT NULL, " +
                    "CONSTRAINT facturas_productos_pk PRIMARY KEY (idFactura, idProducto), " +
                    "CONSTRAINT FK_idFactura FOREIGN KEY (idFactura) REFERENCES facturas (idFactura), " +
                    "CONSTRAINT FK_idProducto FOREIGN KEY (idProducto) REFERENCES productos (idProducto));");

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

}

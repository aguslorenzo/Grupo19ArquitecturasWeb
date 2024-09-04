package org.example.utils;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
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
        String dropPersona = "DROP TABLE IF EXISTS cliente";
        this.conn.prepareStatement(dropPersona).execute();
        this.conn.commit();

        String dropDireccion = "DROP TABLE IF EXISTS producto";
        this.conn.prepareStatement(dropDireccion).execute();
        this.conn.commit();

        String dropFactura = "DROP TABLE IF EXISTS factura";
        this.conn.prepareStatement(dropFactura).execute();
        this.conn.commit();
    }

    public void createTables() throws SQLException {
        String tableCliente = "CREATE TABLE IF NOT EXISTS cliente(" +
                "idCliente INT NOT NULL, " +
                "nombre VARCHAR(500), " +
                "email VARCHAR(150), " +
                "CONSTRAINT cliente_pk PRIMARY KEY (idCliente));" ;
        this.conn.prepareStatement(tableCliente).execute();
        this.conn.commit();
        String tableProducto = "CREATE TABLE IF NOT EXISTS producto(" +
                "idProducto INT NOT NULL, " +
                "nombre VARCHAR(45), " +
                "valor FLOAT(10,2) NOT NULL, " +
                "CONSTRAINT producto_pk PRIMARY KEY (idProducto));";
        this.conn.prepareStatement(tableProducto).execute();
        this.conn.commit();
        String tableFactura = "CREATE TABLE IF NOT EXISTS factura(" +
                "idFactura INT NOT NULL, " +
                "idCliente INT NOT NULL, " +
                "CONSTRAINT factura_pk PRIMARY KEY (idFactura), "+
                "CONSTRAINT FK_idDireccion FOREIGN KEY (idCliente) REFERENCES cliente (idCliente))";
        this.conn.prepareStatement(tableFactura).execute();
        this.conn.commit();
        String tableFacturaProducto = "CREATE TABLE IF NOT EXISTS factura_producto(" +
                "idFactura INT NOT NULL, " +
                "idProducto INT NOT NULL, " +
                "cantidad INT NOT NULL, " +
                "CONSTRAINT factura_producto_pk PRIMARY KEY (idFactura, idProducto), " +
                "CONSTRAINT FK_idFactura FOREIGN KEY (idFactura) REFERENCES factura (idFactura), " +
                "CONSTRAINT FK_idProducto FOREIGN KEY (idProducto) REFERENCES producto (idProducto))";
        this.conn.prepareStatement(tableFacturaProducto).execute();
        this.conn.commit();
    }


}

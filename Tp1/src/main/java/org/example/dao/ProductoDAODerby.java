package org.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductoDAODerby extends ProductoDAOImpl{

    public ProductoDAODerby(Connection conn){
        this.conn=conn;
    }

    @Override
    public String obtenerProductoMasRecaudado() {
        String res = "";
        String query = "SELECT p.idProducto, p.nombre, SUM(fp.cantidad * p.valor) AS recaudacion " +
                "FROM productos p " +
                "JOIN facturas_productos fp ON p.idProducto = fp.idProducto " +
                "GROUP BY p.idProducto, p.nombre " +
                "ORDER BY recaudacion DESC " +
                "FETCH FIRST 1 ROWS ONLY";

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                int idProducto = rs.getInt("idProducto");
                String nombreProducto = rs.getString("nombre");
                float recaudacion = rs.getFloat("recaudacion");

                res = "Producto que más recaudó: " +
                        "\nID: " + idProducto +
                        "\nNombre: " + nombreProducto +
                        "\nRecaudación: " + recaudacion;

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}

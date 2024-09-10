package org.example.dao;

import org.example.entities.Cliente;
import org.example.entities.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    private Connection conn;

    public ProductoDAO(Connection conn) {
        this.conn = conn;
    }

    public void insertProducto(Producto producto) {
        String query = "INSERT INTO productos (idProducto, nombre, valor) VALUES (?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, producto.getId());
            ps.setString(2, producto.getNombre());
            ps.setFloat(3, producto.getValor());
            ps.executeUpdate();
            System.out.println("Producto insertada exitosamente.");
        } catch (
                SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Producto> selectAll() {
        String query = "SELECT * FROM productos";
        PreparedStatement ps;
        List<Producto> resultado = new ArrayList<>();
        try {
            ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                resultado.add(new Producto(rs.getInt(1),rs.getString(2),rs.getFloat(3)));
            }
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }


    public Producto obtenerProductoMasRecaudado() {
        String sql = "SELECT p.idProducto, p.nombre, SUM(fp.cantidad * p.valor) AS recaudacion " +
                "FROM productos p " +
                "JOIN facturas_productos fp ON p.idProducto = fp.idProducto " +
                "GROUP BY p.idProducto, p.nombre " +
                "ORDER BY recaudacion DESC " +
                "LIMIT 1";

        Producto producto = null;
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                int idProducto = rs.getInt("idProducto");
                String nombreProducto = rs.getString("nombre");
                float recaudacion = rs.getFloat("recaudacion");

                producto = new Producto(idProducto, nombreProducto, recaudacion);
                System.out.println("Producto que más recaudó:");
                System.out.println("ID: " + idProducto);
                System.out.println("Nombre: " + nombreProducto);
                System.out.println("Recaudación: " + recaudacion);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producto;
    }
}

package org.example.dao;

import org.example.entities.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySQLProductoDAO implements ProductoDAO{
    private Connection conn;

    public MySQLProductoDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Producto> getAll() {
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

    @Override
    public Optional<Producto> getById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void insert(Producto producto) {
        String query = "INSERT INTO productos (idProducto, nombre, valor) VALUES (?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, producto.getId());
            ps.setString(2, producto.getNombre());
            ps.setFloat(3, producto.getValor());
            ps.executeUpdate();
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

    @Override
    public void update(Producto producto) {

    }

    @Override
    public void delete(Producto producto) {

    }

    public String obtenerProductoMasRecaudado() {
        String res = "";
        String query = "SELECT p.idProducto, p.nombre, SUM(fp.cantidad * p.valor) AS recaudacion " +
                "FROM productos p " +
                "JOIN facturas_productos fp ON p.idProducto = fp.idProducto " +
                "GROUP BY p.idProducto, p.nombre " +
                "ORDER BY recaudacion DESC " +
                "LIMIT 1";

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

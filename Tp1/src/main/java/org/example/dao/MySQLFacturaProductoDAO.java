package org.example.dao;

import org.example.entities.FacturaProducto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySQLFacturaProductoDAO implements FacturaProductoDAO{
    private Connection conn;

    public MySQLFacturaProductoDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<FacturaProducto> getAll() {
        String query = "SELECT * FROM facturas_productos";
        PreparedStatement ps;
        List<FacturaProducto> resultado = new ArrayList<>();
        try {
            ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                resultado.add(new FacturaProducto(rs.getInt(1),rs.getInt(2),rs.getInt(3)));
            }
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }

    @Override
    public Optional<FacturaProducto> getById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void insert(FacturaProducto facturaProducto) {
        String query = "INSERT INTO facturas_productos (idFactura, idProducto, cantidad) VALUES (?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, facturaProducto.getIdFactura());
            ps.setInt(2, facturaProducto.getIdProducto());
            ps.setInt(3, facturaProducto.getCantidad());
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
    public void update(FacturaProducto facturaProducto) {

    }

    @Override
    public void delete(FacturaProducto facturaProducto) {

    }
}

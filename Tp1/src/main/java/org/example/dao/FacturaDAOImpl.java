package org.example.dao;

import org.example.entities.Factura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FacturaDAOImpl implements FacturaDAO{
    private Connection conn;

    public FacturaDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Factura> getAll() {
        String query = "SELECT * FROM facturas";
        PreparedStatement ps;
        List<Factura> resultado = new ArrayList<>();
        try {
            ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                resultado.add(new Factura(rs.getInt(1),rs.getInt(2)));
            }
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }

    @Override
    public Optional<Factura> getById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void insert(Factura factura) {
        String query = "INSERT INTO facturas (idFactura, idCliente) VALUES (?, ?)";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, factura.getId());
            ps.setInt(2, factura.getId_cliente());
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
    public void update(Factura factura) {

    }

    @Override
    public void delete(Factura factura) {

    }
}

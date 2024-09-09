package org.example.dao;

import org.example.entities.Cliente;
import org.example.entities.Factura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FacturaDAO {
    private Connection conn;

    public FacturaDAO(Connection conn) {
        this.conn = conn;
    }

    public void insertFactura(Factura factura){
        String query = "INSERT INTO factura (idFactura, idCliente) VALUES (?, ?)";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, factura.getId());
            ps.setInt(2, factura.getId_cliente());
            ps.executeUpdate();
            System.out.println("Factura insertada exitosamente.");
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

    public List<Factura> selectAll() {
        String query = "SELECT * FROM factura";
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
}

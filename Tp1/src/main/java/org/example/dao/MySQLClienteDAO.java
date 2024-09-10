package org.example.dao;

import org.example.entities.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySQLClienteDAO implements ClienteDAO{
    private Connection conn;

    public MySQLClienteDAO(Connection conn){
        this.conn=conn;
    }

    @Override
    public List<Cliente> getAll() {
        String query = "SELECT * FROM clientes";
        PreparedStatement ps;
        List<Cliente> resultado = new ArrayList<>();
        try {
            ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                resultado.add(new Cliente(rs.getInt(1),rs.getString(2),rs.getString(3)));
            }
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }

    @Override
    public Optional<Cliente> getById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void insert(Cliente cliente) {
        String query = "INSERT INTO clientes (idCliente, nombre, email) VALUES (?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, cliente.getId());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getEmail());
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
    public void update(Cliente cliente) {

    }

    @Override
    public void delete(Cliente cliente) {

    }
}

package arquitectura.grupo19.dao;

import arquitectura.grupo19.entities.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteDAOImpl implements ClienteDAO{
    private Connection conn;

    public ClienteDAOImpl(Connection conn){
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
        //TODO implementar luego si hace falta
    }

    @Override
    public void delete(Cliente cliente) {
        //TODO implementar luego si hace falta
    }

    public List<Cliente> obtenerFacturacionClientes() {
        List<Cliente> clientes = new ArrayList<>();
         String query = "SELECT c.idCliente, c.nombre, c.email, SUM(p.valor * fp.cantidad) AS total_facturado " +
        "FROM clientes c JOIN facturas f ON c.idCliente = f.idCliente " +
        "JOIN facturas_productos fp ON f.idFactura = fp.idFactura "+
        "JOIN productos p on fp.idProducto = p.idProducto "+
        "GROUP BY c.idCliente, c.nombre, c.email "+
        "ORDER BY total_facturado DESC";

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idCliente = rs.getInt("idCliente");
                String nombreCliente = rs.getString("nombre");
                String emailCliente = rs.getString("email");

                Cliente cliente = new Cliente(idCliente, nombreCliente, emailCliente);
                clientes.add(cliente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }
}

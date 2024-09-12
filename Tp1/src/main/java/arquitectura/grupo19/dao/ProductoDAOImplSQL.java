package arquitectura.grupo19.dao;

import arquitectura.grupo19.entities.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class ProductoDAOImplSQL implements ProductoDAO{
    protected Connection conn;

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
        //TODO implementar luego si hace falta
    }

    @Override
    public void delete(Producto producto) {
        //TODO implementar luego si hace falta
    }

    public abstract String obtenerProductoMasRecaudado();

}

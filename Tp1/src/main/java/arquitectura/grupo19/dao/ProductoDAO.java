package arquitectura.grupo19.dao;

import arquitectura.grupo19.entities.Producto;

public interface ProductoDAO extends CRUD<Producto> {

    String obtenerProductoMasRecaudado();
}
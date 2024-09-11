package arquitectura.grupo19.dao;

import arquitectura.grupo19.entities.Cliente;

import java.util.List;

public interface ClienteDAO extends CRUD<Cliente> {

    List<Cliente> obtenerFacturacionClientes();
}

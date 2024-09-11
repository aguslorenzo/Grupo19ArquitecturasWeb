package org.example.dao;

import org.example.entities.Cliente;

import java.util.List;

public interface ClienteDAO extends CRUD<Cliente> {

    List<Cliente> obtenerFacturacionClientes();
}

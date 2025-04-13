package alquilercoches.personal;

import alquilercoches.Cliente;
import alquilercoches.db.DBCRUDClientes;

import java.util.List;

public class Usuario extends Empleado {
    public Usuario(String dni, String nombre) {
        super(dni, nombre);
    }

    public void insertarCliente(Cliente c) {
        DBCRUDClientes.insertarCliente(c);
    }

    public Cliente buscarCliente(String dni) {
        return DBCRUDClientes.buscarCliente(dni);
    }

    public List<Cliente> obtenerTodosLosClientes() {
        return DBCRUDClientes.obtenerTodosLosClientes();
    }

    public void actualizarCliente(Cliente c) {
        DBCRUDClientes.actualizarCliente(c);
    }

    public void eliminarCliente(String dni) {
        DBCRUDClientes.eliminarCliente(dni);
    }
}
package alquilercoches.personal;

import alquilercoches.db.DBCRUDAlquileres;
import alquilercoches.negocio.Alquiler;
import alquilercoches.negocio.Cliente;
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

    public void insertarAlquiler(Alquiler c) {
        DBCRUDAlquileres.insertarAlquiler(c);
    }

    public Alquiler buscarAlquiler(String matricula) {
        return DBCRUDAlquileres.buscarAlquiler(matricula);
    }

    public List<Alquiler> obtenerTodosLosAlquileres() {
        return DBCRUDAlquileres.obtenerTodosLosAlquileres();
    }

    public void actualizarAlquiler(Alquiler c) {
        DBCRUDAlquileres.actualizarAlquiler(c);
    }

    public void eliminarAlquiler(String dni) {
        DBCRUDAlquileres.eliminarAlquiler(dni);
    }
}
package alquilercoches.db;

import alquilercoches.Cliente;

import java.sql.*;
import java.util.*;

public class DBCRUDClientes {

    public static void insertarCliente(Cliente c) {
        String sql = "INSERT INTO clientes (dni, nombre, direccion, telefono, email) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getDni());
            stmt.setString(2, c.getNombre());
            stmt.setString(3, c.getDireccion());
            stmt.setString(4, c.getTelefono());
            stmt.setString(5, c.getEmail());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Cliente buscarCliente(String dni) {
        String sql = "SELECT * FROM clientes WHERE dni = ?";
        Cliente cliente = null;

        try (Connection conn = DBConexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cliente = new Cliente(
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("email")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cliente;
    }

    public static List<Cliente> obtenerTodosLosClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try (Connection conn = DBConexion.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cliente c = new Cliente(
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("email")
                );
                clientes.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }

    public static void actualizarCliente(Cliente c) {
        String sql = "UPDATE clientes SET nombre = ?, direccion = ?, telefono = ?, email = ? WHERE dni = ?";

        try (Connection conn = DBConexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getNombre());
            stmt.setString(2, c.getDireccion());
            stmt.setString(3, c.getTelefono());
            stmt.setString(4, c.getEmail());
            stmt.setString(5, c.getDni());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void eliminarCliente(String dni) {
        String sql = "DELETE FROM clientes WHERE dni = ?";

        try (Connection conn = DBConexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, dni);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

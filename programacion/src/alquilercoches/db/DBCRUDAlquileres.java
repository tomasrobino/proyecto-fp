package alquilercoches.db;

import alquilercoches.negocio.Alquiler;

import java.sql.*;
import java.util.*;

public abstract class DBCRUDAlquileres {

    // Insertar un nuevo alquiler
    public static void insertarAlquiler(Alquiler a) {
        String sql = "INSERT INTO alquileres (matricula, modelo, marca, cliente) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, a.getMatricula());
            stmt.setString(2, a.getModelo());
            stmt.setString(3, a.getMarca());
            stmt.setString(4, a.getCliente());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Buscar un alquiler por matrícula
    public static Alquiler buscarAlquiler(String matricula) {
        String sql = "SELECT * FROM alquileres WHERE matricula = ?";
        Alquiler alquiler = null;

        try (Connection conn = DBConexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, matricula);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                alquiler = new Alquiler(
                        rs.getString("matricula"),
                        rs.getString("modelo"),
                        rs.getString("marca"),
                        rs.getString("cliente")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return alquiler;
    }

    // Obtener todos los alquileres
    public static List<Alquiler> obtenerTodosLosAlquileres() {
        List<Alquiler> alquileres = new ArrayList<>();
        String sql = "SELECT * FROM alquileres";

        try (Connection conn = DBConexion.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Alquiler a = new Alquiler(
                        rs.getString("matricula"),
                        rs.getString("modelo"),
                        rs.getString("marca"),
                        rs.getString("cliente")
                );
                alquileres.add(a);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return alquileres;
    }

    // Actualizar un alquiler
    public static void actualizarAlquiler(Alquiler a) {
        String sql = "UPDATE alquileres SET modelo = ?, marca = ?, cliente = ? WHERE matricula = ?";

        try (Connection conn = DBConexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, a.getModelo());
            stmt.setString(2, a.getMarca());
            stmt.setString(3, a.getCliente());
            stmt.setString(4, a.getMatricula());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Eliminar un alquiler
    public static void eliminarAlquiler(String matricula) {
        String sql = "DELETE FROM alquileres WHERE matricula = ?";

        try (Connection conn = DBConexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, matricula);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

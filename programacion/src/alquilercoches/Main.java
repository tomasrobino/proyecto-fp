package alquilercoches;

import alquilercoches.db.DBConexion;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            Connection conexion = DBConexion.conectar();
            if (conexion != null) {
                System.out.println("Conexion exitosa");
                conexion.close();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
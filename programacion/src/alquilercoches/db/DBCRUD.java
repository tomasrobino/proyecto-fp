package alquilercoches.db;

import alquilercoches.vehiculos.Furgoneta;
import alquilercoches.vehiculos.Motocicleta;
import alquilercoches.vehiculos.Turismo;
import alquilercoches.vehiculos.Vehiculo;

import java.sql.*;
import java.util.Date;

public abstract class DBCRUD {
    public static void insertVehiculo(Vehiculo v) {
        String sqlVehiculo = "INSERT INTO vehiculos (tipo, subtipo, precio, fabricacion, aptitud) VALUES (?, ?, ?, ?, ?)";
        String sqlMantenimiento = "INSERT INTO mantenimientos (vehiculo_id, fecha) VALUES (?, ?)";

        try (Connection conn = DBConexion.conectar()) {
            conn.setAutoCommit(false);

            // INSERT vehiculo
            try (PreparedStatement stmt = conn.prepareStatement(sqlVehiculo, Statement.RETURN_GENERATED_KEYS)) {
                String tipo, subtipo = null;

                if (v instanceof Furgoneta f) {
                    tipo = "furgoneta";
                    subtipo = f.getTipo().name();
                } else if (v instanceof Turismo t) {
                    tipo = "turismo";
                    subtipo = t.getTipo().name();
                } else if (v instanceof Motocicleta) {
                    tipo = "motocicleta";
                } else {
                    throw new IllegalArgumentException("Tipo de vehículo no reconocido");
                }

                v.calcularMantenimiento(); // actualiza la aptitud

                stmt.setString(1, tipo);
                stmt.setString(2, subtipo);
                stmt.setDouble(3, v.getPrecio());
                stmt.setDate(4, new java.sql.Date(v.getFabricacion().getTime()));
                stmt.setInt(5, v.getAptitud());
                stmt.executeUpdate();

                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    int idVehiculo = keys.getInt(1);

                    // INSERT mantenimientos
                    try (PreparedStatement mStmt = conn.prepareStatement(sqlMantenimiento)) {
                        for (Date fecha : v.getMantenimientos()) {
                            mStmt.setInt(1, idVehiculo);
                            mStmt.setDate(2, new java.sql.Date(fecha.getTime()));
                            mStmt.addBatch();
                        }
                        mStmt.executeBatch();
                    }
                }

                conn.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    
}

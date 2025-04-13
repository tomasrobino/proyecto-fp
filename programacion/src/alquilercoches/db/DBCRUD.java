package alquilercoches.db;

import alquilercoches.TipoFurgoneta;
import alquilercoches.TipoTurismo;
import alquilercoches.vehiculos.Furgoneta;
import alquilercoches.vehiculos.Motocicleta;
import alquilercoches.vehiculos.Turismo;
import alquilercoches.vehiculos.Vehiculo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


    public static List<Vehiculo> getVehiculos() {
        ArrayList<Vehiculo> lista = new ArrayList<>();
        String sqlVehiculos = "SELECT * FROM vehiculos";
        String sqlMantenimientos = "SELECT fecha FROM mantenimientos WHERE vehiculo_id = ? ORDER BY fecha";

        try (Connection conn = DBConexion.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlVehiculos)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String tipo = rs.getString("tipo");
                String subtipo = rs.getString("subtipo");
                double precio = rs.getDouble("precio");
                Date fabricacion = rs.getDate("fabricacion");

                Vehiculo v;
                switch (tipo) {
                    case "furgoneta":
                        v = new Furgoneta(TipoFurgoneta.valueOf(subtipo), precio, fabricacion);
                        break;
                    case "turismo":
                        v = new Turismo(TipoTurismo.valueOf(subtipo), precio, fabricacion);
                        break;
                    case "motocicleta":
                        v = new Motocicleta(precio, fabricacion);
                        break;
                    default:
                        continue;
                }

                // Cargar mantenimientos
                try (PreparedStatement mStmt = conn.prepareStatement(sqlMantenimientos)) {
                    mStmt.setInt(1, id);
                    ResultSet mRs = mStmt.executeQuery();
                    while (mRs.next()) {
                        v.getMantenimientos().add(mRs.getDate("fecha"));
                    }
                }

                v.calcularMantenimiento(); // actualiza aptitud

                lista.add(v);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

}

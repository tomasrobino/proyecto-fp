package alquilercoches.json;

import alquilercoches.db.DBCRUDAlquileres;
import alquilercoches.db.DBCRUDClientes;
import alquilercoches.db.DBCRUDVehiculos;
import alquilercoches.negocio.Alquiler;
import alquilercoches.negocio.Cliente;
import alquilercoches.vehiculos.Furgoneta;
import alquilercoches.vehiculos.Motocicleta;
import alquilercoches.vehiculos.Turismo;
import alquilercoches.vehiculos.Vehiculo;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class JSON {

    public static void generarJSON() {
        // Create a local instance of SimpleDateFormat
        final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        
        FileWriter fileWriter = null;
        List<Vehiculo> vehiculos = DBCRUDVehiculos.getVehiculos();
        List<Alquiler> alquileres = DBCRUDAlquileres.obtenerTodosLosAlquileres();
        List<Cliente> clientes = DBCRUDClientes.obtenerTodosLosClientes();

        try {
            fileWriter = new FileWriter("programacion/json/vehiculos.json");

            fileWriter.write("[\n");

            for (int i = 0; i < vehiculos.size(); i++) {
                Vehiculo v = vehiculos.get(i);
                fileWriter.write("  {\n");
                fileWriter.write("    \"matricula\": \"" + v.getMatricula() + "\",\n");
                fileWriter.write("    \"modelo\": \"" + v.getModelo() + "\",\n");
                fileWriter.write("    \"marca\": \"" + v.getMarca() + "\",\n");
                if (v.getClass() != Motocicleta.class) {
                    if (v.getClass() == Furgoneta.class) {
                        fileWriter.write("    \"tipo\": \"" + "furgoneta" + "\",\n");
                        fileWriter.write("    \"subtipo\": \"" + ((Furgoneta)v).getTipo() + "\",\n");
                    } else {
                        fileWriter.write("    \"tipo\": \"" + "turismo" + "\",\n");
                        fileWriter.write("    \"subtipo\": \"" + ((Turismo)v).getTipo() + "\",\n");
                    }

                } else {
                    fileWriter.write("    \"tipo\": \"" + "motocicleta" + "\",\n");
                }
                fileWriter.write("    \"precio\": " + v.getPrecio() + ",\n");
                fileWriter.write("    \"fabricacion\": \"" + formatoFecha.format(v.getFabricacion()) + "\",\n");
                fileWriter.write("    \"mantenimientos\": [\n");
                for (int j = 0; j < v.getMantenimientos().size(); j++) {
                    fileWriter.write("        \"" + formatoFecha.format(v.getMantenimientos().get(j)) + "\"");

                    if (j < v.getMantenimientos().size() - 1) {
                        fileWriter.write(",\n");
                    } else {
                        fileWriter.write("\n");
                    }
                }
                fileWriter.write("    ],\n");
                fileWriter.write("    \"apto\": \"" + v.getAptitud() + "\",\n");
                fileWriter.write("    \"alquilado\": \"" + v.isAlquilado() + "\"\n");
                if (v.isAlquilado()) {
                    Optional<Alquiler> alquiler = alquileres.stream().filter(a -> v.getMatricula().equals(a.getMatricula())).findFirst();
                    if (alquiler.isPresent()) {
                        Optional<Cliente> cliente = clientes.stream().filter(c -> alquiler.get().getCliente().equals(c.getNombre())).findFirst();
                        if (cliente.isPresent()) {
                            fileWriter.write("    \"nombreCliente\": \"" + cliente.get().getNombre() + "\"\n");
                        }
                    }
                }
                fileWriter.write("  }");

                if (i < vehiculos.size() - 1) {
                    fileWriter.write(",\n");
                } else {
                    fileWriter.write("\n");
                }
            }

            fileWriter.write("]");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
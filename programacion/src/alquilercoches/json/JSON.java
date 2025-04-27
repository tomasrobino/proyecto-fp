package alquilercoches.json;

import alquilercoches.vehiculos.Furgoneta;
import alquilercoches.vehiculos.Motocicleta;
import alquilercoches.vehiculos.Turismo;
import alquilercoches.vehiculos.Vehiculo;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public abstract class JSON {
    private static final SimpleDateFormat FORMATO_FECHA = new SimpleDateFormat("yyyy-MM-dd");

    public static void generateJsonFile(List<Vehiculo> vehiculos, String filename) {
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(filename);

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
                fileWriter.write("    \"fabricacion\": \"" + FORMATO_FECHA.format(v.getFabricacion()) + "\",\n");
                fileWriter.write("    \"mantenimientos\": \"" + FORMATO_FECHA.format(v.getMantenimientos()) + "\"\n");
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

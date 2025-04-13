package alquilercoches.vehiculos;

import java.util.Date;

public class Motocicleta extends Vehiculo {
    public Motocicleta(double precio, Date fabricacion) {
        this.precio = precio;
        this.fabricacion = fabricacion;
    }

    @Override
    public void calcularMantenimiento() {
        Date hoy = new Date();
        // 126230400000 es la cantidad de milisegundos en cuatro años
        if (hoy.getTime()-fabricacion.getTime() < 126230400000L) {
            // 63115200000 es la cantidad de milisegundos en 24 meses
            if (hoy.getTime()- mantenimientos.get(mantenimientos.size()-1).getTime() > 63115200000L) {
                aptitud = 0;
            } else if (hoy.getTime()- mantenimientos.get(mantenimientos.size()-1).getTime() > 63115200000L - tiempoAmarillo) {
                aptitud = 1;
            } else aptitud = 2;
        } else {
            if (hoy.getTime()- mantenimientos.get(mantenimientos.size()-1).getTime() > 31557600000L) {
                aptitud = 0;
            } else if (hoy.getTime()- mantenimientos.get(mantenimientos.size()-1).getTime() > 31557600000L - tiempoAmarillo) {
                aptitud = 1;
            } else aptitud = 2;
        }
    }
}

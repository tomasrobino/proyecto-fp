package alquilercoches.vehiculos;

import alquilercoches.TipoTurismo;

import java.util.Date;

public class Turismo extends Vehiculo {
    private TipoTurismo tipo;

    public Turismo(TipoTurismo tipo, double precio, Date fabricacion) {
        this.tipo = tipo;
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

    public TipoTurismo getTipo() {
        return tipo;
    }

    public void setTipo(TipoTurismo tipo) {
        this.tipo = tipo;
    }
}

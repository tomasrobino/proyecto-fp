package alquilercoches.vehiculos;

import alquilercoches.TipoFurgoneta;

import java.util.Date;

public class Furgoneta extends Vehiculo {
    private TipoFurgoneta tipo;

    public Furgoneta(TipoFurgoneta tipo, double precio, Date fabricacion) {
        this.tipo = tipo;
        this.precio = precio;
        this.fabricacion = fabricacion;
    }

    @Override
    public void calcularMantenimiento() {
        Date hoy = new Date();
        if (hoy.getTime()-fabricacion.getTime() < 63115200000L) {
            if (hoy.getTime()-fechaMantenimiento.getTime() > 31557600000L) {
                aptitud = 0;
            } else if (hoy.getTime()-fechaMantenimiento.getTime() > 31557600000L - tiempoAmarillo) {
                aptitud = 1;
            } else aptitud = 2;
        } else {
            if (hoy.getTime()-fechaMantenimiento.getTime() > 15778800000L) {
                aptitud = 0;
            } else if (hoy.getTime()-fechaMantenimiento.getTime() > 15778800000L - tiempoAmarillo) {
                aptitud = 1;
            } else aptitud = 2;
        }
    }

    public TipoFurgoneta getTipo() {
        return tipo;
    }

    public void setTipo(TipoFurgoneta tipo) {
        this.tipo = tipo;
    }
}

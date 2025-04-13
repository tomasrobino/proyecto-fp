package alquilercoches.vehiculos;

import alquilercoches.TipoFurgoneta;

import java.util.Date;

public class Furgoneta extends Vehiculo {
    private TipoFurgoneta tipo;

    public Furgoneta(TipoFurgoneta tipo, double precio, Date fabricacion, String matricula, String marca, String modelo) {
        this.tipo = tipo;
        this.precio = precio;
        this.fabricacion = fabricacion;
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
    }

    @Override
    public void calcularMantenimiento() {
        Date hoy = new Date();
        if (hoy.getTime()-fabricacion.getTime() < 63115200000L) {
            if (hoy.getTime()- mantenimientos.get(mantenimientos.size()-1).getTime() > 31557600000L) {
                aptitud = 0;
            } else if (hoy.getTime()- mantenimientos.get(mantenimientos.size()-1).getTime() > 31557600000L - tiempoAmarillo) {
                aptitud = 1;
            } else aptitud = 2;
        } else {
            if (hoy.getTime()- mantenimientos.get(mantenimientos.size()-1).getTime() > 15778800000L) {
                aptitud = 0;
            } else if (hoy.getTime()- mantenimientos.get(mantenimientos.size()-1).getTime() > 15778800000L - tiempoAmarillo) {
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

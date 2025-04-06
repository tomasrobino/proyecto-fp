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
}

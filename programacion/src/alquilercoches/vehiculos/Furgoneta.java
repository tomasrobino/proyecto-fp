package alquilercoches.vehiculos;

import alquilercoches.TipoFurgoneta;

public class Furgoneta extends Vehiculo {
    private TipoFurgoneta tipo;

    public Furgoneta(TipoFurgoneta tipo, double precio) {
        this.tipo = tipo;
        this.precio = precio;
    }
}

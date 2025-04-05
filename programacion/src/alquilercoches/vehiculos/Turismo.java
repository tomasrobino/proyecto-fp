package alquilercoches.vehiculos;

import alquilercoches.TipoTurismo;

public class Turismo extends Vehiculo {
    private TipoTurismo tipo;

    public Turismo(TipoTurismo tipo, double precio) {
        this.tipo = tipo;
        this.precio = precio;
    }
}

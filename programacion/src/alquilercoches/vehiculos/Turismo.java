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
}

package alquilercoches.vehiculos;

import java.util.Date;

public abstract class Vehiculo {
    final long tiempoAmarillo = 2629800000L; //milisegundos en un mes
    double precio;
    Date fechaMantenimiento;
    Date fabricacion;
    int aptitud;

    abstract void calcularMantenimiento();
}

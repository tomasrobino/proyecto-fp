package alquilercoches.vehiculos;

import java.util.Date;

public abstract class Vehiculo {
    final long tiempoAmarillo = 2629800000L; //milisegundos en un mes
    double precio;
    Date fechaMantenimiento;
    Date fabricacion;
    int aptitud;

    abstract void calcularMantenimiento();

    public long getTiempoAmarillo() {
        return tiempoAmarillo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Date getFechaMantenimiento() {
        return fechaMantenimiento;
    }

    public void setFechaMantenimiento(Date fechaMantenimiento) {
        this.fechaMantenimiento = fechaMantenimiento;
    }

    public Date getFabricacion() {
        return fabricacion;
    }

    public void setFabricacion(Date fabricacion) {
        this.fabricacion = fabricacion;
    }

    public int getAptitud() {
        return aptitud;
    }

    public void setAptitud(int aptitud) {
        this.aptitud = aptitud;
    }
}

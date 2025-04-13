package alquilercoches.vehiculos;

import java.util.ArrayList;
import java.util.Date;

public abstract class Vehiculo {
    final long tiempoAmarillo = 2629800000L; //milisegundos en un mes
    double precio;
    final ArrayList<Date> mantenimientos = new ArrayList<>();
    Date fabricacion;
    int aptitud;

    public abstract void calcularMantenimiento();

    public long getTiempoAmarillo() {
        return tiempoAmarillo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public ArrayList<Date> getMantenimientos() {
        return mantenimientos;
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

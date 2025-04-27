package alquilercoches.personal;

public abstract class Empleado {
    String dni, nombre;

    public Empleado(String dni, String nombre) {
        this.dni = dni;
        this.nombre = nombre;
    }
}

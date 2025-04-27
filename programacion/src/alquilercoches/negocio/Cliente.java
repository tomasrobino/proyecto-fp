package alquilercoches.negocio;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String telefono;
    private String dni;
    private String nombre;
    private String direccion;
    private String email;
    private final List<String> vehiculosAlquilados = new ArrayList<>();

    public Cliente(String dni, String nombre, String direccion, String telefono, String email) {
        this.dni = dni;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }

    public String getDni() { return dni; }
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }
    public List<String> getVehiculosAlquilados() { return vehiculosAlquilados; }
    public void addVehiculo(String vehiculo) { this.vehiculosAlquilados.add(vehiculo); }
    public void removeVehiculo(String vehiculo) { this.vehiculosAlquilados.remove(vehiculo); }

    public void setDni(String dni) { this.dni = dni; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setEmail(String email) { this.email = email; }
}


package alquilercoches.personal;

import alquilercoches.db.DBCRUDVehiculos;
import alquilercoches.vehiculos.Vehiculo;

import java.util.Date;
import java.util.List;

public class Administrador extends Empleado {
    public Administrador(String dni, String nombre) {
        super(dni, nombre);
    }

    public void eliminarVehiculo(String matricula) {
        Integer id = DBCRUDVehiculos.getId(matricula);
        if (id != null) {
            DBCRUDVehiculos.deleteVehiculo(id);
        }
    }

    public void insertarVehiculo(Vehiculo v) {
        DBCRUDVehiculos.insertVehiculo(v);
    }

    public void modificarVehiculo(String matricula, Vehiculo v) {
        Integer id = DBCRUDVehiculos.getId(matricula);
        if (id != null) {
            DBCRUDVehiculos.updateVehiculo(id, v);
        }
    }

    public void insertarMantenimiento(String matricula, Date fecha) {
        Integer id = DBCRUDVehiculos.getId(matricula);
        if (id != null) {
            DBCRUDVehiculos.insertarMantenimiento(id, fecha);
        }
    }

    public List<Vehiculo> getVehiculos() {
        return DBCRUDVehiculos.getVehiculos();
    }
}

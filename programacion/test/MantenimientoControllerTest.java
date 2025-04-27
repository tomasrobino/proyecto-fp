package alquilercoches.gui;

import alquilercoches.TipoTurismo;
import alquilercoches.personal.Administrador;
import alquilercoches.vehiculos.Vehiculo;
import alquilercoches.vehiculos.Turismo;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class MantenimientoControllerTest {

    @Test
    void testCrearVista() {
        Administrador admin = new Administrador("123", "Admin");
        VehiculosController vehiculosController = new VehiculosController(admin);
        MantenimientoController mantenimientoController = new MantenimientoController(admin, vehiculosController);

        assertNotNull(mantenimientoController.getView(), "La vista no debe ser nula");
        assertNotNull(mantenimientoController.getTablaMantenimiento(), "La tabla de mantenimiento no debe ser nula");
    }

    @Test
    void testSeleccionarVehiculo() {
        // Crear un administrador y controlador de vehículos
        Administrador admin = new Administrador("123", "Admin");
        VehiculosController vehiculosController = new VehiculosController(admin);
        MantenimientoController mantenimientoController = new MantenimientoController(admin, vehiculosController);

        // Crear un vehículo de prueba
        Vehiculo vehiculo = new Turismo(TipoTurismo.LUJO, 432.4, new Date(), "fsdfds", "ff234rf", "ewfewfewf");
        admin.insertarVehiculo(vehiculo);

        // Seleccionar un vehículo
        mantenimientoController.getTablaMantenimiento().getSelectionModel().select(vehiculo);

        assertEquals(vehiculo, mantenimientoController.getTablaMantenimiento().getSelectionModel().getSelectedItem(), "El vehículo seleccionado debe ser el correcto");
    }
}

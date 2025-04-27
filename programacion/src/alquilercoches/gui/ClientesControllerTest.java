package alquilercoches.gui;

import alquilercoches.negocio.Cliente;
import alquilercoches.personal.Usuario;
import javafx.scene.layout.BorderPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientesControllerTest {

    private ClientesController controller;

    @BeforeEach
    void setUp() {
        controller = new ClientesController(null);
    }

    @Test
    void testVistaNoEsNula() {
        BorderPane vista = controller.getView();
        assertNotNull(vista, "La vista no debe ser nula");
    }

    @Test
    void testCargaClientesPorDefecto() {
        // No tenemos acceso directo a la tabla ni a los datos, pero al menos probamos que la vista esté construida.
        assertNotNull(controller.getView().getCenter(), "La tabla de clientes debería estar en el centro");
    }
}

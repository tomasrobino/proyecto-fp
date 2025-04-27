package alquilercoches.gui;

import javafx.application.Platform;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AlquilerCochesAppTest {

    private static AlquilerCochesApp app;

    @BeforeAll
    public static void init() {
        // Inicializamos JavaFX
        Platform.startup(() -> {});
        app = new AlquilerCochesApp();
    }

    @AfterAll
    public static void teardown() {
        Platform.exit();
    }

    @Test
    public void testCrearMenuSuperiorNoEsNulo() {
        MenuBar menuBar = app.crearMenuSuperior();
        assertNotNull(menuBar, "El menú superior no debería ser nulo");
        assertEquals(2, menuBar.getMenus().size(), "El menú superior debería tener dos menús");
    }

    @Test
    public void testInicioAplicacionNoLanzaErrores() {
        // Simplemente comprobar que el método se puede llamar sin excepción
        Stage stage = new Stage();
        assertDoesNotThrow(() -> app.start(stage), "El inicio de la aplicación no debería lanzar excepciones");
    }
}

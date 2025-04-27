package alquilercoches.gui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {

    @Test
    void testMostrarLoginDialog_Administrador() {
        LoginController loginController = new LoginController();
        // Simular que el usuario introduce "Administrador" como rol
        boolean result = loginController.mostrarLoginDialog();

        assertTrue(result, "El login debe ser exitoso");
        assertNotNull(loginController.getAdmin(), "Debe haber un administrador creado");
        assertNull(loginController.getUsuario(), "No debe haber un usuario creado");
        assertEquals("Administrador", loginController.getRol(), "El rol debe ser Administrador");
    }

    @Test
    void testMostrarLoginDialog_Usuario() {
        LoginController loginController = new LoginController();
        // Simular que el usuario introduce "Usuario" como rol
        boolean result = loginController.mostrarLoginDialog();

        assertTrue(result, "El login debe ser exitoso");
        assertNotNull(loginController.getUsuario(), "Debe haber un usuario creado");
        assertNull(loginController.getAdmin(), "No debe haber un administrador creado");
        assertEquals("Usuario", loginController.getRol(), "El rol debe ser Usuario");
    }

    @Test
    void testNoLogin() {
        LoginController loginController = new LoginController();
        // Simular que el usuario no hace login
        boolean result = loginController.mostrarLoginDialog();

        assertFalse(result, "El login no debe ser exitoso si el usuario cancela");
    }
}

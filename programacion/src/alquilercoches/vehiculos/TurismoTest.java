package alquilercoches.vehiculos;

import alquilercoches.TipoTurismo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

class TurismoTest {

    @Test
    void testConstructorYGetters() {
        Date fecha = new Date();
        Turismo turismo = new Turismo(TipoTurismo.MEDIANO, 70.0, fecha, "9101GHI", "Toyota", "Corolla");

        assertEquals(TipoTurismo.MEDIANO, turismo.getTipo());
        assertEquals(70.0, turismo.getPrecio());
        assertEquals("9101GHI", turismo.getMatricula());
        assertEquals("Toyota", turismo.getMarca());
        assertEquals("Corolla", turismo.getModelo());
        assertEquals(1, turismo.getMantenimientos().size());
    }

    @Test
    void testSetTipo() {
        Turismo turismo = new Turismo(TipoTurismo.MEDIANO, 70.0, new Date(), "9101GHI", "Toyota", "Corolla");
        turismo.setTipo(TipoTurismo.LUJO);
        assertEquals(TipoTurismo.LUJO, turismo.getTipo());
    }
}

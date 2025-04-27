package alquilercoches.vehiculos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

class MotocicletaTest {

    @Test
    void testConstructor() {
        Date fecha = new Date();
        Motocicleta moto = new Motocicleta(50.0, fecha, "5678DEF", "Yamaha", "YZF-R3");

        assertEquals(50.0, moto.getPrecio());
        assertEquals("5678DEF", moto.getMatricula());
        assertEquals("Yamaha", moto.getMarca());
        assertEquals("YZF-R3", moto.getModelo());
        assertEquals(1, moto.getMantenimientos().size());
    }
}

package alquilercoches.vehiculos;

import alquilercoches.TipoFurgoneta;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

class FurgonetaTest {

    @Test
    void testConstructorYGetters() {
        Date fecha = new Date();
        Furgoneta furgoneta = new Furgoneta(TipoFurgoneta.ESTANDAR, 100.0, fecha, "1234ABC", "Ford", "Transit");

        assertEquals(TipoFurgoneta.ESTANDAR, furgoneta.getTipo());
        assertEquals(100.0, furgoneta.getPrecio());
        assertEquals("1234ABC", furgoneta.getMatricula());
        assertEquals("Ford", furgoneta.getMarca());
        assertEquals("Transit", furgoneta.getModelo());
        assertEquals(1, furgoneta.getMantenimientos().size());
    }

    @Test
    void testSetTipo() {
        Furgoneta furgoneta = new Furgoneta(TipoFurgoneta.ESTANDAR, 100.0, new Date(), "1234ABC", "Ford", "Transit");
        furgoneta.setTipo(TipoFurgoneta.GRAN_CARGA);
        assertEquals(TipoFurgoneta.GRAN_CARGA, furgoneta.getTipo());
    }
}

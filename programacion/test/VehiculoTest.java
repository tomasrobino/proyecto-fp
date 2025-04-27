package alquilercoches.vehiculos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

class VehiculoTest {

    class VehiculoDummy extends Vehiculo {
        @Override
        public void calcularMantenimiento() {}
    }

    @Test
    void testAlquiler() {
        VehiculoDummy vehiculo = new VehiculoDummy();
        Date inicio = new Date();
        Date fin = new Date(inicio.getTime() + 86400000L); // +1 día

        vehiculo.setAlquiler(inicio, fin);

        assertTrue(vehiculo.isAlquilado());
        assertEquals(inicio, vehiculo.getInicioAlquiler());
        assertEquals(fin, vehiculo.getFinAlquiler());
    }

    @Test
    void testSettersYGetters() {
        VehiculoDummy vehiculo = new VehiculoDummy();
        vehiculo.setPrecio(200.0);
        vehiculo.setMatricula("XYZ789");
        vehiculo.setMarca("Kia");
        vehiculo.setModelo("Rio");

        assertEquals(200.0, vehiculo.getPrecio());
        assertEquals("XYZ789", vehiculo.getMatricula());
        assertEquals("Kia", vehiculo.getMarca());
        assertEquals("Rio", vehiculo.getModelo());
    }
}

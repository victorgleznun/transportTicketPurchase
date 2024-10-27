package uva.tds.practica3_grupo5;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;


class RecorridoTest {
	private Recorrido recorrido;
	private Recorrido recorridoTren;
	private LocalDateTime fecha;
	
	@BeforeEach
	public void Before(){
		fecha = LocalDateTime.of(2020, 4, 1, 17, 30);
		recorrido = new Recorrido ("1","Uva", "Casa", TipoTransporte.AUTOBUS, 20.0, fecha, 50, 30);
		recorridoTren = new Recorrido ("1","Uva", "Casa", TipoTransporte.TREN, 20.0, fecha, 50, 30);
	}
	
	@Test
	void testInicializarConParametrosAutobus() {
		assertNotNull(recorrido);
		assertEquals("1", recorrido.getIdentificador());
		assertEquals("Uva", recorrido.getOrigen());
		assertEquals("Casa", recorrido.getDestino());
		assertEquals(TipoTransporte.AUTOBUS, recorrido.getTipo());
		assertEquals(20.0, recorrido.getPrecio());
		assertEquals(recorrido.getFechaHora(), LocalDateTime.of(2020, 4, 1, 17, 30));
		assertEquals(50, recorrido.getPlazasDisponibles());
		assertEquals(30, recorrido.getDuracion());
	}
	
	@Test
	void testSetPrecio() {
		recorrido.setPrecio(30.0);
		assertEquals(30.0, recorrido.getPrecio());
	}
	
	@Test
	void testSetFechaHora() {
		fecha = LocalDateTime.of(2020, 5, 8, 13, 45);
		recorrido.setFechaHora(fecha);
		assertEquals(recorrido.getFechaHora(), fecha);
	}
	
	@Test
	void testSetPlazasDisponibles() {
		recorrido.setPlazasDisponibles(40);
		assertEquals(40, recorrido.getPlazasDisponibles());
	}
	
	@Test
    void testRecorridoIdentificadorNulo() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Recorrido(null, "Uva", "Casa", TipoTransporte.AUTOBUS, 20.0, fecha, 50, 30);;
        });
        assertEquals("Los parametros no pueden ser nulos", thrown.getMessage());
    }
	
	@Test
    void testRecorridoIdentificadorNoValido() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Recorrido("", "Uva", "Casa", TipoTransporte.AUTOBUS, 20.0, fecha, 50, 30);;
        });
        assertEquals("Identificador debe tener al menos un caracter", thrown.getMessage());
    }
	
	@Test
    void testRecorridoOrigenNulo() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Recorrido("1", null, "Casa", TipoTransporte.AUTOBUS, 20.0, fecha, 50, 30);;
        });
        assertEquals("Los parametros no pueden ser nulos", thrown.getMessage());
    }
	
	@Test
    void testRecorridoDestinoNulo() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Recorrido("1", "Uva", null, TipoTransporte.AUTOBUS, 20.0, fecha, 50, 30);;
        });
        assertEquals("Los parametros no pueden ser nulos", thrown.getMessage());
    }
	
	@Test
    void testRecorridoPrecioNegativo() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Recorrido("1","Uva", "Casa", TipoTransporte.AUTOBUS, -3, fecha, 50, 30);;
        });
        assertEquals("Precio debe ser un numero mayor o igual a 0", thrown.getMessage());
    }
	
	@Test
    void testRecorridoPlazasDisponiblesNegativas() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Recorrido("1","Uva", "Casa", TipoTransporte.AUTOBUS, 20.0, fecha, -1, 30);;
        });
        assertEquals("Plazas disponibles no pueden ser negativas", thrown.getMessage());
    }
	
	@Test
    void testRecorridoPlazasAutobusMayor50() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Recorrido("1","Uva", "Casa", TipoTransporte.AUTOBUS, 20.0, fecha, 56, 30);;
        });
        assertEquals("El numero de plazas debe ser un numero entre 1 y 50 para los autobuses", thrown.getMessage());
    }
	
	@Test
    void testRecorridoPlazasAutobusMenor1() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Recorrido("1","Uva", "Casa", TipoTransporte.AUTOBUS, 20.0, fecha, 0, 30);;
        });
        assertEquals("El numero de plazas debe ser un numero entre 1 y 50 para los autobuses", thrown.getMessage());
    }
	
	@Test
    void testRecorridoPlazasTrenMayor250() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Recorrido("1","Uva", "Casa", TipoTransporte.TREN, 20.0, fecha, 300, 30);;
        });
        assertEquals("El numero de plazas debe ser un numero entre 1 y 250 para los trenes", thrown.getMessage());
    }
	
	@Test
    void testRecorridoPlazasTrenMenor1() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Recorrido("1","Uva", "Casa", TipoTransporte.TREN, 20.0, fecha, 0, 30);;
        });
        assertEquals("El numero de plazas debe ser un numero entre 1 y 250 para los trenes", thrown.getMessage());
    }
	
	@Test
    void testRecorridoDuracionMayor0() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Recorrido("1","Uva", "Casa", TipoTransporte.TREN, 20.0, fecha, 50, 0);;
        });
        assertEquals("Duracion debe ser positivo y mayor que 0", thrown.getMessage());
    }
	
	
	@Test
    void testRecorridoOrigenValido() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Recorrido("1","", "Casa", TipoTransporte.TREN, 20.0, fecha, 300, 30);;
        });
        assertEquals("Origen debe tener al menos un caracter", thrown.getMessage());
    }
	
	@Test
    void testRecorridoDestinoValido() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Recorrido("1","Uva", "", TipoTransporte.TREN, 20.0, fecha, 300, 30);;
        });
        assertEquals("Destino debe tener al menos un caracter", thrown.getMessage());
    }
	
	@Test
	void testRecorridoIgual() {
		assertTrue(recorrido.isEquals(recorridoTren));
	}
	
	@Test
	void testRecorridoNoIgual() {
		assertFalse(recorrido.isEquals(new Recorrido ("2","Uva", "Casa", TipoTransporte.AUTOBUS, 20.0, fecha, 50, 30)));
	}
	
	@Test
    void testRecorridoSetPlazasMenor0() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            recorrido.setPlazasDisponibles(-1);;
        });
        assertEquals("El numero de plazas no puede ser un numero fuera del rango [0,250]", thrown.getMessage());
    }
	
	@Test
    void testRecorridoSetPlazasMayor250() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            recorrido.setPlazasDisponibles(300);;
        });
        assertEquals("El numero de plazas no puede ser un numero fuera del rango [0,250]", thrown.getMessage());
    }
	
	@Test
    void testRecorridoSetPrecioNegativo() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            recorrido.setPrecio(-2);;
        });
        assertEquals("Precio debe ser un numero mayor o igual a 0", thrown.getMessage());
    }
	
}

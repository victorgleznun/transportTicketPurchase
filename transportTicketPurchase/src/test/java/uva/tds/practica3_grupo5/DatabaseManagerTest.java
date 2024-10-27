package uva.tds.practica3_grupo5;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DatabaseManagerTest {
	
	private DatabaseManager base;
	private Usuario usuario;
	private Recorrido recorrido;
	private LocalDateTime fecha;
	private Recorrido recorridoTren;
	private Billete billete;

	@BeforeEach
	void setUp() {
		fecha = LocalDateTime.of(2020, 4, 1, 17, 30);
		base = new DatabaseManager();
		usuario = new Usuario("97828568T", "Gustavo");
		recorrido = new Recorrido ("1","Uva", "Casa", TipoTransporte.AUTOBUS, 20.0, fecha, 45, 30);
		billete = new Billete("12", usuario, recorrido, false);
		base.addRecorrido(recorrido);
		base.addUsuario(usuario);
		base.addBillete(billete);
		recorridoTren = new Recorrido("2","Barcelona", "Valencia", TipoTransporte.TREN, 60.0, LocalDateTime.of(2022, 5, 1, 18, 30), 35, 120);
	}
	
	@AfterEach
	void tearDown() {
		base.clearDatabase();
	}

	
	@Test
	void testSistemaAgregarRecorrido() {
		assertTrue(base.getRecorridos().get(0).isEquals(recorrido));
	}
	
	@Test
	void testGetRecorridoIdNulo() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
	        base.getRecorrido(null);;
	    });
	    assertEquals("id es nulo", thrown.getMessage());
	}
	
	
	@Test
    void testAgregarRecorridoNulo() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
        	base.addRecorrido(null);;
        });
        assertEquals("Recorrido es nulo", thrown.getMessage());
    }
	
	@Test
    void testAgregarRecorridoExistente() {
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
        	base.addRecorrido(recorrido);;
        });
        assertEquals("Ya existe un recorrido con el id proporcionado", thrown.getMessage());
    }
	
	@Test
    void testEliminarRecorridoNulo() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
        	base.eliminarRecorrido(null);;
        });
        assertEquals("id es nulo", thrown.getMessage());
    }
	
	@Test
	void testEliminarRecorrido() {
		assertEquals(1, base.getRecorridos().size());
		base.eliminarRecorrido("1");
		assertEquals(0, base.getRecorridos().size());
	}
	
	@Test
    void testActualizarRecorridoNulo() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
        	base.actualizarRecorrido(null);;
        });
        assertEquals("Recorrido es nulo", thrown.getMessage());
    }
	
	@Test
    void testActualizarRecorridoInexistente() {
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
        	base.actualizarRecorrido(new Recorrido ("23","Uva", "Casa", TipoTransporte.AUTOBUS, 20.0, LocalDateTime.of(2021, 4, 1, 17, 30), 45, 30));;
        });
        assertEquals("No existe un recorrido con el id proporcionado", thrown.getMessage());
    }
	
	@Test
	void testActualizarReorrido() {
		Recorrido nuevo = new Recorrido ("1","Uva", "Casa", TipoTransporte.AUTOBUS, 20.0, LocalDateTime.of(2021, 4, 1, 17, 30), 45, 30);
		base.actualizarRecorrido(nuevo);
		assertEquals(base.getRecorrido("1").getFechaHora(), LocalDateTime.of(2021, 4, 1, 17, 30));
	}
	
	@Test
	void testGetRecorridos() {
		base.addRecorrido(recorridoTren);
		assertEquals(2, base.getRecorridos().size());
		assertTrue(base.getRecorridos().get(1).isEquals(recorridoTren));
	}
	
	@Test
	void testGetRecorridosFecha() {
		base.addRecorrido(recorridoTren);
		assertTrue(base.getRecorridos(recorrido.getFechaHora().toLocalDate()).get(0).isEquals(recorrido));
	}
	
	@Test
	void testGetUsuarioNulo() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
        	base.getUsuario(null);;
        });
        assertEquals("id es nulo", thrown.getMessage());
	}
	
	@Test
	void testGetUsuario() {
		assertTrue(base.getUsuario(usuario.getNif()).isEquals(usuario));
	}
	
	@Test
	void testAddUsuarioNulo() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
        	base.addUsuario(null);;
        });
        assertEquals("Usuario es nulo", thrown.getMessage());
	}
	
	@Test
	void testAddUsuarioExistente() {
		IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
        	base.addUsuario(usuario);;
        });
        assertEquals("Ya existe un usuario", thrown.getMessage());
	}
	
	@Test
	void testEliminarUsuarioNulo() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
        	base.eliminarUsuario(null);;
        });
        assertEquals("Id es nulo", thrown.getMessage());
	}
	
	@Test
	void testEliminarUsuario() {
		Usuario otroUsuario = new Usuario("58447675Y", "Victor");
		base.addUsuario(otroUsuario);
		base.eliminarUsuario("58447675Y");
		base.addUsuario(otroUsuario);
		assertTrue(base.getUsuario("58447675Y").isEquals(otroUsuario));
	}
	
	@Test
	void testActualizarUsuarioNulo() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
        	base.actualizarUsuario(null);;
        });
        assertEquals("Usuario es nulo", thrown.getMessage());
	}
	
	@Test
	void testActualizarUsuarioInexistente() {
		IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
        	base.actualizarUsuario(new Usuario("71322835L", "Asi"));;
        });
        assertEquals("No existe un usuario con el id proporcionado", thrown.getMessage());
	}
	
	@Test
	void testActualizarUsuario() {
		Usuario nuevo = new Usuario("97828568T", "Izan");
		base.actualizarUsuario(nuevo);
		assertEquals("Izan", base.getUsuario(nuevo.getNif()).getNombre());
	}
	
	@Test
	void testGetBilletes() {
		assertEquals(1, base.getBilletes(billete.getLocalizador()).size());
		assertTrue(base.getBilletes(billete.getLocalizador()).get(0).isEquals(billete));
	}
	
	@Test
	void testAddBilleteNulo() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
        	base.addBillete(null);;
        });
        assertEquals("Billete es nulo", thrown.getMessage());
	}
	
	@Test
	void testAddBillete() {
		assertEquals(1, base.getBilletes(billete.getLocalizador()).size());
		base.addBillete(new Billete("13", usuario, recorrido, false));
		assertEquals(1, base.getBilletes("13").size());
	}
	
	@Test
	void testEliminarBilleteNulo() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
        	base.eliminarBilletes(null);;
        });
        assertEquals("Localizador es nulo", thrown.getMessage());
	}
	
	@Test
	void testEliminarBillete() {
		assertEquals(1, base.getBilletes(billete.getLocalizador()).size());
		base.eliminarBilletes(billete.getLocalizador());
		assertEquals(0, base.getBilletes(billete.getLocalizador()).size());
	}
	
	@Test
	void testActualizarBilleteNulo() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
        	base.actualizarBilletes(null);;
        });
        assertEquals("Billete es nulo", thrown.getMessage());
	}
	
	@Test
	void testActualizarBilleteInexistente() {
		IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
        	base.actualizarBilletes(new Billete("123", usuario, recorrido, false));;
        });
        assertEquals("No existe ningun billete con el localizador proporcionado", thrown.getMessage());
	}
	
	@Test
	void testActualizarBillete() {
		base.actualizarBilletes(new Billete("12", usuario, recorrido, true));
		assertTrue(base.getBilletes("12").get(0).isReservado());
	}
	
	@Test
	void testGetBilletesRecorrido() {
		assertEquals(1, base.getBilletesDeRecorrido(recorrido.getIdentificador()).size());
		assertEquals(0, base.getBilletesDeRecorrido(recorridoTren.getIdentificador()).size());
	}
	
	@Test
	void testGetBilletesUsuario() {
		Usuario nuevoUsuario = new Usuario("71322835L", "Asier");
		base.addUsuario(nuevoUsuario);
		Billete nuevoBillete = new Billete("1234", nuevoUsuario, recorrido, false);
		base.addBillete(nuevoBillete);
		assertEquals(1, base.getBilletesDeUsuario(usuario.getNif()).size());
	}
	
}

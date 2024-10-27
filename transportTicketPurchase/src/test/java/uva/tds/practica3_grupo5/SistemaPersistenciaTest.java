package uva.tds.practica3_grupo5;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Tag;

import org.easymock.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


class SistemaPersistenciaTest {
	
	private Billete billete;
	private Recorrido recorrido;
	private Usuario usuario;
	private LocalDateTime fecha;
	private LocalDateTime otraFecha;
	private Recorrido otroRecorrido;
	
	
	@TestSubject
	private SistemaPersistencia sistemaPersistenciaTest;
	
	@Mock
	private IDatabaseManager IDatabaseManager;

	@BeforeEach
	void setUp() {
		IDatabaseManager = EasyMock.createMock(IDatabaseManager.class);
		sistemaPersistenciaTest = new SistemaPersistencia(IDatabaseManager);
		
		usuario = new Usuario("97828568T", "Gustavo");
		fecha = LocalDateTime.of(2020, 4, 1, 17, 30);
		otraFecha = LocalDateTime.of(2021, 4, 3, 20, 25);
		recorrido = new Recorrido ("1","Uva", "Casa", TipoTransporte.AUTOBUS, 20.0, fecha, 45, 30);
		billete = new Billete ("12345", usuario, recorrido, false);
		otroRecorrido = new Recorrido ("1","Uva", "Casa", TipoTransporte.AUTOBUS, 20.0, otraFecha, 45, 30);
	}

	@Test
	@Tag("Cobertura")
	void testAddRecorrido() {
		IDatabaseManager.addRecorrido(recorrido);
		EasyMock.expectLastCall();
		EasyMock.expect(IDatabaseManager.getRecorrido("1")).andReturn(recorrido).times(1);
		EasyMock.replay(IDatabaseManager);
		sistemaPersistenciaTest.addRecorrido(recorrido);
		assertEquals("1", sistemaPersistenciaTest.getRecorrido("1").getIdentificador());
		EasyMock.verify(IDatabaseManager);
	}
	
	@Test
	@Tag("Cobertura")
	void testAddRecorridoExistente() {
		IDatabaseManager.addRecorrido(recorrido);
		EasyMock.expectLastCall();
		IDatabaseManager.addRecorrido(recorrido);
		EasyMock.expectLastCall().andThrow(new IllegalStateException());
		EasyMock.replay(IDatabaseManager);
		sistemaPersistenciaTest.addRecorrido(recorrido);
		assertThrows(IllegalStateException.class, () -> {
			sistemaPersistenciaTest.addRecorrido(recorrido);
		});
		EasyMock.verify(IDatabaseManager);
	}
	
	@Test
	@Tag("Cobertura")
	void testAddRecorridoNulo() {
		IDatabaseManager.addRecorrido(null);
		EasyMock.expectLastCall().andThrow(new IllegalArgumentException());
		EasyMock.replay(IDatabaseManager);
		assertThrows(IllegalArgumentException.class, () -> {
			sistemaPersistenciaTest.addRecorrido(null);
		});
		EasyMock.verify(IDatabaseManager);
	}
	
	@Test
	@Tag("Cobertura")
	void testEliminarRecorrido() {
		IDatabaseManager.addRecorrido(recorrido);
		EasyMock.expectLastCall();
		IDatabaseManager.eliminarRecorrido("1");
		EasyMock.expectLastCall();
		EasyMock.expect(IDatabaseManager.getRecorrido("1")).andReturn(null).times(1);
		EasyMock.replay(IDatabaseManager);
		sistemaPersistenciaTest.addRecorrido(recorrido);
		sistemaPersistenciaTest.eliminarRecorrido("1");
		assertNull(sistemaPersistenciaTest.getRecorrido("1"));
		EasyMock.verify(IDatabaseManager);
	}

	@Test
	@Tag("Cobertura")
	void testEliminarRecorridoNulo() {
		IDatabaseManager.eliminarRecorrido(null);
		EasyMock.expectLastCall().andThrow(new IllegalArgumentException());
		EasyMock.replay(IDatabaseManager);
		assertThrows(IllegalArgumentException.class, () -> {
			sistemaPersistenciaTest.eliminarRecorrido(null);
		});
		EasyMock.verify(IDatabaseManager);
	}
	
	@Test
	@Tag("Cobertura")
	void testActualizarRecorrido() {
		IDatabaseManager.addRecorrido(recorrido);
		EasyMock.expectLastCall();
		IDatabaseManager.actualizarRecorrido(EasyMock.anyObject(Recorrido.class));
		EasyMock.expectLastCall();
		EasyMock.expect(IDatabaseManager.getRecorrido("1")).andReturn(otroRecorrido).times(2);
		EasyMock.replay(IDatabaseManager);
		sistemaPersistenciaTest.addRecorrido(recorrido);
		sistemaPersistenciaTest.actualizarRecorrido(otraFecha, "1");
		assertTrue(otraFecha.isEqual(sistemaPersistenciaTest.getRecorrido("1").getFechaHora()));
		EasyMock.verify(IDatabaseManager);
	}
	
	@Test
	@Tag("Cobertura")
	void testActualizarRecorridoNoExiste() {
		IDatabaseManager.addRecorrido(recorrido);
		EasyMock.expectLastCall();
		EasyMock.expect(IDatabaseManager.getRecorrido("234567")).andReturn(otroRecorrido).times(1);
		EasyMock.expectLastCall();
		IDatabaseManager.actualizarRecorrido(EasyMock.anyObject(Recorrido.class));
		EasyMock.expectLastCall().andThrow(new IllegalStateException());
		EasyMock.replay(IDatabaseManager);
		sistemaPersistenciaTest.addRecorrido(recorrido);
		assertThrows(IllegalStateException.class, () -> {
			sistemaPersistenciaTest.actualizarRecorrido(otraFecha, "234567");
		});
		EasyMock.verify(IDatabaseManager);
	}
	
	@Test
    void testActualizarRecorridoEsNulo() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
        	sistemaPersistenciaTest.actualizarRecorrido(null, "1");
        });
        assertEquals("La fecha y la hora no pueden ser nulo", thrown.getMessage());
    }
	
	@Test
	@Tag("Cobertura")
	void testComprarBillete() {
		ArrayList<Billete> expectedLista = new ArrayList<>();
		expectedLista.add(billete);
		expectedLista.add(billete);
		expectedLista.add(billete);
		
		IDatabaseManager.addBillete(EasyMock.anyObject(Billete.class));
		EasyMock.expectLastCall().times(3);
		EasyMock.expect(IDatabaseManager.getRecorrido("1")).andReturn(recorrido).times(3);
		IDatabaseManager.actualizarRecorrido(EasyMock.anyObject(Recorrido.class));
		EasyMock.expectLastCall().times(3);
		EasyMock.expect(IDatabaseManager.getBilletes("12345")).andReturn(expectedLista).times(1);
		
		EasyMock.replay(IDatabaseManager);
		List<Billete> resultado = new ArrayList<>();
		resultado = sistemaPersistenciaTest.getBilletesAsociados("12345");
		
		sistemaPersistenciaTest.comprarBillete("12345", usuario, recorrido, 3);
		assertEquals(3, resultado.size());
		EasyMock.verify(IDatabaseManager);
	}
	
	@Test
    void testComprarBilleteUsuarioNulo() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
        	sistemaPersistenciaTest.comprarBillete("1234", null, recorrido, 3);
        });
        assertEquals("El usuario no puede ser nulo", thrown.getMessage());
    }
	
	@Test
    void testComprarBilleteRecorridoNulo() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
        	sistemaPersistenciaTest.comprarBillete("1234", usuario, null, 3);
        });
        assertEquals("El recorrido no puede ser nulo", thrown.getMessage());
	}
	
	@Test
    void testComprarBilleteNumBilletesIncorrecto() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
        	sistemaPersistenciaTest.comprarBillete("1234", usuario, recorrido, -1);
        });
        assertEquals("Se debe comprar al menos un billete", thrown.getMessage());
    }
	
	@Test
    void testComprarBilleteNumBilletesMasQuePlazas() {
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
        	sistemaPersistenciaTest.comprarBillete("1234", usuario, recorrido, 300);
        });
        assertEquals("No hay suficientes plazas disponibles", thrown.getMessage());
    }
	
	@Test
	void testComprarBilleteLocalizadorNulo() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
        	sistemaPersistenciaTest.comprarBillete(null, usuario, recorrido, 25);
        });
        assertEquals("El localizador no puede ser nulo", thrown.getMessage());
	}
	
	@Test
	@Tag("Cobertura")
	void testDevolverBillete() {
		ArrayList<Billete> expectedLista = new ArrayList<>();
		expectedLista.add(billete);
		expectedLista.add(billete);
		expectedLista.add(billete);
		
		EasyMock.expect(IDatabaseManager.getBilletes("12345")).andReturn(expectedLista).times(1);
		IDatabaseManager.eliminarBilletes("12345");
		EasyMock.expectLastCall().times(1);
		EasyMock.expect(IDatabaseManager.getRecorrido("1")).andReturn(recorrido).times(5);
		IDatabaseManager.actualizarRecorrido(EasyMock.anyObject(Recorrido.class));
		EasyMock.expectLastCall().times(5);
		IDatabaseManager.addBillete(EasyMock.anyObject(Billete.class));
		EasyMock.expectLastCall().times(4);
		
		EasyMock.replay(IDatabaseManager);
		
		sistemaPersistenciaTest.comprarBillete("12345", usuario, recorrido, 3);
		sistemaPersistenciaTest.devolverBillete("12345", 2);
		EasyMock.verify(IDatabaseManager);
	}
	
	@Test
	void testDevolverBilleteLocalizadorNulo() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			sistemaPersistenciaTest.devolverBillete(null, 3);
        });
        assertEquals("Localizador no puede ser nulo", thrown.getMessage());
	}
	
	@Test
	void testDevolverBilleteNumBilleteInvalido() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			sistemaPersistenciaTest.devolverBillete("0987", -1);
        });
        assertEquals("Numero de billetes no puede ser 0 o menor", thrown.getMessage());
	}
	
	@Test
	@Tag("Cobertura")
	void testDevolverBilleteDemasiadosBilletes() {
		ArrayList<Billete> expectedLista = new ArrayList<>();
		expectedLista.add(billete);
		
		EasyMock.expect(IDatabaseManager.getBilletes("12345")).andReturn(expectedLista).times(1);
		
		EasyMock.replay(IDatabaseManager);
		
		assertThrows(IllegalStateException.class, () -> {
			sistemaPersistenciaTest.devolverBillete("12345", 2);
		});
		EasyMock.verify(IDatabaseManager);
	}
	
	
	@Test
	@Tag("Cobertura")
	void testCompraBilletesReservados() {
		ArrayList<Billete> expectedLista = new ArrayList<>();
		expectedLista.add(new Billete("0987", usuario, recorrido, true));
		expectedLista.add(new Billete("0987", usuario, recorrido, false));
		expectedLista.add(new Billete("0987", usuario, recorrido, true));
		
		
		EasyMock.expect(IDatabaseManager.getBilletes("0987")).andReturn(expectedLista).times(2);
		IDatabaseManager.eliminarBilletes("0987");
		EasyMock.expectLastCall().times(1);
		IDatabaseManager.addBillete(EasyMock.anyObject(Billete.class));
		EasyMock.expectLastCall().times(3);
		
		EasyMock.replay(IDatabaseManager);
		List<Billete> resultado = new ArrayList<>();
		resultado = sistemaPersistenciaTest.getBilletesAsociados("0987");
		
		sistemaPersistenciaTest.compraBilleteReservado("0987");
		assertEquals(3, resultado.size());
		assertFalse(resultado.get(0).isReservado());
		assertFalse(resultado.get(1).isReservado());
		assertFalse(resultado.get(2).isReservado());
		
		EasyMock.verify(IDatabaseManager);
	}
	
	@Test
	void testCompraBilletesReservadosConLocalizadorNulo() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			sistemaPersistenciaTest.compraBilleteReservado(null);
        });
        assertEquals("Localizador no puede ser nulo", thrown.getMessage());
	}
	
	@Test
	@Tag("Cobertura")
	void testReservarBillete() {
		ArrayList<Billete> expectedLista = new ArrayList<>();
		expectedLista.add(new Billete("0987", usuario, recorrido, true));
		expectedLista.add(new Billete("0987", usuario, recorrido, true));
		expectedLista.add(new Billete("0987", usuario, recorrido, true));
		
		EasyMock.expect(IDatabaseManager.getRecorrido("1")).andReturn(recorrido).times(3);
		EasyMock.expect(IDatabaseManager.getBilletes("0987")).andReturn(expectedLista).times(1);
		IDatabaseManager.addBillete(EasyMock.anyObject(Billete.class));
		EasyMock.expectLastCall().times(3);
		IDatabaseManager.actualizarRecorrido(EasyMock.anyObject(Recorrido.class));
		EasyMock.expectLastCall().times(3);
		
		EasyMock.replay(IDatabaseManager);
		
		List<Billete> resultado = new ArrayList<>();
		
		sistemaPersistenciaTest.reservarBillete("0987", usuario, recorrido, 3);
		resultado = sistemaPersistenciaTest.getBilletesAsociados("0987");
		assertEquals(3, resultado.size());
		assertTrue(resultado.get(0).isReservado());
		assertTrue(resultado.get(1).isReservado());
		assertTrue(resultado.get(2).isReservado());
		EasyMock.verify(IDatabaseManager);
	}
	
	@Test
	@Tag("Cobertura")
	void testReservarBilleteTren() {
		Recorrido recorridoTren = new Recorrido ("1","Uva", "Casa", TipoTransporte.TREN, 10.0, fecha, 175, 30);
		ArrayList<Billete> expectedLista = new ArrayList<>();
		expectedLista.add(new Billete("0987", usuario, recorridoTren, true));
		expectedLista.add(new Billete("0987", usuario, recorridoTren, true));
		expectedLista.add(new Billete("0987", usuario, recorridoTren, true));
		
		EasyMock.expect(IDatabaseManager.getRecorrido("1")).andReturn(recorridoTren).times(3);
		EasyMock.expect(IDatabaseManager.getBilletes("0987")).andReturn(expectedLista).times(1);
		IDatabaseManager.addBillete(EasyMock.anyObject(Billete.class));
		EasyMock.expectLastCall().times(3);
		IDatabaseManager.actualizarRecorrido(EasyMock.anyObject(Recorrido.class));
		EasyMock.expectLastCall().times(3);
		
		EasyMock.replay(IDatabaseManager);
		
		List<Billete> resultado = new ArrayList<>();
		
		sistemaPersistenciaTest.reservarBillete("0987", usuario, recorridoTren, 3);
		resultado = sistemaPersistenciaTest.getBilletesAsociados("0987");
		assertEquals(3, resultado.size());
		assertTrue(resultado.get(0).isReservado());
		assertTrue(resultado.get(1).isReservado());
		assertTrue(resultado.get(2).isReservado());
		EasyMock.verify(IDatabaseManager);
	}
	
	@Test
	void testReservarBilleteNumBilletesInvalido() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			sistemaPersistenciaTest.reservarBillete("12345", usuario, otroRecorrido, -2);
        });
        assertEquals("Se debe reservar al menos un billete", thrown.getMessage());
	}
	
	@Test
	void testReservarBilleteMasQueDisponibles() {
		IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
			sistemaPersistenciaTest.reservarBillete("12345", usuario, otroRecorrido, 200);
        });
        assertEquals("No se pueden reservar mas plazas que las disponibles", thrown.getMessage());
	}
	
	@Test
	void testReservarBilleteMenosDisponiblesQueLaMitadDeTotalesAutobus() {
		Recorrido masRecorrido = new Recorrido ("1","Uva", "Casa", TipoTransporte.AUTOBUS, 20.0, fecha, 20, 30);
		IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
			sistemaPersistenciaTest.reservarBillete("12345", usuario, masRecorrido, 3);
        });
        assertEquals("El numero de plazas disponibles es menor que la mitad de plazas totales", thrown.getMessage());
	}
	
	@Test
	void testReservarBilleteMenosDisponiblesQueLaMitadDeTotalesTren() {
		Recorrido masRecorrido = new Recorrido ("1","Uva", "Casa", TipoTransporte.TREN, 20.0, fecha, 20, 30);
		IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
			sistemaPersistenciaTest.reservarBillete("12345", usuario, masRecorrido, 3);
        });
        assertEquals("El numero de plazas disponibles es menor que la mitad de plazas totales", thrown.getMessage());
	}
	
	@Test
	@Tag("Cobertura")
	void testAnularReserva() {
		ArrayList<Billete> expectedLista = new ArrayList<>();
		expectedLista.add(new Billete("0987", usuario, recorrido, true));
		expectedLista.add(new Billete("0987", usuario, recorrido, false));
		
		EasyMock.expect(IDatabaseManager.getRecorrido("1")).andReturn(recorrido).times(4);
		EasyMock.expect(IDatabaseManager.getBilletes("0987")).andReturn(expectedLista).times(2);
		IDatabaseManager.addBillete(EasyMock.anyObject(Billete.class));
		EasyMock.expectLastCall().times(4);
		IDatabaseManager.actualizarRecorrido(EasyMock.anyObject(Recorrido.class));
		EasyMock.expectLastCall().times(4);
		IDatabaseManager.eliminarBilletes("0987");
		EasyMock.expectLastCall().times(1);
		
		EasyMock.replay(IDatabaseManager);
		
		List<Billete> resultado = new ArrayList<>();
		
		sistemaPersistenciaTest.reservarBillete("0987", usuario, recorrido, 3);
		sistemaPersistenciaTest.anularReserva("0987", 2);
		resultado = sistemaPersistenciaTest.getBilletesAsociados("0987");
		assertEquals(1, resultado.size());
		assertTrue(resultado.get(0).isReservado());
		EasyMock.verify(IDatabaseManager);
	}
	
	@Test
	void testAnularReservaConLocalizadorNulo() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			sistemaPersistenciaTest.anularReserva(null, 3);
        });
        assertEquals("Localizador no puede ser nulo", thrown.getMessage());
	}
	
	@Test
	void testAnularReservaConNumBilletesInvalido() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			sistemaPersistenciaTest.anularReserva("12345", -1);
        });
        assertEquals("Numero de billetes no puede ser 0 o menor", thrown.getMessage());
	}
	
	@Test
	@Tag("Cobertura")
	void precioBilletesUsuario() {
		Recorrido masRecorrido = new Recorrido ("1","Uva", "Casa", TipoTransporte.TREN, 10.0, fecha, 100, 30);
		Billete otroBillete = new Billete("12345", usuario, masRecorrido, false);
		ArrayList<Billete> expectedLista = new ArrayList<>();
		expectedLista.add(billete);
		expectedLista.add(otroBillete);
		
		EasyMock.expect(IDatabaseManager.getBilletesDeUsuario("97828568T")).andReturn(expectedLista).times(1);
		
		EasyMock.replay(IDatabaseManager);
		
		assertEquals(29.0, sistemaPersistenciaTest.precioBilletesUsuario("97828568T"));
		EasyMock.verify(IDatabaseManager);
	}
	
	@Test
	void testPrecioBilletesUsuarioNifNulo() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			sistemaPersistenciaTest.precioBilletesUsuario(null);
        });
        assertEquals("El dni del usuario no puede ser nulo", thrown.getMessage());
	}
	
	@Test
	@Tag("Cobertura")
	void testRecorridoFecha() {
		LocalDate masFecha = fecha.toLocalDate();
		ArrayList<Recorrido> expectedLista = new ArrayList<>();
		expectedLista.add(recorrido);
		expectedLista.add(recorrido);
		
		EasyMock.expect(IDatabaseManager.getRecorridos(masFecha)).andReturn(expectedLista).times(1);
		
		EasyMock.replay(IDatabaseManager);
		
		List<Recorrido> resultado = sistemaPersistenciaTest.recorridoFecha(masFecha);
		
		assertTrue(resultado.get(0).getFechaHora().toLocalDate().isEqual(masFecha));
		assertTrue(resultado.get(1).getFechaHora().toLocalDate().isEqual(masFecha));
		EasyMock.verify(IDatabaseManager);
	}
	
	


}
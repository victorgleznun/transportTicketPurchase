package uva.tds.practica3_grupo5;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class SistemaTest{
	private Sistema sistema;
	private Usuario usuario;
	private Recorrido recorrido;
	private LocalDateTime fecha;
	private Recorrido recorridoMediterraneo;
	private Recorrido recorridoTren;

	@BeforeEach
	void setUp() {
		fecha = LocalDateTime.of(2020, 4, 1, 17, 30);
		sistema = new Sistema();
		usuario = new Usuario("97828568T", "Gustavo");
		recorrido = new Recorrido ("1","Uva", "Casa", TipoTransporte.AUTOBUS, 20.0, fecha, 45, 30);
		recorridoMediterraneo = new Recorrido("2","Barcelona", "Valencia", TipoTransporte.AUTOBUS, 60.0, LocalDateTime.of(2022, 5, 1, 18, 30), 35, 120);
		sistema.addRecorrido(recorrido);
		recorridoTren = new Recorrido("2","Barcelona", "Valencia", TipoTransporte.TREN, 60.0, LocalDateTime.of(2022, 5, 1, 18, 30), 35, 120);
	}
	
	@Test
	void testSistemaAgregarRecorrido() {
		assertTrue(sistema.getRecorridos().get(0).isEquals(recorrido));
	}
	
	@Test
	void testSistemaComprarBillete() {
		sistema.comprarBillete("12", usuario, recorrido, 1);
		assertEquals("12", sistema.getBilletes().get(0).getLocalizador());
	}
	
	@Test
    void testAgregarRecorridoNoValido() {
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
        	sistema.addRecorrido(new Recorrido("1","Uva", "Casa", TipoTransporte.AUTOBUS, 20.0, fecha, 45, 30));;
        });
        assertEquals("Recorrido ya existente en el sistema", thrown.getMessage());
    }
	
	@Test
    void testAgregarRecorridoIdNulo() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
        	sistema.addRecorrido(null);;
        });
        assertEquals("El recorrido no puede ser nulo", thrown.getMessage());
    }
	
	@Test
    void testEliminarRecorridoIdNulo() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
        	sistema.eliminarRecorrido(null);;
        });
        assertEquals("El identificador no puede ser nulo", thrown.getMessage());
    }
	
	@Test
	void testEliminarRecorrido() {
		assertEquals(1, sistema.getRecorridos().size());
		sistema.eliminarRecorrido(recorrido.getIdentificador());
		assertEquals(sistema.getRecorridos().size(), 0);
	}
	
	@Test
    void testEliminarRecorridoConBilleteAsociado() {
		sistema.comprarBillete("12", usuario, recorrido, 1);
		sistema.addRecorrido(recorridoMediterraneo);
		sistema.comprarBillete("15", usuario, recorridoMediterraneo, 1);
		IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
        	sistema.eliminarRecorrido(recorrido.getIdentificador());;
        });
		assertEquals("Recorrido seleccionado con billete asociado", thrown.getMessage());
    }
	
	@Test
    void testEliminarRecorridoNoExistente() {
		IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
        	sistema.eliminarRecorrido("34");;
        });
		assertEquals("Recorrido no existente", thrown.getMessage());
    }
	
	@Test
	void testActualizarFechaHora() {
		sistema.actualizarRecorrido(LocalDateTime.of(2020, 4, 1, 18, 30), "1");
		assertEquals(sistema.getRecorridos().get(0).getFechaHora(), LocalDateTime.of(2020, 4, 1, 18, 30));
	}
	
	@Test
	void testActualizarFechaHoraIdNull() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			sistema.actualizarRecorrido(LocalDateTime.of(2020, 4, 1, 18, 30), null);;
		});
		assertEquals("El identificador no puede ser nulo", thrown.getMessage());
	}
	
	@Test
	void testActualizarFechaHoraNoEncontrado() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			sistema.actualizarRecorrido(LocalDateTime.of(2020, 4, 1, 18, 30), "23");;
		});
		assertEquals("Recorrido no existe en el sistema", thrown.getMessage());
	}

	@Test
	void testReservarBillete() {
		sistema.reservarBillete("ri6gh5", usuario, recorrido, 3);
		assertEquals("ri6gh5", sistema.getBilletes().get(0).getLocalizador());
		assertTrue(sistema.getBilletes().get(0).isReservado());
		assertEquals(42, recorrido.getPlazasDisponibles());
	}
	
	@Test
	void testReservarBilleteMalConActualesMenorQueLaMitadDeTotales() {
		sistema.addRecorrido(recorridoTren);
		IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
			sistema.reservarBillete("ri6gh5", usuario, recorridoTren, 4);;
		});
		assertEquals("No se pueden reservar billetes por debajo del limite de reserva", thrown.getMessage());
	}
	
	@Test
	void testReservarBilleteMalConNumeroDeBilletesMayorQueDisponibles() {
		IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
			sistema.reservarBillete("ri6gh5", usuario, recorrido, 54);;
		});
		assertEquals("No se pueden reservar mas billetes que plazas disponibles", thrown.getMessage());
	}
	
	@Test
	void testReservarBilleteMalConNumeroDeBilletesMenor1() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			sistema.reservarBillete("ri6gh5", usuario, recorrido, -5);;
		});
		assertEquals("Numero billetes no puede ser menor que 1", thrown.getMessage());
	}
	
	@Test
	void testReservarBilleteMalLocalizadorNulo() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			sistema.reservarBillete(null, usuario, recorrido, 2);;
		});
		assertEquals("Localizador no puede ser nulo", thrown.getMessage());
	}
	
	@Test
	void testReservarBilleteMalUsuarioNulo() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			sistema.reservarBillete("ri6gh5", null, recorrido, 2);;
		});
		assertEquals("Usuario no puede ser nulo", thrown.getMessage());
	}
	
	@Test
	void testReservarBilleteMalRecorridoNulo() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			sistema.reservarBillete("ri6gh5", usuario, null, 2);;
		});
		assertEquals("Recorrido no puede ser nulo", thrown.getMessage());
	}
	
	@Test
	void testReservarBilleteMalConRecorridoNoRegistradoEnElSistema() {
		IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
			sistema.reservarBillete("ri6gh5", usuario, recorridoMediterraneo, 3);;
		});
		assertEquals("Recorrido no existe en el sistema", thrown.getMessage());
	}
	
	
	@Test
	void testAnularReserva() {
		sistema.addRecorrido(recorridoMediterraneo);
		sistema.comprarBillete("r5", usuario, recorrido, 1);
		sistema.comprarBillete("ri6gh5", usuario, recorrido, 1);
		sistema.reservarBillete("ri6gh5", usuario, recorrido, 3);
		sistema.anularReserva("ri6gh5", 3);
		assertEquals(43, recorrido.getPlazasDisponibles());
	}
	
	@Test
	void testAnularReservaIdNulo() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			sistema.anularReserva(null, 2);;
		});
		assertEquals("Localizador no puede ser nulo", thrown.getMessage());
	}
	
	@Test
	void testAnularReservaNBilletesMenor1() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			sistema.anularReserva("ri6gh5", 0);;
		});
		assertEquals("Numero de billetes no puede ser 0 o menor", thrown.getMessage());
	}
	
	@Test
	void testAnularReservaMasBilletesQueReservas() {
		sistema.reservarBillete("ri6gh5", usuario, recorrido, 3);
		IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
			sistema.anularReserva("ri6gh5", 7);;
		});
		assertEquals("No se pueden devolver mas billetes de los reservados", thrown.getMessage());
	}
	
	@Test
	void testAnularReservaNoHayReservasQueAnular() {
		IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
			sistema.anularReserva("aaa3e", 1);
		});
		assertEquals("No hay billetes reservados", thrown.getMessage());
	}
	
	@Test
	void testComprarBillete() {
		sistema.comprarBillete("12345", usuario, recorrido, 2);
		assertEquals(43, recorrido.getPlazasDisponibles());
	}
	
	@Test
	void testComprarBilleteLocalizadorNulo() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->{
			sistema.comprarBillete(null, usuario, recorrido, 58);;
		});
		assertEquals ("Localizador no puede ser nulo",thrown.getMessage());
	}
	
	@Test
	void testComprarBilleteRecorridoInexistente() {
		IllegalStateException thrown = assertThrows(IllegalStateException.class, () ->{
			sistema.comprarBillete("12", usuario, recorridoMediterraneo, 58);;
		});
		assertEquals ("Recorrido no existe en el sistema",thrown.getMessage());
	}
	
	@Test
	void testComprarBilleteUsuarioNulo() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->{
			sistema.comprarBillete("123", null, recorrido, 58);;
		});
		assertEquals ("Usuario no puede ser nulo",thrown.getMessage());
	}
	
	@Test
	void testComprarBilleteRecorridoNulo() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->{
			sistema.comprarBillete("123", usuario, null, 58);;
		});
		assertEquals ("Recorrido no puede ser nulo",thrown.getMessage());
	}
	
	@Test
	void testComprarBilleteNumeroMenor1() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->{
			sistema.comprarBillete("123", usuario, recorrido, 0);;
		});
		assertEquals ("Numero de billetes no puede ser 0 o menor",thrown.getMessage());
	}
	
	@Test
	void testComprarMasBilletesQuePlazas() {
		IllegalStateException thrown = assertThrows(IllegalStateException.class, () ->{
			sistema.comprarBillete("123", usuario, recorrido, 79);;
		});
		assertEquals ("Numero de billetes mayor que las plazas disponibles",thrown.getMessage());
	}
	
	@Test
	void testDevolverBilletes() {
		sistema.addRecorrido(recorridoMediterraneo);
		sistema.comprarBillete("12345", usuario, recorrido, 2);
		sistema.comprarBillete("123", usuario, recorrido, 1);
		sistema.devolverBillete("12345", 2);
		assertEquals(44, recorrido.getPlazasDisponibles());
	}
	
	@Test
	void testDevolverBilleteLocalizadorNulo() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->{
			sistema.devolverBillete(null, 3);;
		});
		assertEquals ("Localizador no puede ser nulo",thrown.getMessage());
	}
	
	@Test
	void testDevolverBilleteMenor1() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->{
			sistema.devolverBillete("1", 0);;
		});
		assertEquals ("Numero de billetes no puede ser 0 o menor",thrown.getMessage());
	}
	
	@Test
	void testDevolverMasBilletesQueComprados() {
		sistema.comprarBillete("12", usuario, recorrido, 1);
		IllegalStateException thrown = assertThrows(IllegalStateException.class, () ->{
			sistema.devolverBillete("12", 3);;
		});
		assertEquals ("No se pueden devolver mas billetes de los comprados",thrown.getMessage());
	}

	@Test
	void testComprarBilleteReservadoLocalizadorNulo() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->{
			sistema.compraBilleteReservado(null);;
		});
		assertEquals ("Localizador no puede ser nulo",thrown.getMessage());
	}
	
	@Test
	void testComprarBilletesReservados() {
		sistema.comprarBillete("12", usuario, recorrido, 1);
		sistema.reservarBillete("12", usuario, recorrido, 1);
		sistema.comprarBillete("12345", usuario, recorrido, 1);
		sistema.reservarBillete("12345", usuario, recorrido, 3);
		sistema.compraBilleteReservado("12345");
		assertEquals(39, recorrido.getPlazasDisponibles());
	}
	
	@Test
	void testPrecioBilletesUsuario() {
		sistema.comprarBillete("12345", usuario, recorrido, 2);
		sistema.addRecorrido(recorridoTren);
		Usuario izan = new Usuario("71188068D", "Izan");
		sistema.comprarBillete("12345", izan, recorridoTren, 1);
		assertEquals(40.0, sistema.precioBilletesUsuario("97828568T"));
		assertEquals(54.0, sistema.precioBilletesUsuario("71188068D"));
	}
	
	@Test
	void testPrecioBilletesNifNull() {
		sistema.comprarBillete("12345", usuario, recorrido, 2);
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			sistema.precioBilletesUsuario(null);
		});
		assertEquals("El dni del usuario no puede ser nulo", thrown.getMessage());
	}
	
	@Test
	void testRecorridosFecha() {
		sistema.addRecorrido(recorridoMediterraneo);
		assertEquals(sistema.recorridosFecha(fecha.toLocalDate()).get(0).getIdentificador(), recorrido.getIdentificador());
		
	}
	
	
}

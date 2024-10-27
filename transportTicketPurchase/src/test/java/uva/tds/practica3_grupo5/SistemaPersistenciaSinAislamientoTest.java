package uva.tds.practica3_grupo5;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class SistemaPersistenciaSinAislamientoTest {
	
	private Recorrido recorrido;
	private Usuario usuario;
	private LocalDateTime fecha;
	private LocalDateTime otraFecha;
	private Recorrido otroRecorrido;
	private SistemaPersistenciaSinAislamiento sistema;
	private Recorrido recorridoTren;
	private DatabaseManager base;
	
	@BeforeEach
	void setUp() {
		base = new DatabaseManager();
		sistema = new SistemaPersistenciaSinAislamiento(base);
		usuario = new Usuario("97828568T", "Gustavo");
		fecha = LocalDateTime.of(2020, 4, 1, 17, 30);
		otraFecha = LocalDateTime.of(2021, 4, 3, 20, 25);
		recorrido = new Recorrido ("1","Uva", "Casa", TipoTransporte.AUTOBUS, 20.0, fecha, 45, 30);
		otroRecorrido = new Recorrido ("3","Uva", "Casa", TipoTransporte.AUTOBUS, 20.0, otraFecha, 2, 20);
		recorridoTren = new Recorrido("2","Barcelona", "Valencia", TipoTransporte.TREN, 60.0, LocalDateTime.of(2022, 5, 1, 18, 30), 35, 120);
		sistema.addRecorrido(recorrido);
		base.addUsuario(usuario);
		sistema.comprarBillete("12345", usuario, recorrido, 2);
	}

	@AfterEach
	void tearDown() {
		base.clearDatabase();
	}
	
	@Test
	void testAddRecorrido() {
		assertTrue(sistema.getRecorrido("1").isEquals(recorrido));
	}
	
	@Test
	void testEliminarRecorrido() {
			sistema.eliminarRecorrido(recorrido.getIdentificador());
			assertEquals(null, sistema.getRecorrido("1"));
		}

	@Test
	void testActualizarFechaHora() {
		sistema.actualizarRecorrido(LocalDateTime.of(2020, 4, 1, 18, 30), "1");
		assertEquals(sistema.getRecorrido("1").getFechaHora(), LocalDateTime.of(2020, 4, 1, 18, 30));
	}
	
	@Test 
	void testActualizarRecorridoFechaNull() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			sistema.actualizarRecorrido(null,"1");;
		});
		assertEquals("La fecha y la hora no pueden ser nulo", thrown.getMessage());
	}
	
	
	@Test
	void testGetBilletesAsociados() {
		assertEquals(2, sistema.getBilletesAsociados("12345").size());
	}
	
	@Test
	void testComprarBilleteLocalizadorNulo() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->{
			sistema.comprarBillete(null, usuario, recorrido, 58);;
		});
		assertEquals ("El localizador no puede ser nulo",thrown.getMessage());
	}
		
	@Test
	void testComprarBilleteUsuarioNulo() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->{
			sistema.comprarBillete("123", null, recorrido, 58);;
		});
		assertEquals ("El usuario no puede ser nulo",thrown.getMessage());
	}
	
	@Test
	void testComprarBilleteRecorridoNulo() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->{
			sistema.comprarBillete("123", usuario, null, 58);;
		});
		assertEquals ("El recorrido no puede ser nulo",thrown.getMessage());
	}
	
	@Test
	void testComprarBilleteNumeroMenor1() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->{
			sistema.comprarBillete("123", usuario, recorrido, 0);;
		});
		assertEquals ("Se debe comprar al menos un billete",thrown.getMessage());
	}
	
	@Test
	void testComprarMasBilletesQuePlazas() {
		IllegalStateException thrown = assertThrows(IllegalStateException.class, () ->{
			sistema.comprarBillete("123", usuario, recorrido, 79);;
		});
		assertEquals ("No hay suficientes plazas disponibles",thrown.getMessage());
	}
	
	@Test
	void testDevolverBillete() {
		sistema.devolverBillete("12345", 1);
		assertEquals(1, sistema.getBilletesAsociados("12345").size());
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
	void testComprarBilleteReservado() {
		sistema.reservarBillete("234", usuario, recorrido, 3);
		sistema.comprarBillete("234", usuario, recorrido, 1);
		sistema.compraBilleteReservado("234");
		assertEquals(4, sistema.getBilletesAsociados("234").size());
		assertFalse(sistema.getBilletesAsociados("234").get(0).isReservado());
		assertFalse(sistema.getBilletesAsociados("234").get(1).isReservado());
		assertFalse(sistema.getBilletesAsociados("234").get(2).isReservado());
		assertFalse(sistema.getBilletesAsociados("234").get(3).isReservado());
	}
	
	@Test
	void testComprarBilleteReservadoLocalizadorNulo() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->{
			sistema.compraBilleteReservado(null);
		});
		assertEquals ("Localizador no puede ser nulo",thrown.getMessage());
	}
	
	@Test
	void testReservarBillete() {
		sistema.reservarBillete("ri6gh5", usuario, recorrido, 3);
		assertEquals(3, sistema.getBilletesAsociados("ri6gh5").size());
		assertTrue(sistema.getBilletesAsociados("ri6gh5").get(0).isReservado());
		assertTrue(sistema.getBilletesAsociados("ri6gh5").get(1).isReservado());
		assertTrue(sistema.getBilletesAsociados("ri6gh5").get(2).isReservado());
		}
	
	@Test
	void testReservarBilleteTrenValido() {
		Recorrido otroRecorrido = new Recorrido("2","Barcelona", "Valencia", TipoTransporte.TREN, 60.0, LocalDateTime.of(2022, 5, 1, 18, 30), 125, 120);
		sistema.addRecorrido(otroRecorrido);
		sistema.reservarBillete("ri6gh5", usuario, otroRecorrido, 3);
		assertEquals(3, sistema.getBilletesAsociados("ri6gh5").size());
		assertTrue(sistema.getBilletesAsociados("ri6gh5").get(0).isReservado());
		assertTrue(sistema.getBilletesAsociados("ri6gh5").get(1).isReservado());
		assertTrue(sistema.getBilletesAsociados("ri6gh5").get(2).isReservado());
		}
	
	@Test
	void testReservarBilleteMalConActualesMenorQueLaMitadDeTotalesTren() {
		sistema.addRecorrido(recorridoTren);
		IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
			sistema.reservarBillete("ri6gh5", usuario, recorridoTren, 4);;
		});
		assertEquals("El numero de plazas disponibles es menor que la mitad de plazas totales", thrown.getMessage());
	}
	
	@Test
	void testReservarBilleteMalConActualesMenorQueLaMitadDeTotalesBus() {
		sistema.addRecorrido(otroRecorrido);
		IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
			sistema.reservarBillete("ri6gh5", usuario, otroRecorrido, 1);;
		});
		assertEquals("El numero de plazas disponibles es menor que la mitad de plazas totales", thrown.getMessage());
	}
	
	@Test
	void testReservarBilleteMalConNumeroDeBilletesMayorQueDisponibles() {
		IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
			sistema.reservarBillete("ri6gh5", usuario, recorrido, 54);;
		});
		assertEquals("No se pueden reservar mas plazas que las disponibles", thrown.getMessage());
	}
	
	@Test
	void testReservarBilleteMalConNumeroDeBilletesMenor1() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			sistema.reservarBillete("ri6gh5", usuario, recorrido, -5);;
		});
		assertEquals("Se debe reservar al menos un billete", thrown.getMessage());
	}
	
	@Test
	void testAnularReserva() {
		sistema.reservarBillete("12345", usuario, recorrido, 5);
		sistema.anularReserva("12345", 3);
		assertEquals(4, sistema.getBilletesAsociados("12345").size());
	}
	
	@Test
	void testAnularReservaLocalizadorNulo() {
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
	void testPrecioBilletesUsuario() {
		sistema.addRecorrido(recorridoTren);
		Usuario otroUsuario = new Usuario("71322835L", "Asier");
		base.addUsuario(otroUsuario);
		sistema.comprarBillete("12345", otroUsuario, recorridoTren, 1);
		assertEquals(40.0, sistema.precioBilletesUsuario("97828568T"));
		assertEquals(54.0, sistema.precioBilletesUsuario("71322835L"));
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
		assertEquals(1, sistema.recorridoFecha(fecha.toLocalDate()).size());
	}
}


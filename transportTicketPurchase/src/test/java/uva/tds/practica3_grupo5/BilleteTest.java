package uva.tds.practica3_grupo5;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;

class BilleteTest {
	
	private Recorrido recorrido;
	private LocalDateTime fecha;
	private Usuario usuarioBueno;
	
	@BeforeEach
	public void setUp() {
		usuarioBueno = new Usuario("97828568T", "Gustavo");
		fecha = LocalDateTime.of(2020, 4, 1, 17, 30);
		recorrido = new Recorrido ("1","Uva", "Casa", TipoTransporte.AUTOBUS, 20.0, fecha, 50, 30);
	}
	

	@Test
	void testCrearBilleteBueno() {
		Billete otroBillete = new Billete ("12345", usuarioBueno, recorrido, true);
		assertNotNull(otroBillete);
		assertEquals("12345", otroBillete.getLocalizador());
		assertEquals(otroBillete.getRecorrido(), recorrido);
		assertEquals(otroBillete.getUsuario(), usuarioBueno);
		assertTrue(otroBillete.isReservado());
		otroBillete.comprarBillete();
		assertFalse(otroBillete.isReservado());
	}
	
	@Test
	void testLocalizadorNull() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->{
			new Billete(null, usuarioBueno, recorrido, false);;
		});
		assertEquals ("El localizador no puede ser nulo",thrown.getMessage());
	}
	
	@Test
	void testLocalizadorMasCaracteres() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->{
			new Billete("12345678901",usuarioBueno,recorrido, false);;
		});
		assertEquals ("El localizador tiene que tener tener entre 1 y 8 caracteres",thrown.getMessage());
	}
	
	@Test
	void testLocalizadorMenosCaracteres() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->{
			new Billete("",usuarioBueno,recorrido, false);;
		});
		assertEquals ("El localizador tiene que tener tener entre 1 y 8 caracteres",thrown.getMessage());
	}
	
	@Test
	void testUsuarioNull() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->{
			new Billete("12345",null,recorrido, false);;
		});
		assertEquals ("El usuario no puede ser nulo",thrown.getMessage());
	}
	
	@Test
	void testRecorridoNull() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->{
			new Billete("12345",usuarioBueno,null, false);;
		});
		assertEquals ("El recorrido no puede ser nulo",thrown.getMessage());
	}
}

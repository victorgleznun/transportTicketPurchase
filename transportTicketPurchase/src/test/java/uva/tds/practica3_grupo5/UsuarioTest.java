package uva.tds.practica3_grupo5;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;


class UsuarioTest {
	
	private Usuario buenUsuario;
	
	@BeforeEach
	void setUp() {
		buenUsuario = new Usuario("97828568T", "Gustavo");
	}

	@Test
	void testBuenUsuario() {
		Usuario otroUsuario = new Usuario("32935085M", "Carlos");
		assertNotNull(otroUsuario);
		assertEquals("32935085M", otroUsuario.getNif());
		assertEquals("Carlos", otroUsuario.getNombre());
	}
	
	@Test
	void testMalUsuarioMalNombreMenosDeUnaLetra() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Usuario("03658457A", "");;
		});
		assertEquals("El nombre debe tener entre 1 y 15 caracteres", thrown.getMessage());
	}
	
	@Test
	void testMalUsuarioMalNombreMasDeQuinceLetras() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Usuario("03658457A", "Aaabbbcccdddeeefff");;
		});
		assertEquals("El nombre debe tener entre 1 y 15 caracteres", thrown.getMessage());
	}
	
	@Test
	void testMalUsuarioMalNombreEsNull() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Usuario("0365723Y", null);;
		});
		assertEquals("El nombre no puede ser nulo", thrown.getMessage());
	}
	
	@Test
	void testMalUsuarioMalNifEsNull() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Usuario(null, "Carlos");;
		});
		assertEquals("El dni no puede ser nulo", thrown.getMessage());
	}
	
	@Test
	void testMalUsuarioMalNifNoTieneNueveCaracteres() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Usuario("03657A", "Carlos");;
		});
		assertEquals("El dni debe cumplir las especeficaciones de DNI en España", thrown.getMessage());
	}
	
	@Test
	void testMalUsuarioMalNifSusOchoPrimerosCaracteresNoSonNumericos() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Usuario("5212f493C", "Carlos");;
		});
		assertEquals("El dni debe cumplir las especeficaciones de DNI en España", thrown.getMessage());
	}
	
	@Test
	void testMalUsuarioMalNifSuLetraNoEsCorrecta() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Usuario("46577817Ñ", "Carlos");;
		});
		assertEquals("El dni debe cumplir las especeficaciones de DNI en España", thrown.getMessage());
	}
	
	@Test
	void testMismoUsuario() {
		Usuario otroUsuario = new Usuario("97828568T", "Gustavo");
		assertTrue(otroUsuario.isEquals(buenUsuario));
	}
	
	@Test
	void testNoEsMismoUsuario() {
		Usuario otroUsuario = new Usuario("13113573P", "Carlos");
		assertFalse(otroUsuario.isEquals(buenUsuario));
	}
}

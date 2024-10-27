package uva.tds.practica3_grupo5;

import javax.persistence.*;
import java.util.List;
/**
 * Tipo de dato que implementa la funcionalidad
 * de un usuario del sistema de compra de billetes
 * @author victogo
 * @author izajime
 * @author asigarc
 */

@Entity
@Table(name="USUARIO")
public class Usuario {
	
	@Id
	@Column (name="id")
	private String nif;
	
	private String nombre;
	
	@OneToMany(mappedBy = "usuario",fetch=FetchType.EAGER)
	private List<Billete> billete;
	
	public Usuario() {
		
	}

	/**
	 * Construye un usuario dado un nif y un nombre
	 * @param nuevoNif El nif del usuario
	 * @param nuevoNombre El nombre del usuario
	 * @throws IllegalArgumentException si el nombre del usuario es nulo
	 * @throws IllegalArgumentException si el nombre del usuario es tiene menos de 1 caracter o mas de 15 carcateres
	 * @throws IllegalArgumentException si el dni del usuario es nulo
	 * @throws IllegalArgumentException si el dni del usuario no cumple las especificaciones del DNI en españa
	 */
	public Usuario(String nuevoNif, String nuevoNombre) {	
		if (nuevoNombre == null) {
			throw new IllegalArgumentException("El nombre no puede ser nulo");
		}

		if (nuevoNombre.length() < 1 || nuevoNombre.length()> 15) {
			throw new IllegalArgumentException("El nombre debe tener entre 1 y 15 caracteres");
		}
		this.nombre = nuevoNombre;

		if (nuevoNif == null) {
			throw new IllegalArgumentException("El dni no puede ser nulo");
		}

		if (comprobarDni(nuevoNif)) this.nif = nuevoNif;
		else throw new IllegalArgumentException("El dni debe cumplir las especeficaciones de DNI en España");
	}

	/**
	 * Comprueba si el dni cumple las especificaciones de dni en España
	 * @param dni El dni que vamos a comprobar
	 * @return true si cumpple las especificaciones y false si no las cumple
	 */
	private boolean comprobarDni(String dni) {
		String letrasDni = "TRWAGMYFPDXBNJZSQVHLCKE";
		if (dni.length()!=9) return false;

		String numero = dni.substring(0, 8);

		if (!isNumeric(numero)) return false;

		char letra = dni.charAt(8);
		int resto = Integer.parseInt(numero) % 23;
		return (letrasDni.charAt(resto) == letra);
	}
	/**
	 * Comprueba si los 8 primeros caracteres del dni son numeros
	 * @param cadena Los 8 primeros caracteres del dni
	 * @return true si son numeros y false si hay algun caracter que no es numerico
	 */
	private boolean isNumeric(String cadena) {
		try {
			Integer.parseInt(cadena);
			return true;
		}catch(NumberFormatException excepcion) {
			return false;
		}
	}

	/**
	 * Consulta el nif del usuario
	 * @return nif El nif del usuario
	 */
	public String getNif() {
		return nif;
	}

	/**
	 * Consulta el nombre del usuario
	 * @return nombre El nombre del usuario
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Comprueba si dos usuarios son iguales
	 * @return true si los usuarios son iguales y false si los usuarios no son iguales
	 * con respecto al nif de ellos
	 */
	public boolean isEquals(Usuario otroUsuario) {
		return nif.equals(otroUsuario.getNif());
	}
}

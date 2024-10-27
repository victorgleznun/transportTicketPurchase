package uva.tds.practica3_grupo5;

import javax.persistence.*;
/**
 * Tipo de dato que implementa la funcionalidad
 * de un billete de transporte
 * @author victogo
 * @author izajime
 * @author asigarc
 */

@Entity
@Table(name="BILLETE")
public class Billete{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	private String localizador;
	
	private boolean reservado;
	
	@ManyToOne()
	@JoinColumn(name = "idU", referencedColumnName = "id")
	private Usuario usuario;
	
	@ManyToOne()
	@JoinColumn(name = "idR", referencedColumnName = "id")
	private Recorrido recorrido;
	
	public Billete() {
		
	}
	
	/**
	 * Construye un billete con un localizador, que estara
	 * asociado a un usuario y a un recorrido indicado
	 * @param nuevoLocalizador El localizador del billete
	 * @param nuevoUsuario El usuario asociado al billete
	 * @param nuevoRecorrido El recorrido asociado al billete
	 * @throws IllegalArgumentException si el localizador es nulo
	 * @throws IllegalArgumentException si el localizador tiene menos de 1 caracter o mas de 8 caracteres
	 * @throws IllegalArgumentException si el usuario es nulo
	 * @throws IllegalArgumentException si el recorrido es nulo
	 */
	public Billete (String nuevoLocalizador, Usuario nuevoUsuario, Recorrido nuevoRecorrido, boolean reservado) {
		if(nuevoLocalizador == null){
			throw new IllegalArgumentException ("El localizador no puede ser nulo");
		}
		if(nuevoLocalizador.length() < 1 || nuevoLocalizador.length() > 8) {
			throw new IllegalArgumentException ("El localizador tiene que tener tener entre 1 y 8 caracteres");
		}
		if(nuevoUsuario == null) {
			throw new IllegalArgumentException ("El usuario no puede ser nulo");
		}
		if(nuevoRecorrido == null) {
			throw new IllegalArgumentException ("El recorrido no puede ser nulo");
		}
		
		localizador = nuevoLocalizador;
		usuario = nuevoUsuario;
		recorrido = nuevoRecorrido;
		this.reservado = reservado;
	}

	/**
	 * Consulta el localizador del billete
	 * @return localizador El localizador del billete
	 */
	public String getLocalizador() {
		return localizador;
	}
	
	/**
	 * Consulta el usuario al que esta asociado el billete
	 * @return usuario El usuario asociado al billete
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * Consulta el recorrido del billete
	 * @return recorrido El recorrido asociado al billete
	 */
	public Recorrido getRecorrido() {
		return recorrido;
	}
	
	/**
	 * Consulta si un billete esta reservado o no
	 * @return reservado true si el billete esta reservado y false si no esta reservado
	 */
	public boolean isReservado() {
		return reservado;
	}
	
	/**
	 * Cambia el estado del billete reservado a false una vez comprado
	 */
	public void comprarBillete() {
		this.reservado = false;
	}
	
	/*
	 * Comprueba si un billete es igual a otro, a partir de su localizador
	 * @return true si son iguales y false si son diferentes
	 */
	public boolean isEquals(Billete otroBillete) {
		return localizador.equals(otroBillete.getLocalizador());
	}
}
	
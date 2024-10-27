package uva.tds.practica3_grupo5;

import java.time.LocalDateTime;
import javax.persistence.*;
import java.util.List;

/**
 * Tipo de dato que implementa la funcionalidad
 * de un recorrido de un medio de transporte
 * @author victogo
 * @author izajime
 * @author asigarc
 */

@Entity
@Table(name="RECORRIDO")
public class Recorrido {
	@Id
	@Column(name="id")
	private String identificador;
	
	private String origen;
	private String destino;
	
	@Enumerated(EnumType.STRING)
	private TipoTransporte tipo; 
	
	private double precio;
	private LocalDateTime fechaHora;
	private int plazasDisponibles;
	private int duracion;
	
	@OneToMany(mappedBy = "recorrido",fetch=FetchType.EAGER,cascade = CascadeType.ALL)
	private List<Billete> billete;
	
	public Recorrido() {
		
	}
	
	/**
	 * Crea un recorrido que tiene un origen, un destino, un tipo
	 * de transporte (AUTOBUS o TREN), un precio, fecha y hora,
	 * plazas disponibles para el recorrido y duracion en minutos
	 * del mismo. Tambien dispone de un identificador unico
	 * @param origen El origen del recorrido
	 * @param destino El destino del recorrido
	 * @param tipo El tipo del recorrido
	 * @param precio El precio del recorrido
	 * @param fechaHora La fecha y hora del recorrido
	 * @param plazasDisponibles El numero de plazas disponibles del recorrido
	 * @param duracion La duracion del recorrido
	 * @throws IllegalArgumentException si el identificador es nulo
	 * @throws IllegalArgumentException si el identificador tiene menos de un caracter
	 * @throws IllegalArgumentException si el origen es nulo
	 * @throws IllegalArgumentException si el origen tiene menos de un caracter
	 * @throws IllegalArgumentException si el destino es nulo
	 * @throws IllegalArgumentException si el destino tiene menos de un caracter
	 * @throws IllegalArgumentException si el precio es negativo
	 * @throws IllegalArgumentException si el numero de plazas disponibles es negativo
	 * @throws IllegalArgumentException si el numero de plazas disponibles es menor que 1 o mayor que 50 en los autobuses
	 * @throws IllegalArgumentException si el numero de plazas disponibles es menor que 1 o mayor que 250 en los trenes
	 * @throws IllegalArgumentException si la duracion del trayecto es negativo o 0
	 */
	public Recorrido (String identificador, String origen, String destino, TipoTransporte tipo, double precio, LocalDateTime fechaHora, int plazasDisponibles, int duracion) {
		if (identificador == null || origen == null || destino == null) {
			throw new IllegalArgumentException("Los parametros no pueden ser nulos");
		}
		if (identificador.length()==0) {
			throw new IllegalArgumentException("Identificador debe tener al menos un caracter");
		}
		if (origen.length()==0) {
			throw new IllegalArgumentException("Origen debe tener al menos un caracter");
		}
		if (destino.length()==0) {
			throw new IllegalArgumentException("Destino debe tener al menos un caracter");
		}
		if (precio < 0) {
			throw new IllegalArgumentException("Precio debe ser un numero mayor o igual a 0");
		}
		if (plazasDisponibles<0) {
			throw new IllegalArgumentException("Plazas disponibles no pueden ser negativas");
		}
			
		if (tipo == TipoTransporte.AUTOBUS) {
			if (plazasDisponibles < 1 || plazasDisponibles >50) {
				throw new IllegalArgumentException("El numero de plazas debe ser un numero entre 1 y 50 para los autobuses");
				}
		}else {
			if (plazasDisponibles < 1 || plazasDisponibles >250) {
				throw new IllegalArgumentException("El numero de plazas debe ser un numero entre 1 y 250 para los trenes");
			}
		}
		if (duracion < 1) {
			throw new IllegalArgumentException("Duracion debe ser positivo y mayor que 0");
		}
		this.identificador = identificador;
		this.origen = origen;
		this.destino = destino;
		this.tipo = tipo;
		this.precio = precio;
		this.fechaHora = fechaHora;
		this.plazasDisponibles = plazasDisponibles;
		this.duracion = duracion;
	}
	
	/**
	 * Consulta el origen del recorrido
	 * @return origen El origen del recorrido
	 */
	public String getOrigen() {
		return origen;
	}
	
	/**
	 * Consulta el destino del recorrido
	 * @return destino El destino del recorrido
	 */
	public String getDestino() {
		return destino;
	}
	
	/**
	 * Consulta el medio de transporte del recorrido
	 * @return transporte El tipo de  medio de transporte del recorrico
	 */
	public TipoTransporte getTipo() {
		return tipo;
	}
	
	/**
	 * Consulta el precio del recorrido
	 * @return precio El precio del recorrido
	 */
	public double getPrecio() {
		return precio;
	}
	
	/**
	 * Cambia el precio del recorrido
	 * @param precio El precio del recorrido
	 * @throws IllegalArgumentException si el precio es negativo
	 */
	public void setPrecio(double precio) {
		if (precio < 0) {
			throw new IllegalArgumentException("Precio debe ser un numero mayor o igual a 0");
		}
		this.precio = precio;
	}
	
	/**
	 * Consulta la fecha y hora del recorrido
	 * @return fechaHora La fecha y la hora del recorrido  
	 */
	public LocalDateTime getFechaHora() {
		return fechaHora;
	}
	
	/**
	 * Cambia la fecha y hora del recorrido
	 * @param fechaHora La fecha y la hora del recorrido
	 */
	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}
	
	/**
	 * Consulta las plazas disponibles de un recorrido
	 * @return plazasDisponibles El numero de plaza disponibles del recorrido
	 */
	public int getPlazasDisponibles() {
		return plazasDisponibles;
	}
	
	/**
	 * Cambia las plazas disponibles de un recorrido
	 * @param plazasDisponibles El numero de plaza disponibles del recorrido
	 * @throws IllegalArgumentException si el numero de plazas disponibles es menor que 0 o mayor que 250
	 */
	public void setPlazasDisponibles(int plazasDisponibles) {
		if (plazasDisponibles < 0 || plazasDisponibles >250) {
			throw new IllegalArgumentException("El numero de plazas no puede ser un numero fuera del rango [0,250]");
		}
		this.plazasDisponibles = plazasDisponibles;
	}
	
	/**
	 * Consulta la duracion del recorrido
	 * @return duracion La duracion del recorrido
	 */
	public int getDuracion() {
		return duracion;
	}
	
	/**
	 * Consulta el identificador del recorrido
	 * @return identificador El identificador del recorrido
	 */
	public String getIdentificador() {
		return identificador;
	}
	
	/**
	 * Comprueba si dos recorridos son iguales dependiendo del identificador
	 * @return true si los dos recorridos son iguales y false si los dos recorridos no son iguales
	 * con respecto a su identificador
	 */
	public boolean isEquals(Recorrido recorrido) {
		return identificador.equals(recorrido.getIdentificador());
	}
}

package uva.tds.practica3_grupo5;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Tipo de dato que implementa la funcionalidad
 * de un sistema de compra de billetes de transporte
 * @author victogo
 * @author izajime
 * @author asigarc
 */
public class SistemaPersistenciaSinAislamiento {
	
	public static final String MENSAJE_LOCALIZADOR = "Localizador no puede ser nulo";
	
	private DatabaseManager databaseManager;
	
	/**
	 * Contruye un sistema de compra de billetes
	 */
	public SistemaPersistenciaSinAislamiento(DatabaseManager databaseManager) {
		this.databaseManager = databaseManager;
	}
	
	/**
	 * Agrega un recorrido al sistema
	 * @param recorrido El recorrido que se desea agregar al sistema
	 */
	public void addRecorrido(Recorrido recorrido) {
		databaseManager.addRecorrido(recorrido);	
	}
	
	/**
	 * Elimina un recorrido del sistema dado el identificador del que se desea quitar
	 * @param identificador El identificador del recorrido que se desea eliminar
	 */
	public void eliminarRecorrido(String identificador) {
		databaseManager.eliminarRecorrido(identificador);
	}
	
	/**
	 * Cambia la fecha y hora de un recorrido seleccionado por el indice de la lista
	 * @param fechaHora La fecha y la hora del recorrido que se desea cambiar
	 * @param idRecorrido El id del recorrido que se desea actualizar
	 * @throws IllegalArgumentException si la fecha y la hora son nulos
	 */
	public void actualizarRecorrido (LocalDateTime fechaHora, String idRecorrido) {
		if (fechaHora == null) throw new IllegalArgumentException ("La fecha y la hora no pueden ser nulo");
		Recorrido recorridoAlmacenado = databaseManager.getRecorrido(idRecorrido);
		String origen = recorridoAlmacenado.getOrigen();
		String destino = recorridoAlmacenado.getDestino();
		TipoTransporte tipo = recorridoAlmacenado.getTipo();
		double precio = recorridoAlmacenado.getPrecio();
		int plazas = recorridoAlmacenado.getPlazasDisponibles();
		int duracion = recorridoAlmacenado.getDuracion();
		Recorrido recorridoActualizado = new Recorrido(idRecorrido, origen, destino, tipo, precio, fechaHora, plazas, duracion);
		databaseManager.actualizarRecorrido(recorridoActualizado);
	}
	
	/**
	 * Cambia la fecha y hora de un recorrido seleccionado por el indice de la lista y elimina una plaza
	 * @param idRecorrido El identificador del recorrido que se desea actualizar
	 * 
	 */
	private void actualizarRecorridoMenosPlazas (String idRecorrido) {
		Recorrido recorridoAlmacenado = databaseManager.getRecorrido(idRecorrido);
		String origen = recorridoAlmacenado.getOrigen();
		String destino = recorridoAlmacenado.getDestino();
		TipoTransporte tipo = recorridoAlmacenado.getTipo();
		double precio = recorridoAlmacenado.getPrecio();
		LocalDateTime fechaHora = recorridoAlmacenado.getFechaHora();
		int plazas = recorridoAlmacenado.getPlazasDisponibles();
		int duracion = recorridoAlmacenado.getDuracion();
		Recorrido recorridoActualizado = new Recorrido(idRecorrido, origen, destino, tipo, precio, fechaHora, plazas-1, duracion);
		databaseManager.actualizarRecorrido(recorridoActualizado);
	}
	
	/**
	 * Cambia la fecha y hora de un recorrido seleccionado por el indice de la lista y aumenta una plaza
	 * @param idRecorrido El identificador del recorrido que se desea actualizar
	 */
	private void actualizarRecorridoMasPlazas (String idRecorrido) {
		Recorrido recorridoAlmacenado = databaseManager.getRecorrido(idRecorrido);
		String origen = recorridoAlmacenado.getOrigen();
		String destino = recorridoAlmacenado.getDestino();
		TipoTransporte tipo = recorridoAlmacenado.getTipo();
		double precio = recorridoAlmacenado.getPrecio();
		LocalDateTime fechaHora = recorridoAlmacenado.getFechaHora();
		int plazas = recorridoAlmacenado.getPlazasDisponibles();
		int duracion = recorridoAlmacenado.getDuracion();
		Recorrido recorridoActualizado = new Recorrido(idRecorrido, origen, destino, tipo, precio, fechaHora, plazas+1, duracion);
		databaseManager.actualizarRecorrido(recorridoActualizado);
	}
	
	/**
	 * Consulta el recorrido a partir de su id
	 * @param idRecorrido El id del recorrido
	 * @return el recorrido asociado a ese id
	 */
	public Recorrido getRecorrido(String idRecorrido) {
		return databaseManager.getRecorrido(idRecorrido);
	}
	
	/**
	 * Consulta la lista de billetes asociados a un localizador
	 * @param localizador El localizado del que se quiere consultar los billetes asociados
	 * @return la lista de los billetes asociados a ese localizador
	 */
	public List<Billete> getBilletesAsociados(String localizador){
		return databaseManager.getBilletes(localizador);
	}
	
	/**
	 * Compra uno o varios billetes para un recorrido
	 * @param localizador El localizador de los billetes que se desean comprar
	 * @param usuario El usuario que desea comprar los billetes
	 * @param recorrido El recorrido correcpondiente a los billetes que se quiere comprar
	 * @param numBilletes El numero de billetes que se desean comprar
	 * @throws IllegalArgumentException si el localizador es nulo
	 * @throws IllegalArgumentException si el numero de billetes es menor que 1
	 * @throws IllegalArgumentException si el usuario es nulo
	 * @throws IllegalArgumentException si el recorrido es nulo
	 * @throws IllegalStateException si no hay suficientes plazas disponibles
	 */
	public void comprarBillete(String localizador, Usuario usuario, Recorrido recorrido, int numBilletes){
		if (localizador == null) throw new IllegalArgumentException ("El localizador no puede ser nulo");
		if (usuario == null) throw new IllegalArgumentException ("El usuario no puede ser nulo");
		if (recorrido == null) throw new IllegalArgumentException ("El recorrido no puede ser nulo");
		if (numBilletes < 1) throw new IllegalArgumentException("Se debe comprar al menos un billete");
		if (numBilletes > recorrido.getPlazasDisponibles()) throw new IllegalStateException("No hay suficientes plazas disponibles");
		for(int i = 0; i<numBilletes; i++) {
			databaseManager.addBillete(new Billete (localizador, usuario, recorrido, false));
			actualizarRecorridoMenosPlazas(recorrido.getIdentificador());
		}
	}
	
	/**
	 * Devuelve uno o varios billetes
	 * @param localizador El localizador de los billetes que se desea devolver
	 * @param numBilletes El numero de billetes que se desea devolver
	 * @throws IllegalArgumentException si el localizador es nulo
	 * @throws IllegalArgumentException si el numero de billetes es menor que 1
	 * @throws IllegalStateException si se intenta devolver mas billetes de los comprados
	 */
	public void devolverBillete(String localizador, int numBilletes){
		if (localizador == null) throw new IllegalArgumentException (MENSAJE_LOCALIZADOR);
		if (numBilletes <1) throw new IllegalArgumentException ("Numero de billetes no puede ser 0 o menor");
		
		ArrayList<Billete> listaBilletesLocalizador = databaseManager.getBilletes(localizador);
		if (listaBilletesLocalizador.size() < numBilletes) throw new IllegalStateException ("No se pueden devolver mas billetes de los comprados");
		databaseManager.eliminarBilletes(localizador);
		
		for(int i=0; i<numBilletes;i++) {
			actualizarRecorridoMasPlazas(listaBilletesLocalizador.get(i).getRecorrido().getIdentificador());
			listaBilletesLocalizador.remove(listaBilletesLocalizador.get(i));	
		}
		
		for(Billete billete : listaBilletesLocalizador) {
			databaseManager.addBillete(new Billete(billete.getLocalizador(), billete.getUsuario(), billete.getRecorrido(), billete.isReservado()));
		}
	}
	
	/**
	 * Compra uno o varios billestes reservados
	 * @param localizador El localizador de los billetes reservados que se desea comprar
	 * @throws IllegalArgumentException si el localizador es nulo
	 */
	public void compraBilleteReservado(String localizador){
		if (localizador == null) throw new IllegalArgumentException (MENSAJE_LOCALIZADOR);
		ArrayList<Billete> listaBilletesLocalizador = databaseManager.getBilletes(localizador);
		for(Billete billete : listaBilletesLocalizador) {
			if(billete.isReservado()) {
				billete.comprarBillete();
			}
		}
		
		databaseManager.eliminarBilletes(localizador);
		for(Billete billete : listaBilletesLocalizador) {
			databaseManager.addBillete(new Billete(billete.getLocalizador(), billete.getUsuario(), billete.getRecorrido(), false));
		}
	}
	
	/**
	 * Reserva un billete 
	 * @param localizador El localizador del billete que se desea reservar
	 * @param usuario El usuario que desea reservar los billetes
	 * @param recorrido El recorrido de los billetes que se desean reservar
	 * @param nBilletes El numero de billetes que se desea reservar
	 * @throws IllegalArgumentException si se intenta reservar menos de 1 billete
	 * @throws IllegalStateException si se intenta reservar mas plazas de las disponibles
	 * @throws IllegalStateException si el numero de plazas disponibles es menor que la mitad de plazas totales en un autobus
	 * @throws IllegalStateException si numero de plazas disponibles es menor que la mitad de plazas totales en un tren
	 */
	public void reservarBillete(String localizador, Usuario usuario, Recorrido recorrido, int nBilletes) {
		if(nBilletes<1) throw new IllegalArgumentException ("Se debe reservar al menos un billete");
		if(nBilletes>recorrido.getPlazasDisponibles()) throw new IllegalStateException ("No se pueden reservar mas plazas que las disponibles");
		if(recorrido.getTipo() == TipoTransporte.AUTOBUS) {
			if (recorrido.getPlazasDisponibles()<25) {
				throw new IllegalStateException ("El numero de plazas disponibles es menor que la mitad de plazas totales");
			}
		} else {
			if (recorrido.getPlazasDisponibles()<125) {
				throw new IllegalStateException ("El numero de plazas disponibles es menor que la mitad de plazas totales");
			}
		}
		
		for(int i=0; i<nBilletes; i++) {
			actualizarRecorridoMenosPlazas(recorrido.getIdentificador());
			databaseManager.addBillete(new Billete(localizador, usuario, recorrido, true));
		}
	}
	
	/**
	 * Anula la reserva de un billete reservado anteriormente
	 * @param localizador El localizador del billete que se desea anular
	 * @param nBilletes El numero de billetes que se desean anular
	 * @throws IllegalArgumentException si el localizador es nulo
	 * @throws IllegalArgumentException si el numero de billetes que se intenta anular es menor que 1
	 */
	public void anularReserva(String localizador, int nBilletes) {
		if (localizador == null) throw new IllegalArgumentException (MENSAJE_LOCALIZADOR);
		if (nBilletes <1) throw new IllegalArgumentException ("Numero de billetes no puede ser 0 o menor");
		
		ArrayList<Billete> listaBilletesLocalizador = databaseManager.getBilletes(localizador);
		int nBilletesTotal = listaBilletesLocalizador.size();
		
		for (int i=0; i<listaBilletesLocalizador.size();i++) {
			if(!listaBilletesLocalizador.get(i).isReservado()) {
				listaBilletesLocalizador.remove(i);
			}
		}
		
		databaseManager.eliminarBilletes(localizador);
		for (int i = 0; i<(nBilletesTotal-nBilletes); i++) {
			actualizarRecorridoMasPlazas(listaBilletesLocalizador.get(i).getRecorrido().getIdentificador());
			databaseManager.addBillete(new Billete(listaBilletesLocalizador.get(i).getLocalizador(), listaBilletesLocalizador.get(i).getUsuario(), listaBilletesLocalizador.get(i).getRecorrido(), false));
		}
		
	}
	/**
	 * Consulta el precio total de los billetes de un usuario
	 * @param listaBilletesUsuario La lista de los billetes de un usuario
	 * @return precioTotal El precio total de los billetes de un usuario
	 */
	private double getPrecioTotal(ArrayList<Billete> listaBilletesUsuario) {
		double precioTotal = 0;
		for (Billete billete : listaBilletesUsuario) {
			if (billete.getRecorrido().getTipo() == TipoTransporte.TREN) {
				precioTotal = precioTotal + billete.getRecorrido().getPrecio()*0.9;
			}
			else {
				precioTotal = precioTotal + billete.getRecorrido().getPrecio();
			}
		}
		return precioTotal;
	}
	
	/**
	 * Consulta el precio de los billetes de un usuario
	 * @param nif El nif del usuario del que se desea consultar el precio de los billetes asociados a el
	 * @return precioTotal El precio total de los billetes asociados a un usuario
	 * @throws IllegalArgumentException si el deni del usuario es nulo
	 */
	public double precioBilletesUsuario(String nif) {
		if (nif == null) throw new IllegalArgumentException ("El dni del usuario no puede ser nulo");
		ArrayList<Billete> listaBilletesUsuario = databaseManager.getBilletesDeUsuario(nif);
		return getPrecioTotal(listaBilletesUsuario);
	}
	
	/**
	 * Consulta la lista de los recorridos disponibles en una fecha
	 * @param fecha La fecha de la que se quiere consultar los recorridos disponibles
	 * @return los recorridos de la fecha que se consulta
	 */
	public List<Recorrido> recorridoFecha(LocalDate fecha){
		return databaseManager.getRecorridos(fecha);
	}
}


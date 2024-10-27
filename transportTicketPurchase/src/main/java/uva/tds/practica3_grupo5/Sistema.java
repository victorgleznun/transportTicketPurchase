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
public class Sistema {
	
	public static final String MENSAJE_RECORRIDO = "Recorrido no existe en el sistema";
	public static final String MENSAJE_LOCALIZADOR = "Localizador no puede ser nulo";
	public static final String MENSAJE_BILLETES = "Numero de billetes no puede ser 0 o menor";
	
	private ArrayList<Recorrido> listaRecorridos;
	private ArrayList<Billete> listaBilletes;
	
	/**
	 * Contruye un sistema de compra de billetes
	 */
	public Sistema() {
		listaRecorridos = new ArrayList<>();
		listaBilletes = new ArrayList<>();
	}
	
	
	/**
	 * Consulta la lista de recorridos del sistema
	 * @return listaRecorridos La lista de los recorridos
	 */
	public List<Recorrido> getRecorridos(){
		return listaRecorridos;
	}
	
	/**
	 * Consulta la lista de los billetes del sistema
	 * @return listaBilletes La lista de los billetes
	 */
	public List<Billete> getBilletes(){
		return listaBilletes;
	}
	
	
	/**
	 * Agrega un recorrido al sistema
	 * @param recorrido El recorrido que se quiere agregar al sistema
	 * @throws IllegalArgumentException si el recorrido que se quiere añadir es nulo 
	 * @throws IllegalStateException si el recorrido que se quiere añadir ya existe en la lista de recorridos del sistema
	 * con respecto a sus identificadores
	 */
	public void addRecorrido(Recorrido recorrido) {
		if (recorrido == null) throw new IllegalArgumentException("El recorrido no puede ser nulo");
		for (Recorrido recorridoSistema : listaRecorridos) {
			if (recorridoSistema.getIdentificador().equals(recorrido.getIdentificador())) {
				throw new IllegalStateException("Recorrido ya existente en el sistema");
			}
		}
		listaRecorridos.add(recorrido);
	}
	
	/**
	 * Elimina un recorrido del sistema dado el identificador del que se desea quitar
	 * @param identificador El identificador del recorrido que se desea eliminar 
	 * @throws IllegalArgumentException si el identificador del recorrido que se desea eliminar es nulo
	 * @throws IllegalStateException si el recorrido que se desea eliminar tiene algun billete asociado
	 * @throws IllegalStateException si el recorrido que se desea eliminar no existe
	 */
	public void eliminarRecorrido(String identificador) {
		boolean sePuedeBorrar = true;
		boolean existe = false;
		int index = 0;
		if (identificador == null) throw new IllegalArgumentException("El identificador no puede ser nulo");
		
		for (Billete comprobarBillete : listaBilletes) {
			if (comprobarBillete.getRecorrido().getIdentificador().equals(identificador)) {
				sePuedeBorrar = false;
			}
		}
		if (!sePuedeBorrar) throw new IllegalStateException("Recorrido seleccionado con billete asociado");
		for(int i = 0; i<listaRecorridos.size(); i++) {
			if (listaRecorridos.get(i).getIdentificador().equals(identificador)) {
				existe = true;
				index = i;
			}
		}
		if (!existe) throw new IllegalStateException("Recorrido no existente");
		listaRecorridos.remove(index);
	}
	
	/**
	 * Cambia la fecha y hora de un recorrido seleccionado por el indice de la lista
	 * @param fechaHora La fecha y la hora que se desea modificar
	 * @param identificador El identificador del recorrido que se desea modificar
	 * @throws IllegalArgumentException si el identificador del recorrido que se desea modificar es nulo
	 * @throws IllegalArgumentException si el recorrido que se desea modificar no existe en el sistema
	 */
	public void actualizarRecorrido (LocalDateTime fechaHora, String identificador) {
		if (identificador == null) {
			throw new IllegalArgumentException("El identificador no puede ser nulo");
		}
		boolean existeRecorrido = false;
		for (Recorrido comprobarRecorrido : listaRecorridos) {
			if (comprobarRecorrido.getIdentificador().equals(identificador)) {
				existeRecorrido = true;
				comprobarRecorrido.setFechaHora(fechaHora);
			}
		}
		if (!existeRecorrido) {
			throw new IllegalArgumentException(MENSAJE_RECORRIDO);
		}
	}
	
	/**
	 * Reserva un billete 
	 * @param localizador El localizador del billete que se desea reservar
	 * @param usuario El usuario que desea reserva el billete
	 * @param recorrido El recorrido del billete que se desea reservar
	 * @param nBilletes El numero de billetes que se desean reservar
	 * @throws IllegalArgumentException si el localizador del billete es nulo
	 * @throws IllegalArgumentException si el usuario que desea reservar el billete es nulo
	 * @throws IllegalArgumentException si el recorrido del billete es nulo
	 * @throws IllegalArgumentException si el numero de billetes es menor que 1
	 * @throws IllegalStateException si el recorrido no existe en el sistema
	 * @throws IllegalStateException si se intentan reservar billetes por encima del limite
	 * @throws IllegalStateException si se intentan reservar mas billetes del numero de plazas disponibles
	 */
	public void reservarBillete(String localizador, Usuario usuario, Recorrido recorrido, int nBilletes) {
		boolean encontrado = false;
		if(localizador == null) throw new IllegalArgumentException (MENSAJE_LOCALIZADOR);
		if (usuario == null) throw new IllegalArgumentException ("Usuario no puede ser nulo");
		if (recorrido == null) throw new IllegalArgumentException ("Recorrido no puede ser nulo");
		if (nBilletes < 1) throw new IllegalArgumentException ("Numero billetes no puede ser menor que 1");
		for (Recorrido recorridoSistema : listaRecorridos) {
			if (recorridoSistema.getIdentificador().equals(recorrido.getIdentificador())) {
				encontrado = true;
			}
		}
		if (!encontrado) throw new IllegalStateException (MENSAJE_RECORRIDO);
		int limiteReserva = 0;
		if(recorrido.getTipo()== TipoTransporte.AUTOBUS) {
			limiteReserva=25;
		}
		else{
			limiteReserva=125;
		}
		if(recorrido.getPlazasDisponibles() < limiteReserva) throw new IllegalStateException ("No se pueden reservar billetes por debajo del limite de reserva");
		if(nBilletes > recorrido.getPlazasDisponibles())throw new IllegalStateException ("No se pueden reservar mas billetes que plazas disponibles");
		for (int i=0; i<nBilletes; i++) {
			listaBilletes.add(new Billete(localizador, usuario, recorrido, true));
		}
		int plazasDisponibles = recorrido.getPlazasDisponibles() - nBilletes;
		recorrido.setPlazasDisponibles(plazasDisponibles);
	}
	
	/**
	 * Anula la reserva de un billete reservado anteriormente
	 * @param localizador El localizador del billete que se desea anular la reservar
	 * @param nBilletes El numero de billetes que se desean anular
	 * @throws IllegalArgumentException si el localizador del billete del que se desea anular la reserva es nulo
	 * @throws IllegalArgumentException si el numero de billetes del que se desea anular la reserva se menor que 1
	 * @throws IllegalStateException si no hay billetes reservados
	 * @throws IllegalStateExceptionsi se intenta devolver mas billetes de los reservados
	 */
	public void anularReserva(String localizador, int nBilletes) {
		if (localizador == null) throw new IllegalArgumentException (MENSAJE_LOCALIZADOR);
		if (nBilletes <1) throw new IllegalArgumentException (MENSAJE_BILLETES);
		boolean hayReservados = false;
		ArrayList<Billete> listaBilletesReservadosLocalizador = new ArrayList <>();
		for(int i=0; i<listaBilletes.size(); i++) {
			if(listaBilletes.get(i).getLocalizador().equals(localizador) && listaBilletes.get(i).isReservado()) {
				listaBilletesReservadosLocalizador.add(listaBilletes.get(i));
				hayReservados = true;
			}
		}
		if (!hayReservados) throw new IllegalStateException ("No hay billetes reservados");
		if (listaBilletesReservadosLocalizador.size() < nBilletes) throw new IllegalStateException ("No se pueden devolver mas billetes de los reservados");
		for (int i = 0; i<nBilletes; i++) {
			listaBilletes.remove(listaBilletesReservadosLocalizador.get(i));
		}
		int plazasDisponibles = listaBilletesReservadosLocalizador.get(0).getRecorrido().getPlazasDisponibles() + nBilletes;
		String idRecorrido = listaBilletesReservadosLocalizador.get(0).getRecorrido().getIdentificador();
		for (Recorrido recorrido : listaRecorridos) {
			if (recorrido.getIdentificador().equals(idRecorrido)) {
				recorrido.setPlazasDisponibles(plazasDisponibles);
			}
		}
	}
	
	/**
	 * Compra uno o varios billetes para un recorrido
	 * @param localizador El localizador de los billetes que se desea comprar
	 * @param usuario El usuario que desea comprar los billetes
	 * @param recorrido El recorrido de los billetes que se desea comprar
	 * @param numBilletes El numero de billetes que se desea comprar
	 * @throws IllegalArgumentException si el localizador de los billetes que se desea comprar es nulo
	 * @throws IllegalArgumentException si el usuario que desea comprar los billetes es nulo
	 * @throws IllegalArgumentException si el recorrido de los billetes que se desea comprar es nulo
	 * @throws IllegalStateException si el recorrido de los billetes que se desea comprar no existe en el sistema
	 * @throws IllegalArgumentException si el numero de billetes que se desea comprar es menor que 1
	 * @throws IllegalStateException si el numero de billetes que se desea comprar es mayor que las plazas disponibles
	 */
	public void comprarBillete(String localizador, Usuario usuario, Recorrido recorrido, int numBilletes){
		boolean encontrado = false;
		if (localizador == null) throw new IllegalArgumentException (MENSAJE_LOCALIZADOR);
		if (usuario == null) throw new IllegalArgumentException ("Usuario no puede ser nulo");
		if (recorrido == null) throw new IllegalArgumentException ("Recorrido no puede ser nulo");
		for (Recorrido recorridoSistema : listaRecorridos) {
			if (recorridoSistema.getIdentificador().equals(recorrido.getIdentificador())) {
				encontrado = true;
			}
		}
		if (!encontrado) throw new IllegalStateException (MENSAJE_RECORRIDO);
		if (numBilletes <1) throw new IllegalArgumentException (MENSAJE_BILLETES);
		if (numBilletes > recorrido.getPlazasDisponibles()) throw new IllegalStateException ("Numero de billetes mayor que las plazas disponibles");
		for (int i = 0; i<numBilletes; i++) {
			listaBilletes.add(new Billete(localizador, usuario, recorrido, false));
		}
		int plazasDisponibles = recorrido.getPlazasDisponibles() - numBilletes;
		recorrido.setPlazasDisponibles(plazasDisponibles);
	}
	
	/**
	 * Devuelve uno o varios billetes
	 * @param localizador El localizador de los billetes que se desea devolver
	 * @param numBilletes El numero de billetes que se desea devolver
	 * @throws IllegalArgumentException si el localizador de los billetes que se quiere devolver es nulo
	 * @throws IllegalArgumentException si el numero de los billetes que se quiere devolver es menor que 1
	 * @throws IllegalStateException si el numero de los billetes que intentan devolver es mayor que el de los comprados
	 */
	public void devolverBillete(String localizador, int numBilletes){
		if (localizador == null) throw new IllegalArgumentException (MENSAJE_LOCALIZADOR);
		if (numBilletes <1) throw new IllegalArgumentException (MENSAJE_BILLETES);
		ArrayList<Billete> listaBilletesLocalizador = new ArrayList<>();
		for (Billete billete : listaBilletes) {
			if (billete.getLocalizador().equals(localizador)) {
				listaBilletesLocalizador.add(billete);
			}
		}
		if (listaBilletesLocalizador.size() < numBilletes) throw new IllegalStateException ("No se pueden devolver mas billetes de los comprados");
		for (int i = 0; i<numBilletes; i++) {
			listaBilletes.remove(listaBilletesLocalizador.get(i));
		}
		int plazasDisponibles = listaBilletesLocalizador.get(0).getRecorrido().getPlazasDisponibles() + numBilletes;
		String idRecorrido = listaBilletesLocalizador.get(0).getRecorrido().getIdentificador();
		for (Recorrido recorrido : listaRecorridos) {
			if (recorrido.getIdentificador().equals(idRecorrido)) {
				recorrido.setPlazasDisponibles(plazasDisponibles);
			}
		}
	}
	
	/**
	 * Compra un billete que ha debido de ser reservado con anterioridad
	 * @param localizador El localizador del billete que se quiere comprar
	 * @throws IllegalArgumentException si el localizador del billete que se desea comprar es nulo
	 */
	public void compraBilleteReservado(String localizador){
		if (localizador == null) throw new IllegalArgumentException (MENSAJE_LOCALIZADOR);
		for (Billete billete : listaBilletes) {
			if (billete.getLocalizador().equals(localizador) && billete.isReservado()) {
				billete.comprarBillete();
			}
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
	 * @param nif El nif del usuario del que se quiere consultar
	 * @return precioTotal El precio total de los billetes asociados a un usuario
	 * @throws IllegalArgumentException si el dni del usuario es nulo
	 */
	public double precioBilletesUsuario(String nif) {
		if (nif == null) throw new IllegalArgumentException ("El dni del usuario no puede ser nulo");
		ArrayList<Billete> listaBilletesUsuario = new ArrayList<>();
		for (Billete billete : listaBilletes) {
			if (billete.getUsuario().getNif().equals(nif)) {
				listaBilletesUsuario.add(billete);
			}
		}
		return getPrecioTotal(listaBilletesUsuario);
	}
	
	/**
	 * Consulta la lista de los recorridos disponibles en una fecha
	 * @param fecha La fecha de la que se quiere consultar los recorridos disponibles
	 * @return listaRecorridosFecha La lista de los recorridos disponibles en esa fecha
	 */
	public List<Recorrido> recorridosFecha(LocalDate fecha){
		ArrayList<Recorrido> listaRecorridosFecha = new ArrayList<>();
		for (Recorrido recorrido : listaRecorridos) {
			if (recorrido.getFechaHora().toLocalDate().isEqual(fecha)) {
				listaRecorridosFecha.add(recorrido);
			}
		}
		return listaRecorridosFecha;
	}
}

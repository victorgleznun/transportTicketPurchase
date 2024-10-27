package uva.tds.practica3_grupo5;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.*;

public class DatabaseManager implements IDatabaseManager{
	
	private Session getSession() {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session;
		try {
			session = factory.getCurrentSession();
			return session;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Recorrido getRecorrido(String idRecorrido) {
		if (idRecorrido == null) {
			throw new IllegalArgumentException ("id es nulo");
		}
		Session session = getSession();
		try {
			session.beginTransaction();

			Recorrido recorrido = session.get(Recorrido.class, idRecorrido);
			return recorrido;

		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		return null;

	}
	
	@Override
	public void addRecorrido(Recorrido recorrido) {
		if (recorrido == null) {
			throw new IllegalArgumentException("Recorrido es nulo");
		}
		if (getRecorrido(recorrido.getIdentificador()) != null) {
			throw new IllegalStateException("Ya existe un recorrido con el id proporcionado");
		}
		
		Session session = getSession();

		try {
			session.beginTransaction();

			session.persist(recorrido);
			session.flush();

		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
	}
	
	@Override
	public void eliminarRecorrido(String idRecorrido) {
		if (idRecorrido == null) {
			throw new IllegalArgumentException("id es nulo");
		}
		Session session = getSession();

		try {
			session.beginTransaction();
			Recorrido borrar = session.get(Recorrido.class, idRecorrido);
			session.delete(borrar);
			session.flush();

		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
	}
	
	@Override
	public void actualizarRecorrido(Recorrido recorrido) {
		if (recorrido == null) {
			throw new IllegalArgumentException("Recorrido es nulo");
		}
		if (getRecorrido(recorrido.getIdentificador()) == null) {
			throw new IllegalStateException("No existe un recorrido con el id proporcionado");
		}
		
		Session session = getSession();

		try {
			session.beginTransaction();
			session.update(recorrido);
			session.flush();

		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		
	}
	
	@Override
	public ArrayList<Recorrido> getRecorridos(){
		Session session = getSession();
		try {
			ArrayList<Recorrido> recorridosFecha = new ArrayList<>();
			session.beginTransaction();
			List<Recorrido> allRecorridos = session.createQuery("FROM Recorrido", Recorrido.class).list();
			recorridosFecha.addAll(allRecorridos);
			return recorridosFecha;
			
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		return null;

	}
	
	@Override
	public ArrayList<Recorrido> getRecorridos(LocalDate fecha){
		Session session = getSession();
		try {
			ArrayList<Recorrido> recorridosFecha = new ArrayList<>();
			session.beginTransaction();
			List<Recorrido> allRecorridos = session.createQuery("FROM Recorrido", Recorrido.class).list();
			for (Recorrido recorrido : allRecorridos) {
				if (recorrido.getFechaHora().toLocalDate().equals(fecha)) {
					recorridosFecha.add(recorrido);
				}
			}
			return recorridosFecha;
			
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		return null;

	}

	@Override
	public Usuario getUsuario(String idUsuario) {
		if (idUsuario == null) {
			throw new IllegalArgumentException ("id es nulo");
		}
		Session session = getSession();
		try {
			session.beginTransaction();

			Usuario usuario = session.get(Usuario.class, idUsuario);
			return usuario;

		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		return null;

	}
	
	@Override
	public void addUsuario(Usuario usuario) {
		if (usuario == null) {
			throw new IllegalArgumentException("Usuario es nulo");
		}
		if (getUsuario(usuario.getNif()) != null) {
			throw new IllegalStateException("Ya existe un usuario");
		}
		
		Session session = getSession();

		try {
			session.beginTransaction();
			session.persist(usuario);
			session.flush();

		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
	}
	
	@Override
	public void eliminarUsuario(String idUsuario) {
		if (idUsuario == null) {
			throw new IllegalArgumentException("Id es nulo");
		}
		Session session = getSession();

		try {
			session.beginTransaction();
			Usuario borrar = session.get(Usuario.class, idUsuario);
			session.delete(borrar);
			session.flush();

		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
	}
	
	@Override
	public void actualizarUsuario(Usuario usuario) {
		if (usuario == null) {
			throw new IllegalArgumentException("Usuario es nulo");
		}
		if (getUsuario(usuario.getNif()) == null) {
			throw new IllegalStateException("No existe un usuario con el id proporcionado");
		}
		
		Session session = getSession();

		try {
			session.beginTransaction();
			session.update(usuario);
			session.flush();

		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
	}
	
	@Override
	public ArrayList<Billete> getBilletes(String localizadorBilletes){
		Session session = getSession();
		try {
			ArrayList<Billete> billetesLoc = new ArrayList<>();
			session.beginTransaction();
			List<Billete> allBilletes = session.createQuery("FROM Billete", Billete.class).list();
			for (Billete billete : allBilletes) {
				if (billete.getLocalizador().equals(localizadorBilletes)) {
					billetesLoc.add(billete);
				}
			}
			return billetesLoc;
			
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		return null;

	}
	
	@Override
	public void addBillete(Billete billete) {
		if (billete == null) {
			throw new IllegalArgumentException("Billete es nulo");
		}
		
		Session session = getSession();

		try {
			session.beginTransaction();
			session.persist(billete);
			session.flush();

		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
	}
	
	@Override
	public void eliminarBilletes(String localizadorBillete) {
		if (localizadorBillete == null) {
			throw new IllegalArgumentException("Localizador es nulo");
		}
		ArrayList<Billete> billetes= getBilletes(localizadorBillete);
		Session session = getSession();
		try {
			session.beginTransaction();
			
			for(Billete billete: billetes) {
				session.delete(billete);
			}
			
			session.flush();
			
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
	}
	
	@Override
	public void actualizarBilletes(Billete billete) {
		if (billete == null) {
			throw new IllegalArgumentException("Billete es nulo");
		}
		if (getBilletes(billete.getLocalizador()).size() == 0) {
			throw new IllegalStateException("No existe ningun billete con el localizador proporcionado");
		}
		
		int nBilletes = getBilletes(billete.getLocalizador()).size();
		eliminarBilletes(billete.getLocalizador());
		Session session = getSession();

		try {
			session.beginTransaction();
			for(int i=0; i<nBilletes; i++) {
				session.persist(new Billete(billete.getLocalizador(), billete.getUsuario(), billete.getRecorrido(), billete.isReservado()));
			}
			session.flush();
			
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
	}
	
	@Override
	public ArrayList<Billete> getBilletesDeRecorrido(String idRecorrido){
		Session session = getSession();
		try {
			ArrayList<Billete> billetesDeRecorrido = new ArrayList<>();
			session.beginTransaction();
			List<Billete> allBilletes = session.createQuery("FROM Billete", Billete.class).list();
			for (Billete billete : allBilletes) {
				if (billete.getRecorrido().getIdentificador().equals(idRecorrido)) {
					billetesDeRecorrido.add(billete);
				}
			}
			return billetesDeRecorrido;
			
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		return null;
	}
	
	@Override
	public ArrayList<Billete> getBilletesDeUsuario(String idUsuario){
		Session session = getSession();
		try {
			ArrayList<Billete> billetesDeUsuario = new ArrayList<>();
			session.beginTransaction();
			List<Billete> allBilletes = session.createQuery("FROM Billete", Billete.class).list();
			for (Billete billete : allBilletes) {
				if (billete.getUsuario().getNif().equals(idUsuario)) {
					billetesDeUsuario.add(billete);
				}
			}
			return billetesDeUsuario;
			
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		return null;
	}
	
	/**
	 * Elimina las tablas de la base de datos
	 */
	public void clearDatabase() {
		Session session = getSession();
		session.getTransaction().begin();
		Query query = session.createSQLQuery("Truncate table BILLETE");
		query.executeUpdate();
		query = session.createSQLQuery("Truncate table USUARIO");
		query.executeUpdate();
		query = session.createSQLQuery("Truncate table RECORRIDO");
		query.executeUpdate();
		session.close();

	}

	
}

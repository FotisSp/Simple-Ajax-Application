package org.webapp.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.webapp.entities.HomeAddress;
import org.webapp.entities.User;
import org.webapp.entities.WorkAddress;

public class UserModel {

	protected SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private static final int ID = 0;
	private static final int NAME = 1;
	private static final int SURNAME = 2;
	private static final int GENDER = 3;
	private static final int BIRTHDATE = 4;
	private static final int HOMEADDRESS = 5;
	private static final int WORKADDRESS = 6;

	public boolean create(User u) {
		boolean result = true;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(u);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			result = false;
		} finally {
			session.close();
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getUsers(String query) {
		Session session = null;
		Transaction transaction = null;
		User u = null;
		List<User> users = new ArrayList<User>();

		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			
			List<Object[]> list = session.createNativeQuery(query).list();
			for (Object[] obj : list) {
				u = new User();
				u.setId((Integer)obj[ID]);
				u.setName((String)obj[NAME]);
				u.setSurname((String)obj[SURNAME]);
				users.add(u);
	         }
			
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			return null;
		} finally {
			session.close();
		}
		return users;
	}
	
	public User getSingleUser(String query, int id) {
		Session session = null;
		Transaction transaction = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		User u = new User();
		
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();

			Object[] user = (Object[]) session.createNativeQuery(query).getSingleResult();
			u.setId((Integer)user[ID]);
			u.setName((String)user[NAME]);
			u.setSurname((String)user[SURNAME]);
			u.setGender((String)user[GENDER]);
			u.setBirthdate(dateFormat.format(user[BIRTHDATE]));
			
			HomeAddress homeAdd = new HomeAddress();
			homeAdd.setId(id);
			homeAdd.setAddress((String)user[HOMEADDRESS]);
			homeAdd.setUser(u);
			u.setHomeAddress(homeAdd);
			
			WorkAddress workAdd = new WorkAddress();
			workAdd.setId(id);
			workAdd.setAddress((String)user[WORKADDRESS]);
			workAdd.setUser(u);
			u.setWorkAddress(workAdd);
			
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println("\nrollback\n");
			e.printStackTrace();
			u = null;
		} finally {
			session.close();
		}
		return u;
	}

}
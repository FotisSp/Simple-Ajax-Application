package org.webapp.models;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.webapp.entities.User;

/**
 * @author Fotis Spanopoulos
 *
 */
public class UserModel {

	protected SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private static final int ID = 0;
	private static final int NAME = 1;
	private static final int SURNAME = 2;

	/**
	 * Creates a new user and returns a boolean value accordingly.
	 * If creation fails roll back is executed.
	 * 
	 * @param 	u 	User object with all the necessary information
	 * @return 		True if creation was successful, False otherwise
	 */
	public boolean create(User u) {
		boolean result = true;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.persist(u);
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
	
	/**
	 * Updates the information of a single user to the SQL Database.
	 * User object contains the required info to be updated.
	 * 
	 * @param 	us 		A User Object containing the updated info.
	 * @return 			True if update was successful, False otherwise
	 */
	public boolean update(User us) {
		boolean result = true;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			session.merge(us);
				
			result = true;
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
			result = false;
		} finally {
			session.close();
		}
		return result;
	}
	
	/**
	 * Returns a List of all the registered users inside the SQL Database.
	 * If something fails roll back is executed.
	 * 
	 * @param 	query 	A single Native SQL query to retrieve all users
	 * @return 			List of User objects which contain the information about the Users.
	 */
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
	
	/**
	 * Returns a single User object which contains the 
	 * information about the user that was selected.
	 * If something fails during this procedure roll back is executed.
	 * 
	 * @param 	query	Native SQL query to retrieve single user.
	 * @param 	id		id to populate the foreign keys of child tables.
	 * @return			Single User object with the requested information. 
	 */
	public User getSingleUser(String query, int id) {
		Session session = null;
		Transaction transaction = null;
		User u = new User();
		
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();

			u = session.createNativeQuery(query,User.class).getSingleResult();
			
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return u;
	}
	
	/**
	 * Deletes the specified user with the help of a Native SQL query. 
	 * 
	 * @param 	query	Native SQL query to delete user.
	 * @return			True if delete was successful False otherwise. 
	 */
	@SuppressWarnings("rawtypes")
	public boolean deleteUser(String query) {
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();

			NativeQuery q = session.createNativeQuery(query);
			q.executeUpdate();
			
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
		return true;
	}

}
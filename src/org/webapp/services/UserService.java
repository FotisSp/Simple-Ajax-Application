package org.webapp.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Session;
import org.webapp.entities.HomeAddress;
import org.webapp.entities.WorkAddress;
import org.webapp.models.HibernateUtil;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.webapp.entities.User;

@Path("/user")
public class UserService {
	private static final int ID = 0;
	private static final int NAME = 1;
	private static final int SURNAME = 2;
	private static final int GENDER = 3;
	private static final int BIRTHDATE = 4;
	private static final int HOMEADDRESS = 5;
	private static final int WORKADDRESS = 6;
	private static List<User> users = new ArrayList<User>();

	@SuppressWarnings("unchecked")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUsers() throws Exception {
		users = new ArrayList<User>();
		
		Session session = null;
		User u = null;

		session = HibernateUtil.getSessionFactory().openSession();
		
		String query = "SELECT " + 
				"    u.id, " + 
				"    u.name, " + 
				"    u.surname " + 
				" FROM " + 
				"    webapp.users as u " + 
				" ORDER BY u.name ASC;";
		
		List<Object[]> list = session.createNativeQuery(query).list();
		for (Object[] obj : list) {
			u = new User();
			u.setId((Integer)obj[ID]);
			u.setName((String)obj[NAME]);
			u.setSurname((String)obj[SURNAME]);
			users.add(u);
         }
		
		//TODO
//	    ObjectMapper mapper = new ObjectMapper();
//	    mapper.setSerializationInclusion(Include.NON_NULL);

//		System.out.println(mapper.writeValueAsString(users));

		return users;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public User getUser(@PathParam("id") int id) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Session session = HibernateUtil.getSessionFactory().openSession();
		User u = new User();
		
		String query = "SELECT DISTINCT" + 
				"    u.id," + 
				"    u.name," + 
				"    u.surname," + 
				"    u.gender," + 
				"    u.birthdate," + 
				"    h.homeAddress," + 
				"    w.workAddress" + 
				" FROM" + 
				"    webapp.users AS u" + 
				"        INNER JOIN" + 
				"    webapp.home_address AS h ON u.id = h.user_id" + 
				"        INNER JOIN" + 
				"    webapp.work_address AS w ON u.id = w.user_id" + 
				" WHERE" + 
				"   u.id = " + id + ";";
		
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
		
		return u;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	@Path("/register")
	public Response registerUser(User us) {
		System.out.println("test");
		users.add(us);
		return Response.status(200).entity("Successfully added user " + us.getName()).build();
	}
	
	
}

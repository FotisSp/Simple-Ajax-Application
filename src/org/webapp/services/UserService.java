package org.webapp.services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Session;
import org.webapp.models.HibernateUtil;
import org.webapp.entities.User;

@Path("/user")
public class UserService {
	private static final int ID = 0;
	private static final int NAME = 1;
	private static final int SURNAME = 2;

	@GET  
    @Path("/{param}")  
    public Response getMsg(@PathParam("param") String msg) {  
        String output = "Jersey say : " + msg;  
        return Response.status(200).entity(output).build();  
    } 
	
	@SuppressWarnings("unchecked")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<User> getAllUsers() throws Exception {
		List<User> users = new ArrayList<User>();
		
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
		
		System.out.println(users.get(0).toString());
		return users;
	}
}

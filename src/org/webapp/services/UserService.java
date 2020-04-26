package org.webapp.services;

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

import org.webapp.models.UserModel;
import org.webapp.entities.HomeAddress;
import org.webapp.entities.User;
import org.webapp.entities.WorkAddress;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserService {
	private static List<User> users = new ArrayList<User>();

//	@SuppressWarnings("unchecked")
	@GET
	public List<User> getAllUsers() throws Exception {
		UserModel um = new UserModel();

		String query = "SELECT " + 
				"    u.id, " + 
				"    u.name, " + 
				"    u.surname " + 
				" FROM " + 
				"    webapp.users as u " + 
				" ORDER BY u.name ASC;";
		
		//TODO
//	    ObjectMapper mapper = new ObjectMapper();
//	    mapper.setSerializationInclusion(Include.NON_NULL);
//		System.out.println(mapper.writeValueAsString(users));
		
		users = um.getUsers(query); 
		
		return users;
	}
	
	@GET
	@Path("{id}")
	public User getUser(@PathParam("id") int id) {
		UserModel um = new UserModel();
		
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
		
		return um.getSingleUser(query, id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	@Path("/register")
	public Response registerUser(User us) {
		UserModel um = new UserModel();

		HomeAddress hAdd = us.getHomeAddress();
		hAdd.setId(us.getId());
		hAdd.setUser(us);
		us.setHomeAddress(hAdd);
		
		WorkAddress wAdd = us.getWorkAddress();
		wAdd.setId(us.getId());
		wAdd.setUser(us);
		us.setWorkAddress(wAdd);
		
		users.add(us);
		if(um.create(us)) {
			return Response.status(200).entity("Successfully registered user " + us.getName()).build();
		} 		
		return Response.status(200).entity("Failed registering user " + us.getName()).build();
	}
	
	@GET
	@Path("/delete/{id}")
	public boolean deleteUser(@PathParam("id") int id) {
		UserModel um = new UserModel();
		
		String query = "DELETE FROM `webapp`.`users` WHERE (`id` = '" + id + "');";
		
		return um.deleteUser(query);
	}
	
	
}

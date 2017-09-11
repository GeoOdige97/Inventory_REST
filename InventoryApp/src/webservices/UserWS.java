package webservices;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import controller.EntityDAO;
import entities.Component;
import entities.User;
import exceptions.NonexistentEntityException;
import exceptions.RollbackFailureException;
import mappers.UserMapper;

@Path("/service")
public class UserWS {
	
	private EntityDAO<User> jpac;
	private Class<User> userClass = User.class;
	
	public UserWS() {
		this.jpac = new EntityDAO<User>();
	}

	@GET
	@Path("/user/findAll")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public List<User> getAllUser(){
		
		System.out.println("get all users");
		List<User> userList = UserMapper.findAll();
		return userList;	
	}
	
	@GET
	@Path("/user/find/{id}")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public List<User> getUser(@PathParam("id") Integer userId ){
		
		System.out.println("get user");
		List<User> userList = UserMapper.findById(userId);
		
		return userList;
	}
	
	
	@POST
	@Path("/user/add")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	public void addUser( String JSONUser ) throws NonexistentEntityException, RollbackFailureException, Exception{
		
		Gson gson = new Gson();
		User user = gson.fromJson(JSONUser, userClass);
		this.jpac.create(new Component<User>(user));
	}
	
	
	@PUT
	@Path("/user/update")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	public void editUser(@PathParam("JSONUser") String JSONUser ) throws NonexistentEntityException, RollbackFailureException, Exception{
		
		Gson gson = new Gson();
		User user = gson.fromJson(JSONUser, userClass);
		this.jpac.edit(new Component<User>(user));
	}
	
	@DELETE
	@Path("/user/delete/{id}")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	public void deleteUser(@PathParam("userId") int userId ) throws NonexistentEntityException, RollbackFailureException, Exception{
		
		this.jpac.destroy(userId,userClass);
	}
	
}

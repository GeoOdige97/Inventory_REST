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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import controller.EntityDAO;
import entities.Component;
import entities.Registration;
import exceptions.NonexistentEntityException;
import exceptions.RollbackFailureException;
import mappers.RegistrationMapper;

@Path("/service")
public class RegistrationWS {


	private EntityDAO<Registration> registrationJpac = null;
	private Class<Registration> registrationClass = Registration.class;
	public RegistrationWS() {

		this.registrationJpac = new EntityDAO<Registration>();
	}

	@GET
	@Path("/registration/findAll")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_XML,})
	public Response getAllRegistrations(){
		
		List<Registration> registrationList = RegistrationMapper.findAll();
		Gson gson = new GsonBuilder().serializeNulls().create();	
		return Response.ok(gson.toJson(registrationList), MediaType.APPLICATION_JSON).build();	
	}
	
	@GET
	@Path("/registration/findAllLatest")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_XML,})
	public Response getLatestRegistration(){
		
		List<Registration> registrationList = RegistrationMapper.findAll();
		Gson gson = new GsonBuilder().serializeNulls().create();	
		return Response.ok(gson.toJson(registrationList), MediaType.APPLICATION_JSON).build();	
	}	

	@GET
	@Path("/registration/find/{id}")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	public Response getRegistration(@PathParam("id") Integer registrationId ) throws NonexistentEntityException, RollbackFailureException, Exception{
		
		List<Registration> registrationList =  RegistrationMapper.findById(registrationId);
		Gson gson = new GsonBuilder().serializeNulls().create();	
		return Response.ok(gson.toJson(registrationList), MediaType.APPLICATION_JSON).build();	
		
	}
	
	@GET
	@Path("/registration/findLatest/{id}")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	public Response getLatestRegistration(@PathParam("id") Integer deviceId ) throws NonexistentEntityException, RollbackFailureException, Exception{
		/*
		Registration registrationList =  RegistrationMapper.findLatestRegistration(deviceId,null);
		System.out.println(registrationList.toString());
		Gson gson = new GsonBuilder().serializeNulls().create();	*/
		return null;/*Response.ok(gson.toJson(registrationList), MediaType.APPLICATION_JSON).build();	*/
		
	}
	
	
	
	@POST
	@Path("/registration/add")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	public Response addRegistration( String formParams ) throws NonexistentEntityException, RollbackFailureException, Exception{

		Gson gson = new GsonBuilder().create();
		Registration registration = gson.fromJson(formParams, registrationClass);
		
		/*
		 * Verify if id exist using jpa finders
		 * 
		 * */
		
		this.registrationJpac.create(new Component<Registration>(registration));
		
		
		// After Data Insert/Verification resend the added object
		GenericEntity<Registration> entity = new GenericEntity<Registration>(registration) {};
		return Response.ok(entity, MediaType.APPLICATION_JSON).build();	
	}


	@PUT
	@Path("/registration/update")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response editRegistration( String JSONregistration ) throws NonexistentEntityException, RollbackFailureException, Exception{

		Gson gson = new GsonBuilder().create();
		Registration registration = gson.fromJson(JSONregistration, registrationClass);
		this.registrationJpac.edit(new Component<Registration>(registration));
		
		GenericEntity<Registration> entity = new GenericEntity<Registration>(registration) {};
		return Response.ok(entity, MediaType.APPLICATION_JSON).build();	
	}

	@DELETE
	@Path("/registration/delete/{id}")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public void deleteRegistration(@PathParam("id") int registrationId ) throws NonexistentEntityException, RollbackFailureException, Exception{

		System.out.println("DELETE: registration id "+ registrationId);
		this.registrationJpac.destroy(registrationId,registrationClass);
	}
	

}

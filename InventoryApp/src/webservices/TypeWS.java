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
import entities.Type;
import exceptions.NonexistentEntityException;
import exceptions.RollbackFailureException;
import mappers.TypeMapper;

@Path("/service")
public class TypeWS {


	private EntityDAO<Type> typeJpac = null;
	private Class<Type> typeClass = Type.class;
	public TypeWS() {
		this.typeJpac = new EntityDAO<Type>();
	}

	@GET
	@Path("/type/findAll")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_XML,})
	public Response getAllTypes(){
		List<Type> typeList = TypeMapper.findAll();
		//typeList = addLatestRegistrationsToTypes(typeList);
		Gson gson = new GsonBuilder().serializeNulls().create();	
		return Response.ok(gson.toJson(typeList), MediaType.APPLICATION_JSON).build();	
		
	}
	
	@GET
	@Path("/type/find/{id}")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	public Response getType(@PathParam("id") Integer typeId ) throws NonexistentEntityException, RollbackFailureException, Exception{
		List<Type> typeList =  TypeMapper.findById(typeId);		
		//typeList = addLatestRegistrationsToTypes(typeList);
		Gson gson = new GsonBuilder().serializeNulls().create();	
		return Response.ok(gson.toJson(typeList), MediaType.APPLICATION_JSON).build();	
		
	}
	
	@POST
	@Path("/type/add")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	public Response addType( String formParams ) throws NonexistentEntityException, RollbackFailureException, Exception{
		Gson gson = new GsonBuilder().create();
		Type type = gson.fromJson(formParams, typeClass);
		
		/*
		 * Verify if id exist using jpa finders
		 * 
		 * */
		
		this.typeJpac.create(new Component<Type>(type));
		
		
		// After Data Insert/Verification resend the added object
		GenericEntity<Type> entity = new GenericEntity<Type>(type) {};
		return Response.ok(entity, MediaType.APPLICATION_JSON).build();	
	}


	@PUT
	@Path("/type/update")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response editType( String JSONtype ) throws NonexistentEntityException, RollbackFailureException, Exception{

		Gson gson = new GsonBuilder().create();
		Type type = gson.fromJson(JSONtype, typeClass);
		this.typeJpac.edit(new Component<Type>(type));
		
		GenericEntity<Type> entity = new GenericEntity<Type>(type) {};
		return Response.ok(entity, MediaType.APPLICATION_JSON).build();	
	}

	@DELETE
	@Path("/type/delete/{id}")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public void deleteType(@PathParam("id") int typeId ) throws NonexistentEntityException, RollbackFailureException, Exception{

		System.out.println("DELETE: type id "+ typeId);
		this.typeJpac.destroy(typeId,typeClass);
	}
/*	
	private List<Type> addRegistrationsToTypes(List<Type> typeList) {
		for (int i = 0; i < typeList.size(); i++){
			int typeId = typeList.get(i).getId();
			typeList.get(i).setRegistrationList(TypeMapper.findRegistrationsByTypeId(typeId));
		}
		return typeList;
	}
	
	private List<Type> addLatestRegistrationsToTypes(List<Type> typeList) {
		for (int i = 0; i < typeList.size(); i++){
			int typeId = typeList.get(i).getId();
			typeList.get(i).setRegistrationList(TypeMapper.findLatestRegistrationsByTypeId(typeId));
		}
		return typeList;
	}
	*/

}

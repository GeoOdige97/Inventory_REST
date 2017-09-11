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
import entities.Location;
import exceptions.NonexistentEntityException;
import exceptions.RollbackFailureException;
import mappers.LocationMapper;

@Path("/service")
public class LocationWS {
	
	private EntityDAO<Location> jpac;
	private Class<Location> entityClass = Location.class;
	
	public LocationWS() {
		
		this.jpac = new EntityDAO<Location>();
	}

	@GET
	@Path("/location/findAll")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public List<Location> getAllLocation(){
		System.out.println("get all locations");
		List<Location> locationList = LocationMapper.findAll();
		return locationList;	
	}
	
	@GET
	@Path("/location/find/{id}")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public List<Location> getLocation(@PathParam("id") Integer locationId ){
		System.out.println("get location");
		List<Location> location = LocationMapper.findById(locationId);
		return location;
	}
	
	
	@POST
	@Path("/location/add")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	public void addLocation(@PathParam("JSONlocation") String JSONlocation ) throws NonexistentEntityException, RollbackFailureException, Exception{
		
		Gson gson = new Gson();
		Location location = gson.fromJson(JSONlocation, entityClass);
		this.jpac.create(new Component<Location>(location));
	}
	
	@PUT
	@Path("/location/update")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	public void editLocation(@PathParam("JSONlocation") String JSONlocation ) throws NonexistentEntityException, RollbackFailureException, Exception{
		
		Gson gson = new Gson();
		Location location = gson.fromJson(JSONlocation, entityClass);
		this.jpac.edit(new Component<Location>(location));
	}
	
	@DELETE
	@Path("/location/delete/{id}")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	public void deleteLocation(@PathParam("locationId") int locationId ) throws NonexistentEntityException, RollbackFailureException, Exception{
		
		this.jpac.destroy(locationId,entityClass);
	}
	
}

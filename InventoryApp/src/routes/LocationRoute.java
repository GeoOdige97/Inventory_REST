package routes;

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

import dao.DBContextListener;
import dao.interf.IMasterEntityDAO;
import entities.impl.Location;
import exceptions.NonexistentEntityException;
import exceptions.RollbackFailureException;
import mappers.LocationMapper;

@Path("/service")
public class LocationRoute {
	
	private IMasterEntityDAO context;
	
	public LocationRoute() {
		this.context = DBContextListener.getMasterDAO();
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
		Location location = gson.fromJson(JSONlocation, Location.class);
		context.getLocationDAO().create(location);
	}
	
	@PUT
	@Path("/location/update")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	public void editLocation(@PathParam("JSONlocation") String JSONlocation ) throws NonexistentEntityException, RollbackFailureException, Exception{
		
		Gson gson = new Gson();
		Location location = gson.fromJson(JSONlocation, Location.class);
		context.getLocationDAO().edit(location);
	}
	
	@DELETE
	@Path("/location/delete/{id}")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	public void deleteLocation(@PathParam("locationId") int locationId ) throws NonexistentEntityException, RollbackFailureException, Exception{
		
		context.getLocationDAO().destroy(locationId);
	}
	
}

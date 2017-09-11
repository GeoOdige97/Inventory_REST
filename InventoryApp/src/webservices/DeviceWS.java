package webservices;

import java.util.ArrayList;
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
import entities.Computer;
import entities.Device;
import entities.Location;
import entities.Network;
import entities.Product;
import entities.Registration;
import entities.User;
import exceptions.NonexistentEntityException;
import exceptions.RollbackFailureException;
import mappers.DeviceMapper;
import mappers.LocationMapper;
import mappers.ProductMapper;
import mappers.UserMapper;

@Path("/service")
public class DeviceWS {


	private EntityDAO<Device> deviceJpac = null;
	private EntityDAO<Network> networkJpac = null;
	private EntityDAO<Computer> computerJpac = null;
	private EntityDAO<Registration> registrationJpac = null;

	private Class<Device> deviceClass = Device.class;
	private Class<Network> networkClass = Network.class;
	private Class<Computer> computerClass = Computer.class;
	private Class<Registration> registrationClass = Registration.class;
	
	public DeviceWS() {
		this.deviceJpac = new EntityDAO<Device>();
		this.networkJpac = new EntityDAO<Network>();
		this.computerJpac = new EntityDAO<Computer>();
		this.registrationJpac = new EntityDAO<Registration>();
	}

	@GET
	@Path("/device/findAll")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_XML,})
	public Response getAllDevices(){
		
		List<Device> deviceList = DeviceMapper.findAll();
		deviceList = addLatestRegistrationsToDevices(deviceList);
		Gson gson = new GsonBuilder().serializeNulls().create();	
		return Response.ok(gson.toJson(deviceList), MediaType.APPLICATION_JSON).build();	
		
	}
	
	@GET
	@Path("/device/find/{id}")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	public Response getDevice(@PathParam("id") Integer deviceId ) throws NonexistentEntityException, RollbackFailureException, Exception{
		
		List<Device> deviceList =  DeviceMapper.findById(deviceId);		
		deviceList = addLatestRegistrationsToDevices(deviceList);
		Gson gson = new GsonBuilder().serializeNulls().create();	
		return Response.ok(gson.toJson(deviceList), MediaType.APPLICATION_JSON).build();	
		
	}
	
	@POST
	@Path("/device/add")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	public Response addDevice( String formParams ) throws NonexistentEntityException, RollbackFailureException, Exception{

		Gson gson = new GsonBuilder().create();
		Device device = gson.fromJson(formParams, deviceClass);
		Registration registration = gson.fromJson(formParams, Registration.class);
		
		List<User> userList = UserMapper.findById(registration.getUser().getId());
		List<Location> locationList = LocationMapper.findById(device.getLocation().getId());
		List<Product> productList = ProductMapper.findById(device.getProduct().getId());
		
		int networkId = this.networkJpac.getMax(networkClass);
		int computerId = this.computerJpac.getMax(computerClass);
		
		System.out.println(computerId);
		
		device.getNetwork().setId(networkId + 1);
		device.getComputer().setId(computerId + 1);
		device.setProduct(productList.get(0));
		device.setLocation(locationList.get(0));
		registration.setUser(userList.get(0));
		
		/*
		 * Verify if id exist using jpa finders
		 * 
		 */
		this.deviceJpac.create(new Component<Device>(device));
		this.registrationJpac.create(new Component<Registration>(registration));
		
		
		// After Data Insert/Verification resend the added object
		List<Device> deviceList = new ArrayList<Device>(0);
		deviceList.add(device);
		return Response.ok(gson.toJson(deviceList), MediaType.APPLICATION_JSON).build();	
	}


	@PUT
	@Path("/device/update")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response editDevice( String JSONdevice ) throws NonexistentEntityException, RollbackFailureException, Exception{

		Gson gson = new GsonBuilder().create();
		Device device = gson.fromJson(JSONdevice, deviceClass);
		this.deviceJpac.edit(new Component<Device>(device));
		
		GenericEntity<Device> entity = new GenericEntity<Device>(device) {};
		return Response.ok(entity, MediaType.APPLICATION_JSON).build();	
	}

	@DELETE
	@Path("/device/delete/{id}")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public void deleteDevice(@PathParam("id") int deviceId ) throws NonexistentEntityException, RollbackFailureException, Exception{
		System.out.println("DELETE: device id "+ deviceId);
		this.deviceJpac.destroy(deviceId,deviceClass);
	}
	
	private List<Device> addRegistrationsToDevices(List<Device> deviceList) {
		for (int i = 0; i < deviceList.size(); i++){
			int deviceId = deviceList.get(i).getId();
			deviceList.get(i).setRegistrationList(DeviceMapper.findRegistrationsByDeviceId(deviceId));
		}
		return deviceList;
	}
	
	private List<Device> addLatestRegistrationsToDevices(List<Device> deviceList) {
		for (int i = 0; i < deviceList.size(); i++){
			int deviceId = deviceList.get(i).getId();
			deviceList.get(i).setRegistrationList(DeviceMapper.findLatestRegistrationsByDeviceId(deviceId));
		}
		return deviceList;
	}
	

}

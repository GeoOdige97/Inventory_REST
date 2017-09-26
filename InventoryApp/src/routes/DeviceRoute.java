package routes;

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

import dao.DBContextListener;
import dao.interf.IMasterEntityDAO;
import entities.creators.impl.DeviceCreator;
import entities.creators.impl.RegistrationCreator;
import entities.impl.Device;
import entities.impl.Registration;
import exceptions.NonexistentEntityException;
import exceptions.RollbackFailureException;
import mappers.DeviceMapper;

@Path("/service")
public class DeviceRoute {

	private IMasterEntityDAO context;
	
	public DeviceRoute() {
		this.context = DBContextListener.getMasterDAO();
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
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,
		       MediaType.APPLICATION_FORM_URLENCODED})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,
		       MediaType.APPLICATION_FORM_URLENCODED})
	public Response addDevice( String jsonParams ) throws NonexistentEntityException, RollbackFailureException, Exception{

		Gson gson = new GsonBuilder().create();
		
		DeviceCreator deviceCreator = new DeviceCreator();
		Device device = (Device) deviceCreator.buildFromJson(context, jsonParams);
		RegistrationCreator registrationCreator = new RegistrationCreator();
		Registration registration = (Registration) registrationCreator
				                    .buildFromJson(context, jsonParams);
		
		Integer deviceId = context.getDeviceDAO().create(device);
		device.setId(deviceId);
		registration.setDevice(device);
		Integer registratonId = context.getRegistrationDAO().create(registration);
		List<Device> deviceList = new ArrayList<Device>(0);
		if (deviceId != null){
			deviceList = DeviceMapper.findById(deviceId);
		}
		return Response.ok(gson.toJson(deviceList), MediaType.APPLICATION_JSON).build();	
	}

	@PUT
	@Path("/device/update")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response editDevice( String jsonParams ) throws NonexistentEntityException, RollbackFailureException, Exception{

		Gson gson = new GsonBuilder().create();
		Device device = gson.fromJson(jsonParams, Device.class);
		context.getDeviceDAO().edit(device);

		GenericEntity<Device> entity = new GenericEntity<Device>(device) {};
		return Response.ok(entity, MediaType.APPLICATION_JSON).build();	
	}

	@DELETE
	@Path("/device/delete/{id}")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public void deleteDevice(@PathParam("id") Integer deviceId ) throws NonexistentEntityException, RollbackFailureException, Exception{
		//System.out.println("DELETE: device id "+ deviceId);
		if (deviceId != null){
			context.getDeviceDAO().destroy(deviceId);
		}
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

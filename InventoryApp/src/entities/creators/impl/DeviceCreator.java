package entities.creators.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dao.interf.IEntity;
import dao.interf.IMasterEntityDAO;
import entities.creators.interf.IEntityCreator;
import entities.impl.Device;
import exceptions.NonexistentEntityException;

public class DeviceCreator implements IEntityCreator{
	
	public DeviceCreator (){
	
	}
	
	@Override
	public IEntity buildFromJson (IMasterEntityDAO context, String jsonParams) throws NonexistentEntityException{
		
		Gson gson = new GsonBuilder().create();
		JsonElement jsonElem = gson.fromJson(jsonParams, JsonElement.class);
		JsonObject jsonObject = jsonElem.getAsJsonObject();
		
		JsonElement jsonDevice = jsonObject.get("device");
		IEntity equipment = gson.fromJson(jsonDevice, Device.class);
		
		// TODO: validate Device fields
		
		equipment = addForeignEntities(context,equipment);
		
		return equipment;

	}
	
	@Override
	public IEntity validateForeignEntities(IEntity device){
		return null;
	}
	
	@Override
	public IEntity addForeignEntities(IMasterEntityDAO context, IEntity equipment) throws NonexistentEntityException {
		
		Device device = (Device) equipment;

		if (device.getNetwork() != null){
			if (device.getNetwork().getId() <= 0 ){
				device.getNetwork().setId(context.getNetworkDAO().getMax() + 1);
			}
		}
		
		if (device.getComputer() != null){
			if (device.getNetwork().getId() <= 0 ){
				device.getComputer().setId(context.getComputertDAO().getMax() + 1);
			}
		}
		
		if (device.getPrinter() != null){
			if (device.getPrinter().getId() <= 0){
				device.getPrinter().setId(context.getPrinterDAO().getMax() + 1);  
			}
		}
		
		if (device.getLocation() != null){
			Integer locationId = device.getLocation().getId();
			if (locationId > 0 || locationId != null){
				if(!context.getLocationDAO().entityExist(locationId)){
					device.getLocation().setId(0);
				}
			}
		}
		// TODO: Declare exception

		if (device.getProduct() != null){
			Integer productId = device.getProduct().getId();
			if (productId > 0 || productId != null){
				if(!context.getProductDAO().entityExist(productId)){
					device.getProduct().setId(0);
				}
			}
		}
		// TODO: Declare exception
		return device;
	}
}

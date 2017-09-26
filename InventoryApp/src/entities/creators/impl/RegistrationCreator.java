package entities.creators.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dao.interf.IEntity;
import dao.interf.IMasterEntityDAO;
import entities.creators.interf.IEntityCreator;
import entities.impl.Registration;
import exceptions.NonexistentEntityException;

public class RegistrationCreator implements IEntityCreator{
	
	public RegistrationCreator (){}
	
	@Override
	public IEntity buildFromJson (IMasterEntityDAO context, String jsonParams) throws NonexistentEntityException{
		
		Gson gson = new GsonBuilder().create();
		JsonElement jsonElem = gson.fromJson(jsonParams, JsonElement.class);
		JsonObject jsonObject = jsonElem.getAsJsonObject();
		
		JsonElement jsonDevice = jsonObject.get("registration");
		IEntity equipment = gson.fromJson(jsonDevice, Registration.class);
		
		// TODO: validate Registration fields
		
		equipment = addForeignEntities(context,equipment);
		
		return  (Registration) equipment;

	}
	
	@Override
	public IEntity validateForeignEntities(IEntity registration){
		return null;
	}
	
	@Override
	public IEntity addForeignEntities(IMasterEntityDAO context, IEntity equipment) throws NonexistentEntityException {
		
		Registration registration = (Registration) equipment;
		registration.setLatestRegistration(true);
		
		if (registration.getUser() != null){
			Integer userId = registration.getUser().getId();
			if (userId > 0 || userId != null){
				if(!context.getUserDAO().entityExist(userId)){
					registration.getUser().setId(0);
				}
			}
		}
		
		if (registration.getDevice() != null){
			Integer deviceId = registration.getDevice().getId();
			if (deviceId > 0 || deviceId != null){
				if(!context.getUserDAO().entityExist(deviceId)){
					registration.getDevice().setId(0);
				}
			}
		}
		
		return registration;
	}
}

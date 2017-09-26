package entities.creators.interf;

import dao.interf.IEntity;
import dao.interf.IMasterEntityDAO;
import exceptions.NonexistentEntityException;

public interface IEntityCreator {
	
	
	IEntity validateForeignEntities(IEntity equipment);

	IEntity buildFromJson(IMasterEntityDAO context, String jsonParams) throws NonexistentEntityException;

	IEntity addForeignEntities(IMasterEntityDAO context, IEntity equipment) throws NonexistentEntityException;

}

package dao.interf;

import exceptions.NonexistentEntityException;
import exceptions.PreexistingEntityException;
import exceptions.RollbackFailureException;

public interface IEntityDAO<T extends IEntity> {
	
	void EntityDAO();
	
	Integer create(T component) throws PreexistingEntityException, RollbackFailureException, Exception;

	Integer edit(T component) throws NonexistentEntityException, RollbackFailureException, Exception;

	Integer destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception;

	Boolean entityExist(Integer id) throws NonexistentEntityException;

	int getMax();


	

}

package dao.impl;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import dao.DBContextListener;
import dao.interf.IEntity;
import dao.interf.IEntityDAO;
import exceptions.NonexistentEntityException;
import exceptions.PreexistingEntityException;
import exceptions.RollbackFailureException;


/**
 *
 * @author Geodner
 * 
 * Reflection class that allows the app to perform the following 
 * database transaction: insert, update and delete
 */
public class EntityDAO<T extends IEntity> implements Serializable,IEntityDAO<T> {

	private static final long serialVersionUID = 1L;
	final Class<T> typeParameterClass;

	
	public EntityDAO(Class <T> typeParameterClass){
		this.typeParameterClass = typeParameterClass;
	}
	
	
	@Override
	public Integer create(T component) throws PreexistingEntityException, RollbackFailureException, Exception {

		EntityManager em = null;
		Boolean transactionResult = false;

		try {
			em = DBContextListener.getEntityManager();
			em.getTransaction().begin();
			component.setId(getMax() + 1 );
			em.persist(component);
			em.getTransaction().commit();
			em.clear();
			transactionResult = true;
		} catch (Exception ex) {
			transactionResult = false;
			try {
				em.getTransaction().rollback();
			} catch (Exception re) {
				throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
			}
			if (!entityExist(component.getId())){
				throw new PreexistingEntityException(
						component.getClass().toString() + " " + component + " already exists.", ex);
			}
			throw ex;
		} finally {
			if (em != null) {
				 em.close();
			}
		}
		
		if(transactionResult){
			return component.getId();
		}else{
			return null;
		}
	}

	@Override
	public Integer edit(IEntity component) throws NonexistentEntityException, RollbackFailureException, Exception {

		EntityManager em = null;
		Boolean transactionResult = false;
			
		try {
			em = DBContextListener.getEntityManager();
			em.getTransaction().begin();
			em.merge(component);
			em.getTransaction().commit();
			transactionResult = true;
		} catch (Exception ex) {
			transactionResult = false;
			try {
				em.getTransaction().rollback();
			} catch (Exception re) {
				transactionResult = false;
				throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
			}
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = component.getId();
				if (!entityExist(id)) {
					throw new NonexistentEntityException(
							"The " + component.getClass().toString() + " with id " + id + " no longer exists.");	
				}
			}
			throw ex;
		} finally {
			if (em != null) {
				 em.close();
			}
		}
		
		if(transactionResult){
			return component.getId();
		}else{
			return null;
		}
	}

	@Override
	public Integer destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {

		IEntity component = null;
		EntityManager em = null;
		Boolean transactionResult = false;
		
		try {
			
			try {
				em = DBContextListener.getEntityManager();
				em.getTransaction().begin();
				em.getReference(typeParameterClass, id);
				component.getId();
			} catch (EntityNotFoundException enfe) {
				transactionResult = false;
				throw new NonexistentEntityException(
					"The " + component.getClass().toString() + " with id " + id + " no longer exists.", enfe);
			}
			em.remove(component);
			em.getTransaction().commit();
			transactionResult = true;
		} catch (Exception ex) {
			transactionResult = false;
			try {
				em.getTransaction().rollback();
			} catch (Exception re) {
				throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
			}
			throw ex;
		} finally {
			if (em != null) {
				 em.close();
			}
		}
		
		if(transactionResult){
			return component.getId();
		}else{
			return null;
		}
	}

	@Override
	public Boolean entityExist(Integer id) throws NonexistentEntityException {
		
		EntityManager em = null;
		Boolean transactionResult = false;
		try {
			em = DBContextListener.getEntityManager();
			em.find(typeParameterClass, id);
			transactionResult = true;
		} catch (EntityNotFoundException enfe) {
			transactionResult = false;
			throw new NonexistentEntityException( "The " + typeParameterClass.toString() + " with id " + id + " no longer exists.", enfe);
		} finally {
			if (em != null) {
				 em.close();
			}
		}
		return transactionResult;
	}

	@Override
	public int getMax() {
	
		EntityManager em = null;
		try {
			em = DBContextListener.getEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<?> cq = cb.createQuery(typeParameterClass);
			Root<?> rt = cq.from(typeParameterClass);
			cq.select(cb.greatest(rt.get("id")));
			Query q = em.createQuery(cq);
			return ((Integer) q.getSingleResult()).intValue();
		} finally {
			if (em != null) {
				 em.close();
			}
		}
	}

	@Override
	public void EntityDAO() {
		// TODO Auto-generated method stub
		
	}
	
	
}

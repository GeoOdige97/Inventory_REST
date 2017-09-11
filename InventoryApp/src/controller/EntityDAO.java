package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import entities.Component;
import exceptions.NonexistentEntityException;
import exceptions.PreexistingEntityException;
import exceptions.RollbackFailureException;


/**
 *
 * @author Geodner
 */
public class EntityDAO<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	

	public EntityDAO() {}
	
	public void create(Component<T> component) throws PreexistingEntityException, RollbackFailureException, Exception {

		EntityManager em = null;

		try {
			em = ServletListener.getEntityManager();
			em.getTransaction().begin();
			component.setComponentID(getMax(component.getClassType()) + 1 );
			em.persist(component.getComponent());
			em.getTransaction().commit();
		} catch (Exception ex) {
			try {
				em.getTransaction().rollback();
			} catch (Exception re) {
				throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
			}
			if (findById(component.getComponentID(), component.getClassType()) != null) {
				throw new PreexistingEntityException(
						component.getClassType().toString() + " " + component + " already exists.", ex);
			}
			throw ex;
		} finally {
			if (em != null) {
				 em.close();
			}
		}
	}

	public void edit(Component<T> component) throws NonexistentEntityException, RollbackFailureException, Exception {

		EntityManager em = null;
			
		try {
			em = ServletListener.getEntityManager();
			em.getTransaction().begin();
			component.setComponent(em.merge(component.getComponent()));
			em.getTransaction().commit();
		} catch (Exception ex) {
			try {
				em.getTransaction().rollback();
			} catch (Exception re) {
				throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
			}
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = component.getComponentID();
				if (findById(id, component.getClassType()) == null) {
					throw new NonexistentEntityException(
							"The " + component.getClassType().toString() + " with id " + id + " no longer exists.");
				}
			}
			throw ex;
		} finally {
			if (em != null) {
				 em.close();
			}
		}
	}

	public void destroy(Integer id, Class<T> entityClass) throws NonexistentEntityException, RollbackFailureException, Exception {

		Component<T> component = null;
		EntityManager em = null;
		
		try {
			
			try {
				em = ServletListener.getEntityManager();
				em.getTransaction().begin();
				component = new Component<T>(em.getReference(entityClass, id));
				component.getComponentID();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException(
					"The " + component.getClassType().toString() + " with id " + id + " no longer exists.", enfe);
			}
			em.remove(component.getComponent());
			em.getTransaction().commit();
		} catch (Exception ex) {
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
	}

	private List<T> findById(Integer id, Class<T> entityClass) {
		
		EntityManager em = null;
		try {
			em = ServletListener.getEntityManager();
			List<T> entityList = new ArrayList<T>();
			entityList.add(em.find(entityClass, id));
			return entityList;
		} finally {
			if (em != null) {
				 em.close();
			}
		}
	}

	public int getMax(Class<T> entityClass) {
	
		EntityManager em = null;
		try {
			em = ServletListener.getEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<T> cq = cb.createQuery(entityClass);
			Root<T> rt = cq.from(entityClass);
			cq.select(cb.greatest(rt.get("id")));
			Query q = em.createQuery(cq);
			return ((Integer) q.getSingleResult()).intValue();
		} finally {
			if (em != null) {
				 em.close();
			}
		}
	}
	
	
}

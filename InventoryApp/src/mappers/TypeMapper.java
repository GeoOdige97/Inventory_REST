package mappers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import dao.DBContextListener;
import entities.impl.Type;

public class TypeMapper {
	
	
	TypeMapper(){}
	
	// Find all types from the SQL table
	public static List<Type> findAll() {
		return findAll(true, -1, -1);
	}

	// Find all types from the SQL table
	// that satisfy the criteriasS
	public static List<Type> findAll(int maxResults, int firstResult) {
		return findAll(false, maxResults, firstResult);
	}

	// Find all types implementation
	private static List<Type> findAll(boolean all, int maxResults, int firstResult) {
	
		EntityManager em = null;
		try {
			em = DBContextListener.getEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Type> cq = cb.createQuery(Type.class);
			cq.select(cq.from(Type.class)); 
			Query q = em.createQuery(cq); 
			if (!all) {
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}
			return q.getResultList(); // Execute query
		} finally {
			if (em != null) {
				 em.close();
			}
		}
	}

	// Find the type using the primary key
	public static List<Type> findById(Integer id) {
		
		EntityManager em = null;
		try {
			em = DBContextListener.getEntityManager();
			List<Type> entityList = new ArrayList<Type>();
			entityList.add((Type) em.find(Type.class, id));
			return entityList;
		} finally {
			if (em != null) {
				 em.close();
			}
		}
	}
	
/*	public static List<Registration> findRegistrationsByTypeId(Integer typeId) {
		return  RegistrationMapper.findTypeRegistrations(typeId);
	}
	
	public static List<Registration> findLatestRegistrationsByTypeId(Integer typeId) {
		return  RegistrationMapper.findLatestRegistration(typeId, null);
	}*/
	
	

}

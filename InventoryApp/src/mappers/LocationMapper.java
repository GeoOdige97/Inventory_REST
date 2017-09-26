package mappers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import dao.DBContextListener;
import entities.impl.Location;

public class LocationMapper {
	
	
	
	LocationMapper(){
		
	}
	
	// Find all locations from the SQL table
	public static List<Location> findAll() {
		return findAll(true, -1, -1);
	}

	// Find all locations from the SQL table
	// that satisfy the criteriasS
	public static List<Location> findAll(int maxResults, int firstResult) {
		return findAll(false, maxResults, firstResult);
	}

	// Find all locations implementation
	private static List<Location> findAll(boolean all, int maxResults, int firstResult) {
	
		EntityManager em = null;
		try {
			em = DBContextListener.getEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Location> cq = cb.createQuery(Location.class);
			cq.select(cq.from(Location.class)); 
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

	// Find the location using the primary key
	public static List<Location> findById(Integer id) {
		
		EntityManager em = null;
		try {
			em = DBContextListener.getEntityManager();
			List<Location> entityList = new ArrayList<Location>();
			entityList.add((Location) em.find(Location.class, id));
			return entityList;
		} finally {
			if (em != null) {
				 em.close();
			}
		}
	}

}

package mappers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import dao.DBContextListener;
import entities.impl.Network;


public class NetworkMapper {
	
	NetworkMapper(){
		
	}
	
	// Find all departments from the SQL table
	public static List<Network> findAll() {
		return findAll(true, -1, -1);
	}

	// Find all departments from the SQL table
	// that satisfy the criteriasS
	public static List<Network> findAll(int maxResults, int firstResult) {
		return findAll(false, maxResults, firstResult);
	}

	// Find all departments implementation
	private static List<Network> findAll(boolean all, int maxResults, int firstResult) {
	
		EntityManager em = null;
		try {
			em = DBContextListener.getEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Network> cq = cb.createQuery(Network.class);
			cq.select(cq.from(Network.class)); 
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

	// Find the department using the primary key
	public static List<Network> findById(Integer id) {
		
		EntityManager em = null;
		try {
			em = DBContextListener.getEntityManager();
			List<Network> entityList = new ArrayList<Network>();
			entityList.add((Network) em.find(Network.class, id));
			return entityList;
		} finally {
			if (em != null) {
				 em.close();
			}
		}
	}

}

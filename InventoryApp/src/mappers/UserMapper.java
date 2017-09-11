package mappers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import controller.ServletListener;
import entities.User;

public class UserMapper {
	
	UserMapper() {

	}
	
	// Find all users from the SQL table
	public static List<User> findAll() {
		return findAll(true, -1, -1);
	}

	// Find all users from the SQL table
	// that satisfy the criteriasS
	public static List<User> findAll(int maxResults, int firstResult) {
		return findAll(false, maxResults, firstResult);
	}

	// Find all users implementation
	private static List<User> findAll(boolean all, int maxResults, int firstResult) {
	
		EntityManager em = null;
		try {
			em = ServletListener.getEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<User> cq = cb.createQuery(User.class);
			cq.select(cq.from(User.class)); 
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

	// Find the user using the primary key
	public static List<User> findById(Integer id) {
		
		EntityManager em = null;
		try {
			em = ServletListener.getEntityManager();
			List<User> entityList = new ArrayList<User>();
			entityList.add((User) em.find(User.class, id));
			return entityList;
		} finally {
			if (em != null) {
				 em.close();
			}
		}
	}
	
	
	

}

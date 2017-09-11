package mappers;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import controller.ServletListener;
import entities.Username;

public class UsernameMapper {
	
	UsernameMapper(){
		
	}
	
	// Find all usernames from the SQL table
	public static List<Username> findAll() {
		return findAll(true, -1, -1);
	}

	// Find all usernames from the SQL table
	// that satisfy the criterias
	public static List<Username> findAll(int maxResults, int firstResult) {
		return findAll(false, maxResults, firstResult);
	}

	// Find all usernames implementation
	private static List<Username> findAll(boolean all, int maxResults, int firstResult) {
	
		EntityManager em = null;
		try {
			em = ServletListener.getEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Username> cq = cb.createQuery(Username.class);
			cq.select(cq.from(Username.class)); 
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

	// Find the username using the primary key
	public static List<Username> findById(Integer id) {
		
		EntityManager em = null;
		try {
			em = ServletListener.getEntityManager();
			List<Username> entityList = new ArrayList<Username>();
			entityList.add((Username) em.find(Username.class, id));
			return entityList;
		} finally {
			if (em != null) {
				 em.close();
			}
		}
	}
	
	public static Username compareCredentials(String username,String password){
		EntityManager em = null;
		try {
			em = ServletListener.getEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			
			CriteriaQuery<Username> cq = cb.createQuery(Username.class);
			Root<Username> fromUsername = cq.from(Username.class); // 

			/* SELECT * FROM USERNAME WHERE USERNAME=? AND PASSWORD=? */
			cq.where(
					cb.and(
						cb.equal(fromUsername.get("username"), username),
						cb.equal(fromUsername.get("password") ,password)
						)
					);
			
			Query q = em.createQuery(cq);
			
			return (Username) q.getSingleResult(); // Execute query
		} finally {
			if (em != null) {
				 em.close();
			}
		}
	}
}

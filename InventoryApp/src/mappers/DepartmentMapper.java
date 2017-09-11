package mappers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import controller.ServletListener;
import entities.Department;


public class DepartmentMapper {
	
	DepartmentMapper(){
		
	}
	
	// Find all departments from the SQL table
	public static List<Department> findAll() {
		return findAll(true, -1, -1);
	}

	// Find all departments from the SQL table
	// that satisfy the criteriasS
	public static List<Department> findAll(int maxResults, int firstResult) {
		return findAll(false, maxResults, firstResult);
	}

	// Find all departments implementation
	private static List<Department> findAll(boolean all, int maxResults, int firstResult) {
	
		EntityManager em = null;
		try {
			em = ServletListener.getEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Department> cq = cb.createQuery(Department.class);
			cq.select(cq.from(Department.class)); 
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
	public static List<Department> findById(Integer id) {
		
		EntityManager em = null;
		try {
			em = ServletListener.getEntityManager();
			List<Department> entityList = new ArrayList<Department>();
			entityList.add((Department) em.find(Department.class, id));
			return entityList;
		} finally {
			if (em != null) {
				 em.close();
			}
		}
	}

}

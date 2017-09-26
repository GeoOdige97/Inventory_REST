package mappers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import dao.DBContextListener;
import entities.impl.Printer;


public class PrinterMapper {
	
	PrinterMapper(){
		
	}
	
	// Find all departments from the SQL table
	public static List<Printer> findAll() {
		return findAll(true, -1, -1);
	}

	// Find all departments from the SQL table
	// that satisfy the criteriasS
	public static List<Printer> findAll(int maxResults, int firstResult) {
		return findAll(false, maxResults, firstResult);
	}

	// Find all departments implementation
	private static List<Printer> findAll(boolean all, int maxResults, int firstResult) {
	
		EntityManager em = null;
		try {
			em = DBContextListener.getEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Printer> cq = cb.createQuery(Printer.class);
			cq.select(cq.from(Printer.class)); 
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
	public static List<Printer> findById(Integer id) {
		
		EntityManager em = null;
		try {
			em = DBContextListener.getEntityManager();
			List<Printer> entityList = new ArrayList<Printer>();
			entityList.add((Printer) em.find(Printer.class, id));
			return entityList;
		} finally {
			if (em != null) {
				 em.close();
			}
		}
	}

}

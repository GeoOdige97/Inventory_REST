package mappers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import dao.DBContextListener;
import entities.impl.Product;

public class ProductMapper {
	
	ProductMapper(){}
	
	// Find all products from the SQL table
	public static List<Product> findAll() {
		return findAll(true, -1, -1);
	}

	// Find all products from the SQL table
	// that satisfy the criteriasS
	public static List<Product> findAll(int maxResults, int firstResult) {
		return findAll(false, maxResults, firstResult);
	}

	// Find all products implementation
	private static List<Product> findAll(boolean all, int maxResults, int firstResult) {
	
		EntityManager em = null;
		try {
			em = DBContextListener.getEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Product> cq = cb.createQuery(Product.class);
			cq.select(cq.from(Product.class)); 
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

	// Find the product using the primary key
	public static List<Product> findById(Integer id) {
		
		EntityManager em = null;
		try {
			em = DBContextListener.getEntityManager();
			List<Product> entityList = new ArrayList<Product>();
			entityList.add((Product) em.find(Product.class, id));
			return entityList;
		} finally {
			if (em != null) {
				 em.close();
			}
		}
	}
}

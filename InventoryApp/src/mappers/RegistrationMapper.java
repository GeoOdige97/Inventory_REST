package mappers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import controller.ServletListener;
import entities.Registration;
import entities.User;

public class RegistrationMapper {

	RegistrationMapper(){

	}

	// Find all usernames from the SQL table
	public static List<Registration> findAll() {
		return findAll(true, -1, -1);
	}

	// Find all usernames from the SQL table
	// that satisfy the criterias
	public static List<Registration> findAll(int maxResults, int firstResult) {
		return findAll(false, maxResults, firstResult);
	}

	// Find all usernames implementation
	private static List<Registration> findAll(boolean all, int maxResults, int firstResult) {

		EntityManager em = null;
		try {
			em = ServletListener.getEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Registration> cq = cb.createQuery(Registration.class);
			cq.select(cq.from(Registration.class)); 
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
	public static List<Registration> findById(Integer id) {

		EntityManager em = null;
		try {
			em = ServletListener.getEntityManager();
			List<Registration> entityList = new ArrayList<Registration>();
			entityList.add((Registration) em.find(Registration.class, id));
			return entityList;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
	
	public static List<Registration> findLatestRegistration (Integer deviceId, Integer userId){
		EntityManager em = null;
		Predicate pDeviceId = null;
		Predicate pUserId = null;
		Predicate pLatestRegistration = null;
		List<Registration> registrationList = new ArrayList<Registration>(0);

		try {
			em = ServletListener.getEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();

			CriteriaQuery<?> cq = cb.createQuery();
			Root<Registration> registrationTable = cq.from(Registration.class);

			pDeviceId = cb.equal(registrationTable.get("device").get("id"),deviceId);
			pUserId = cb.equal(registrationTable.get("user").get("id"),userId);
			pLatestRegistration = cb.equal(registrationTable.get("latestRegistration"),true);


			cq = selectRegistration(cq, registrationTable);
			if(deviceId != null && userId == null ){
				cq.where(cb.and(pDeviceId,pLatestRegistration));
			}
			else if (userId != null && deviceId == null){
				cq.where(cb.and(pUserId,pLatestRegistration));
			}
			else if (userId != null && deviceId != null){
				cq.where(cb.and(pUserId,pDeviceId,pLatestRegistration));
			}
			else{ 
				cq.where(pLatestRegistration); 
			}
			Query q = em.createQuery(cq);
			return  buildRegistrationList(q);
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public static List<Registration> findAllLatestRegistration (String column){
		EntityManager em = null;
		try {
			em = ServletListener.getEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();

			CriteriaQuery<Registration> cq = cb.createQuery(Registration.class);
			Root<Registration> registrationTable = cq.from(Registration.class); // 

			// SELECT * FROM Registration
			// WHERE LATESTREGISTRATION = TRUE

			cq.select(registrationTable);
			cq.where(cb.equal(registrationTable.get("latestRegistration"),true));
			Query q = em.createQuery(cq);
			return (List<Registration>) q.getResultList(); // Execute query
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public static List<Registration> findDeviceRegistrations (Integer deviceId){

		EntityManager em = null;
		Predicate pDeviceId = null;
		try {
			em = ServletListener.getEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();

			CriteriaQuery<?> cq = cb.createQuery();
			Root<Registration> registrationTable = cq.from(Registration.class);

			pDeviceId = cb.equal(registrationTable.get("device").get("id"), deviceId);
			cq = selectRegistration(cq, registrationTable);
			cq.where(pDeviceId);
			Query q = em.createQuery(cq);
			return buildRegistrationList(q);
			
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
	
	public static List<Registration> findUserRegistrations (Integer userId){

		EntityManager em = null;
		Predicate pUserId = null;
		try {
			em = ServletListener.getEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();

			CriteriaQuery<?> cq = cb.createQuery();
			Root<Registration> registrationTable = cq.from(Registration.class);

			pUserId = cb.equal(registrationTable.get("user").get("id"), userId);
			cq = selectRegistration(cq, registrationTable);
			cq.where(pUserId);
			Query q = em.createQuery(cq);
			return buildRegistrationList(q);
			
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	private static CriteriaQuery<?> selectRegistration(CriteriaQuery<?> cq, Root<Registration> registrationTable) {
		return cq.multiselect(
				registrationTable.get("id"),
				registrationTable.get("dateRented"),
				registrationTable.get("dateReturn"),
				registrationTable.get("latestRegistration"),
				registrationTable.get("user")
				);
	}

	private static List<Registration> buildRegistrationList (Query q) {
		
		List<Registration> registrationList = new ArrayList<Registration>(0);
		Iterator<Object[]> resultList =  q.getResultList().iterator();
		while (resultList.hasNext()) {
			Object[] array = resultList.next();
			Registration r = new Registration();
			r.setId((Integer) array[0]);
			r.setDateRented((String) array[1]);
			r.setDateReturn((String) array[2]);
			r.setLatestRegistration((Boolean) array[3]);
			r.setUser((User) array[4]);
			registrationList.add(r);
		}
		return registrationList;
	}
}

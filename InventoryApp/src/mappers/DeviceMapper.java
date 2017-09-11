package mappers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import controller.ServletListener;
import entities.Device;
import entities.Registration;

public class DeviceMapper {
	
	
	DeviceMapper(){
	
	}
	
	// Find all devices from the SQL table
	public static List<Device> findAll() {
		return findAll(true, -1, -1);
	}

	// Find all devices from the SQL table
	// that satisfy the criteriasS
	public static List<Device> findAll(int maxResults, int firstResult) {
		return findAll(false, maxResults, firstResult);
	}

	// Find all devices implementation
	private static List<Device> findAll(boolean all, int maxResults, int firstResult) {
	
		EntityManager em = null;
		try {
			em = ServletListener.getEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Device> cq = cb.createQuery(Device.class);
			cq.select(cq.from(Device.class)); 
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

	// Find the device using the primary key
	public static List<Device> findById(Integer id) {
		
		EntityManager em = null;
		try {
			em = ServletListener.getEntityManager();
			List<Device> entityList = new ArrayList<Device>();
			entityList.add((Device) em.find(Device.class, id));
			return entityList;
		} finally {
			if (em != null) {
				 em.close();
			}
		}
	}
	
	public static List<Registration> findRegistrationsByDeviceId(Integer deviceId) {
		return  RegistrationMapper.findDeviceRegistrations(deviceId);
	}
	
	public static List<Registration> findLatestRegistrationsByDeviceId(Integer deviceId) {
		return  RegistrationMapper.findLatestRegistration(deviceId, null);
	}
	
	

}

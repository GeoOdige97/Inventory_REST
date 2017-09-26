package dao.interf;

import entities.impl.Computer;
import entities.impl.Department;
import entities.impl.Device;
import entities.impl.Location;
import entities.impl.Network;
import entities.impl.Printer;
import entities.impl.Product;
import entities.impl.Registration;
import entities.impl.Type;
import entities.impl.User;

public interface IMasterEntityDAO {

	public IEntityDAO<Device> getDeviceDAO();
	
	public IEntityDAO<Location> getLocationDAO();
	
	public IEntityDAO<User> getUserDAO();
	
	public IEntityDAO<Product> getProductDAO();
	
	public IEntityDAO<Network> getNetworkDAO();
	
	public IEntityDAO<Printer> getPrinterDAO();
	
	public IEntityDAO<Type> getTypeDAO();
	
	public IEntityDAO<Registration> getRegistrationDAO();
	
	public IEntityDAO<Department> getDepartmentDAO();
	
	public IEntityDAO<Computer> getComputertDAO();
	
}

package dao.impl;

import dao.interf.IEntityDAO;
import dao.interf.IMasterEntityDAO;
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

public class MasterEntityDAO implements IMasterEntityDAO {
	
	private IEntityDAO<Device> deviceDAO;
	private IEntityDAO<Location> locationDAO;
	private IEntityDAO<User> userDAO;
	private IEntityDAO<Product> productDAO;
	private IEntityDAO<Network> networkDAO;
	private IEntityDAO<Printer> printerDAO;
	private IEntityDAO<Type> typeDAO;
	private IEntityDAO<Registration> registrationDAO;
	private IEntityDAO<Department> departmentDAO;
	private IEntityDAO<Computer> computerDAO;

	public MasterEntityDAO() {
		deviceDAO = new EntityDAO<Device>(Device.class);
		locationDAO = new EntityDAO<Location>(Location.class);
		userDAO = new EntityDAO<User>(User.class);
		productDAO = new EntityDAO<Product>(Product.class);
		networkDAO = new EntityDAO<Network>(Network.class);
		printerDAO = new EntityDAO<Printer>(Printer.class);
		typeDAO = new EntityDAO<Type>(Type.class);
		registrationDAO = new EntityDAO<Registration>(Registration.class);
		departmentDAO = new EntityDAO<Department>(Department.class);
		computerDAO = new EntityDAO<Computer>(Computer.class);
	}

	@Override
	public IEntityDAO<Device> getDeviceDAO() {
		// TODO Auto-generated method stub
		return this.deviceDAO;
	}

	@Override
	public IEntityDAO<Location> getLocationDAO() {
		// TODO Auto-generated method stub
		return this.locationDAO;
	}

	@Override
	public IEntityDAO<User> getUserDAO() {
		// TODO Auto-generated method stub
		return this.userDAO;
	}

	@Override
	public IEntityDAO<Product> getProductDAO() {
		// TODO Auto-generated method stub
		return this.productDAO;
	}

	@Override
	public IEntityDAO<Network> getNetworkDAO() {
		// TODO Auto-generated method stub
		return this.networkDAO;
	}

	@Override
	public IEntityDAO<Printer> getPrinterDAO() {
		// TODO Auto-generated method stub
		return this.printerDAO;
	}

	@Override
	public IEntityDAO<Type> getTypeDAO() {
		// TODO Auto-generated method stub
		return this.typeDAO;
	}

	@Override
	public IEntityDAO<Registration> getRegistrationDAO() {
		// TODO Auto-generated method stub
		return this.registrationDAO;
	}

	@Override
	public IEntityDAO<Department> getDepartmentDAO() {
		// TODO Auto-generated method stub
		return this.departmentDAO;
	}

	@Override
	public IEntityDAO<Computer> getComputertDAO() {
		// TODO Auto-generated method stub
		return this.computerDAO;
	}
}

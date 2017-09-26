package test;

import org.junit.Test;

import dao.impl.EntityDAO;
import entities.impl.Username;
import mappers.UsernameMapper;

public class DataBaseTransaction {


	//private static DevicesWS devicesWS;

/*	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		devicesWS = new DevicesWS();

	}
	
	@AfterClass
	public static void setUpAfterClass() throws Exception {
		devicesWS = new DevicesWS();

	}*/


	/*@Test
	public void POJOTToJSONTest() throws NonexistentEntityException, RollbackFailureException, Exception {

		Gson gson = new Gson();
		
		String json = gson.toJson(devicesWS.getDevice(5));
		//devicesWS.editDevice(json);
		
		System.out.println("JSON " + json);
		
	}
	
	@Test
	public void JSONToPOJOTest() throws NonexistentEntityException, RollbackFailureException, Exception{
		
		Devices newDevice = new DeviceBuilder()
				.id(14)
				.name("Nintendo Switch")
				.company("Nintendo")
				.serialNumber("45435455")
				.type("Game Hardware")
				.userId(1)
				.locationId(1)
				.model("H9X99992")
				.condition(StatusEnum.GOOD.getName())
				.dateRented("2017-03-27")
				.dateReturn("2018-04-16")
				.build();
		newDevice.getId();
		
		Gson gson = new Gson();
		String json = gson.toJson(newDevice);
		System.out.println(json);
	
	//devicesWS.addDevice(json);
	}

*/
	@Test
	public void testEnum(){
		
		/*EntityDAO<Username> jpacUsername = new EntityDAO<Username>(); 
		
		 Username admin = UsernameMapper.compareCredentials("admin", "admin");
		 
		 System.out.println("user in database: " + admin);*/
	}


}

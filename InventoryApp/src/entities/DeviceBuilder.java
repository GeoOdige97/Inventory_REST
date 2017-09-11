package entities;

public class DeviceBuilder {

	private int id;
	private String name;
	private String type;
	private String company;
	private String serialNumber;
	private String model;
	private String condition;
	private String dateRented;
	private String dateReturn;
	
	

	public DeviceBuilder id(int id) {
		this.id = id;
		return this;
	}

	public DeviceBuilder name(String name) {
		this.name  = name;
		return this;
	}

	public DeviceBuilder type(String type) {
		this.type = type;
		return this;
	}
	
	public DeviceBuilder company(String company) {
		this.company = company;
		return this;
	}
	
	public DeviceBuilder serialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
		return this;
	}
	
	public DeviceBuilder locationId(Integer locationId) {
		return this;
	}
	
	public DeviceBuilder userId(Integer userId) {
		return this;
	}
	
	public DeviceBuilder model(String model) {
		this.model = model;
		return this;
	}
	
	public DeviceBuilder condition(String condition) {
		this.condition = condition;
		return this;
	}
	
	public DeviceBuilder dateRented(String dateRented) {
		this.dateRented = dateRented;
		return this;
	}
	
	public DeviceBuilder dateReturn(String dateReturn) {
		this.dateReturn = dateReturn;
		return this;
	}
	
	public DeviceBuilder(){
		this.id = -1;
		this.name = "N\\A";
		this.type =  "N\\A";
		this.company = "Sika";
		this.serialNumber = "N\\A";
		this.model =  "N\\A";
		this.condition = "Good";
		this.dateRented = "01-01-2017";
		this.dateReturn = "01-01-2018";
	}
	public Device build(){
		
		Device d = new Device();
		
		/*d.set_id(this.id);
		d.set_Name(this.name);
		d.setype(this.type);
		d.setCompany(this.company);
		d.setSerialNumber(this.serialNumber);
		//d.setLocationId(this.locationId);
		//d.setUserId(this.userId);
		d.setModel(this.model);
		d.setCondition(this.condition);
		d.setDateRented(this.dateRented);
		d.setDateReturn(this.dateReturn);	
		*/
		return d;
	}
	
	/*
	this.id = -1;
	this.name = "N\\A";
	this.type =  "N\\A";
	this.company = "Sika";
	this.serialNumber = -1;
	this.locationId = -1;
	this.userId = -1;
	this.model =  "N\\A";
	this.condition = "Good";
	this.dateRented = "01-01-2017";
	this.dateReturn = "01-01-2018";*/	
}

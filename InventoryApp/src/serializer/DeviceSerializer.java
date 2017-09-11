package serializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import entities.Device;

public class DeviceSerializer extends StdSerializer<List<Device>> {
	
	private static final long serialVersionUID = 1L;

	public DeviceSerializer() {
	        this(null);
	    }
	 
	    public DeviceSerializer(Class<List<Device>> t) {
	        super(t);
	    }
	 
	    @Override
	    public void serialize(List<Device> deviceList, JsonGenerator generator, SerializerProvider provider) 
	      throws IOException, JsonProcessingException {
	         
	        List<Integer> devices = new ArrayList<Integer>();
	        for (Device device : deviceList) {
	        	devices.add(device.getId());
	        }
	        generator.writeObject(devices);
	    }

	


	
}
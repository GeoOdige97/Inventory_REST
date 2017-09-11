package serializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import entities.User;

public class UserSerializer extends StdSerializer<List<User>> {

	private static final long serialVersionUID = 1L;

	public UserSerializer() {
		this(null);
	}

	public UserSerializer(Class<List<User>> t) {
		super(t);
	}

	@Override
	public void serialize(List<User> userList, JsonGenerator generator, SerializerProvider provider) 
			throws IOException, JsonProcessingException {

		List<Integer> users = new ArrayList<Integer>();
		for (User user : userList) {
			users.add(user.getId());
		}
		generator.writeObject(users);
	}





}
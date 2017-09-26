package routes;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import entities.impl.Username;
import exceptions.NonexistentEntityException;
import exceptions.RollbackFailureException;
import mappers.UsernameMapper;
import tools.JsonMessage;

@Path("/service")
public class LoginRoute {

	private Class<Username> usernameClass = Username.class;
	public LoginRoute() {
	}

	@POST
	@Path("/login")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	public Response login(String formParams) throws NonexistentEntityException, 
	RollbackFailureException, Exception{

		String devicePage = "http://localhost:8080/ClientInventory/DevicePage.html";
		Gson gson = new GsonBuilder().create();
		Username credentials = gson.fromJson(formParams, usernameClass);
		try {

			// Authenticate the user using the credentials provided
			Username loginUser = UsernameMapper.compareCredentials(credentials.getUsername(), credentials.getPassword());

			// Issue a token for the user()
			// String token = issueToken(loginUser);
			// Return the token on the response

			JsonMessage jm = new JsonMessage();
			jm.setPath(devicePage);

			GenericEntity<JsonMessage> entity = new GenericEntity<JsonMessage>(jm) {};
			return Response.ok(jm, MediaType.APPLICATION_JSON).build();

		} catch (Exception e) {
			return null;   
		}
	}


	private String issueToken(String username) {
		// Issue a token (can be a random String persisted to a database or a JWT token)
		// The issued token must be associated to a user
		// Return the issued token
		return null;
	}



}

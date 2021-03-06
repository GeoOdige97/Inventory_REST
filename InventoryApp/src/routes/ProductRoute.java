package routes;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.DBContextListener;
import dao.interf.IMasterEntityDAO;
import entities.impl.Product;
import exceptions.NonexistentEntityException;
import exceptions.RollbackFailureException;
import mappers.ProductMapper;

@Path("/service")
public class ProductRoute {

	private Class<Product> productClass = Product.class;
	private IMasterEntityDAO context;
	
	public ProductRoute() {
		this.context = DBContextListener.getMasterDAO();
	}

	@GET
	@Path("/product/findAll")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_XML,})
	public Response getAllProducts(){
		
		List<Product> productList = ProductMapper.findAll();
		//productList = addLatestRegistrationsToProducts(productList);
		Gson gson = new GsonBuilder().serializeNulls().create();	
		return Response.ok(gson.toJson(productList), MediaType.APPLICATION_JSON).build();	
		
	}
	
	@GET
	@Path("/product/find/{id}")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	public Response getProduct(@PathParam("id") Integer productId ) throws NonexistentEntityException, RollbackFailureException, Exception{
		
		List<Product> productList =  ProductMapper.findById(productId);		
		//productList = addLatestRegistrationsToProducts(productList);
		Gson gson = new GsonBuilder().serializeNulls().create();	
		return Response.ok(gson.toJson(productList), MediaType.APPLICATION_JSON).build();	
		
	}
	
	@POST
	@Path("/product/add")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	public Response addProduct( String formParams ) throws NonexistentEntityException, RollbackFailureException, Exception{

		Gson gson = new GsonBuilder().create();
		Product product = gson.fromJson(formParams, productClass);
		
		/*
		 * Verify if id exist using jpa finders
		 * 
		 * */
		
		context.getProductDAO().create(product);
		
		
		// After Data Insert/Verification resend the added object
		GenericEntity<Product> entity = new GenericEntity<Product>(product) {};
		return Response.ok(entity, MediaType.APPLICATION_JSON).build();	
	}


	@PUT
	@Path("/product/update")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response editProduct( String JSONproduct ) throws NonexistentEntityException, RollbackFailureException, Exception{

		Gson gson = new GsonBuilder().create();
		Product product = gson.fromJson(JSONproduct, Product.class);
		context.getProductDAO().edit(product);
		
		GenericEntity<Product> entity = new GenericEntity<Product>(product) {};
		return Response.ok(entity, MediaType.APPLICATION_JSON).build();	
	}

	@DELETE
	@Path("/product/delete/{id}")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public void deleteProduct(@PathParam("id") int productId ) throws NonexistentEntityException, RollbackFailureException, Exception{

		System.out.println("DELETE: product id "+ productId);
		context.getProductDAO().destroy(productId);
	}

}

package routes;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.DBContextListener;
import dao.interf.IMasterEntityDAO;
import entities.impl.Department;
import exceptions.NonexistentEntityException;
import exceptions.RollbackFailureException;
import mappers.DepartmentMapper;


@Path("/service")
public class DepartmentRoute {

	private IMasterEntityDAO context;

	public DepartmentRoute() {
		this.context = DBContextListener.getMasterDAO();
	}

	@GET
	@Path("/department/findAll")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	public List<Department> getAllDepartment(){

		System.out.println("get all departments");
		List<Department> departmentList = DepartmentMapper.findAll();
		return departmentList;	
	}

	@GET
	@Path("/department/find/{id}")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	public List<Department> getDepartment(@PathParam("id") Integer departmentId ) throws NonexistentEntityException, RollbackFailureException, Exception{

		System.out.println("get department");
		List<Department> departmentList = DepartmentMapper.findById(departmentId);
		//GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = new Gson();  
		System.out.println(gson.toJson(departmentList));

		return departmentList;
	}


	@POST
	@Path("/department/add")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	public List<Department> addDepartment( String formParams ) throws NonexistentEntityException, RollbackFailureException, Exception{

		Gson gson = new GsonBuilder().create();
		Department department = gson.fromJson(formParams, Department.class);
		context.getDepartmentDAO().create(department);
		List<Department> departmentList = new ArrayList<Department>();

		departmentList.add(department);
		return departmentList;
	}


	@PUT
	@Path("/department/update")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public void editDepartment( String JSONdepartment) throws NonexistentEntityException, RollbackFailureException, Exception{

		Gson gson = new GsonBuilder().create();
		Department department = gson.fromJson(JSONdepartment, Department.class);
		context.getDepartmentDAO().edit(department);
	}

	@DELETE
	@Path("/department/delete/{id}")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public void deleteDepartment(@PathParam("id") int departmentId ) throws NonexistentEntityException, RollbackFailureException, Exception{

		context.getDepartmentDAO().destroy(departmentId);
	}

}

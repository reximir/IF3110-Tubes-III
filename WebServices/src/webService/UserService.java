package webService;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.google.gson.Gson;

import model.AccessManager;

import dto.Category;
import dto.Course;
import dto.User;


import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.QueryParam;
import dto.UserBean;


@Path("/userservice")
public class UserService
{	
	@GET
	@Path("/user/{paramID}")
	@Produces("application/json")
	public String userById(@PathParam("paramID") int paramID)
	{
		String user = null;
		ArrayList<User> userList = new ArrayList<User>();
		try
		{
			userList = new AccessManager().getUsersById(paramID);
			Gson gson = new Gson();
			user = gson.toJson(userList);
		} catch (Exception e)
		{
				e.printStackTrace();
		}
		return user;
	}
	
	@GET
	@Path("/user2/{paramID}")
	@Produces("application/json")
	public String userById2(@PathParam("paramID") int paramID)
	{
		String user = null;
		ArrayList<UserBean> userList = new ArrayList<UserBean>();
		try
		{
			userList = new AccessManager().getUsersById2(paramID);
			Gson gson = new Gson();
			user = gson.toJson(userList);
		} catch (Exception e)
		{
				e.printStackTrace();
		}
		return user;
	}
	
	
	@GET
	@Path("/userlimit1/{paramID}")
	@Produces("application/json")
	public String userByIdlimit1(@PathParam("paramID") int paramID)
	{
		String user = null;
		ArrayList<User> userList = new ArrayList<User>();
		try
		{
			userList = new AccessManager().getUsersByIdlimit1(paramID);
			Gson gson = new Gson();
			user = gson.toJson(userList);
		} catch (Exception e)
		{
				e.printStackTrace();
		}
		return user;
	}
	
	@GET
	@Path("/username/{paramName}/{paramPass}")
	@Produces("application/json")
	public String userByNamePass(@PathParam("paramName") String paramName,@PathParam("paramPass") String paramPass)
	{
		String user = null;
		ArrayList<User> userList = new ArrayList<User>();
		try
		{
			userList = new AccessManager().getUsersByNamePass(paramName,paramPass);
			Gson gson = new Gson();
			user = gson.toJson(userList);
		} catch (Exception e)
		{
				e.printStackTrace();
		}
		return user;
	}
	
	@GET
	@Path("/nomorkartu/{noKartu}")
	@Produces("application/json")
	public String userByNoKartu(@PathParam("noKartu") String noKartu)
	{
		String user = null;
		ArrayList<User> userList = new ArrayList<User>();
		try
		{
			userList = new AccessManager().getUserByNoKartu(noKartu);
			Gson gson = new Gson();
			user = gson.toJson(userList);
		} catch (Exception e)
		{
				e.printStackTrace();
		}
		return user;
	}
	
	
	@GET
	@Path("/namakartu/{namaKartu}")
	@Produces("application/json")
	public String userByNamaKartu(@PathParam("namaKartu") String namaKartu)
	{
		String user = null;
		ArrayList<User> userList = new ArrayList<User>();
		try
		{
			userList = new AccessManager().getUserByNamaKartu(namaKartu);
			Gson gson = new Gson();
			user = gson.toJson(userList);
		} catch (Exception e)
		{
				e.printStackTrace();
		}
		return user;
	}
	
	//USER UPDATE
	@POST
	@Path("/updatecard")
	public void updateCard(@FormParam("name") String name, @FormParam("num") String num, @FormParam("date") String date, @FormParam("id") String id)
	{
		try
		{
			new AccessManager().updateCard(name, num, date, id);
		} catch (Exception e)
		{
				e.printStackTrace();
		}
	}
	
	@POST
	@Path("/updatetrans")
	public void updateTranscation(@FormParam("num") String num,  @FormParam("id") String id)
	{
		try
		{
			new AccessManager().updateTransaction(num, id);
		} catch (Exception e)
		{
				e.printStackTrace();
		}
	}
	
	
	@POST
	@Path("/updateuser")
	public void updateUser(@FormParam("name") String  name ,@FormParam("pass") String password ,@FormParam("email") String email ,@FormParam("phone") String  telephone ,@FormParam("add") String address ,@FormParam("city") String city ,@FormParam("prov") String province ,@FormParam("post") String postal ,@FormParam("id") String id)
	{
		try
		{
			new AccessManager().updateUser(name, password, email, telephone, address, city, province, postal, id);
		} catch (Exception e)
		{
				e.printStackTrace();
		}
	}
	
	@POST
	@Path("/insertuser")
	public void insertUser(@FormParam("name") String  name, @FormParam("user") String  username ,@FormParam("pass") String password ,@FormParam("email") String email ,@FormParam("phone") String  telephone ,@FormParam("add") String address ,@FormParam("city") String city ,@FormParam("prov") String province ,@FormParam("post") String postal)
	{
		try
		{
			new AccessManager().insertUser(name, username, password, email, telephone, address, city, province, postal);
		} catch (Exception e)
		{
				e.printStackTrace();
		}
	}
	
}

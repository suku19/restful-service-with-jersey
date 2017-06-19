package com.user.app.service;



import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.user.app.model.ResponseVO;
import com.user.app.model.UserVO;
import com.user.app.util.UserUtil;
 
   
/**
 * @author Sukanta Monda;
 *
 */
@Path("/users") // This means URL's start with /users (after Application path)
public class UserResource { // This means that this class is a Resource
	
	@GET // this means the http method type
	@Produces(MediaType.APPLICATION_JSON) // This returns a JSON with the users
	public List<UserVO> getAllUsers() {
		
		return UserUtil.getUsers();
	}
	
	@GET // this means the http method type
	@Produces(MediaType.APPLICATION_JSON) // This returns a JSON with the users
	@Path("/{id}") // need to pass id to get access of this method
	public UserVO getUser(@PathParam("id") int userid) {
		// This returns a JSON or XML with the users
		
		return UserUtil.getUser(userid);
	}
	
	@PUT
	public ResponseVO addNewUser (UserVO userVo) {
		System.out.println(userVo);
		UserUtil.addUser(userVo);
		return new ResponseVO(true,"New user Created successfully");
	}
	
	@POST 
	public ResponseVO updateUser (UserVO userVo) {
		System.out.println(userVo);
		UserUtil.updateUser(userVo);
		return new ResponseVO(true,"User data updated successfully");
	}
	
	@DELETE
	@Path("/{id}") // need to pass id to get access of this method
	public ResponseVO deleteUser (@PathParam("id") int userid) {
		System.out.println(userid);
		UserUtil.deleteUser(userid);
		return new ResponseVO(true,"User deleted successfully");
	}
}

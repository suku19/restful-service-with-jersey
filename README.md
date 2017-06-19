
# restful-service-with-jersey
Jersey 2.0 is the reference implementation of the JAX-RS 2.0 (JSR 339 specification). Along with the enhancements in Java EE 7, JAX-RS 2.0 has also been revised dramatically. JAX-RS 2.0 is a framework that helps you in writing the RESTful web services on the client side as well as on the server side.

## Prerequisite Tools/Software
>- Install JDK 7 and Apache tomcat webserver
>- Install Maven plugin (m2e) for an existing IDE
>- Postman Collection, a Chrome extension, to test our webservices.

## Creating the Source Files:

### Create Request and Response Value Object

/src/main/java/com/user/app/model/ResponseVO.java


```
package com.user.app.model;

/**
 * @author Sukanta Mondal
 * 
 * Response value object
 *
 */
public class ResponseVO {
	
	private boolean success;
	private String message;
	
	public ResponseVO(boolean success, String message){
		this.success=success;
		this.message=message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
```

/src/main/java/com/user/app/model/UserVO.java
```
package com.user.app.model;

/**
 * @author Sukanta Mondal
 * 
 * Request value object.
 *
 */
public class UserVO {
	
	private int id;

	private String name;

	private String email;

	private String profession;
	
	public UserVO(){
		
	}
	
	public UserVO(int id, String name, String email, String profession){
		this.id= id;
		this.name=name;
		this.email=email;
		this.profession=profession;
	}
	
	
	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String toString() {// overriding the toString() method
		return id + " " + name + " " + email + " " + profession;
	}
}


```
Above model is used to create/map the request and response.

### Create a new Util class to manage User data
/src/main/java/com/user/app/util/UserUtil.java
```
package com.user.app.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.user.app.model.UserVO;

/**
 * @author Sukanta Mondal
 * 
 * Util class to manage user data. In real time scenarios this will be replace by DAO layer
 *
 */
public class UserUtil {
	static List<UserVO> userList = new ArrayList<UserVO>();
	static int userId = 1;
	public static List<UserVO> getUsers() {
		return userList;
	}

	public static UserVO getUser(int id) {
		for (UserVO userVo : userList) {
			if (userVo.getId() == id) {
				return userVo;
			}
		}
		return new UserVO();
	}

	public static void addUser(UserVO userVO) {
		userVO.setId(userId++);
		userList.add(userVO);
	}

	public static void updateUser(UserVO user) {
		for (UserVO userVo : userList) {
			if (userVo.getId() == user.getId()) {
				userVo.setEmail(user.getEmail());
				userVo.setName(user.getName());
				userVo.setProfession(user.getProfession());
			}
		}
	}

	public static void deleteUser(int id) {
		Iterator<UserVO> itr = userList.iterator();

		while (itr.hasNext()) {
			UserVO userVo = itr.next();
			if (userVo.getId() == id) {
				itr.remove();
			}
		}
	}
}

```
Above Util class is used to manage user data. In real time scenarios this will be replace by DAO layer.

### Create a Resource Class

```
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

```
The above example does specify GET, PUT, POST, and DELETE. There are two important points to be noted about the main program,

UserResource.java

 - The first step is to specify a path for the web service using @Path annotation to the UserResource.
 - The second step is to specify a path for the particular web service method using @Path annotation to method of UserService. 
 - Jersey frameworks maps all HTTP operations according to the http method type and path. If you need same method multiple time within resource then you need to define unique path. Example:  ``` @Path("/testPath") ```

## Creating the Web.xml configuration File

You need to create a Web xml Configuration file which is an XML file and is used to specify Jersey framework servlet for our application.

```
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://java.sun.com/xml/ns/javaee" xmlns:web="http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
	id="WebApp_ID" version="2.5">
	<display-name>Jersey2Sample</display-name>
	<welcome-file-list>
		<welcome-file>index.jsp</welcome-file>
	</welcome-file-list>

	<servlet>
		<servlet-name>RestJaxRSServlet</servlet-name>
		<servlet-class>org.glassfish.jersey.servlet.ServletContainer</servlet-class>
		<init-param>
			<param-name>jersey.config.server.provider.packages</param-name>
			<param-value>com.user.app.service</param-value>
		</init-param>
		<init-param>
			<param-name>jersey.config.server.provider.classnames</param-name>
			<param-value>org.glassfish.jersey.jackson.JacksonFeature</param-value>
		</init-param>
		<load-on-startup>100</load-on-startup>
	</servlet>
	<servlet-mapping>
		<servlet-name>RestJaxRSServlet</servlet-name>
		<url-pattern>/rest/*</url-pattern>
	</servlet-mapping>
</web-app>
```

## Add Maven Dependency in Deployment Assembly

 - When you create a maven web project and start to add maven dependencies, the dependent jars are not assembled and deployed to your local tomcat server. Here is a simple step to add these libraries so every time you add dependencies to pom, they are deployed.
right click the project, and bring up the properties -> Deployment Assembly.

----------
![enter image description here](http://www.thespringriver.com/wp-content/uploads/2015/01/eclipse-assemply.jpg)

----------

 - Click add
 
 ----------
![enter image description here](http://www.thespringriver.com/wp-content/uploads/2015/01/assemply-add-1024x512.jpg)

----------
 - Then select Java Build Path Entries -> Maven Dependencies -> Finish

## Deploying the Program

Once you are done with creating source and web configuration files, you are ready for this step which is compiling and running your program. To do this, using Eclipse, export your application as a war file and deploy the same in tomcat.

To create a WAR file using eclipse, follow the option Right Click on the Project→ Run As → Maven Install. Upon maven build success war file will be generated in target folder of the project root directory. To deploy a war file in Tomcat, place the **"restful-service-with-jersey.war"** in the Tomcat Installation Directory → webapps directory and start the Tomcat.

## Test the application

Now that the application is running, you can test it.

Install Postman. [Installing the Postman Chrome App](https://www.getpostman.com/docs/introduction)

> **Note:** Install Postman. Postman is available as a native app (recommended) for Mac / Windows / Linux, and as a Chrome App. The Postman Chrome app can only run on the Chrome browser. To use the Postman Chrome app, you will first need to install Google Chrome. 


  Import the collection file "[UserDataManagement_POSTMAN Collection.postman_collection](https://github.com/suku19/restful-service-with-jersey/blob/master/UserDataManagement_POSTMAN%20Collection.postman_collection.json)". Click on the ‘Import’ button on the top bar, and paste a URL to the collection, or the collection JSON itself, and click ‘Import’. Find the more details in [Getting started with postman Collections](https://www.getpostman.com/docs/collections)
 
 **Test Results:**

Add User

----------

![Add User](https://github.com/suku19/restful-service-with-jersey/blob/master/img/addUser.png)

----------
Get User By ID

----------

![getUserByID](https://github.com/suku19/restful-service-with-jersey/blob/master/img/getUserById.PNG)

----------
Get All User

----------
![getAllUser](https://github.com/suku19/restful-service-with-jersey/blob/master/img/getAllUser.PNG)

----------
Update User

----------

![updateUser](https://github.com/suku19/restful-service-with-jersey/blob/master/img/updateUser.png)

----------
Delete user by Id

----------

![deleteById](https://github.com/suku19/restful-service-with-jersey/blob/master/img/deleteUser.PNG)

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

package com.fsd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Users {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "userid")
	long userid;
	
	@Column(name = "firstname", nullable=false)
	@Size(max = 20, min = 3, message = "{Users.firstname.invalid}")
	String firstname;
	
	@Column(name = "lastname", nullable=false)
	@Size(max = 20, min = 3, message = "{Users.lastname.invalid}")
	String lastname;
		
	@Column(name = "employeeid", nullable=false)
	long employeeid;

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public long getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(long employeeid) {
		this.employeeid = employeeid;
	}
	
	public Users() {
		
	}

	public Users(@Size(max = 20, min = 3, message = "{Users.firstname.invalid}") String firstname,
			@Size(max = 20, min = 3, message = "{Users.lastname.invalid}") String lastname, long employeeid) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.employeeid = employeeid;
	}
		
}

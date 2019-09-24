package com.fsd.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "project")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Project {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "projectid")
	long projectid;
	
	@Column(name = "projectname")
	@Size(max = 50, min = 6, message = "{Project.projectname.invalid}")
	String projectname;
	
	@Column(name = "startdate")
	Date startdate;
		
	@Column(name = "enddate")
	Date enddate;
	
	@Column(name = "priority")
	int priority;
	
	@ManyToOne
	@JoinColumn(name="userid", nullable=false)
	Users user;

	public long getProjectid() {
		return projectid;
	}

	public void setProjectid(long projectid) {
		this.projectid = projectid;
	}

	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Project(@Size(max = 50, min = 6, message = "{Project.projectname.invalid}") String projectname,
			Date startdate, Date enddate, int priority, Users user) {
		super();
		this.projectname = projectname;
		this.startdate = startdate;
		this.enddate = enddate;
		this.priority = priority;
		this.user = user;
	}	
}

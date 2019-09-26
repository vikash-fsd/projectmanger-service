package com.fsd.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Formula;
import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
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
	
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	@Column(name = "startdate")
	Date startdate;
	
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	@Column(name = "enddate")
	Date enddate;
	
	@Column(name = "priority")
	int priority;
	
	@Column(name = "userid")
	long userid;
	
	@Transient
	@Formula("(select count(*) from task t where t.projectid = projectid)")
	int numoftask;
	
	@Transient
	@Formula("(select count(*) from task t where t.projectid = projectid and t.status = 1)")
	int numofcomptask;

	public int getNumoftask() {
		return numoftask;
	}

	public void setNumoftask(int numoftask) {
		this.numoftask = numoftask;
	}

	public int getNumofcomptask() {
		return numofcomptask;
	}

	public void setNumofcomptask(int numofcomptask) {
		this.numofcomptask = numofcomptask;
	}

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

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}
	
	public Project() {
		
	}

	public Project(@Size(max = 50, min = 6, message = "{Project.projectname.invalid}") String projectname,
			Date startdate, Date enddate, int priority, long userid) {
		super();
		this.projectname = projectname;
		this.startdate = startdate;
		this.enddate = enddate;
		this.priority = priority;
		this.userid = userid;
	}	
}

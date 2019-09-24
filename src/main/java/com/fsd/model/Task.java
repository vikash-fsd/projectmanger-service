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

@Entity
@Table(name = "task")
public class Task {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "taskid")
	long taskid;
	
	@Column(name = "parentid")
	long parentid;
	
	@ManyToOne
	@JoinColumn(name="projectid", nullable=false)
	Project project;

	
	@Column(name = "taskname")
	@Size(max = 100, min = 10, message = "{Task.taskname.invalid}")
	String taskname;
	
	@Column(name = "startdate")
	Date startdate;
		
	@Column(name = "enddate")
	Date enddate;
	
	@Column(name = "priority")
	int priority;
	
	@Column(name = "status")
	String status;
	
	@ManyToOne
	@JoinColumn(name="userid", nullable=false)
	Users user;

	public long getTaskid() {
		return taskid;
	}

	public void setTaskid(long taskid) {
		this.taskid = taskid;
	}

	public long getParentid() {
		return parentid;
	}

	public void setParentid(long parentid) {
		this.parentid = parentid;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Task(long parentid, Project project,
			@Size(max = 100, min = 10, message = "{Task.taskname.invalid}") String taskname, Date startdate,
			Date enddate, int priority, String status, Users user) {
		super();
		this.parentid = parentid;
		this.project = project;
		this.taskname = taskname;
		this.startdate = startdate;
		this.enddate = enddate;
		this.priority = priority;
		this.status = status;
		this.user = user;
	}

}

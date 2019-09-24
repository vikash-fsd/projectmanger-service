package com.fsd.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "task")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Task {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "taskid")
	long taskid;
	
	@Column(name = "parentid", nullable=true)
	Long parentid;
	
	@Column(name = "projectid")
	long projectid;

	
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
	int status;
	
	@Column(name = "userid")
	long userid;

	public long getTaskid() {
		return taskid;
	}

	public void setTaskid(long taskid) {
		this.taskid = taskid;
	}

	public Long getParentid() {
		return parentid;
	}

	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}

	public long getProjectid() {
		return projectid;
	}

	public void setProjectid(long projectid) {
		this.projectid = projectid;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}
	
	public Task() {
		
	}

	public Task(Long parentid, long projectid,
			@Size(max = 100, min = 10, message = "{Task.taskname.invalid}") String taskname, Date startdate,
			Date enddate, int priority, int status, long userid) {
		super();
		this.parentid = parentid;
		this.projectid = projectid;
		this.taskname = taskname;
		this.startdate = startdate;
		this.enddate = enddate;
		this.priority = priority;
		this.status = status;
		this.userid = userid;
	}

}

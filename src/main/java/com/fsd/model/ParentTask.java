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
@Table(name = "parenttask")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ParentTask {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "parentid")
	long parentid;
	
	@Column(name = "parenttaskname", nullable=false)
	@Size(max = 100, min = 10, message = "{ParentTask.parenttaskname.invalid}")
	String parenttaskname;

	public long getParentid() {
		return parentid;
	}

	public void setParentid(long parentid) {
		this.parentid = parentid;
	}

	public String getParenttaskname() {
		return parenttaskname;
	}

	public void setParenttaskname(String parenttaskname) {
		this.parenttaskname = parenttaskname;
	}
	
	public ParentTask() {
		
	}

	public ParentTask(@Size(max = 100, min = 10, message = "{ParentTask.parenttaskname.invalid}") String parenttaskname) {
		super();
		this.parenttaskname = parenttaskname;
	}
}

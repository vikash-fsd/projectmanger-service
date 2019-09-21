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
@Table(name = "parent_task")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ParentTask {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "parentid")
	long parentid;
	
	@Column(name = "parenttask", nullable=false)
	@Size(max = 100, min = 10, message = "{ParentTask.parenttask.invalid}")
	String parenttask;

	public long getParentid() {
		return parentid;
	}

	public void setParentid(long parentid) {
		this.parentid = parentid;
	}

	public String getParenttask() {
		return parenttask;
	}

	public void setParenttask(String parenttask) {
		this.parenttask = parenttask;
	}
}

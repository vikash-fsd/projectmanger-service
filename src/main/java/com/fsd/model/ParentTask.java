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
	@Column(name = "parent_id")
	long parent_id;
	
	@Column(name = "parent_task", nullable=false)
	@Size(max = 100, min = 10, message = "{parent_task.parent_task.invalid}")
	String parent_task;

	public long getParent_id() {
		return parent_id;
	}

	public void setParent_id(long parent_id) {
		this.parent_id = parent_id;
	}

	public String getParent_task() {
		return parent_task;
	}

	public void setParent_task(String parent_task) {
		this.parent_task = parent_task;
	}
}

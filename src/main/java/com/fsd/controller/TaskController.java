package com.fsd.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fsd.exception.ResourceNotFoundException;
import com.fsd.model.Task;
import com.fsd.repository.TaskRepository;

@RestController
public class TaskController {

	@Autowired
	private TaskRepository taskRepository;
	
	@GetMapping("/Tasks")
	public ResponseEntity<List<Task>> allTasks() {
		List<Task> tasks = taskRepository.findAll();
		return ResponseEntity.ok().body(tasks);
	}

	@GetMapping("/sortTasks/{sort_by}")
	public ResponseEntity<List<Task>> getAllTasksInOrder(@PathVariable("sort_by") String sort_by) {
		Sort sort = new Sort(Sort.DEFAULT_DIRECTION, sort_by);
		List<Task> tasks = taskRepository.findAll(sort);
		return ResponseEntity.ok().body(tasks);
	}
	
	@GetMapping("/Task/{taskid}")
	public ResponseEntity<Task> getTaskById(@PathVariable("taskid") long taskid) {
		Task tasks = taskRepository.getOne(taskid);
		if(tasks!=null)
			return ResponseEntity.ok().body(tasks);
		else
			throw new ResourceNotFoundException("Task", "ID", taskid);
	}

	@DeleteMapping("/deleteTask/{taskid}")
	public ResponseEntity<?> deleteTask(@PathVariable("taskid") long taskid) {
		Task tasks = taskRepository.getOne(taskid);
		if(tasks!=null) {
			taskRepository.delete(tasks);
			return ResponseEntity.ok().body("Task with ID " + taskid + " has been deleted.");
		
		}else
			throw new ResourceNotFoundException("Task", "ID", taskid);
	}

	@PostMapping("/addTask")
	public ResponseEntity<?> saveTask(@RequestBody Task task) {
		taskRepository.save(task);
		return ResponseEntity.accepted().body("Task detail has been saved.");
	}
	
	@PutMapping("/updateTask/{taskid}")
	public ResponseEntity<?> updateTask(@Valid @PathVariable("taskid") long taskid, @Valid @RequestBody Task taskDetail) {
		Task task = taskRepository.getOne(taskid);
		if(task!=null) {
			task.setTaskname(taskDetail.getTaskname());
			task.setParentid(taskDetail.getParentid());
			task.setProjectid(taskDetail.getProjectid());
			task.setStartdate(taskDetail.getStartdate());
			task.setEnddate(taskDetail.getEnddate());
			task.setPriority(taskDetail.getPriority());
			task.setUser(taskDetail.getUser());
			taskRepository.save(task);
			return ResponseEntity.ok().body("Task with ID " + taskid + " has been updated.");
		
		}else
			throw new ResourceNotFoundException("Task", "ID", taskid);
	}
}

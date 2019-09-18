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
	
	@GetMapping("/Tasks/{task_id}")
	public ResponseEntity<Task> getTaskById(@PathVariable("task_id") long task_id) {
		Task tasks = taskRepository.getOne(task_id);
		if(tasks!=null)
			return ResponseEntity.ok().body(tasks);
		else
			throw new ResourceNotFoundException("Task", "ID", task_id);
	}

	@DeleteMapping("/deleteTask/{task_id}")
	public ResponseEntity<?> deleteTask(@PathVariable("task_id") long task_id) {
		Task tasks = taskRepository.getOne(task_id);
		if(tasks!=null) {
			taskRepository.delete(tasks);
			return ResponseEntity.ok().body("Task with ID " + task_id + " has been deleted.");
		
		}else
			throw new ResourceNotFoundException("Task", "ID", task_id);
	}

	@PostMapping("/addTask")
	public ResponseEntity<?> saveTask(@RequestBody Task task) {
		taskRepository.save(task);
		return ResponseEntity.ok().body("Task detail has been saved.");
	}
	
	@PutMapping("/updateTask/{task_id}")
	public ResponseEntity<?> updateTask(@Valid @PathVariable("task_id") long task_id, @Valid @RequestBody Task taskDetail) {
		Task task = taskRepository.getOne(task_id);
		if(task!=null) {
			task.setTask_name(taskDetail.getTask_name());
			task.setParent_id(taskDetail.getParent_id());
			task.setProject_id(taskDetail.getProject_id());
			task.setStart_date(taskDetail.getStart_date());
			task.setEnd_date(taskDetail.getEnd_date());
			task.setPriority(taskDetail.getPriority());
			task.setUser(taskDetail.getUser());
			taskRepository.save(task);
			return ResponseEntity.ok().body("Task with ID " + task_id + " has been updated.");
		
		}else
			throw new ResourceNotFoundException("Task", "ID", task_id);
	}
}

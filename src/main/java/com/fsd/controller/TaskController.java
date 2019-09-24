package com.fsd.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fsd.AppResponse;
import com.fsd.model.Project;
import com.fsd.model.Task;
import com.fsd.repository.TaskRepository;

@RestController
public class TaskController {

	@Autowired
	private TaskRepository taskRepository;
	private AppResponse appResponse;
	
	
	@GetMapping("/Tasks")
	public ResponseEntity<?> allTasks() {
		List<Task> tasks = taskRepository.findAll();
		return ResponseEntity.ok().body(appResponse = new AppResponse(true, tasks, "Task List"));
	}

	@GetMapping("/sortTasks/{sort_by}")
	public ResponseEntity<?> getAllTasksInOrder(@PathVariable("sort_by") String sort_by) {
		Sort sort = new Sort(Sort.DEFAULT_DIRECTION, sort_by);
		List<Task> tasks = taskRepository.findAll(sort);
		return ResponseEntity.ok().body(appResponse = new AppResponse(true, tasks, "Task Sorted By "+sort));		
	}
	
	@GetMapping("/Task/{taskid}")
	public ResponseEntity<?> getTaskById(@PathVariable("taskid") long taskid) {
		Task task = taskRepository.getOne(taskid);
		if(task!=null) {
			return ResponseEntity.ok().body(appResponse = new AppResponse(true, task, "Task Detail Of "+taskid));
		}
		else
			return ResponseEntity.ok().body(appResponse = new AppResponse(false, null, "Task detail not found for "+taskid));
	}
	
	@GetMapping("/searchTask/{taskname}")
	public ResponseEntity<?> searchByProjectName(@PathVariable("taskname") String taskname) {
		List<Task> tasks = taskRepository.findByTaskName(taskname);		
		return ResponseEntity.ok().body(appResponse = new AppResponse(true, tasks, "Project Searched By "+taskname));
	}

	@GetMapping("/deleteTask/{taskid}")
	public ResponseEntity<?> deleteTask(@PathVariable("taskid") long taskid) {
		Task task = taskRepository.getOne(taskid);
		if(task!=null) {
			taskRepository.delete(task);
			return ResponseEntity.ok().body(appResponse = new AppResponse(true, null, "Task detail of "+taskid+ "deleted."));		
		}else {
			return ResponseEntity.ok().body(appResponse = new AppResponse(false, null, "Problem deleting detail of "+taskid));					
		}
	}

	@PostMapping("/addTask")
	public ResponseEntity<?> saveTask(@RequestBody Task task) {
		taskRepository.save(task);
		return ResponseEntity.ok().body(appResponse = new AppResponse(true, task, "Task detail added."));					
	}
	
	@PostMapping("/updateTask/{taskid}")
	public ResponseEntity<?> updateTask(@Valid @PathVariable("taskid") long taskid, @Valid @RequestBody Task taskDetail) {
		Task task = taskRepository.getOne(taskid);
		if(task!=null) {
			task.setTaskname(taskDetail.getTaskname());
			task.setParentid(taskDetail.getParentid());
			task.setProject(taskDetail.getProject());
			task.setStartdate(taskDetail.getStartdate());
			task.setEnddate(taskDetail.getEnddate());
			task.setPriority(taskDetail.getPriority());
			task.setUser(taskDetail.getUser());
			taskRepository.save(task);
			return ResponseEntity.ok().body(appResponse = new AppResponse(true, task, "Task detail of "+taskid+ " updated."));
		}else
			return ResponseEntity.ok().body(appResponse = new AppResponse(false, task, "Problem updating User detail of "+taskid));
		}
}

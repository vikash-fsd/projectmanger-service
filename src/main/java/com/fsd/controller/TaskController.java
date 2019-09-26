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
import com.fsd.model.ParentTask;
import com.fsd.model.Task;
import com.fsd.repository.ParentTaskRepository;
import com.fsd.repository.TaskRepository;

@RestController
public class TaskController {

	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private ParentTaskRepository parentTaskRepository;
	private AppResponse appResponse;
	
	
	@GetMapping("/Tasks")
	public ResponseEntity<?> allTasks() {
		List<Task> tasks = taskRepository.findAll();
		return ResponseEntity.ok().body(appResponse = new AppResponse(true, tasks, "Task List"));
	}
	
	@GetMapping("/parentTasks")
	public ResponseEntity<?> allParentTasks() {
		List<ParentTask> parenttasks = parentTaskRepository.findAll();
		return ResponseEntity.ok().body(appResponse = new AppResponse(true, parenttasks, "Parent Task List"));
	}

	@GetMapping("/sortTasks/{sort_by}")
	public ResponseEntity<?> getAllTasksInOrder(@PathVariable("sort_by") String sort_by) {
		Sort sort = new Sort(Sort.DEFAULT_DIRECTION, sort_by);
		List<Task> tasks = taskRepository.findAll(sort);
		return ResponseEntity.ok().body(appResponse = new AppResponse(true, tasks, "Task Sorted By "+sort_by));		
	}
	
	@GetMapping("/sortProjectTasks/{projectid}/{sort_by}")
	public ResponseEntity<?> getAllProjectTasksInOrder(@PathVariable("projectid") long projectid, @PathVariable("sort_by") String sort_by) {
		List<Task> tasks = taskRepository.sortProjectTask(projectid, sort_by);
		return ResponseEntity.ok().body(appResponse = new AppResponse(true, tasks, "Project ID " + projectid +" Tasks Sorted By "+sort_by));		
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
	
	@GetMapping("/parentTask/{parentid}")
	public ResponseEntity<?> getParentTaskById(@PathVariable("parentid") long parentid) {
		ParentTask parenttask = parentTaskRepository.getOne(parentid);
		if(parenttask!=null) {
			return ResponseEntity.ok().body(appResponse = new AppResponse(true, parenttask, "Parent Task Detail Of "+parentid));
		}
		else
			return ResponseEntity.ok().body(appResponse = new AppResponse(false, null, "Task detail not found for "+parentid));
	}
	
	@GetMapping("/Project/Task/{projectid}")
	public ResponseEntity<?> getTaskByProjectId(@PathVariable("projectid") long projectid) {
		List<Task> tasks = taskRepository.findTasksByProjectID(projectid);
		if(tasks!=null) {
			return ResponseEntity.ok().body(appResponse = new AppResponse(true, tasks, "Project Task Detail Of "+projectid));
		}
		else
			return ResponseEntity.ok().body(appResponse = new AppResponse(false, null, "Project Task detail not found for "+projectid));
	}
	
	@GetMapping("/searchParentTask/{parenttask}")
	public ResponseEntity<?> getParentTaskByName(@PathVariable("parenttask") String parenttask) {
		List<ParentTask> parenttasks = parentTaskRepository.findByParentTaskName(parenttask);
		if(parenttask!=null) {
			return ResponseEntity.ok().body(appResponse = new AppResponse(true, parenttasks, "Parent Task Detail Of "+parenttask));
		}
		else
			return ResponseEntity.ok().body(appResponse = new AppResponse(false, null, "Parent Task detail not found for "+parenttask));
	}
	
	@GetMapping("/searchTask/{taskname}")
	public ResponseEntity<?> searchByTasktName(@PathVariable("taskname") String taskname) {
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
	
	@PostMapping("/addParentTask")
	public ResponseEntity<?> saveParentTask(@RequestBody ParentTask parenttask) {
		parentTaskRepository.save(parenttask);
		return ResponseEntity.ok().body(appResponse = new AppResponse(true, parenttask, "Parent Task detail added."));					
	}
	
	@PostMapping("/updateTask/{taskid}")
	public ResponseEntity<?> updateTask(@Valid @PathVariable("taskid") long taskid, @Valid @RequestBody Task taskDetail) {
		Task task = taskRepository.getOne(taskid);
		if(task!=null) {
			task.setTaskname(taskDetail.getTaskname());
			task.setParentid(taskDetail.getParentid());
			task.setProjectid(taskDetail.getProjectid());
			task.setStartdate(taskDetail.getStartdate());
			task.setEnddate(taskDetail.getEnddate());
			task.setPriority(taskDetail.getPriority());
			task.setUserid(taskDetail.getUserid());
			task.setStatus(taskDetail.getStatus());
			taskRepository.save(task);
			return ResponseEntity.ok().body(appResponse = new AppResponse(true, task, "Task detail of "+taskid+ " updated."));
		}else
			return ResponseEntity.ok().body(appResponse = new AppResponse(false, task, "Problem updating User detail of "+taskid));
		}
}

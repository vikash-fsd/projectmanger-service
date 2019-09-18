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
import com.fsd.model.Project;
import com.fsd.repository.ProjectRepository;

@RestController
public class ProjectController {

	@Autowired
	private ProjectRepository projectRepository;
	
	@GetMapping("/Projects")
	public ResponseEntity<List<Project>> getAllProjects() {
		List<Project> projects = projectRepository.findAll();
		return ResponseEntity.ok().body(projects);
	}
	
	@GetMapping("/sortProjects/{sort_by}")
	public ResponseEntity<List<Project>> getAllProjectsInOrder(@PathVariable("sort_by") String sort_by) {
		Sort sort = new Sort(Sort.DEFAULT_DIRECTION, sort_by);
		List<Project> projects = projectRepository.findAll(sort);
		return ResponseEntity.ok().body(projects);
	}
	
	@GetMapping("/Project/{project_id}")
	public ResponseEntity<Project> getProjectById(@PathVariable("project_id") long project_id) {
		Project projects = projectRepository.getOne(project_id);
		if(projects!=null)
			return ResponseEntity.ok().body(projects);
		else
			throw new ResourceNotFoundException("Project", "ID", project_id);
	}
	
	@GetMapping("/searchProject/{project_name}")
	public ResponseEntity<List<Project>> searchByProjectName(@PathVariable("project_name") String project_name) {
		List<Project> projects = projectRepository.findByProjectName(project_name);
		return ResponseEntity.ok().body(projects);
	}

	@DeleteMapping("/deleteProject/{project_id}")
	public ResponseEntity<?> deleteProject(@PathVariable("project_id") long project_id) {
		Project projects = projectRepository.getOne(project_id);
		if(projects!=null) {
			projectRepository.delete(projects);
			return ResponseEntity.ok().body("Project with ID " + project_id + " has been deleted.");
		
		}else
			throw new ResourceNotFoundException("Project", "ID", project_id);
	}

	@PostMapping("/addProject")
	public ResponseEntity<?> saveProject(@RequestBody Project project) {
		projectRepository.save(project);
		return ResponseEntity.ok().body("Project detail has been saved.");
	}
	
	@PutMapping("/updateProject/{project_id}")
	public ResponseEntity<?> updateProject(@Valid @PathVariable("project_id") long project_id, @Valid @RequestBody Project projectDetail) {
		Project project = projectRepository.getOne(project_id);
		if(project!=null) {
			project.setProject_name(projectDetail.getProject_name());
			project.setStart_date(projectDetail.getStart_date());
			project.setEnd_date(projectDetail.getEnd_date());
			project.setPriority(projectDetail.getPriority());
			project.setUser(projectDetail.getUser());
			projectRepository.save(project);
			return ResponseEntity.ok().body("Project with ID " + project_id + " has been updated.");
		
		}else
			throw new ResourceNotFoundException("Project", "ID", project_id);
	}
}

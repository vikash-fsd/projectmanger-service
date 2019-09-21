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
	
	@GetMapping("/Project/{projectid}")
	public ResponseEntity<Project> getProjectById(@PathVariable("projectid") long projectid) {
		Project projects = projectRepository.getOne(projectid);
		if(projects!=null)
			return ResponseEntity.ok().body(projects);
		else
			throw new ResourceNotFoundException("Project", "ID", projectid);
	}
	
	@GetMapping("/searchProject/{projectname}")
	public ResponseEntity<List<Project>> searchByProjectName(@PathVariable("projectname") String projectname) {
		List<Project> projects = projectRepository.findByProjectName(projectname);
		return ResponseEntity.ok().body(projects);
	}

	@DeleteMapping("/deleteProject/{projectid}")
	public ResponseEntity<?> deleteProject(@PathVariable("projectid") long projectid) {
		Project projects = projectRepository.getOne(projectid);
		if(projects!=null) {
			projectRepository.delete(projects);
			return ResponseEntity.ok().body("Project with ID " + projectid + " has been deleted.");
		
		}else
			throw new ResourceNotFoundException("Project", "ID", projectid);
	}

	@PostMapping("/addProject")
	public ResponseEntity<?> saveProject(@RequestBody Project project) {
		projectRepository.save(project);
		return ResponseEntity.accepted().body("Project detail has been saved.");
	}
	
	@PutMapping("/updateProject/{projectid}")
	public ResponseEntity<?> updateProject(@Valid @PathVariable("projectid") long projectid, @Valid @RequestBody Project projectDetail) {
		Project project = projectRepository.getOne(projectid);
		if(project!=null) {
			project.setProject_name(projectDetail.getProject_name());
			project.setStartdate(projectDetail.getStartdate());
			project.setEnddate(projectDetail.getEnddate());
			project.setPriority(projectDetail.getPriority());
			project.setUser(projectDetail.getUser());
			projectRepository.save(project);
			return ResponseEntity.ok().body("Project with ID " + projectid + " has been updated.");
		
		}else
			throw new ResourceNotFoundException("Project", "ID", projectid);
	}
}

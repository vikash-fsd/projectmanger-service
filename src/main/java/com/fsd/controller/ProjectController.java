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
import com.fsd.repository.ProjectRepository;

@RestController
public class ProjectController {

	@Autowired
	private ProjectRepository projectRepository;
	private AppResponse appResponse;
	
	@GetMapping("/Projects")
	public ResponseEntity<?> getAllProjects() {
		List<Project> projects = projectRepository.findAll();		
		return ResponseEntity.ok().body(appResponse = new AppResponse(true, projects, "Project List"));
	}
	
	@GetMapping("/sortProject/{sort_by}")
	public ResponseEntity<?> getAllProjectInOrder(@PathVariable("sort_by") String sort_by) {
		Sort sort = new Sort(Sort.DEFAULT_DIRECTION, sort_by);
		List<Project> projects = projectRepository.findAll(sort);
		return ResponseEntity.ok().body(appResponse = new AppResponse(true, projects, "Project Sorted By "+sort));
	}
	
	@GetMapping("/Project/{projectid}")
	public ResponseEntity<?> getProjectById(@PathVariable("projectid") long projectid) {
		Project project = projectRepository.getOne(projectid);
		if(project!=null) {
			return ResponseEntity.ok().body(appResponse = new AppResponse(true, project, "Project Detail Of "+projectid));
		}
		else
			return ResponseEntity.ok().body(appResponse = new AppResponse(false, null, "Project detail not found for "+projectid));
	}
	
	@GetMapping("/searchProject/{projectname}")
	public ResponseEntity<?> searchByProjectName(@PathVariable("projectname") String projectname) {
		List<Project> projects = projectRepository.findByProjectName(projectname);		
		return ResponseEntity.ok().body(appResponse = new AppResponse(true, projects, "Project Searched By "+projectname));
	}

	@GetMapping("/deleteProject/{projectid}")
	public ResponseEntity<?> deleteProject(@PathVariable("projectid") long projectid) {
		Project project = projectRepository.getOne(projectid);
		if(project!=null) {
			projectRepository.delete(project);
			return ResponseEntity.ok().body(appResponse = new AppResponse(true, null, "Project detail of "+projectid+ "deleted."));		
		}else {
			return ResponseEntity.ok().body(appResponse = new AppResponse(false, null, "Problem deleting detail of "+projectid));					
		}
			
	}

	@PostMapping("/addProject")
	public ResponseEntity<?> saveProject(@Valid @RequestBody Project project) {
		projectRepository.save(project);
		return ResponseEntity.ok().body(appResponse = new AppResponse(true, project, "Project detail added."));					
	}
	
	@PostMapping("/updateProject/{projectid}")
	public ResponseEntity<?> updateProject(@Valid @PathVariable("projectid") long projectid, @Valid @RequestBody Project projectDetail) {
		Project project = projectRepository.getOne(projectid);
		if(project!=null) {
			project.setProjectname(projectDetail.getProjectname());
			project.setPriority(projectDetail.getPriority());
			project.setStartdate(projectDetail.getStartdate());
			project.setEnddate(projectDetail.getEnddate());
			project.setUser(projectDetail.getUser());
			projectRepository.save(project);
			return ResponseEntity.ok().body(appResponse = new AppResponse(true, project, "Project detail of "+projectid+ " updated."));
		}else
			return ResponseEntity.ok().body(appResponse = new AppResponse(false, project, "Problem updating project detail of "+projectid));
		}
}

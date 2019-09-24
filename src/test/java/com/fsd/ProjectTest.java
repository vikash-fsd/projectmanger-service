package com.fsd;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fsd.model.Project;
import com.fsd.repository.ProjectRepository;

public class ProjectTest extends AbstractTest{

	@Autowired
	private ProjectRepository projectRepository;
	
	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void testGetAllProjects() throws Exception {
		String uri = "/Projects";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		//String content = mvcResult.getResponse().getContentAsString();
		//Users[] userlist = super.mapFromJson(content, Users[].class);
		//assertTrue(userlist.length > 0);
	}
	
	@Test
	public void testGetProjectById() throws Exception {
		String uri = "/Project/1";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		//String content = mvcResult.getResponse().getContentAsString();
		//Users user = super.mapFromJson(content, Users.class);
		//assertTrue(user.getUserid() == 1);
	}
	
	@Test
	public void testAddProject() throws Exception {
		String uri = "/addProject";
		Project prj = new Project("Added Test Project", new Date(2019-9-20), new Date(2020-9-20), 2, (long)1);
		String inputJson = super.mapToJson(prj);
		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		//String content = mvcResult.getResponse().getContentAsString();
		//assertEquals(content, "User detail has been saved.");
	}
	
	@Test
	public void testUpdateProject() throws Exception {		
		List<Project> projects = projectRepository.findByProjectName("Test Project");
		
		Project project = projects.get(0);
		String uri = "/updateProject/"+project.getProjectid();
		project.setPriority(20);
		
		String inputJson = super.mapToJson(project);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		//content = mvcResult.getResponse().getContentAsString();
		//(content, "User with ID " + user.getUserid() + " has been updated.");
	}
	@Test
	public void testSortProjects() throws Exception {
		String uri = "/sortProjects/projectname";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		//String content = mvcResult.getResponse().getContentAsString();
		//Users[] userlist = super.mapFromJson(content, Users[].class);
		//assertTrue(userlist.length > 0);
	}
	
	@Test
	public void testSearchProjectByProjectName() throws Exception {
		String uri = "/searchProject/Test Project";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		//String content = mvcResult.getResponse().getContentAsString();
		//Users[] userlist = super.mapFromJson(content, Users[].class);
		//assertTrue(userlist.length > 0);
	}

	@Test
	public void testDeleteProject() throws Exception {
		List<Project> projects = projectRepository.findByProjectName("Added Test Project");
		
		Project project = projects.get(0);

		String uri = "/deleteProject/"+project.getProjectid();
		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		//content = mvcResult.getResponse().getContentAsString();
		//assertEquals(content, "User with ID " + user.getUserid() + " has been deleted.");
	}
}

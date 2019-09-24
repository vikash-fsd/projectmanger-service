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

import com.fsd.model.Task;
import com.fsd.repository.TaskRepository;

public class TaskTest extends AbstractTest{
	@Autowired
	private TaskRepository taskRepository;
	
	
	@Override
	@Before
	public void setUp() {
		super.setUp();
	}
	
	@Test
	public void testGetAllTasks() throws Exception {
		String uri = "/Tasks";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		//String content = mvcResult.getResponse().getContentAsString();
		//Users[] userlist = super.mapFromJson(content, Users[].class);
		//assertTrue(userlist.length > 0);
	}
	
	@Test
	public void testGetTaskById() throws Exception {
		String uri = "/Task/1";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		//String content = mvcResult.getResponse().getContentAsString();
		//Users user = super.mapFromJson(content, Users.class);
		//assertTrue(user.getUserid() == 1);
	}
	
	@Test
	public void testSearchTaskByTaskName() throws Exception {
		String uri = "/searchTask/Test Task Name";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		//String content = mvcResult.getResponse().getContentAsString();
		//Users[] userlist = super.mapFromJson(content, Users[].class);
		//assertTrue(userlist.length > 0);
	}
	
	@Test
	public void testSortTasks() throws Exception {
		String uri = "/sortTasks/taskname";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		//String content = mvcResult.getResponse().getContentAsString();
		//Users[] userlist = super.mapFromJson(content, Users[].class);
		//assertTrue(userlist.length > 0);
	}

	@Test
	public void testAddTask() throws Exception {
		String uri = "/addTask";
		Task task = new Task(null, 1, "Added Test Task", new Date(2019-9-20), new Date(2020-9-20), 10, 0, 1);
		String inputJson = super.mapToJson(task);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		//String content = mvcResult.getResponse().getContentAsString();
		//assertEquals(content, "User detail has been saved.");
	}

	@Test
	public void testUpdateTask() throws Exception {		
		List<Task> tasks = taskRepository.findByTaskName("Added Test Task");
		
		Task task = tasks.get(0);
		String uri = "/updateTask/"+task.getTaskid();
		task.setPriority(20);
		
		String inputJson = super.mapToJson(task);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		//content = mvcResult.getResponse().getContentAsString();
		//(content, "User with ID " + user.getUserid() + " has been updated.");
	}

	@Test
	public void testDeleteTask() throws Exception {
		List<Task> tasks = taskRepository.findByTaskName("Added Test Task");
		
		Task task = tasks.get(0);

		String uri = "/deleteTask/"+task.getTaskid();
		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		//content = mvcResult.getResponse().getContentAsString();
		//assertEquals(content, "User with ID " + user.getUserid() + " has been deleted.");
	}
}

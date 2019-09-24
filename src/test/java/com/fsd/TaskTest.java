package com.fsd;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fsd.model.Project;
import com.fsd.model.Task;
import com.fsd.model.Users;
import com.fsd.repository.ProjectRepository;
import com.fsd.repository.TaskRepository;
import com.fsd.repository.UsersRepository;

public class TaskTest extends AbstractTest{
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
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
	public void testSortTasks() throws Exception {
		String uri = "/sortTasks/taskid";
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
		Users user = usersRepository.getOne((long)1);
		Project project = projectRepository.getOne((long)1);
		String uri = "/addTask";
		Task task = new Task(0,project,"Added Test Task", new Date("2019/09/20"), new Date("2020/09/20"), 10, "Inprogress", user);
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

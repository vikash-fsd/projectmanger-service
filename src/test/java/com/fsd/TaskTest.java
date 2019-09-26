package com.fsd;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fsd.model.ParentTask;
import com.fsd.model.Task;
import com.fsd.repository.TaskRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
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
	}
	
	@Test
	public void testGetAllParentTasks() throws Exception {
		String uri = "/parentTasks";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	
	@Test
	public void testGetTaskById() throws Exception {
		String uri = "/Task/1";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	
	@Test
	public void testGettParentTaskById() throws Exception {
		String uri = "/parentTask/1";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);	}
	
	@Test
	public void testSearchTaskByTaskName() throws Exception {
		String uri = "/searchTask/Test Task Name";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	
	@Test
	public void testGetParentTaskByName() throws Exception {
		String uri = "/searchParentTask/Testing Parent Task";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	
	@Test
	public void testSortTasks() throws Exception {
		String uri = "/sortTasks/taskname";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);	}
	
	@Test
	public void testGetAllProjectTasksInOrder() throws Exception {
		String uri = "/sortProjectTasks/1/taskname";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	@Test
	public void testAddTask() throws Exception {
		String uri = "/addTask";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startdate = dateFormat.parse("23-09-2023");
		Date enddate = dateFormat.parse("22-09-2019");
		Task task = new Task(null, 1, "Added Test Task", startdate, enddate, 10, 0, 1);
		String inputJson = super.mapToJson(task);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	
	/*@Test
	public void testAddParentTask() throws Exception {
		String uri = "/addParentTask";
		ParentTask parentTask = new ParentTask("Testing Task Parent");
		String inputJson = super.mapToJson(parentTask);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}*/

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
	}

	@Test
	public void testDeleteTask() throws Exception {
		List<Task> tasks = taskRepository.findByTaskName("Added Test Task");
		
		Task task = tasks.get(0);

		String uri = "/deleteTask/"+task.getTaskid();
		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
}

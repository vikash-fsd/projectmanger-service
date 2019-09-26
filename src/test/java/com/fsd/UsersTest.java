package com.fsd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

import com.fsd.model.Users;
import com.fsd.repository.UsersRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersTest extends AbstractTest{

	@Autowired
	private UsersRepository usersRepository;
	
	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void testGetAllUsers() throws Exception {
		String uri = "/Users";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		//String content = mvcResult.getResponse().getContentAsString();
		//Users[] userlist = super.mapFromJson(content, Users[].class);
		//assertTrue(userlist.length > 0);
	}
	
	@Test
	public void testGetUserById() throws Exception {
		String uri = "/User/1";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		//String content = mvcResult.getResponse().getContentAsString();
		//Users user = super.mapFromJson(content, Users.class);
		//assertTrue(user.getUserid() == 1);
	}
	
	@Test
	public void testSortUsers() throws Exception {
		String uri = "/sortUsers/firstname";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		//String content = mvcResult.getResponse().getContentAsString();
		//Users[] userlist = super.mapFromJson(content, Users[].class);
		//assertTrue(userlist.length > 0);
	}
	
	@Test
	public void testSearchUserByFirstName() throws Exception {
		String uri = "/searchUser/Test";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		//String content = mvcResult.getResponse().getContentAsString();
		//Users[] userlist = super.mapFromJson(content, Users[].class);
		//assertTrue(userlist.length > 0);
	}

	@Test
	public void testAddUser() throws Exception {
		String uri = "/addUser";
		Users user = new Users("Added", "User", 100000);
		String inputJson = super.mapToJson(user);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		//String content = mvcResult.getResponse().getContentAsString();
		//assertEquals(content, "User detail has been saved.");
	}

	@Test
	public void testUpdateUser() throws Exception {
		
		List<Users> users = usersRepository.findByFirstName("Added");
		
		Users user = users.get(0);
		String uri = "/updateUser/"+user.getUserid();
		user.setLastname("User_Updated");
		
		String inputJson = super.mapToJson(user);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		//content = mvcResult.getResponse().getContentAsString();
		//(content, "User with ID " + user.getUserid() + " has been updated.");
	}

	@Test
	public void testDeleteUser() throws Exception {
		List<Users> users = usersRepository.findByFirstName("Added");
		
		Users user = users.get(0);

		String uri = "/deleteUser/"+user.getUserid();
		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		//content = mvcResult.getResponse().getContentAsString();
		//assertEquals(content, "User with ID " + user.getUserid() + " has been deleted.");
	}

}

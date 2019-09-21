package com.fsd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fsd.model.Users;

/*@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)*/
public class UsersTest extends AbstractTest{

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
		String content = mvcResult.getResponse().getContentAsString();
		Users[] userlist = super.mapFromJson(content, Users[].class);
		assertTrue(userlist.length > 0);
	}
	
	@Test
	public void testGetUserById() throws Exception {
		String uri = "/User/1";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Users user = super.mapFromJson(content, Users.class);
		assertTrue(user.getUserid() == 1);
	}
	
	@Test
	public void testSortUsers() throws Exception {
		String uri = "/sortUsers/firstname";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Users[] userlist = super.mapFromJson(content, Users[].class);
		assertTrue(userlist.length > 0);
	}
	
	@Test
	public void testSearchUserByFirstName() throws Exception {
		String uri = "/searchUser/Test";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Users[] userlist = super.mapFromJson(content, Users[].class);
		assertTrue(userlist.length > 0);
	}

	@Test
	public void testAddUser() throws Exception {
		String uri = "/addUser";
		Users user = new Users("Added", "User", 100);
		String inputJson = super.mapToJson(user);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(202, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "User detail has been saved.");
	}

	@Test
	public void testUpdateUser() throws Exception {
		
		String uri = "/searchUser/Added";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		Users[] userlist = super.mapFromJson(content, Users[].class);
		Users user = userlist[0];
		uri = "/updateUser/"+user.getUserid();
		user.setLastname("User_Updated");
		
		String inputJson = super.mapToJson(user);
	      mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "User with ID " + user.getUserid() + " has been updated.");
	}

	@Test
	public void testDeleteProduct() throws Exception {
		String uri = "/searchUser/Added";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		Users[] userlist = super.mapFromJson(content, Users[].class);
		Users user = userlist[0];
		uri = "/deleteUser/"+user.getUserid();
		
		mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "User with ID " + user.getUserid() + " has been deleted.");
	}

}

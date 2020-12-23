package main.kts.controller;

import static main.kts.constants.CategoryConstants.*;
import static main.kts.constants.CulturalOfferConstants.DB_UPDATE_ID;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import main.kts.dto.CategoryDTO;
import main.kts.dto.UserLoginDTO;
import main.kts.dto.UserTokenStateDTO;
import main.kts.model.Category;
import main.kts.service.CategoryService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CategoryControllerIntegrationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private CategoryService categoryService;
	
	
	private String accessToken;
	
	@Before
	public void loginAdmin() {
		ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/log-in",
				new UserLoginDTO(ADMIN_EMAIL, ADMIN_PASSWORD), UserTokenStateDTO.class);
		accessToken = "Bearer " + responseEntity.getBody().getAccessToken();
	}
	
	@Test
	public void testGetAll() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		
		ResponseEntity<CategoryDTO[]> responseEntity = restTemplate.exchange("/api/category", HttpMethod.GET, request,
				CategoryDTO[].class);
		CategoryDTO[] categories = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(DB_SIZE, categories.length);
	//	assertEquals(DB_CATEGORY_NAME, categories[0].getName());
	}
	@Test
	public void testGetPage() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		
		ResponseEntity<CategoryDTO[]> responseEntity = restTemplate.exchange("/api/category?page=0&size=2", HttpMethod.GET, request,
				CategoryDTO[].class);
		CategoryDTO[] categories = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(PAGEABLE_SIZE, new Integer(categories.length));
		assertEquals(DB_CATEGORY_NAME, categories[0].getName());
	}
	
	@Test
	public void testGetById() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		
		ResponseEntity<CategoryDTO> responseEntity = restTemplate.exchange("/api/category/"+CATEGORY_ID, HttpMethod.GET, request,
				CategoryDTO.class);
		CategoryDTO category = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(DB_CATEGORY_ID, category.getId());
		assertEquals(DB_CATEGORY_NAME, category.getName());
	}
	
	@Test
	public void testGetById_GivenFalseId() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		
		ResponseEntity<CategoryDTO> responseEntity = restTemplate.exchange("/api/category/"+FALSE_CATEGORY_ID, HttpMethod.GET, request,
				CategoryDTO.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCreate() throws Exception {
		int size = categoryService.findAll().size();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", NEW_CATEGORY_NAME);
		jsonObject.put("description", NEW_CATEGORY_DESCRIPTION);
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);
		ResponseEntity<CategoryDTO> responseEntity = restTemplate.postForEntity("/api/category", request,CategoryDTO.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(NEW_CATEGORY_NAME, responseEntity.getBody().getName());
		
		List<Category> categories = categoryService.findAll();
		assertEquals(size+1, categories.size());
		
		categoryService.delete(responseEntity.getBody().getId());
	
	}
	
	@Test
	public void testCreate_GivenExistingName() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", EXISTING_CATEGORY_NAME);
		jsonObject.put("description", NEW_CATEGORY_DESCRIPTION);
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);
		ResponseEntity<CategoryDTO> responseEntity = restTemplate.postForEntity("/api/category", request,CategoryDTO.class);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	
	}
	
	// update imena prve kat
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdate() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", UPDATE_CATEGORY_NAME);
		jsonObject.put("description", UPDATE_CATEGORY_DESCRIPTION);
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);
		ResponseEntity<CategoryDTO> responseEntity = restTemplate.exchange("/api/category/"+CATEGORY_ID, HttpMethod.PUT, request,CategoryDTO.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(UPDATE_CATEGORY_NAME, responseEntity.getBody().getName());
	}
	
	@Test
	public void testUpdate_GivenFalseId() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", UPDATE_CATEGORY_NAME);
		jsonObject.put("description", UPDATE_CATEGORY_DESCRIPTION);
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);
		ResponseEntity<CategoryDTO> responseEntity = restTemplate.exchange("/api/category/"+FALSE_CATEGORY_ID, HttpMethod.PUT, request,CategoryDTO.class);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	// update prve kat imenom druge kat
	@Test
	public void testUpdate_GivenExistingName() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", EXISTING_CATEGORY_NAME);
		jsonObject.put("description", UPDATE_CATEGORY_DESCRIPTION);
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);
		ResponseEntity<CategoryDTO> responseEntity = restTemplate.exchange("/api/category/"+DB_CATEGORY_ID, HttpMethod.PUT, request,CategoryDTO.class);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	@Test
    @Transactional
    @Rollback(true)
    public void testDelete() throws Exception {
		int size = categoryService.findAll().size();
		HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.AUTHORIZATION, accessToken);
			HttpEntity<Object> request = new HttpEntity<Object>(headers);
			
	        ResponseEntity<Void> responseEntity =
	                restTemplate.exchange("/api/category/" + CATEGORY_ID,
	                        HttpMethod.DELETE, request, Void.class);

	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	        assertEquals(size - 1, categoryService.findAll().size());
	    
	}
	
	@Test
    public void testDelete_GivenFalseId() throws Exception {
		HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.AUTHORIZATION, accessToken);
			HttpEntity<Object> request = new HttpEntity<Object>(headers);
			
	        ResponseEntity<Void> responseEntity =
	                restTemplate.exchange("/api/category/" + FALSE_CATEGORY_ID,
	                        HttpMethod.DELETE, request, Void.class);

	        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	 
	}

}

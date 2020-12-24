package main.kts.controller;


import static main.kts.constants.TypeConstants.*;
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

import main.kts.dto.TypeDTO;
import main.kts.dto.UserLoginDTO;
import main.kts.dto.UserTokenStateDTO;
import main.kts.model.Type;
import main.kts.service.TypeService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class TypeControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private TypeService typeService;
	
	private String accessToken;
	
	@Before
	public void loginAdmin() {
		ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/log-in",
				new UserLoginDTO(ADMIN_EMAIL, ADMIN_PASSWORD), UserTokenStateDTO.class);
		accessToken = "Bearer " + responseEntity.getBody().getAccessToken();
	}
	
//	@Test
//	public void testGetAll() {
//		HttpHeaders headers = new HttpHeaders();
//		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
//		HttpEntity<Object> request = new HttpEntity<Object>(headers);
//		
//		ResponseEntity<TypeDTO[]> responseEntity = restTemplate.exchange("/api/type", HttpMethod.GET, request,
//				TypeDTO[].class);
//		TypeDTO[] types = responseEntity.getBody();
//		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//		assertEquals(DB_SIZE, types.length);
//
//	}
//	@Test
//	public void testGetPage() {
//		HttpHeaders headers = new HttpHeaders();
//		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
//		headers.setContentType(MediaType.APPLICATION_JSON);
//
//		HttpEntity<Object> request = new HttpEntity<Object>(headers);
//		
//		ResponseEntity<TypeDTO[]> responseEntity = restTemplate.exchange("/api/type?page=0&size=4", HttpMethod.GET, request,
//				TypeDTO[].class);
//		TypeDTO[] types = responseEntity.getBody();
//		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//		assertEquals(DB_PAGEABLE_SIZE4, new Integer(types.length));
//		assertEquals(DB_TYPE_NAME, types[0].getName());
//	}
	
//	@Test
//	public void testGetById() {
//		HttpHeaders headers = new HttpHeaders();
//		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		HttpEntity<Object> request = new HttpEntity<Object>(headers);
//		
//		ResponseEntity<TypeDTO> responseEntity = restTemplate.exchange("/api/type/"+TYPE_ID, HttpMethod.GET, request,
//				TypeDTO.class);
//		TypeDTO type = responseEntity.getBody();
//		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//		assertEquals(DB_TYPE_ID, type.getId());
//		assertEquals(DB_TYPE_NAME, type.getName());
//	}
	
	@Test
	public void testGetById_GivenFalseId() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		
		ResponseEntity<TypeDTO> responseEntity = restTemplate.exchange("/api/type/"+FALSE_TYPE_ID, HttpMethod.GET, request,
				TypeDTO.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCreate() throws Exception {
		int size = typeService.findAll().size();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", NEW_TYPE_NAME);
		jsonObject.put("description", NEW_TYPE_DESCRIPTION);
		JSONObject categoryObject = new JSONObject();
		categoryObject.put("id", NEW_CATEGORY_ID);
		categoryObject.put("name", DB_CATEGORY_NAME2);
		categoryObject.put("description", DB_CATEGORY_DESCIPTION2);
		jsonObject.put("categoryDTO", categoryObject);
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);
		ResponseEntity<TypeDTO> responseEntity = restTemplate.postForEntity("/api/type", request,TypeDTO.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(NEW_TYPE_NAME, responseEntity.getBody().getName());
		
		List<Type> categories = typeService.findAll();
		assertEquals(size+1, categories.size());
		
		typeService.delete(responseEntity.getBody().getId());
	
	}
	
	@Test
	public void testCreate_GivenExistingName() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", EXISTING_TYPE_NAME);
		jsonObject.put("description", NEW_TYPE_DESCRIPTION);
		JSONObject categoryObject = new JSONObject();
		categoryObject.put("id", NEW_CATEGORY_ID);
		categoryObject.put("name", DB_CATEGORY_NAME2);
		categoryObject.put("description", DB_CATEGORY_DESCIPTION2);
		jsonObject.put("categoryDTO", categoryObject);
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);
		ResponseEntity<TypeDTO> responseEntity = restTemplate.postForEntity("/api/type", request,TypeDTO.class);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

//	@Test
//	@Transactional
//	@Rollback(true)
//	public void testUpdate() throws Exception {
//		HttpHeaders headers = new HttpHeaders();
//		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("name", UPDATE_TYPE_NAME);
//		jsonObject.put("description", DB_TYPE_DESCRIPTION);
//		JSONObject categoryObject = new JSONObject();
//		categoryObject.put("id", DB_CATEGORY_ID1);
//		categoryObject.put("name", DB_CATEGORY_NAME1);
//		categoryObject.put("description", DB_CATEGORY_DESCIPTION1);
//		jsonObject.put("categoryDTO", categoryObject);
//		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);
//		ResponseEntity<TypeDTO> responseEntity = restTemplate.exchange("/api/type/"+TYPE_ID, HttpMethod.PUT, request,TypeDTO.class);
//		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//		assertEquals(UPDATE_TYPE_NAME, responseEntity.getBody().getName());
//	}
	
	@Test
	public void testUpdate_GivenFalseId() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", UPDATE_TYPE_NAME);
		jsonObject.put("description", DB_TYPE_DESCRIPTION);
		JSONObject categoryObject = new JSONObject();
		categoryObject.put("id", DB_CATEGORY_ID1);
		categoryObject.put("name", DB_CATEGORY_NAME1);
		categoryObject.put("description", DB_CATEGORY_DESCIPTION1);
		jsonObject.put("categoryDTO", categoryObject);
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);
		ResponseEntity<TypeDTO> responseEntity = restTemplate.exchange("/api/type/"+FALSE_TYPE_ID, HttpMethod.PUT, request,TypeDTO.class);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	
	@Test
	public void testUpdate_GivenExistingName() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", EXISTING_TYPE_NAME);
		jsonObject.put("description", DB_TYPE_DESCRIPTION);
		JSONObject categoryObject = new JSONObject();
		categoryObject.put("id", DB_CATEGORY_ID1);
		categoryObject.put("name", DB_CATEGORY_NAME1);
		categoryObject.put("description", DB_CATEGORY_DESCIPTION1);
		jsonObject.put("categoryDTO", categoryObject);
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);
		ResponseEntity<TypeDTO> responseEntity = restTemplate.exchange("/api/type/"+TYPE_ID, HttpMethod.PUT, request,TypeDTO.class);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
//	@Test
//    @Transactional
//    @Rollback(true)
//    public void testDelete() throws Exception {
//		int size = typeService.findAll().size();
//		HttpHeaders headers = new HttpHeaders();
//			headers.add(HttpHeaders.AUTHORIZATION, accessToken);
//			HttpEntity<Object> request = new HttpEntity<Object>(headers);
//			
//	        ResponseEntity<Void> responseEntity =
//	                restTemplate.exchange("/api/type/" + TYPE_ID,
//	                        HttpMethod.DELETE, request, Void.class);
//
//	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//	        assertEquals(size - 1, typeService.findAll().size());
//	}
	
	
	@Test
    public void testDelete_GivenFalseId() throws Exception {
		HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.AUTHORIZATION, accessToken);
			HttpEntity<Object> request = new HttpEntity<Object>(headers);
			
	        ResponseEntity<Void> responseEntity =
	                restTemplate.exchange("/api/type/" + FALSE_TYPE_ID,
	                        HttpMethod.DELETE, request, Void.class);

	        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	 
	}

}

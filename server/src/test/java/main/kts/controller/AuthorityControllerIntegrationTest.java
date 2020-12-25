package main.kts.controller;

import static main.kts.constants.AuthorityConstants.ADMIN_EMAIL;
import static main.kts.constants.AuthorityConstants.ADMIN_PASSWORD;
import static main.kts.constants.AuthorityConstants.AUTHORITY_ID;
import static main.kts.constants.AuthorityConstants.DB_AUTHORITY_ID;
import static main.kts.constants.AuthorityConstants.DB_AUTHORITY_ROLE_EXISTING;
import static main.kts.constants.AuthorityConstants.DB_AUTHORITY_ROLE_EXISTING2;
import static main.kts.constants.AuthorityConstants.DB_SIZE;
import static main.kts.constants.AuthorityConstants.FALSE_AUTHORITY_ID;
import static main.kts.constants.AuthorityConstants.NEW_AUTHORITY_ID;
import static main.kts.constants.AuthorityConstants.NEW_AUTHORITY_ROLE;
import static main.kts.constants.AuthorityConstants.UPDATE_AUTHORITY_ROLE;
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

import main.kts.dto.AuthorityDTO;
import main.kts.dto.UserLoginDTO;
import main.kts.dto.UserTokenStateDTO;
import main.kts.model.Authority;
import main.kts.service.AuthorityService;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class AuthorityControllerIntegrationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private AuthorityService authorityService;
	
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
		
		ResponseEntity<AuthorityDTO[]> responseEntity = restTemplate.exchange("/api/authority", HttpMethod.GET, request,
				AuthorityDTO[].class);
		AuthorityDTO[] authorities = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(DB_SIZE, authorities.length);

	}
	
	
	@Test
	public void testGetById() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		
		ResponseEntity<AuthorityDTO> responseEntity = restTemplate.exchange("/api/authority/"+AUTHORITY_ID, HttpMethod.GET, request,
				AuthorityDTO.class);
		AuthorityDTO authority = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(DB_AUTHORITY_ID, authority.getId());
		assertEquals(DB_AUTHORITY_ROLE_EXISTING, authority.getRole());
	}
	
	@Test
	public void testGetById_GivenFalseId() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		
		ResponseEntity<AuthorityDTO> responseEntity = restTemplate.exchange("/api/authority/"+FALSE_AUTHORITY_ID, HttpMethod.GET, request,
				AuthorityDTO.class);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
		
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCreate() throws Exception {
		int size = authorityService.findAll().size();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("role", NEW_AUTHORITY_ROLE);
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);
		ResponseEntity<AuthorityDTO> responseEntity = restTemplate.postForEntity("/api/authority", request,AuthorityDTO.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(NEW_AUTHORITY_ROLE, responseEntity.getBody().getRole());
		
		List<Authority> authorities = authorityService.findAll();
		assertEquals(size+1, authorities.size());
		
		//authorityService.delete(responseEntity.getBody().getId());
	
	}
	
	@Test
	public void testCreate_GivenExistingRole() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("role", DB_AUTHORITY_ROLE_EXISTING2);
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);
		ResponseEntity<AuthorityDTO> responseEntity = restTemplate.postForEntity("/api/authority", request,AuthorityDTO.class);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
/*
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdate() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("role", UPDATE_AUTHORITY_ROLE);
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);
		ResponseEntity<AuthorityDTO> responseEntity = restTemplate.exchange("/api/authority/"+DB_AUTHORITY_ID,HttpMethod.PUT, request,AuthorityDTO.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(UPDATE_AUTHORITY_ROLE, responseEntity.getBody().getRole());
		
		authorityService.update(new Authority("ROLE_REGISTERED_USER"), DB_AUTHORITY_ID);
	}
	*/
	@Test
	public void testUpdate_GivenFalseId() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("role", UPDATE_AUTHORITY_ROLE);
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);
		ResponseEntity<AuthorityDTO> responseEntity = restTemplate.exchange("/api/authority/"+FALSE_AUTHORITY_ID,HttpMethod.PUT, request,AuthorityDTO.class);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	
	@Test
	public void testUpdate_GivenExistingName() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("role", DB_AUTHORITY_ROLE_EXISTING2);
		HttpEntity<Object> request = new HttpEntity<Object>(jsonObject.toString(), headers);
		ResponseEntity<AuthorityDTO> responseEntity = restTemplate.exchange("/api/authority/"+DB_AUTHORITY_ID,HttpMethod.PUT, request,AuthorityDTO.class);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	@Test
    @Transactional
    @Rollback(true)
    public void testDelete() throws Exception {
		int size = authorityService.findAll().size();
		HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.AUTHORIZATION, accessToken);
			HttpEntity<Object> request = new HttpEntity<Object>(headers);
			
	        ResponseEntity<Void> responseEntity =
	                restTemplate.exchange("/api/authority/" + NEW_AUTHORITY_ID,
	                        HttpMethod.DELETE, request, Void.class);

	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	        assertEquals(size - 1, authorityService.findAll().size());
	}
	
	
	@Test
    public void testDelete_GivenFalseId() throws Exception {
		HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.AUTHORIZATION, accessToken);
			HttpEntity<Object> request = new HttpEntity<Object>(headers);
			
	        ResponseEntity<Void> responseEntity =
	                restTemplate.exchange("/api/authority/" + FALSE_AUTHORITY_ID,
	                        HttpMethod.DELETE, request, Void.class);

	        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	 }
	 
}

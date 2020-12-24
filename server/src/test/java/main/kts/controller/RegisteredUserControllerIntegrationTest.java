package main.kts.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
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

import main.kts.dto.AdminDTO;
import main.kts.dto.RegisteredUserDTO;
import main.kts.dto.UserLoginDTO;
import main.kts.dto.UserTokenStateDTO;
import main.kts.model.Admin;
import main.kts.model.RegisteredUser;
import main.kts.service.RegisteredUserService;

import static main.kts.constants.AdminConstants.*;
import static main.kts.constants.RegisteredUserConstants.*;

import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class RegisteredUserControllerIntegrationTest {
	
	 @Autowired
	 private TestRestTemplate restTemplate;

	 @Autowired
	 private RegisteredUserService service;
	 
	 private String accessToken;

	 
	 public void login() {
		 ResponseEntity<UserTokenStateDTO> responseEntity = 
				 restTemplate.postForEntity("/auth/log-in",
	             new UserLoginDTO(ADMIN_EMAIL_LOGIN, 
	            		 ADMIN_PASSWORD_LOGIN), UserTokenStateDTO.class);
	 
		 accessToken = responseEntity.getBody().getAccessToken();
		 
	 }
	 public void login1() {
		 ResponseEntity<UserTokenStateDTO> responseEntity = 
				 restTemplate.postForEntity("/auth/log-in",
	             new UserLoginDTO(REGISTERED_USER_EMAIL_LOGIN, 
	            		 REGISTERED_USER_PASSWORD_LOGIN), UserTokenStateDTO.class);
	 
		 accessToken = responseEntity.getBody().getAccessToken();
		 
	 }
	 
	 @Test
	 public void testGetAllRegisteredUsers() {
		login();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
	    HttpEntity<Object> request = new HttpEntity<Object>(headers);
	  
	    
	    ResponseEntity<List<RegisteredUserDTO>> responseEntity = restTemplate.exchange("/api/registered_user", 
	    		HttpMethod.GET, request, new ParameterizedTypeReference<List<RegisteredUserDTO>>() {});
	    List<RegisteredUserDTO> users =   responseEntity.getBody();
	   
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(DB_REGISTERED_USER_SIZE, users.size());
        assertEquals(DB_REGISTERED_USER_ID, users.get(0).getId());
        assertEquals(DB_REGISTERED_USER_ID1, users.get(1).getId());
  
	 }
	 @Test
	 public void testGetAllInterestedUsers() {
		login();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
	    HttpEntity<Object> request = new HttpEntity<Object>(headers);
	  
	    ResponseEntity<Integer> responseEntity = restTemplate.exchange("/api/registered_user/interested/"+ DB_REGISTERED_USER_CO, 
	    		HttpMethod.GET, request, Integer.class);
	    Integer users =   responseEntity.getBody();
	   
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(new Integer(DB_REGISTERED_USER_CO_SIZE), users);
	 }
	 @Test
	 public void testGetAllInterestedUsers_NotOK() {
		login();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
	    HttpEntity<Object> request = new HttpEntity<Object>(headers);
	  
	    
	    ResponseEntity<Integer> responseEntity = restTemplate.exchange("/api/registered_user/interested/"+ DB_REGISTERED_USER_CO3, 
	    		HttpMethod.GET, request, Integer.class);
	   
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	 }
	 
	 @Test
	 @Transactional
	 @Rollback(true)
	 public void testUpdateRegisteredUser() throws Exception {
    	login();
	    HttpHeaders headers = new HttpHeaders();
	    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
	    headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
	    RegisteredUserDTO userDto= new RegisteredUserDTO(
	    		NEW_REGISTERED_USER_FIRST_NAME,
	    		NEW_REGISTERED_USER_LAST_NAME,
	    		NEW_REGISTERED_USER_EMAIL,
	    		NEW_REGISTERED_USER_PASSWORD,
	    		NEW_REGISTERED_USER_IMAGE);
	    	    
	    HttpEntity<RegisteredUserDTO> request = new HttpEntity<RegisteredUserDTO>(userDto,headers);
	    
	    ResponseEntity<RegisteredUserDTO> responseEntity = restTemplate.exchange("/api/registered_user/4", 
	    		HttpMethod.PUT, request, RegisteredUserDTO.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        
        //from db
        RegisteredUser userdb = service.findOne(4L);
        assertEquals(NEW_REGISTERED_USER_FIRST_NAME, userdb.getFirstName());
       
	 }
	 @Test
	 public void testUpdateRegisteredUser_NotOK() throws Exception {
    	login();
	    HttpHeaders headers = new HttpHeaders();
	    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
	    headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
	    RegisteredUserDTO userDto= new RegisteredUserDTO(
	    		NEW_REGISTERED_USER_FIRST_NAME,
	    		NEW_REGISTERED_USER_LAST_NAME,
	    		NEW_REGISTERED_USER_EMAIL,
	    		NEW_REGISTERED_USER_PASSWORD,
	    		NEW_REGISTERED_USER_IMAGE);
	    	    
	    HttpEntity<RegisteredUserDTO> request = new HttpEntity<RegisteredUserDTO>(userDto,headers);
	    
	    ResponseEntity<RegisteredUserDTO> responseEntity = restTemplate.exchange("/api/registered_user/76", 
	    		HttpMethod.PUT, request, RegisteredUserDTO.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
       
	 }
	 @Test
	 public void testUpdateRegisteredUser_NotOK1() throws Exception {
    	login();
	    HttpHeaders headers = new HttpHeaders();
	    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
	    headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
	    RegisteredUserDTO userDto= new RegisteredUserDTO(
	    		NEW_REGISTERED_USER_FIRST_NAME,
	    		NEW_REGISTERED_USER_LAST_NAME,
	    		NEW_REGISTERED_USER_EMAIL123,
	    		NEW_REGISTERED_USER_PASSWORD,
	    		NEW_REGISTERED_USER_IMAGE);
	    	    
	    HttpEntity<RegisteredUserDTO> request = new HttpEntity<RegisteredUserDTO>(userDto,headers);
	    
	    ResponseEntity<RegisteredUserDTO> responseEntity = restTemplate.exchange("/api/registered_user/4", 
	    		HttpMethod.PUT, request, RegisteredUserDTO.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	 }
	 @Test
	 public void testUpdateRegisteredUser_NotOK2() throws Exception {
    	login();
	    HttpHeaders headers = new HttpHeaders();
	    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
	    headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
	    RegisteredUserDTO userDto= new RegisteredUserDTO(
	    		NEW_REGISTERED_USER_EMPTY,
	    		NEW_REGISTERED_USER_NULL,
	    		NEW_REGISTERED_USER_EMAIL,
	    		NEW_REGISTERED_USER_PASSWORD,
	    		NEW_REGISTERED_USER_IMAGE);
	    	    
	    HttpEntity<RegisteredUserDTO> request = new HttpEntity<RegisteredUserDTO>(userDto,headers);
	    
	    ResponseEntity<RegisteredUserDTO> responseEntity = restTemplate.exchange("/api/registered_user/4", 
	    		HttpMethod.PUT, request, RegisteredUserDTO.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
       
	 }
	 
	 @Test
	 @Transactional
	 @Rollback(true)
	 public void testDeleteRegisteredUser() throws Exception {
		login();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken); 	   
		
	    HttpEntity<Object> request = new HttpEntity<Object>(headers);
	    
	    ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/registered_user/5", 
	    		HttpMethod.DELETE, request, Void.class);
	    //from db
        RegisteredUser deleted = service.findByIdRU(DB_REGISTERED_USER_ID1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertFalse(deleted.getActive());
      
         
	 }
	 @Test
	 public void testDeleteRegisteredUser_NotOk() throws Exception {
		login();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken); 	   
		
	    HttpEntity<Object> request = new HttpEntity<Object>(headers);
	    
	    ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/registered_user/90", 
	    		HttpMethod.DELETE, request, Void.class);
       
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
         
	 }
}

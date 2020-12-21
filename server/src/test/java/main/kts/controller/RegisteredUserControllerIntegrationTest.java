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
	 
//	 @Test
//	 public void testGetAllRegisteredUsers() {
//		login();
//		HttpHeaders headers = new HttpHeaders();
//		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
//	    HttpEntity<Object> request = new HttpEntity<Object>("",headers);
//	  
//	    
//	    ResponseEntity<List<RegisteredUserDTO>> responseEntity = restTemplate.exchange("/api/registered_user", 
//	    		HttpMethod.GET, request, new ParameterizedTypeReference<List<RegisteredUserDTO>>() {});
//	    List<RegisteredUserDTO> users =   responseEntity.getBody();
//	   
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(DB_REGISTERED_USER_SIZE, users.size());
//        assertEquals(DB_REGISTERED_USER_ID, users.get(0).getId());
//        assertEquals(DB_REGISTERED_USER_ID1, users.get(1).getId());
//  
//	 }
	 
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
        RegisteredUserDTO user = responseEntity.getBody();
   
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
       
	 }
	 
	 @Test
	 @Transactional
	 @Rollback(true)
	 public void testDeleteRegisteredUser() throws Exception {
		login();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken); 	   
		
	    HttpEntity<Object> request = new HttpEntity<Object>("",headers);
	    
	    ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/registered_user/5", 
	    		HttpMethod.DELETE, request, Void.class);
       
        RegisteredUser deleted = service.findByIdRU(DB_REGISTERED_USER_ID1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertFalse(deleted.getActive());
        
        //cleanup
//      deletedAdmin.setActive(true);
//      deletedAdmin = service.update(deletedAdmin,DB_ADMIN_ID_LAST); 
	 }
}

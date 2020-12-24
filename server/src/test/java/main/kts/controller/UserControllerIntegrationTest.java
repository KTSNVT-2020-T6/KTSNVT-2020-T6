package main.kts.controller;


import static main.kts.constants.UserConstants.*;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import main.kts.dto.UserDTO;
import main.kts.dto.UserLoginDTO;
import main.kts.dto.UserTokenStateDTO;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class UserControllerIntegrationTest {
	 @Autowired
	 private TestRestTemplate restTemplate;
		 	 
	 private static final ObjectMapper om = new ObjectMapper();
	 
	 private String accessToken;

	 public void login() {

		 ResponseEntity<UserTokenStateDTO> responseEntity = 
				 restTemplate.postForEntity("/auth/log-in",
	             new UserLoginDTO(ADMIN_EMAIL_LOGIN, 
	            		 ADMIN_PASSWORD_LOGIN), UserTokenStateDTO.class);
	 
		 accessToken = responseEntity.getBody().getAccessToken();
		
		 
	 }
	 private static void printJSON(Object object) {
	        String result;
	        try {
	            result = om.writerWithDefaultPrettyPrinter().writeValueAsString(object);
	            System.out.println(result);
	        } catch (JsonProcessingException e) {
	            e.printStackTrace();
	        }
	    }
	 public void login1() {
		 ResponseEntity<UserTokenStateDTO> responseEntity = 
				 restTemplate.postForEntity("/auth/log-in",
	             new UserLoginDTO(REGISTERED_USER_EMAIL_LOGIN, 
	            		 REGISTERED_USER_PASSWORD_LOGIN), UserTokenStateDTO.class);
	 
		 accessToken = responseEntity.getBody().getAccessToken();
	 }
	 
	 @Test
	 public void testGetAllUsers() throws Exception {
		login();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken); 	   
		
	    HttpEntity<Object> request = new HttpEntity<Object>("",headers);
	   
	    ResponseEntity<List<UserDTO>> responseEntity = restTemplate.exchange("/api/user", 
	    		HttpMethod.GET, request, new ParameterizedTypeReference<List<UserDTO>>() {});
        printJSON(responseEntity);
	    List<UserDTO> users =   responseEntity.getBody();
		   
	    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	    assertEquals(DB_SIZE, users.size());
        
	 }
	 
	 @Test
	 public void testGetAllUsers_NotAdmin() throws Exception {
		login1();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken); 	   
		
	    HttpEntity<Object> request = new HttpEntity<Object>(headers);
	   
	    ResponseEntity<Object> responseEntity = restTemplate.exchange("/api/user", 
	    		HttpMethod.GET, request, Object.class);
	    printJSON(responseEntity);
	    assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	          
	 }
	 
	 
	 @Test
	 public void testLoadUsersPage() throws Exception {
		login();
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken); 	   
		
	    HttpEntity<Object> request = new HttpEntity<Object>(pageable,headers);
	   
	    ResponseEntity<List<UserDTO>> responseEntity = restTemplate.exchange("/api/user", 
	    		HttpMethod.GET, request, new ParameterizedTypeReference<List<UserDTO>>() {});
        printJSON(responseEntity);
	    List<UserDTO> users =   responseEntity.getBody();
	   
	    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	    
	    assertEquals(DB_SIZE, users.size());
        assertEquals(DB_ADMIN_EMAIL, users.get(0).getEmail());
	    
	 }
 
	 @Test
	 public void testGetUserById() throws Exception {
		login();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken); 	   
		
	    HttpEntity<Object> request = new HttpEntity<Object>("",headers);
	   
	    ResponseEntity<UserDTO> responseEntity = restTemplate.exchange("/api/user/id/"+DB_ADMIN_ID, 
	    		HttpMethod.GET, request, UserDTO.class);
        printJSON(responseEntity);
	    UserDTO user = responseEntity.getBody();
		   
	    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	    assertEquals(DB_ADMIN_EMAIL, user.getEmail());
        
	 }
	 

	 @Test
	 public void testGetUserById_NotOK() throws Exception {
		login();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken); 	   
		
	    HttpEntity<Object> request = new HttpEntity<Object>("",headers);
	   
	    ResponseEntity<UserDTO> responseEntity = restTemplate.exchange("/api/user/id/"+NEW_ADMIN_ID_DOESNT_EXIST, 
	    		HttpMethod.GET, request, UserDTO.class);
          
	    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	  
	 }
	 
	 @Test
	 public void testGetUserByEmail() throws Exception {
		login();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken); 	   
		headers.add(HttpHeaders.CONTENT_TYPE, "text/plain");
		headers.add(HttpHeaders.ACCEPT_CHARSET, "ISO-8859-4");
		

	    HttpEntity<Object> request = new HttpEntity<Object>("",headers);
	   
	    ResponseEntity<UserDTO> responseEntity = restTemplate.exchange("/api/user/"+DB_REGISTERED_USER_EMAIL1, 
	    		HttpMethod.GET, request, UserDTO.class);
          
	    printJSON(responseEntity);
	    UserDTO user = responseEntity.getBody();
		   
	    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	    assertEquals(DB_REGISTERED_USER_EMAIL1, user.getEmail());
	  
	 }
	 @Test
	 public void testGetUserByEmail_NotOK() throws Exception {
		login();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken); 	   
		headers.add(HttpHeaders.CONTENT_TYPE, "text/plain");
		headers.add(HttpHeaders.ACCEPT_CHARSET, "ISO-8859-4");
		
	    HttpEntity<Object> request = new HttpEntity<Object>("",headers);
	  
	    ResponseEntity<UserDTO> responseEntity = restTemplate.exchange("/api/user/"+NEW_ADMIN_EMAIL_DOESNT_EXIST, 
	    		HttpMethod.GET, request, UserDTO.class);
        
	    printJSON(responseEntity);
	    		   
	    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	    
	 } 
	 
}

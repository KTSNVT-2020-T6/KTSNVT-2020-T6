package main.kts.controller;


import static main.kts.constants.UserConstants.*;
import static main.kts.constants.UserConstants.ADMIN_PASSWORD_LOGIN;
import static main.kts.constants.UserConstants.NEW_ADMIN_EMAIL;
import static main.kts.constants.UserConstants.NEW_ADMIN_EMAIL1;
import static main.kts.constants.UserConstants.NEW_ADMIN_EMAIL_DOESNT_EXIST;
import static main.kts.constants.UserConstants.NEW_ADMIN_FIRST_NAME;
import static main.kts.constants.UserConstants.NEW_ADMIN_FIRST_NAME1;
import static main.kts.constants.UserConstants.NEW_ADMIN_IMAGE;
import static main.kts.constants.UserConstants.NEW_ADMIN_IMAGE1;
import static main.kts.constants.UserConstants.NEW_ADMIN_LAST_NAME;
import static main.kts.constants.UserConstants.NEW_ADMIN_LAST_NAME1;
import static main.kts.constants.UserConstants.NEW_ADMIN_PASSWORD;
import static main.kts.constants.UserConstants.NEW_ADMIN_PASSWORD1;
import static main.kts.constants.UserConstants.NEW_REGISTERED_USER_EMAIL;
import static main.kts.constants.UserConstants.NEW_REGISTERED_USER_EMAIL1;
import static main.kts.constants.UserConstants.NEW_REGISTERED_USER_FIRST_NAME;
import static main.kts.constants.UserConstants.NEW_REGISTERED_USER_FIRST_NAME1;
import static main.kts.constants.UserConstants.NEW_REGISTERED_USER_IMAGE;
import static main.kts.constants.UserConstants.NEW_REGISTERED_USER_IMAGE1;
import static main.kts.constants.UserConstants.NEW_REGISTERED_USER_LAST_NAME;
import static main.kts.constants.UserConstants.NEW_REGISTERED_USER_LAST_NAME1;
import static main.kts.constants.UserConstants.NEW_REGISTERED_USER_PASSWORD;
import static main.kts.constants.UserConstants.NEW_REGISTERED_USER_PASSWORD1;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import main.kts.controller.AuthenticationController.PasswordChanger;
import main.kts.dto.AdminDTO;
import main.kts.dto.RegisteredUserDTO;
import main.kts.dto.UserDTO;
import main.kts.dto.UserLoginDTO;
import main.kts.dto.UserTokenStateDTO;
import main.kts.model.User;
import main.kts.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class AuthenticationControllerIntegrationTest {
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private UserService service;
	
	private String accessToken;
	private static final ObjectMapper om = new ObjectMapper();
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
	             new UserLoginDTO(DB_REGISTERED_USER_EMAIL_LOGIN,
	            		 REGISTERED_USER_PASSWORD_LOGIN), UserTokenStateDTO.class);
	 
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
	@Test
	public void testChangePasword_NotOk() {
		login1();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken); 	
		PasswordChanger passwordChanger = new PasswordChanger();
		passwordChanger.oldPassword = "asdf12";
		passwordChanger.newPassword = "novasifra";
		HttpEntity<Object> request = new HttpEntity<Object>(passwordChanger,headers);
		ResponseEntity<Object> responseEntity = restTemplate.postForEntity("/auth/change-password",request, Object.class);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	@Test
	@Transactional
	public void testCreateAuthenticationToken_OKCredecials() {

		 ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/log-in",
	                new UserLoginDTO(ADMIN_EMAIL_LOGIN, ADMIN_PASSWORD_LOGIN), UserTokenStateDTO.class);
		 assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	@Test
	public void testCreateAuthenticationToken_NotOKCredencials() {

		 ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/log-in",
	                new UserLoginDTO(NEW_ADMIN_EMAIL_DOESNT_EXIST, ADMIN_PASSWORD_LOGIN), UserTokenStateDTO.class);
		 assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
	}
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateAddAdmin_OK() {
        
		AdminDTO userDto= new AdminDTO(
		    		NEW_ADMIN_FIRST_NAME,
		    		NEW_ADMIN_LAST_NAME,
		    		NEW_ADMIN_EMAIL,
		    		NEW_ADMIN_PASSWORD,
		    		NEW_ADMIN_IMAGE);
		HttpEntity<Object> request = new HttpEntity<Object>(userDto);
		   
		ResponseEntity<AdminDTO> responseEntity = restTemplate.postForEntity("/auth/sign-up-admin",request, AdminDTO.class);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		
		User check = service.findByEmail(NEW_ADMIN_EMAIL);
		assertEquals(NEW_ADMIN_FIRST_NAME, check.getFirstName());
	}
	@Test
	@Transactional
	public void testCreateAddAdmin_NotOkEmailExists() {
        
		AdminDTO userDto= new AdminDTO(
		    		NEW_ADMIN_FIRST_NAME1,
		    		NEW_ADMIN_LAST_NAME1,
		    		NEW_ADMIN_EMAIL1,
		    		NEW_ADMIN_PASSWORD1,
		    		NEW_ADMIN_IMAGE1);
		HttpEntity<Object> request = new HttpEntity<Object>(userDto);
		   
		ResponseEntity<UserDTO> responseEntity = restTemplate.postForEntity("/auth/sign-up-admin",request, UserDTO.class);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	@Test
	@Transactional
	public void testCreateAddAdmin_NotOkEmailNotValid() {
        
		AdminDTO userDto= new AdminDTO(
		    		NEW_ADMIN_FIRST_NAME1,
		    		NEW_ADMIN_LAST_NAME1,
		    		NEW_ADMIN_EMAIL2,
		    		NEW_ADMIN_PASSWORD1,
		    		NEW_ADMIN_IMAGE1);
		HttpEntity<Object> request = new HttpEntity<Object>(userDto);
		   
		ResponseEntity<UserDTO> responseEntity = restTemplate.postForEntity("/auth/sign-up-admin",request, UserDTO.class);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	@Test
	@Transactional
	public void testCreateAddAdmin_NotOkEmptyNull() {
        
		AdminDTO userDto= new AdminDTO(
		    		NEW_ADMIN_EMPTY,
		    		NEW_ADMIN_LAST_NAME1,
		    		NEW_ADMIN_EMAIL2,
		    		NEW_ADMIN_NULL,
		    		NEW_ADMIN_IMAGE1);
		HttpEntity<Object> request = new HttpEntity<Object>(userDto);
		   
		ResponseEntity<UserDTO> responseEntity = restTemplate.postForEntity("/auth/sign-up-admin",request, UserDTO.class);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	
	@Test
	@Transactional
	public void testCreateAddRegisteredUser_NotOkEmailExists() {
		RegisteredUserDTO userDto= new RegisteredUserDTO(
	    		NEW_REGISTERED_USER_FIRST_NAME1,
	    		NEW_REGISTERED_USER_LAST_NAME1,
	    		NEW_REGISTERED_USER_EMAIL1,
	    		NEW_REGISTERED_USER_PASSWORD1,
	    		NEW_REGISTERED_USER_IMAGE1);
		HttpEntity<Object> request = new HttpEntity<Object>(userDto);
		
		ResponseEntity<UserDTO> responseEntity = restTemplate.postForEntity("/auth/sign-up",request, UserDTO.class);
		printJSON(responseEntity);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	@Test
	@Transactional
	public void testCreateAddRegisteredUser_NotOkEmailNotValid() {
		RegisteredUserDTO userDto= new RegisteredUserDTO(
	    		NEW_REGISTERED_USER_FIRST_NAME1,
	    		NEW_REGISTERED_USER_LAST_NAME1,
	    		NEW_REGISTERED_USER_EMAIL2,
	    		NEW_REGISTERED_USER_PASSWORD1,
	    		NEW_REGISTERED_USER_IMAGE1);
		HttpEntity<Object> request = new HttpEntity<Object>(userDto);
		
		ResponseEntity<UserDTO> responseEntity = restTemplate.postForEntity("/auth/sign-up",request, UserDTO.class);
		printJSON(responseEntity);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
//	@Test
//	@Transactional
//	@Rollback(true)
//	public void testRefreshAuthenticationToken_OK() {
//		
//        login1();
//        HttpHeaders headers = new HttpHeaders();
//		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken); 	   
//		
//	    HttpEntity<Object> request = new HttpEntity<Object>(headers);
//		ResponseEntity<UserDTO> responseEntity = restTemplate.postForEntity("/auth/refresh",request, UserDTO.class);
//		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//	}
	@Test
	public void testRefreshAuthenticationToken_NotOK() {
       
		HttpEntity<Object> request = new HttpEntity<Object>(null);
		ResponseEntity<UserDTO> responseEntity = restTemplate.postForEntity("/auth/refresh",request, UserDTO.class);
		assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testChangePasword() {

		login();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken); 	
		PasswordChanger passwordChanger = new PasswordChanger();
		passwordChanger.oldPassword = "asdf";
		passwordChanger.newPassword = "novasifra";
		HttpEntity<Object> request = new HttpEntity<Object>(passwordChanger,headers);
		ResponseEntity<UserDTO> responseEntity = restTemplate.postForEntity("/auth/change-password",request, UserDTO.class);
		assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
	}
	
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateAddRegisteredUser_OK() {
        
		RegisteredUserDTO userDto= new RegisteredUserDTO(
		    		NEW_REGISTERED_USER_FIRST_NAME,
		    		NEW_REGISTERED_USER_LAST_NAME,
		    		NEW_REGISTERED_USER_EMAIL,
		    		NEW_REGISTERED_USER_PASSWORD,
		    		NEW_REGISTERED_USER_IMAGE);
		HttpEntity<Object> request = new HttpEntity<Object>(userDto);
		   
		ResponseEntity<UserDTO> responseEntity = restTemplate.postForEntity("/auth/sign-up",request, UserDTO.class);
		printJSON(responseEntity);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		User check = service.findByEmail(NEW_REGISTERED_USER_EMAIL);
		assertEquals(NEW_REGISTERED_USER_FIRST_NAME, check.getFirstName());
	}


}

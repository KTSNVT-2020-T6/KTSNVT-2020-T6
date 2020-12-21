package main.kts.controller;


import static main.kts.constants.AdminConstants.*;
import static main.kts.constants.AdminConstants.ADMIN_PASSWORD_LOGIN;
import static main.kts.constants.AdminConstants.DB_ADMIN_ID;
import static main.kts.constants.AdminConstants.DB_ADMIN_ID1;
import static main.kts.constants.AdminConstants.DB_ADMIN_SIZE;
import static main.kts.constants.AdminConstants.NEW_ADMIN_EMAIL;
import static main.kts.constants.AdminConstants.NEW_ADMIN_FIRST_NAME;
import static main.kts.constants.AdminConstants.NEW_ADMIN_IMAGE;
import static main.kts.constants.AdminConstants.NEW_ADMIN_LAST_NAME;
import static main.kts.constants.AdminConstants.NEW_ADMIN_PASSWORD;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import main.kts.Application;
import main.kts.dto.AdminDTO;
import main.kts.dto.UserLoginDTO;
import main.kts.dto.UserTokenStateDTO;
import main.kts.model.Admin;
import main.kts.service.AdminService;
import main.kts.service.ImageService;
import net.minidev.json.parser.JSONParser;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class AdminControllerIntegrationTest {
	 // komunikacija sa klijentom i sa servisima
	
	 //on simulira ponasanje web klijenta
	 @Autowired
	 private TestRestTemplate restTemplate;

	 @Autowired
	 private AdminService service;
	 
	 @Autowired
	 private ImageService imageService;
	
	 //private String accessToken;
	
	 private String accessToken;
	
	 @Before
	 public void login() {
		 ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/log-in",
	                new UserLoginDTO(ADMIN_EMAIL_LOGIN, ADMIN_PASSWORD_LOGIN), UserTokenStateDTO.class);
		 accessToken = responseEntity.getBody().getAccessToken();
		 
	 }
//	 
//	 @Test
//	
//	 public void testGetAllAdmins() {
//		HttpHeaders headers = new HttpHeaders();
//		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
//	    HttpEntity<Object> request = new HttpEntity<Object>("",headers);
//	  
//	    
//	    ResponseEntity<List<AdminDTO>> responseEntity = restTemplate.exchange("/api/admin", 
//	    		HttpMethod.GET, request, new ParameterizedTypeReference<List<AdminDTO>>() {});
//	    List<AdminDTO> admins =   responseEntity.getBody();
//	   
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(DB_ADMIN_SIZE, admins.size());
//        assertEquals(DB_ADMIN_ID, admins.get(0).getId());
//        assertEquals(DB_ADMIN_ID1, admins.get(1).getId());
//  
//	 }
	 @Test
	 @Transactional
     @Rollback(true)
	 public void testUpdateAdmin() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
	    AdminDTO adminDto= new AdminDTO(NEW_ADMIN_FIRST_NAME,NEW_ADMIN_LAST_NAME,
	    		NEW_ADMIN_EMAIL,NEW_ADMIN_PASSWORD, NEW_ADMIN_IMAGE);
	    
	    HttpEntity<AdminDTO> request = new HttpEntity<AdminDTO>(adminDto,headers);
	    
	    ResponseEntity<AdminDTO> responseEntity = restTemplate.exchange("/api/admin/2", 
	    		HttpMethod.PUT, request, AdminDTO.class);
        AdminDTO admin = responseEntity.getBody();
        
        /////////////////////////////
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(admin);
        assertEquals(DB_ADMIN_ID1, admin.getId());
        assertEquals(NEW_ADMIN_FIRST_NAME, admin.getFirstName());
        
        //provera u bazi
        Admin dbAdmin = service.findOne(DB_ADMIN_ID1);
        assertEquals(DB_ADMIN_ID1, dbAdmin.getId());
        assertEquals(NEW_ADMIN_FIRST_NAME, dbAdmin.getFirstName());

        //cleanup

//        dbAdmin.setFirstName(DB_ADMIN_FIRST_NAME);
//        dbAdmin.setLastName(DB_ADMIN_LAST_NAME);
//        dbAdmin.setEmail(DB_ADMIN_EMAIL);
//        dbAdmin.setPassword(DB_ADMIN_PASSWORD);
//        Image image = imageService.findOne(DB_ADMIN_IMAGE);
//        dbAdmin.setImage(image);
//        service.update(dbAdmin, DB_ADMIN_ID);
       
	 }

	 
	 @Test
	 @Transactional
	 @Rollback(true)
	 public void testDeleteAdmin() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken); 	   
		System.out.println("nije mi dor"+accessToken);
	    HttpEntity<Object> request = new HttpEntity<Object>("",headers);
	    
	    ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/admin/7", 
	    		HttpMethod.DELETE, request, Void.class);
       
        Admin deletedAdmin = service.findOneChecker(DB_ADMIN_ID_LAST);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertFalse(deletedAdmin.getActive());
        
        //cleanup
//      deletedAdmin.setActive(true);
//      deletedAdmin = service.update(deletedAdmin,DB_ADMIN_ID_LAST); 
	 }
	 
	
	

}

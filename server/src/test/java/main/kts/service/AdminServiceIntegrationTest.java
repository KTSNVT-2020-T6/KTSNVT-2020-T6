package main.kts.service;
import static main.kts.constants.AdminConstants.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import java.util.List;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import main.kts.model.Admin;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class AdminServiceIntegrationTest {
	
	@Autowired
	private AdminService service;
	
	@Test
	public void testFindAll() throws Exception {
		List<Admin> foundAdmins = service.findAll();
		assertEquals(DB_ADMIN_SIZE, foundAdmins.size()); 
    }
	
	@Test
	public void testFindOne() throws Exception {
		Admin foundAdmin = service.findOne(DB_ADMIN_ID);
		assertEquals(DB_ADMIN_ID, foundAdmin.getId());
    }
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCreate() throws Exception {
		Admin newAdmin = new Admin(NEW_ADMIN_FIRST_NAME, 
				NEW_ADMIN_LAST_NAME,
				NEW_ADMIN_EMAIL,
				NEW_ADMIN_PASSWORD,
				NEW_ADMIN_ACTIVE,
				NEW_ADMIN_VERIFIED);
		
		Admin createdAdmin = service.create(newAdmin);
		
		assertEquals(NEW_ADMIN_FIRST_NAME, createdAdmin.getFirstName());
		assertEquals(NEW_ADMIN_LAST_NAME, createdAdmin.getLastName());
		assertEquals(NEW_ADMIN_EMAIL, createdAdmin.getEmail());
	    assertEquals(NEW_ADMIN_VERIFIED, createdAdmin.getActive());
	    
	    //cleanup, za fizicko pokretanje nema potrebe, podaci za testiranje su razliciti  
    }
	
	@Test
	public void testCreate_ExistingGmail() throws Exception {
		Throwable exception = assertThrows(
	            Exception.class, () -> {
	            	Admin newAdmin = new Admin(NEW_ADMIN_FIRST_NAME, 
	        				NEW_ADMIN_LAST_NAME,
	        				DB_ADMIN_EMAIL,
	        				NEW_ADMIN_PASSWORD,
	        				NEW_ADMIN_ACTIVE,
	        				NEW_ADMIN_VERIFIED);
	        		
	        		Admin createdAdmin = service.create(newAdmin);
	        		//assertNull(createdAdmin);
	            }
	    );
	    assertEquals("Admin with given email already exist", exception.getMessage());
    }
	
	
	@Test
	public void testUpdate() throws Exception {
		Admin updateAdmin = new Admin(NEW_ADMIN_FIRST_NAME1, 
				NEW_ADMIN_LAST_NAME1,
				NEW_ADMIN_EMAILL1,
				NEW_ADMIN_PASSWORD1,
				NEW_ADMIN_ACTIVE1,
				NEW_ADMIN_VERIFIED1);
		
		Admin saveForCleanUp = service.findOne(DB_ADMIN_ID1);
		Admin updatedAdmin = service.update(updateAdmin, DB_ADMIN_ID1);
		assertEquals(DB_ADMIN_ID1, updatedAdmin.getId());
		assertEquals(NEW_ADMIN_FIRST_NAME1, updatedAdmin.getFirstName());
		assertEquals(NEW_ADMIN_LAST_NAME1, updatedAdmin.getLastName());
		assertEquals(NEW_ADMIN_EMAILL1, updatedAdmin.getEmail());      
		
		//cleanup
		saveForCleanUp = service.update(saveForCleanUp, DB_ADMIN_ID1);
    }
	@Test
	public void testUpdate_ExistingGmail() throws Exception {
		Throwable exception = assertThrows(
	            Exception.class, () -> {
	            	Admin updateAdmin = new Admin(NEW_ADMIN_FIRST_NAME1, 
	        				NEW_ADMIN_LAST_NAME1,
	        				DB_ADMIN_EMAIL,
	        				NEW_ADMIN_PASSWORD1,
	        				NEW_ADMIN_ACTIVE1,
	        				NEW_ADMIN_VERIFIED1);
	        		
	        		Admin updatedAdmin = service.update(updateAdmin, DB_ADMIN_ID1);
	        		//assertNull(updatedAdmin);
	            }
	    );
	    assertEquals("User with given email already exist", exception.getMessage());
    }
	@Test
	public void testUpdate_NonExistingId() throws Exception {
		Throwable exception = assertThrows(
	            Exception.class, () -> {
	            	Admin updateAdmin = new Admin(NEW_ADMIN_FIRST_NAME1, 
	        				NEW_ADMIN_LAST_NAME1,
	        				NEW_ADMIN_EMAILL1,
	        				NEW_ADMIN_PASSWORD1,
	        				NEW_ADMIN_ACTIVE1,
	        				NEW_ADMIN_VERIFIED1);
	        		
	        		Admin updatedAdmin = service.update(updateAdmin, NEW_ADMIN_ID1);
	        		//assertNull(updatedAdmin);
	            }
	    );
	    assertEquals("Admin with given id doesn't exist", exception.getMessage());
    }
	
	@Test
	public void testDelete() throws Exception {
		service.delete(DB_ADMIN_ID);
        Admin checkAdmin = service.findOneChecker(DB_ADMIN_ID);
        assertFalse(checkAdmin.getActive());    
        //cleanup 
        checkAdmin.setActive(true);
        checkAdmin = service.update(checkAdmin, DB_ADMIN_ID);
    }
	
	@Test
	public void testDelete_NonExistingId() throws Exception {
		Throwable exception = assertThrows(
	            Exception.class, () -> {
	            	service.delete(NEW_ADMIN_ID1);
	            }
	    );
	    assertEquals("Admin doesn't exist", exception.getMessage());
    }

}

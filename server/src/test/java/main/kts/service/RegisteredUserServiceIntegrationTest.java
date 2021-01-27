package main.kts.service;

import static main.kts.constants.RegisteredUserConstants.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import main.kts.model.CulturalOffer;
import main.kts.model.RegisteredUser;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class RegisteredUserServiceIntegrationTest {

	@Autowired
	private RegisteredUserService service;

	@Test
	public void testFindAll() throws Exception {
		List<RegisteredUser> foundRegUsers = service.findAll();
		assertEquals(DB_REGISTERED_USER_SIZE, foundRegUsers.size()); 
    }
	
	@Test
	public void testFindOne() throws Exception {
		RegisteredUser foundRU = service.findOne(DB_REGISTERED_USER_ID);
		assertEquals(DB_REGISTERED_USER_ID, foundRU.getId());
    }
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCreate() throws Exception {
		RegisteredUser newRU= new RegisteredUser(NEW_REGISTERED_USER_FIRST_NAME, 
				NEW_REGISTERED_USER_LAST_NAME,
				NEW_REGISTERED_USER_EMAIL,
				NEW_REGISTERED_USER_PASSWORD,
				NEW_REGISTERED_USER_ACTIVE,
				NEW_REGISTERED_USER_VERIFIED);
		
		RegisteredUser createdRU = service.create(newRU);
		
		assertEquals(NEW_REGISTERED_USER_FIRST_NAME, createdRU.getFirstName());
		assertEquals(NEW_REGISTERED_USER_LAST_NAME, createdRU.getLastName());
		assertEquals(NEW_REGISTERED_USER_EMAIL, createdRU.getEmail());
	    assertEquals(NEW_REGISTERED_USER_VERIFIED, createdRU.getActive()); 
    }
	
	@Test
	public void testCreate_ExistingGmail() throws Exception {
		Throwable exception = assertThrows(
	            Exception.class, () -> {
	            	RegisteredUser newRU = new RegisteredUser(NEW_REGISTERED_USER_FIRST_NAME, 
	        				NEW_REGISTERED_USER_LAST_NAME,
	        				DB_REGISTERED_USER_EMAIL,
	        				NEW_REGISTERED_USER_PASSWORD,
	        				NEW_REGISTERED_USER_ACTIVE,
	        				NEW_REGISTERED_USER_VERIFIED);
	        		
	            	RegisteredUser created = service.create(newRU);
	        		//assertNull(createdRegisteredUser);
	            }
	    );
	    assertEquals("User is already registered", exception.getMessage());
    }
	
	@Test
	public void testUpdate() throws Exception {
		RegisteredUser updateRegisteredUser = new RegisteredUser(NEW_REGISTERED_USER_FIRST_NAME1, 
				NEW_REGISTERED_USER_LAST_NAME1,
				NEW_REGISTERED_USER_EMAILL1,
				NEW_REGISTERED_USER_PASSWORD1,
				NEW_REGISTERED_USER_ACTIVE1,
				NEW_REGISTERED_USER_VERIFIED1);
		
		RegisteredUser saveForCleanUp = service.findOne(DB_REGISTERED_USER_ID1);
		RegisteredUser updatedRegisteredUser = service.update(updateRegisteredUser, DB_REGISTERED_USER_ID1);
		assertEquals(DB_REGISTERED_USER_ID1, updatedRegisteredUser.getId());
		assertEquals(NEW_REGISTERED_USER_FIRST_NAME1, updatedRegisteredUser.getFirstName());
		assertEquals(NEW_REGISTERED_USER_LAST_NAME1, updatedRegisteredUser.getLastName());
		assertEquals(NEW_REGISTERED_USER_EMAILL1, updatedRegisteredUser.getEmail());      
		
		//cleanup
		saveForCleanUp = service.update(saveForCleanUp, DB_REGISTERED_USER_ID1);
    }
	@Test
	public void testUpdate_ExistingGmail() throws Exception {
		Throwable exception = assertThrows(
	            Exception.class, () -> {
	            	RegisteredUser updateRegisteredUser = new RegisteredUser(NEW_REGISTERED_USER_FIRST_NAME1, 
	        				NEW_REGISTERED_USER_LAST_NAME1,
	        				DB_REGISTERED_USER_EMAIL,
	        				NEW_REGISTERED_USER_PASSWORD1,
	        				NEW_REGISTERED_USER_ACTIVE1,
	        				NEW_REGISTERED_USER_VERIFIED1);
	        		
	        		RegisteredUser updatedRegisteredUser = service.update(updateRegisteredUser, DB_REGISTERED_USER_ID1);
	        		//assertNull(updatedRegisteredUser);
	            }
	    );
	    assertEquals("User with given email already exist", exception.getMessage());
    }
	@Test
	public void testUpdate_NonExistingId() throws Exception {
		Throwable exception = assertThrows(
	            Exception.class, () -> {
	            	RegisteredUser updateRegisteredUser = new RegisteredUser(NEW_REGISTERED_USER_FIRST_NAME1, 
	        				NEW_REGISTERED_USER_LAST_NAME1,
	        				NEW_REGISTERED_USER_EMAILL1,
	        				NEW_REGISTERED_USER_PASSWORD1,
	        				NEW_REGISTERED_USER_ACTIVE1,
	        				NEW_REGISTERED_USER_VERIFIED1);
	        		
	        		RegisteredUser updatedRegisteredUser = service.update(updateRegisteredUser, NEW_REGISTERED_USER_ID1);
	        		//assertNull(updatedRegisteredUser);
	            }
	    );
	    assertEquals("User doesn't registered", exception.getMessage());
    }
	
	@Test
	public void testDelete() throws Exception {
		service.delete(DB_REGISTERED_USER_ID);
        RegisteredUser checkRegisteredUser = service.findByIdRU(DB_REGISTERED_USER_ID);
        assertFalse(checkRegisteredUser.getActive());    
        //cleanup 
        checkRegisteredUser.setActive(true);
        checkRegisteredUser = service.update(checkRegisteredUser, DB_REGISTERED_USER_ID);
    }
	
	@Test
	public void testDelete_NonExistingId() throws Exception {
		Throwable exception = assertThrows(
	            Exception.class, () -> {
	            	service.delete(NEW_REGISTERED_USER_ID1);
	            }
	    );
	    assertEquals("Registered user doesn't exist", exception.getMessage());
    }
	@Test
	@Rollback(true)
	@Transactional
	@WithMockUser(username = REGISTERED_USER_EMAIL_LOGIN, password = REGISTERED_USER_PASSWORD_LOGIN,
	roles = "REGISTERED_USER")
	public void testSubscribeUser() throws Exception {

		service.subscribeUser(DB_REGISTERED_USER_CO);
		List<CulturalOffer> found = service.findAllSubscribedCO(DB_REGISTERED_USER_ID1);
		assertEquals(DB_REGISTERED_USER_CO_SIZE, found.size());
    }
	
	@Test
	@Rollback(true)
	@Transactional
	@WithMockUser(username = REGISTERED_USER_EMAIL_LOGIN, password = REGISTERED_USER_PASSWORD_LOGIN,
	roles = "REGISTERED_USER")
	public void testUnsubscribeUser() throws Exception {

		service.unsubscribeUser(DB_REGISTERED_USER_CO1);
		List<CulturalOffer> found = service.findAllSubscribedCO(DB_REGISTERED_USER_ID1);
		assertEquals(1, found.size());
    }
	
	@Test
	public void testfindByIdCO() throws Exception {
		List<Long> found = service.findByIdCO(DB_REGISTERED_USER_CO);
		assertEquals(DB_REGISTERED_USER_CO_SIZE, found.size());
    }
	@Test
	public void testfindAllSubscribedCO() throws Exception {
		List<CulturalOffer> found = service.findAllSubscribedCO(DB_REGISTERED_USER_ID);
		assertEquals(DB_REGISTERED_USER_SUBS_CO, found.size());
    }
	@Test
	public void testfindByIdRU() throws Exception {
		RegisteredUser found = service.findByIdRU(DB_REGISTERED_USER_ID_DoesntActive);
		assertNotEquals(null, found); 
    }

}

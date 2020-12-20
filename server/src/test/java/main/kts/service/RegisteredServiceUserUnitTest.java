package main.kts.service;


import static main.kts.constants.RegisteredUserConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import main.kts.model.RegisteredUser;
import main.kts.repository.RegisteredUserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class RegisteredServiceUserUnitTest {
	@Autowired
	private RegisteredUserService service;
	@MockBean
	private RegisteredUserRepository repository;

	
	@Before
	public void setup() {
		RegisteredUser userFound = new RegisteredUser(DB_REGISTERED_USER_ID, DB_REGISTERED_USER_FIRST_NAME,
				DB_REGISTERED_USER_LAST_NAME,DB_REGISTERED_USER_EMAIL,DB_REGISTERED_USER_PASSWORD,
				 DB_REGISTERED_USER_ACTIVE,DB_REGISTERED_USER_VERIFIED);
		 
	    given(repository.findById(DB_REGISTERED_USER_ID)).willReturn(java.util.Optional.of(userFound));
	   
	    RegisteredUser savedRUser= new RegisteredUser( NEW_REGISTERED_USER_FIRST_NAME,
				NEW_REGISTERED_USER_LAST_NAME,NEW_REGISTERED_USER_EMAIL,NEW_REGISTERED_USER_PASSWORD,
				 NEW_REGISTERED_USER_ACTIVE,NEW_REGISTERED_USER_VERIFIED);
	    savedRUser.setId(DB_REGISTERED_USER_ID);
	    //find by email i save
	    given(repository.findByEmail(NEW_REGISTERED_USER_EMAIL)).willReturn(null);
	    when(repository.save(any(RegisteredUser.class))).thenReturn(savedRUser);

	    RegisteredUser alreadyExists = new RegisteredUser(DB_REGISTERED_USER_ID, DB_REGISTERED_USER_FIRST_NAME,
				DB_REGISTERED_USER_LAST_NAME,DB_REGISTERED_USER_EMAIL,DB_REGISTERED_USER_PASSWORD,
				 DB_REGISTERED_USER_ACTIVE,DB_REGISTERED_USER_VERIFIED);
	    given(repository.findByEmail(DB_REGISTERED_USER_EMAIL1)).willReturn(alreadyExists);
        doNothing().when(repository).delete(savedRUser);
	    
	}
	
	@Test
    public void testUpdate() throws Exception {

	    RegisteredUser user= new RegisteredUser( NEW_REGISTERED_USER_FIRST_NAME,
				NEW_REGISTERED_USER_LAST_NAME,NEW_REGISTERED_USER_EMAIL,NEW_REGISTERED_USER_PASSWORD,
				 NEW_REGISTERED_USER_ACTIVE,NEW_REGISTERED_USER_VERIFIED);
		
        RegisteredUser updated = service.update(user,DB_REGISTERED_USER_ID);

        verify(repository, times(1)).findById(DB_REGISTERED_USER_ID);
        verify(repository, times(1)).findByEmail(NEW_REGISTERED_USER_EMAIL);
      
        assertEquals(new Long(1L), updated.getId());
        assertEquals(NEW_REGISTERED_USER_FIRST_NAME, updated.getFirstName());
    }
	@Test
    public void testUpdate_EmailExists() throws Exception {
		Throwable exception = assertThrows(
	            Exception.class, () -> {
	            	RegisteredUser user= new RegisteredUser(NEW_REGISTERED_USER_FIRST_NAME,
	         				NEW_REGISTERED_USER_LAST_NAME,NEW_REGISTERED_USER_EMAIL1,NEW_REGISTERED_USER_PASSWORD,
	         				 NEW_REGISTERED_USER_ACTIVE,NEW_REGISTERED_USER_VERIFIED);
	       		
	            	RegisteredUser updated = service.update(user,DB_REGISTERED_USER_ID);

	            }
	    );
		verify(repository, times(1)).findById(DB_REGISTERED_USER_ID);
	    verify(repository, times(1)).findByEmail(NEW_REGISTERED_USER_EMAIL1);
	    assertEquals("User with given email already exist", exception.getMessage());
    }
	@Test
    public void testDelete() throws Exception {
		service.delete(DB_REGISTERED_USER_ID);
	
        verify(repository, times(1)).findById(DB_REGISTERED_USER_ID);
    }
	@Test
    public void testDelete_IdDoesntExists() throws Exception {
		Throwable exception = assertThrows(
	            Exception.class, () -> {
	            	service.delete(NEW_REGISTERED_USER_ID1);
	            }
	    );
	    assertEquals("Registered user doesn't exist.", exception.getMessage());
    }
}

package main.kts.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;


import static main.kts.constants.AdminConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;



import static org.mockito.ArgumentMatchers.any;
import main.kts.model.Admin;
import main.kts.repository.AdminRepository;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class AdminServiceUnitTest {

	@Autowired
	private AdminService service;
	
	@MockBean
	private AdminRepository repository;
	
	@Before
	public void setup() {
		Admin adminFound = new Admin(DB_ADMIN_ID, DB_ADMIN_FIRST_NAME, DB_ADMIN_LAST_NAME,DB_ADMIN_EMAIL,DB_ADMIN_PASSWORD,
				 DB_ADMIN_ACTIVE,DB_ADMIN_VERIFIED);
		 
	    given(repository.findById(DB_ADMIN_ID)).willReturn(java.util.Optional.of(adminFound));
	   
	    Admin savedAdmin = new Admin(NEW_ADMIN_FIRST_NAME, NEW_ADMIN_LAST_NAME,NEW_ADMIN_EMAIL,NEW_ADMIN_PASSWORD,
				 NEW_ADMIN_ACTIVE,NEW_ADMIN_VERIFIED);
	    savedAdmin.setId(DB_ADMIN_ID);
	    //find by email i save
	    given(repository.findByEmail(NEW_ADMIN_EMAIL)).willReturn(null);
	    when(repository.save(any(Admin.class))).thenReturn(savedAdmin);

	    Admin alreadyExists = new Admin(DB_ADMIN_ID1, DB_ADMIN_FIRST_NAME1, DB_ADMIN_LAST_NAME1,
	    		DB_ADMIN_EMAIL1,DB_ADMIN_PASSWORD1,
				 DB_ADMIN_ACTIVE1,DB_ADMIN_VERIFIED1);
	    given(repository.findByEmail(NEW_ADMIN_EMAIL1)).willReturn(alreadyExists);
        doNothing().when(repository).delete(savedAdmin);
	    
		 
	}
	@Test
    public void testUpdate() throws Exception {
		Admin admin = new Admin(NEW_ADMIN_FIRST_NAME, NEW_ADMIN_LAST_NAME,NEW_ADMIN_EMAIL,NEW_ADMIN_PASSWORD,
				 NEW_ADMIN_ACTIVE,NEW_ADMIN_VERIFIED);
		
        Admin updated = service.update(admin,DB_ADMIN_ID);

        verify(repository, times(1)).findById(DB_ADMIN_ID);
        verify(repository, times(1)).findByEmail(NEW_ADMIN_EMAIL);
      
        assertEquals(new Long(1L), updated.getId());
        assertEquals(NEW_ADMIN_FIRST_NAME, updated.getFirstName());
    }
	@Test
    public void testUpdate_EmailExists() throws Exception {
		Throwable exception = assertThrows(
	            Exception.class, () -> {
	            	Admin admin = new Admin(NEW_ADMIN_FIRST_NAME, NEW_ADMIN_LAST_NAME,NEW_ADMIN_EMAIL1,NEW_ADMIN_PASSWORD,
	       				 NEW_ADMIN_ACTIVE,NEW_ADMIN_VERIFIED);
	       		
	                Admin updated = service.update(admin,DB_ADMIN_ID);

	            }
	    );
		verify(repository, times(1)).findById(DB_ADMIN_ID);
	    verify(repository, times(1)).findByEmail(NEW_ADMIN_EMAIL1);
	    assertEquals("User with given email already exist", exception.getMessage());
    }
	@Test
    public void testDelete() throws Exception {
		service.delete(DB_ADMIN_ID);
	
        verify(repository, times(1)).findById(DB_ADMIN_ID);
    }
	@Test
    public void testDelete_IdDoesntExists() throws Exception {
		Throwable exception = assertThrows(
	            Exception.class, () -> {
	            	service.delete(NEW_ADMIN_ID1);
	            }
	    );
	    assertEquals("Admin doesn't exist", exception.getMessage());
    }


}

package main.kts.service;

import static main.kts.constants.UserConstants.*;
import static org.junit.Assert.assertEquals;

import java.util.List;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;


import main.kts.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class UserServiceIntegrationTest {
	
	@Autowired
	private UserService service;
	
	@Test
	public void testFindAll_OK() throws Exception {
		List<User> found = service.findAll();
		assertEquals(DB_SIZE, found.size()); 
    }
	@Test
	public void testFindOne_OK() throws Exception {
		User found = service.findOne(DB_ADMIN_ID);
		assertEquals(DB_ADMIN_ID, found.getId());
    }
	@Test
	public void testFindOne_DoesntExist() throws Exception {
		User found = service.findOne(NEW_ADMIN_ID_DOESNT_EXIST);
		assertEquals(null, found);
    }
	@Test
	public void testFindOne_NotActive() throws Exception {
		User found = service.findOne(DB_REGISTERED_USER_NOT_ACTIVE_ID);
		assertEquals(null, found);
    }

	@Test
    public void testFindByEmail_AdminOK() {
        User found = service.findByEmail(DB_ADMIN_EMAIL);
        assertEquals(DB_ADMIN_EMAIL, found.getEmail());
    } 
	@Test
    public void testFindByEmail_RegUserOK() {
        User found = service.findByEmail(DB_REGISTERED_USER_EMAIL);
        assertEquals(DB_REGISTERED_USER_EMAIL, found.getEmail());
    } 
	@Test
    public void testFindByEmail_NotActive() {
        User found = service.findByEmail(DB_REGISTERED_USER_NOT_ACTIVE_EMAIL);
        assertEquals(null, found);
    }
	@Test
    public void testFindByEmail_DoesntExist() {
        User found = service.findByEmail(NEW_ADMIN_EMAIL_DOESNT_EXIST);
        assertEquals(null, found);
    }


}

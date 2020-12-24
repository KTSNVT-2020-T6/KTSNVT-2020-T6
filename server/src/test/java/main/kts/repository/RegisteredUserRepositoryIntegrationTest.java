package main.kts.repository;

import static main.kts.constants.RegisteredUserConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import main.kts.model.RegisteredUser;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:test.properties")
public class RegisteredUserRepositoryIntegrationTest {
	
	@Autowired
	private RegisteredUserRepository repository;
	
	@Test
	public void testFindByIdAndActive() {
		Optional<RegisteredUser> foundRegisteredUser = repository.findByIdAndActive(DB_REGISTERED_USER_ID, true);
		assertEquals(DB_REGISTERED_USER_ID,foundRegisteredUser.get().getId());
	}
	
	@Test
	public void testFindByIdCO() {
		List<Long> users = repository.findByIdCO(DB_REGISTERED_USER_CO);
		assertEquals(DB_REGISTERED_USER_CO_SIZE, users.size());
	}
	@Test
	public void testFindByIdRU() {
		RegisteredUser foundRegisteredUser = repository.findByIdRU(DB_REGISTERED_USER_ID);
		assertEquals(DB_REGISTERED_USER_ID, foundRegisteredUser.getId());
	}
	
	@Test
	public void testFindByEmailAndActive() {
		RegisteredUser foundRegisteredUser = repository.findByEmailAndActive(DB_REGISTERED_USER_EMAIL, true);
		assertEquals(DB_REGISTERED_USER_EMAIL, foundRegisteredUser.getEmail());
	}
	
	
	@Test
	public void testFindByEmail() {
		RegisteredUser foundRegisteredUser = repository.findByEmail(DB_REGISTERED_USER_EMAIL);
		assertEquals(DB_REGISTERED_USER_EMAIL, foundRegisteredUser.getEmail());
	}
	@Test
	public void testFindByEmail_EmailDoesntExists() {
		RegisteredUser foundRegisteredUser = repository.findByEmail(DB_REGISTERED_USER_EMAIL_DoesntExist);
		assertNull(foundRegisteredUser);
	}
	
	@Test
	public void testFindAllRegisteredUser() {
		List<RegisteredUser> foundRegisteredUsers = repository.findAllRegisteredUser();
	    assertEquals(DB_REGISTERED_USER_SIZE, foundRegisteredUsers.size());
	}
			
	@Test
	public void testFindByIdAndActive_IdDoesntExist() {
		Optional<RegisteredUser> foundRegisteredUser = repository.findByIdAndActive(NEW_REGISTERED_USER_ID1, true);
		assertFalse(foundRegisteredUser.isPresent());
	}
	
	@Test
	public void testFindByIdAndActive_IdExistButNotActive() {
		Optional<RegisteredUser> foundRegisteredUser = repository.findByIdAndActive(DB_REGISTERED_USER_ID_DoesntActive, true);
		assertFalse(foundRegisteredUser.isPresent());
	}

}

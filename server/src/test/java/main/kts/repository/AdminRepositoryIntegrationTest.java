package main.kts.repository;

import static main.kts.constants.AdminConstants.DB_ADMIN_EMAIL;
import static main.kts.constants.AdminConstants.DB_ADMIN_EMAIL_DoesntExist;
import static main.kts.constants.AdminConstants.DB_ADMIN_ID;
import static main.kts.constants.AdminConstants.DB_ADMIN_ID_DoesntActive;
import static main.kts.constants.AdminConstants.DB_ADMIN_SIZE;
import static main.kts.constants.AdminConstants.NEW_ADMIN_ID1;
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

import main.kts.model.Admin;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:test.properties")
public class AdminRepositoryIntegrationTest {
	
	@Autowired
	private AdminRepository repository;
	
	@Test
	public void testFindByEmail() {
		Admin foundAdmin = repository.findByEmail(DB_ADMIN_EMAIL);
		assertEquals(DB_ADMIN_EMAIL, foundAdmin.getEmail());
	}
	@Test
	public void testFindByEmail_EmailDoesntExists() {
		Admin foundAdmin = repository.findByEmail(DB_ADMIN_EMAIL_DoesntExist);
		assertNull(foundAdmin);
	}
	
	@Test
	public void testFindAllAdmin() {
		List<Admin> foundAdmins = repository.findAllAdmin();
	    assertEquals(DB_ADMIN_SIZE, foundAdmins.size());
	}
		
	@Test
	public void testFindByIdAndActive() {
		Optional<Admin> foundAdmin = repository.findByIdAndActive(DB_ADMIN_ID, true);
		assertEquals(DB_ADMIN_ID, foundAdmin.get().getId());
	}
	
	@Test
	public void testFindByIdAndActive_IdDoesntExist() {
		Optional<Admin> foundAdmin = repository.findByIdAndActive(NEW_ADMIN_ID1, true);
		assertFalse(foundAdmin.isPresent());
	}
	
	@Test
	public void testFindByIdAndActive_IdExistButNotActive() {
		Optional<Admin> foundAdmin = repository.findByIdAndActive(DB_ADMIN_ID_DoesntActive, true);
		assertFalse(foundAdmin.isPresent());
	}
	
}

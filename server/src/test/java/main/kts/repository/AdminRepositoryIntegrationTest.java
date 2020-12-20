package main.kts.repository;

import static main.kts.constants.AdminConstants.DB_ADMIN_EMAIL;
import static main.kts.constants.AdminConstants.DB_ADMIN_ID;
import static main.kts.constants.AdminConstants.DB_ADMIN_SIZE;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

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
	public void testFindAllAdmin() {
		List<Admin> foundAdmins = repository.findAllAdmin();
	    assertEquals(DB_ADMIN_SIZE, foundAdmins.size());
	}
	
	@Test
	public void testFindByIdAndActive() {
		Optional<Admin> foundAdmin = repository.findByIdAndActive(DB_ADMIN_ID, true);
		assertEquals(DB_ADMIN_ID, foundAdmin.get().getId());
	}
	
}

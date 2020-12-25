package main.kts.service;

import static main.kts.constants.AuthorityConstants.DB_AUTHORITY_ID;
import static main.kts.constants.AuthorityConstants.DB_AUTHORITY_ID2;
import static main.kts.constants.AuthorityConstants.DB_AUTHORITY_ROLE;
import static main.kts.constants.AuthorityConstants.DB_AUTHORITY_ROLE_EXISTING;
import static main.kts.constants.AuthorityConstants.DB_SIZE;
import static main.kts.constants.AuthorityConstants.FALSE_AUTHORITY_ID;
import static main.kts.constants.AuthorityConstants.NEW_AUTHORITY_ROLE;
import static main.kts.constants.AuthorityConstants.UPDATE_AUTHORITY_ROLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import main.kts.model.Authority;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class AuthorityServiceIntegrationTest {

	
	@Autowired
	private AuthorityService authorityService;
	
	
	@Test
	public void testFindAll() {
		List<Authority> found = authorityService.findAll();
		assertEquals(DB_SIZE, found.size());
	}
	
	@Test
	public void testFindByRole() throws Exception {
		Authority existingAuth = authorityService.findByRole(DB_AUTHORITY_ROLE_EXISTING);
		assertEquals(DB_AUTHORITY_ROLE_EXISTING, existingAuth.getRole());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testCreate() throws Exception {
		Authority authority = new Authority(NEW_AUTHORITY_ROLE);
		Authority created = authorityService.create(authority);
		
		assertEquals(NEW_AUTHORITY_ROLE, created.getRole());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testUpdate() throws Exception {
		Authority authority = new Authority(UPDATE_AUTHORITY_ROLE);
		Authority updated = authorityService.update(authority, DB_AUTHORITY_ID2);
				
		assertEquals(UPDATE_AUTHORITY_ROLE, updated.getRole());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testDelete() throws Exception {
		Authority authority = new Authority(DB_AUTHORITY_ROLE);
		authorityService.delete(DB_AUTHORITY_ID);
		Authority auth = authorityService.findOne(DB_AUTHORITY_ID);
		assertNull(auth);
	}
	
	@Test
	public void testDelete_GivenFalseId() throws Exception {

		Throwable exception = assertThrows(Exception.class, () -> {
			authorityService.delete(FALSE_AUTHORITY_ID);
		});
		assertEquals("Authority with given id doesn't exist", exception.getMessage());

	}
}

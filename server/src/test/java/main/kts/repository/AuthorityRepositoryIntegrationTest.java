package main.kts.repository;

import static main.kts.constants.AuthorityConstants.DB_AUTHORITY_ROLE_EXISTING;
import static org.junit.Assert.assertEquals;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import main.kts.model.Authority;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class AuthorityRepositoryIntegrationTest {

	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	
	
	@Test
	public void testFindByRole() throws Exception {
		Authority existingAuth = authorityRepository.findByRole(DB_AUTHORITY_ROLE_EXISTING);
		assertEquals(DB_AUTHORITY_ROLE_EXISTING, existingAuth.getRole());
	}
	
}

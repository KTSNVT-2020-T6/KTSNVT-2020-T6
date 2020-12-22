package main.kts.service;

import static main.kts.constants.AuthorityConstants.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import main.kts.model.Authority;
import main.kts.repository.AuthorityRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class AuthorityServiceUnitTest {
	
	@Autowired
	private AuthorityService authorityService;
	
	@MockBean
	private AuthorityRepository authorityRepository;
	
	@Before
	public void setup() {
		
		List<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority(DB_AUTHORITY_ROLE);
		Authority authority2 = new Authority(DB_AUTHORITY_ROLE2);
		
		authorities.add(authority);
		authorities.add(authority2);
		
		given(authorityRepository.findAll()).willReturn(authorities);
		
		given(authorityRepository.findById(AUTHORITY_ID)).willReturn(Optional.of(authority));
		given(authorityRepository.findByRole(DB_AUTHORITY_ROLE)).willReturn(authority);
		
		Authority newAuthority = new Authority(NEW_AUTHORITY_ROLE);
		Authority savedAuthority = new Authority(NEW_AUTHORITY_ROLE);
		savedAuthority.setId(NEW_AUTHORITY_ID);
		given(authorityRepository.save(newAuthority)).willReturn(savedAuthority);
		
		
		Authority updateAuthority = new Authority(UPDATE_AUTHORITY_ROLE);
		Authority updatedAuthority = new Authority(UPDATE_AUTHORITY_ROLE);
		updatedAuthority.setId(DB_AUTHORITY_ID2);
		given(authorityRepository.save(updateAuthority)).willReturn(updatedAuthority);
		given(authorityRepository.findById(DB_AUTHORITY_ID2)).willReturn(Optional.of(authority2));
		
		given(authorityRepository.findById(FALSE_AUTHORITY_ID)).willReturn(null);
		
		doNothing().when(authorityRepository).delete(authority);
		
	}
	
	@Test
	public void testFindAll() {
		List<Authority> found = authorityService.findAll();
		verify(authorityRepository, times(1)).findAll();
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.size());
	}
	
	@Test
	public void testFindByRole() throws Exception {
		Authority existingAuth = authorityService.findByRole(DB_AUTHORITY_ROLE);
		verify(authorityRepository, times(1)).findByRole(DB_AUTHORITY_ROLE);
		assertEquals(DB_AUTHORITY_ROLE, existingAuth.getRole());
	}
	
	@Test
	public void testCreate() throws Exception {
		Authority authority = new Authority(NEW_AUTHORITY_ROLE);
		Authority created = authorityService.create(authority);
		verify(authorityRepository, times(1)).save(authority);
		
		assertEquals(NEW_AUTHORITY_ROLE, created.getRole());
	}
	
	@Test
	public void testUpdate() throws Exception {
		Authority authority = new Authority(UPDATE_AUTHORITY_ROLE);
		Authority updated = authorityService.update(authority, DB_AUTHORITY_ID2);
		
		verify(authorityRepository, times(1)).findById(DB_AUTHORITY_ID2);
		verify(authorityRepository, times(1)).save(authority);
		
		assertEquals(UPDATE_AUTHORITY_ROLE, updated.getRole());
	}
	
	@Test
	public void testDelete() throws Exception {
		Authority authority = new Authority(DB_AUTHORITY_ROLE);
		authorityService.delete(DB_AUTHORITY_ID);
		verify(authorityRepository, times(1)).findById(DB_AUTHORITY_ID);

		verify(authorityRepository, times(1)).delete(authority);
		
	}
	@Test
	public void testDelete_GivenFalseId() throws Exception {

		Throwable exception = assertThrows(Exception.class, () -> {
			authorityService.delete(FALSE_AUTHORITY_ID);
		});
		verify(authorityRepository, times(1)).findById(FALSE_AUTHORITY_ID);
		assertEquals("Authority with given id doesn't exist", exception.getMessage());

	}
}

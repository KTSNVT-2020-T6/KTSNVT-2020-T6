package main.kts.service;

import static main.kts.constants.CulturalOfferConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import main.kts.dto.UserLoginDTO;
import main.kts.model.Admin;
import main.kts.model.CulturalOffer;
import main.kts.model.Type;
import main.kts.model.User;
import main.kts.repository.AdminRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CulturalOfferServiceIntegrationTest {

	@Autowired
	private CulturalOfferService culturalOfferService;
	
	@Autowired
	private TypeService typeService;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
//	@Before
//	public void setup() {
//		
//        
//	}
	
	@Test
	public void testFindAll() {
		List<CulturalOffer> found = culturalOfferService.findAll();
		assertEquals(DB_SIZE, found.size());
	}
	/*
	@Test
	public void testCombinedSearch() {
		List<CulturalOffer> found = culturalOfferService.findByCombinedSearch(DB_CONTENT, DB_CITY);
		assertEquals(DB_SIZE_BY_COMBINED_SEARCH, found.size());
	}
	*/

	@Test
	public void testFindAllPageable() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<CulturalOffer> found = culturalOfferService.findAll(pageable);
		assertEquals(DB_SIZE, found.getNumberOfElements());
	}

	@Test
	public void testFindById() {
		CulturalOffer found = culturalOfferService.findOne(CO_ID);
		assertEquals(CO_ID, found.getId());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	@WithMockUser(username = "admin@gmail.com", password = "asdf", roles = "ADMIN")
	public void testCreate() throws Exception {	
		Type t = typeService.findOne(1L);
		CulturalOffer culturalOffer = new CulturalOffer(NEW_CO_NAME, NEW_CO_DESCRIPTION, NEW_CO_DATE, NEW_CO_CITY,
				NEW_CO_LAT, NEW_CO_LON, NEW_CO_AVERAGE_RATE, t);
		CulturalOffer created = culturalOfferService.create(culturalOffer);

		assertEquals(NEW_CO_NAME, created.getName());
		assertTrue(created.getActive());
		
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testUpdate() throws Exception {
		Type t = typeService.findOne(1L);
		CulturalOffer culturalOffer = new CulturalOffer(NEW_CO_NAME, NEW_CO_DESCRIPTION, NEW_CO_DATE, NEW_CO_CITY,
				NEW_CO_LAT, NEW_CO_LON, NEW_CO_AVERAGE_RATE, t);

		CulturalOffer created = culturalOfferService.update(culturalOffer, CO_ID);

		assertEquals(CO_ID, created.getId());
		assertEquals(NEW_CO_NAME, created.getName());

	}
	
	@Test
	public void testUpdate_NonexistentId() throws Exception {
		CulturalOffer culturalOffer = new CulturalOffer(NEW_CO_NAME, NEW_CO_DESCRIPTION, NEW_CO_DATE, NEW_CO_CITY,
				NEW_CO_LAT, NEW_CO_LON, NEW_CO_AVERAGE_RATE);

		Throwable exception = assertThrows(Exception.class, () -> {
			CulturalOffer created = culturalOfferService.update(culturalOffer, DB_FALSE_CO_ID);

		});

		assertEquals("Cultural offer with given id doesn't exist", exception.getMessage());

	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testDelete() throws Exception {
		culturalOfferService.delete(CO_ID);		
		CulturalOffer co = culturalOfferService.findOne(CO_ID);
		assertNull(co);
	}
	
	@Test
	public void testDelete_GivenFalseId() throws Exception {
		Throwable exception = assertThrows(Exception.class, () -> {
			culturalOfferService.delete(DB_FALSE_CO_ID);
		});
		assertEquals("Cultural offer with given id doesn't exist", exception.getMessage());
	}
}

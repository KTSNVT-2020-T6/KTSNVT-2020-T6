package main.kts.service;

import static main.kts.constants.RateConstants.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import main.kts.model.CulturalOffer;
import main.kts.model.Rate;
import main.kts.model.RegisteredUser;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class RateServiceIntegrationTest {

	@Autowired
	private RateService rateService;

	@Test
	public void testFindAll() {
		List<Rate> found = rateService.findAll();
		assertEquals(DB_SIZE, found.size());
	}

	@Test
	public void testFindAllPageable() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<Rate> found = rateService.findAll(pageable);
		assertEquals(DB_SIZE, found.getNumberOfElements());
	}

	@Test
	public void testFindById() {
		Rate found = rateService.findOne(RATE_ID);
		assertEquals(RATE_ID, found.getId());
	}

	@Test
	@Rollback(true)
	@Transactional
	public void testCreate() throws Exception {
		RegisteredUser ru = new RegisteredUser();
		ru.setId(DB_USER_ID); // 4
		CulturalOffer co = new CulturalOffer();
		co.setId(DB_CULTURAL_OFFER_ID2); // 2
		Rate rate = new Rate(NEW_RATE_NUMBER, ru, co);
		Rate created = rateService.create(rate);

		assertEquals(NEW_RATE_NUMBER, created.getNumber());
		assertTrue(created.getActive());
	}

	@Test
	public void testCreate_GivenRateAlreadyExists() throws Exception {
		RegisteredUser ru = new RegisteredUser();
		ru.setId(DB_USER_ID); // 4
		CulturalOffer co = new CulturalOffer();
		co.setId(DB_CULTURAL_OFFER_ID); // 1
		Rate rate = new Rate(NEW_RATE_NUMBER, ru, co);
		Throwable exception = assertThrows(Exception.class, () -> {
			Rate created = rateService.create(rate);
		});

		assertEquals("Given user already rated given cultural offer", exception.getMessage());

	}

	@Test
	public void testCreate_NonexistentUser() throws Exception {
		RegisteredUser ru = new RegisteredUser();
		ru.setId(DB_FALSE_USER_ID); // 10
		CulturalOffer co = new CulturalOffer();
		co.setId(DB_CULTURAL_OFFER_ID); // 1
		Rate rate = new Rate(NEW_RATE_NUMBER, ru, co);
		Throwable exception = assertThrows(Exception.class, () -> {
			Rate created = rateService.create(rate);
		});

		assertEquals("User does not exist", exception.getMessage());
	}

	@Test
	public void testCreate_NonexistentCulturalOffer() throws Exception {
		RegisteredUser ru = new RegisteredUser();
		ru.setId(DB_USER_ID); // 4
		CulturalOffer co = new CulturalOffer();
		co.setId(DB_FALSE_CULTURAL_OFFER_ID); // 5
		Rate rate = new Rate(NEW_RATE_NUMBER, ru, co);
		Throwable exception = assertThrows(Exception.class, () -> {
			Rate created = rateService.create(rate);
		});

		assertEquals("Cultural offer does not exist", exception.getMessage());
	}

	@Test
	@Rollback(true)
	@Transactional
	public void testUpdate() throws Exception {
		RegisteredUser ru = new RegisteredUser();
		ru.setId(DB_USER_ID); // 4
		CulturalOffer co = new CulturalOffer();
		co.setId(DB_CULTURAL_OFFER_ID); // 1
		Rate rate = new Rate(UPDATED_RATE_NUMBER, ru, co);
		Rate created = rateService.update(rate, RATE_ID); // 1

		assertEquals(UPDATED_RATE_NUMBER, created.getNumber());
	}
	
	@Test
	public void testUpdate_GivenIdDoesntExist() throws Exception {
		RegisteredUser ru = new RegisteredUser();
		ru.setId(DB_USER_ID); // 4
		CulturalOffer co = new CulturalOffer();
		co.setId(DB_CULTURAL_OFFER_ID); // 1
		Rate rate = new Rate(UPDATED_RATE_NUMBER, ru, co);
		
		Throwable exception = assertThrows(Exception.class, () -> {
			Rate created = rateService.update(rate, DB_FALSE_RATE_ID);
		});
		assertEquals("Rate with given id doesn't exist", exception.getMessage());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testDelete() throws Exception {
		rateService.delete(RATE_ID); // 1
		Rate r = rateService.findOne(RATE_ID);
		assertNull(r);
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testDelete_GivenIdDoesntExist() throws Exception {		
		Throwable exception = assertThrows(Exception.class, () -> {
			rateService.delete(DB_FALSE_RATE_ID); // 3
		});
		assertEquals("Rate with given id doesn't exist", exception.getMessage());
		Rate r = rateService.findOne(RATE_ID);
		assertNotNull(r);
	}

}

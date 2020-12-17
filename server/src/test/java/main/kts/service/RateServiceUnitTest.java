package main.kts.service;

import static main.kts.constants.RateConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import main.kts.model.CulturalOffer;
import main.kts.model.Rate;
import main.kts.model.RegisteredUser;
import main.kts.repository.CulturalOfferRepository;
import main.kts.repository.RateRepository;
import main.kts.repository.RegisteredUserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class RateServiceUnitTest {

	@Autowired
	private RateService rateService;

	@MockBean
	private RateRepository rateRepository;

	@MockBean
	private RegisteredUserRepository registeredUserRepository;

	@MockBean
	private CulturalOfferRepository culturalOfferRepository;

	@Before
	public void setup() {
		List<Rate> rates = new ArrayList<Rate>();
		RegisteredUser existingRegisteredUser = new RegisteredUser(DB_REGISTERED_USER_ID);
		CulturalOffer existingCulturalOffer1 = new CulturalOffer(DB_CULTURAL_OFFER_ID);
		CulturalOffer existingCulturalOffer2 = new CulturalOffer(DB_CULTURAL_OFFER_ID2);
		rates.add(new Rate(DB_RATE_NUMBER, existingRegisteredUser, existingCulturalOffer1));

		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<Rate> ratePage = new PageImpl<>(rates, pageable, PAGEABLE_TOTAL_ELEMENTS);

		given(rateRepository.findByActive(true)).willReturn(rates);
		given(rateRepository.findByActive(pageable, true)).willReturn(ratePage);

		Rate rate = new Rate(NEW_RATE_NUMBER, existingRegisteredUser, existingCulturalOffer2);
		Rate updateRate = new Rate(UPDATED_RATE_NUMBER, existingRegisteredUser, existingCulturalOffer1);

		Rate savedRate = new Rate(NEW_RATE_NUMBER, existingRegisteredUser, existingCulturalOffer2);
		savedRate.setId(RATE_ID);
		Rate updatedRate = new Rate(UPDATED_RATE_NUMBER, existingRegisteredUser, existingCulturalOffer1);
		updatedRate.setId(RATE_ID);

		CulturalOffer savedCO = new CulturalOffer(DB_CULTURAL_OFFER_ID2);
		savedCO.setAverageRate(CO_AVERAGE_RATE2);

		Rate rateFound = new Rate(DB_RATE_ID, DB_RATE_NUMBER, existingRegisteredUser, existingCulturalOffer1);
		given(registeredUserRepository.findById(DB_REGISTERED_USER_ID)).willReturn(Optional.of(existingRegisteredUser));
		given(registeredUserRepository.findById(FALSE_USER_ID)).willReturn(null);

		given(culturalOfferRepository.findById(DB_CULTURAL_OFFER_ID2)).willReturn(Optional.of(existingCulturalOffer2));
		given(culturalOfferRepository.findById(DB_CULTURAL_OFFER_ID)).willReturn(Optional.of(existingCulturalOffer1));
		given(culturalOfferRepository.findById(FALSE_CULTURAL_OFFER_ID)).willReturn(null);
		given(culturalOfferRepository.save(existingCulturalOffer2)).willReturn(savedCO);

		given(rateRepository.findOneByRegisteredUserIdAndCulturalOfferId(DB_REGISTERED_USER_ID, DB_CULTURAL_OFFER_ID))
				.willReturn(rateFound);
		given(rateRepository.findOneByRegisteredUserIdAndCulturalOfferId(DB_REGISTERED_USER_ID, DB_CULTURAL_OFFER_ID2))
				.willReturn(null);
		given(rateRepository.findAllByCulturalOfferId(DB_CULTURAL_OFFER_ID)).willReturn(rates);
		given(rateRepository.findAllByCulturalOfferId(DB_CULTURAL_OFFER_ID2)).willReturn(new ArrayList<Rate>());
		given(rateRepository.save(rate)).willReturn(savedRate);
		given(rateRepository.save(updateRate)).willReturn(updatedRate);
		given(rateRepository.findById(RATE_ID)).willReturn(Optional.of(rate));
		given(rateRepository.findById(FALSE_RATE_ID)).willReturn(null);

		doNothing().when(rateRepository).delete(savedRate);

	}

	@Test
	public void testFindAll() {
		List<Rate> found = rateService.findAll();

		verify(rateRepository, times(1)).findByActive(true);
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.size());
	}

	@Test
	public void testFindAllPageable() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<Rate> found = rateService.findAll(pageable);

		verify(rateRepository, times(1)).findByActive(pageable, true);
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.getNumberOfElements());
	}

	@Test
	public void testCreate() throws Exception {
		RegisteredUser existingRegisteredUser = new RegisteredUser(DB_REGISTERED_USER_ID);
		CulturalOffer existingCulturalOffer2 = new CulturalOffer(DB_CULTURAL_OFFER_ID2);
		Rate rate = new Rate(NEW_RATE_NUMBER, existingRegisteredUser, existingCulturalOffer2);
		Rate created = rateService.create(rate);

		verify(rateRepository, times(1)).findOneByRegisteredUserIdAndCulturalOfferId(DB_REGISTERED_USER_ID,
				DB_CULTURAL_OFFER_ID2);
		verify(rateRepository, times(1)).findAllByCulturalOfferId(DB_CULTURAL_OFFER_ID2);
		verify(rateRepository, times(1)).save(rate);
		verify(registeredUserRepository, times(1)).findById(DB_REGISTERED_USER_ID);
		verify(culturalOfferRepository, times(2)).findById(DB_CULTURAL_OFFER_ID2);
		verify(culturalOfferRepository, times(1)).save(existingCulturalOffer2);

		assertEquals(NEW_RATE_NUMBER, created.getNumber());
	}

	@Test
	/**
	 * Rate with given user id and cultural offer id combination already exists
	 * 
	 */
	public void testCreate_GivenRateAlreadyExists() throws Exception {

		RegisteredUser existingRegisteredUser = new RegisteredUser(DB_REGISTERED_USER_ID);
		CulturalOffer existingCulturalOffer1 = new CulturalOffer(DB_CULTURAL_OFFER_ID);
		Rate rate = new Rate(NEW_RATE_NUMBER, existingRegisteredUser, existingCulturalOffer1);

		Throwable exception = assertThrows(Exception.class, () -> {
			Rate created = rateService.create(rate);
		});

		verify(rateRepository, times(1)).findOneByRegisteredUserIdAndCulturalOfferId(DB_REGISTERED_USER_ID,
				DB_CULTURAL_OFFER_ID);
		verify(registeredUserRepository, times(1)).findById(DB_REGISTERED_USER_ID);
		verify(culturalOfferRepository, times(1)).findById(DB_CULTURAL_OFFER_ID);

		assertEquals("Given user already rated given cultural offer", exception.getMessage());
	}

	@Test
	/**
	 * Given user id doesn't exists
	 * 
	 */
	public void testCreate_NonexistentUser() throws Exception {

		RegisteredUser nonexistingRegisteredUser = new RegisteredUser(FALSE_USER_ID);
		CulturalOffer existingCulturalOffer1 = new CulturalOffer(DB_CULTURAL_OFFER_ID);
		Rate rate = new Rate(NEW_RATE_NUMBER, nonexistingRegisteredUser, existingCulturalOffer1);

		Throwable exception = assertThrows(Exception.class, () -> {
			Rate created = rateService.create(rate);
		});

		verify(registeredUserRepository, times(1)).findById(FALSE_USER_ID);
		assertEquals("User does not exist", exception.getMessage());
	}

	@Test
	/**
	 * Given cultural offer id doesn't exists
	 * 
	 */
	public void testCreate_NonexistentCulturalOffer() throws Exception {

		RegisteredUser existingRegisteredUser = new RegisteredUser(DB_REGISTERED_USER_ID);
		CulturalOffer nonexistingCulturalOffer1 = new CulturalOffer(FALSE_CULTURAL_OFFER_ID);
		Rate rate = new Rate(NEW_RATE_NUMBER, existingRegisteredUser, nonexistingCulturalOffer1);

		Throwable exception = assertThrows(Exception.class, () -> {
			Rate created = rateService.create(rate);
		});

		verify(culturalOfferRepository, times(1)).findById(FALSE_CULTURAL_OFFER_ID);
		assertEquals("Cultural offer does not exist", exception.getMessage());
	}

	@Test
	public void testUpdate() throws Exception {
		RegisteredUser existingRegisteredUser = new RegisteredUser(DB_REGISTERED_USER_ID);
		CulturalOffer existingCulturalOffer1 = new CulturalOffer(DB_CULTURAL_OFFER_ID);
		Rate rate = new Rate(UPDATED_RATE_NUMBER, existingRegisteredUser, existingCulturalOffer1);
		Rate created = rateService.update(rate, RATE_ID);

		verify(rateRepository, times(1)).findById(RATE_ID);
		verify(rateRepository, times(1)).save(rate);
		verify(rateRepository, times(1)).findAllByCulturalOfferId(DB_CULTURAL_OFFER_ID);
		verify(culturalOfferRepository, times(1)).findById(DB_CULTURAL_OFFER_ID);
		verify(culturalOfferRepository, times(1)).save(existingCulturalOffer1);

		assertEquals(UPDATED_RATE_NUMBER, created.getNumber());
	}

	@Test
	/***
	 * Given rate id doesn't exist
	 */
	public void testUpdate_GivenIdDoesntExist() throws Exception {
		RegisteredUser existingRegisteredUser = new RegisteredUser(DB_REGISTERED_USER_ID);
		CulturalOffer existingCulturalOffer1 = new CulturalOffer(DB_CULTURAL_OFFER_ID);
		Rate rate = new Rate(UPDATED_RATE_NUMBER, existingRegisteredUser, existingCulturalOffer1);

		Throwable exception = assertThrows(Exception.class, () -> {
			Rate created = rateService.update(rate, FALSE_RATE_ID);
		});
		verify(rateRepository, times(1)).findById(FALSE_RATE_ID);
		assertEquals("Rate with given id doesn't exist", exception.getMessage());
	}

	@Test
	public void testDelete() throws Exception {
		RegisteredUser existingRegisteredUser = new RegisteredUser(DB_REGISTERED_USER_ID);
		CulturalOffer existingCulturalOffer1 = new CulturalOffer(DB_CULTURAL_OFFER_ID);
		Rate rate = new Rate(NEW_RATE_NUMBER, existingRegisteredUser, existingCulturalOffer1);
		rateService.delete(RATE_ID);

		verify(rateRepository, times(1)).findById(RATE_ID);
		verify(rateRepository, times(1)).save(rate);

	}

	@Test
	public void testDelete_GivenFalseId() throws Exception {
		Throwable exception = assertThrows(Exception.class, () -> {
			rateService.delete(FALSE_RATE_ID);
		});
		verify(rateRepository, times(1)).findById(FALSE_RATE_ID);
		assertEquals("Rate with given id doesn't exist", exception.getMessage());
	}

}

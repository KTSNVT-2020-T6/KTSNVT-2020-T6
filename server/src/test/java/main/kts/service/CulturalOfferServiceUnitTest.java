package main.kts.service;

import static main.kts.constants.CulturalOfferConstants.ADMIN_EMAIL;
import static main.kts.constants.CulturalOfferConstants.ADMIN_ID;
import static main.kts.constants.CulturalOfferConstants.CO_ID;
import static main.kts.constants.CulturalOfferConstants.DB_CO_AVERAGE_RATE;
import static main.kts.constants.CulturalOfferConstants.DB_CO_CITY;
import static main.kts.constants.CulturalOfferConstants.DB_CO_DATE;
import static main.kts.constants.CulturalOfferConstants.DB_CO_DESCRIPTION;
import static main.kts.constants.CulturalOfferConstants.DB_CO_ID;
import static main.kts.constants.CulturalOfferConstants.DB_CO_LAT;
import static main.kts.constants.CulturalOfferConstants.DB_CO_LON;
import static main.kts.constants.CulturalOfferConstants.DB_CO_NAME;
import static main.kts.constants.CulturalOfferConstants.FALSE_ID;
import static main.kts.constants.CulturalOfferConstants.NEW_CO_AVERAGE_RATE;
import static main.kts.constants.CulturalOfferConstants.NEW_CO_CITY;
import static main.kts.constants.CulturalOfferConstants.NEW_CO_DATE;
import static main.kts.constants.CulturalOfferConstants.NEW_CO_DESCRIPTION;
import static main.kts.constants.CulturalOfferConstants.NEW_CO_LAT;
import static main.kts.constants.CulturalOfferConstants.NEW_CO_LON;
import static main.kts.constants.CulturalOfferConstants.NEW_CO_NAME;
import static main.kts.constants.RateConstants.DB_CULTURAL_OFFER_ID;
import static main.kts.constants.RateConstants.DB_REGISTERED_USER_ID;
import static main.kts.constants.RateConstants.FALSE_RATE_ID;
import static main.kts.constants.RateConstants.NEW_RATE_NUMBER;
import static main.kts.constants.RateConstants.RATE_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import main.kts.model.Admin;
import main.kts.model.CulturalOffer;
import main.kts.model.Rate;
import main.kts.model.RegisteredUser;
import main.kts.repository.AdminRepository;
import main.kts.repository.CommentRepository;
import main.kts.repository.CulturalOfferRepository;
import main.kts.repository.PostRepository;
import main.kts.repository.RateRepository;
import main.kts.repository.RegisteredUserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CulturalOfferServiceUnitTest {

	@Autowired
	private CulturalOfferService culturalOfferService;

	@MockBean
	private CulturalOfferRepository culturalOfferRepository;

	@MockBean
	private AdminRepository adminRepository;

	@MockBean
	private RegisteredUserRepository registeredUserRepository;
	
	@MockBean
	private RateRepository rateRepository;
	

	@MockBean
	private EmailService emailService;

	Authentication authentication = Mockito.mock(Authentication.class);
	SecurityContext securityContext = Mockito.mock(SecurityContext.class);

	@Before
	public void setup() {
		CulturalOffer existingCulturalOffer = new CulturalOffer(DB_CO_NAME, DB_CO_DESCRIPTION, DB_CO_DATE, DB_CO_CITY,
				DB_CO_LAT, DB_CO_LON, DB_CO_AVERAGE_RATE);
		existingCulturalOffer.setId(DB_CO_ID);

		CulturalOffer culturalOffer = new CulturalOffer(NEW_CO_NAME, NEW_CO_DESCRIPTION, NEW_CO_DATE, NEW_CO_CITY,
				NEW_CO_LAT, NEW_CO_LON, NEW_CO_AVERAGE_RATE);
		CulturalOffer savedCO = new CulturalOffer(NEW_CO_NAME, NEW_CO_DESCRIPTION, NEW_CO_DATE, NEW_CO_CITY, NEW_CO_LAT,
				NEW_CO_LON, NEW_CO_AVERAGE_RATE);
		savedCO.setId(CO_ID);
		ArrayList<Long> subs = new ArrayList<Long>();
		ArrayList<Rate> rates = new ArrayList<Rate>();
		
		Admin admin = new Admin(ADMIN_ID, ADMIN_EMAIL);
		SecurityContextHolder.setContext(securityContext);

		given(culturalOfferRepository.save(culturalOffer)).willReturn(savedCO);
		given(culturalOfferRepository.save(existingCulturalOffer)).willReturn(existingCulturalOffer);
		given(culturalOfferRepository.findById(CO_ID)).willReturn(Optional.of(existingCulturalOffer));
		given(culturalOfferRepository.findById(FALSE_ID)).willReturn(Optional.empty());
		given(authentication.getName()).willReturn(ADMIN_EMAIL);
		given(securityContext.getAuthentication()).willReturn(authentication);
		given(adminRepository.findByEmail(ADMIN_EMAIL)).willReturn(admin);
		given(adminRepository.save(admin)).willReturn(admin);
		given(registeredUserRepository.findByIdCO(CO_ID)).willReturn(subs);
		given(rateRepository.findAllByCulturalOfferId(DB_CO_ID)).willReturn(rates);
	}

	@Test
	public void testCreate() throws Exception {
		CulturalOffer culturalOffer = new CulturalOffer(NEW_CO_NAME, NEW_CO_DESCRIPTION, NEW_CO_DATE, NEW_CO_CITY,
				NEW_CO_LAT, NEW_CO_LON, NEW_CO_AVERAGE_RATE);
		Admin admin = new Admin(ADMIN_ID, ADMIN_EMAIL);

		CulturalOffer created = culturalOfferService.create(culturalOffer);

		verify(culturalOfferRepository, times(1)).save(culturalOffer);
		verify(adminRepository, times(1)).save(admin);
		verify(adminRepository, times(1)).findByEmail(ADMIN_EMAIL);
		verify(securityContext, times(1)).getAuthentication();
		verify(authentication, times(1)).getName();

		assertEquals(NEW_CO_NAME, created.getName());
		assertEquals(NEW_CO_DESCRIPTION, created.getDescription());
		assertEquals(NEW_CO_DATE, created.getDate());
		assertEquals(NEW_CO_AVERAGE_RATE, created.getAverageRate(), 0);
		assertEquals(NEW_CO_LAT, created.getLat(), 0);
		assertEquals(NEW_CO_LON, created.getLon(), 0);
	}

	@Test
	public void testUpdate() throws Exception {
		ArrayList<RegisteredUser> subs = new ArrayList<RegisteredUser>();
		CulturalOffer culturalOffer = new CulturalOffer(NEW_CO_NAME, NEW_CO_DESCRIPTION, NEW_CO_DATE, NEW_CO_CITY,
				NEW_CO_LAT, NEW_CO_LON, NEW_CO_AVERAGE_RATE);

		CulturalOffer created = culturalOfferService.update(culturalOffer, CO_ID);

		verify(culturalOfferRepository, times(1)).findById(CO_ID);
		verify(registeredUserRepository, times(1)).findByIdCO(DB_CO_ID);
		verify(culturalOfferRepository, times(1)).save(culturalOffer);
		verify(emailService, times(1)).nofiticationForUpdateCulturalOffer(subs, NEW_CO_NAME);

		assertEquals(CO_ID, created.getId());
		assertEquals(NEW_CO_NAME, created.getName());

	}

	@Test
	public void testUpdate_NonexistentId() throws Exception {
		CulturalOffer culturalOffer = new CulturalOffer(NEW_CO_NAME, NEW_CO_DESCRIPTION, NEW_CO_DATE, NEW_CO_CITY,
				NEW_CO_LAT, NEW_CO_LON, NEW_CO_AVERAGE_RATE);

		Throwable exception = assertThrows(Exception.class, () -> {
			CulturalOffer created = culturalOfferService.update(culturalOffer, FALSE_ID);

		});

		verify(culturalOfferRepository, times(1)).findById(FALSE_ID);

		assertEquals("Cultural offer with given id doesn't exist", exception.getMessage());

	}
	
	@Test
	public void testDelete() throws Exception {
		ArrayList<RegisteredUser> subs = new ArrayList<RegisteredUser>();
		CulturalOffer existingCulturalOffer = new CulturalOffer(DB_CO_NAME, DB_CO_DESCRIPTION, DB_CO_DATE, DB_CO_CITY,
				DB_CO_LAT, DB_CO_LON, DB_CO_AVERAGE_RATE);
		existingCulturalOffer.setId(DB_CO_ID);
		
		culturalOfferService.delete(CO_ID);

		verify(culturalOfferRepository, times(1)).findById(CO_ID);
		verify(culturalOfferRepository, times(1)).save(existingCulturalOffer);
		verify(rateRepository, times(1)).findAllByCulturalOfferId(DB_CO_ID);
		verify(registeredUserRepository, times(1)).findByIdCO(DB_CO_ID);
		verify(emailService, times(1)).nofiticationForDeleteCulturalOffer(subs, DB_CO_NAME);
		
	}

	@Test
	public void testDelete_GivenFalseId() throws Exception {
		Throwable exception = assertThrows(Exception.class, () -> {
			culturalOfferService.delete(FALSE_ID);
		});
		verify(culturalOfferRepository, times(1)).findById(FALSE_ID);
		assertEquals("Cultural offer with given id doesn't exist", exception.getMessage());
	}
	
	
	

}

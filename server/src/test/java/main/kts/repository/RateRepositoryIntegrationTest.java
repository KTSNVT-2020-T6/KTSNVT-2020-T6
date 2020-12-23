package main.kts.repository;

import static main.kts.constants.RateConstants.DB_CULTURAL_OFFER_ID;
import static main.kts.constants.RateConstants.DB_FALSE_RATE_ID;
import static main.kts.constants.RateConstants.DB_RATE_ID;
import static main.kts.constants.RateConstants.DB_RATE_NUMBER;
import static main.kts.constants.RateConstants.DB_REGISTERED_USER_ID;
import static main.kts.constants.RateConstants.DB_SIZE;
import static main.kts.constants.RateConstants.DB_SIZE_BY_CO;
import static main.kts.constants.RateConstants.DB_USER_ID;
import static main.kts.constants.RateConstants.PAGEABLE_PAGE;
import static main.kts.constants.RateConstants.PAGEABLE_SIZE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import main.kts.model.Rate;


@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:test.properties")
public class RateRepositoryIntegrationTest {

	@Autowired
	private RateRepository rateRepository;
	
	@Test
    public void testFindByActive() {
        List<Rate> found = rateRepository.findByActive(true);
        assertEquals(DB_SIZE, found.size());
    }
	
	@Test
    public void testFindByActivePage() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        Page<Rate> found = rateRepository.findByActive(pageable, true);
        assertEquals(DB_SIZE, found.getSize());
    }

    @Test
    public void testFindByIdAndActive() {
        Optional<Rate> found = rateRepository.findByIdAndActive(DB_RATE_ID, true);
        Rate r = found.orElse(null);
        assertEquals(r.getNumber(), DB_RATE_NUMBER);
    }
    
    @Test
    public void testFindByIdAndActive_GivenFalseId() {
        Optional<Rate> found = rateRepository.findByIdAndActive(DB_FALSE_RATE_ID, true);
        Rate r = found.orElse(null);
        assertNull(r);
    }
    
    @Test
    public void testFindAllByCulturalOfferId() {
        List<Rate> found = rateRepository.findAllByCulturalOfferId(DB_CULTURAL_OFFER_ID);
        assertEquals(DB_SIZE_BY_CO, found.size());
    }
    
    @Test
    public void testFindOneByRegisteredUserIdAndCulturalOfferId() {
        Rate found = rateRepository.findOneByRegisteredUserIdAndCulturalOfferId(DB_USER_ID, DB_CULTURAL_OFFER_ID);
        System.out.println(DB_CULTURAL_OFFER_ID);
        System.out.println(DB_USER_ID);
        System.out.println(found + "lalalal");
        assertEquals(DB_RATE_NUMBER, found.getNumber());
        assertEquals(DB_CULTURAL_OFFER_ID, found.getCulturalOffer().getId());
        assertEquals(DB_USER_ID, found.getRegistredUser().getId());
    }
    
    @Test
    public void testFindOneByRegisteredUserIdAndCulturalOfferId_GivenFalseIds() {
        Rate found = rateRepository.findOneByRegisteredUserIdAndCulturalOfferId(DB_CULTURAL_OFFER_ID, DB_REGISTERED_USER_ID);
        assertNull(found);
    }
    
}

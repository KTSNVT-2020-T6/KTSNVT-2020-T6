package main.kts.repository;

import static main.kts.constants.CulturalOfferConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import main.kts.model.CulturalOffer;
import main.kts.model.Rate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CulturalOfferRepositoryIntegrationTest {

	@Autowired
	private CulturalOfferRepository culturalOfferRepository;
	
	@Test
    public void testFindByActive() {
        List<CulturalOffer> found = culturalOfferRepository.findByActive(true);
        assertEquals(DB_SIZE, found.size());
    }
	
	@Test
    public void testFindByActivePage() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        Page<CulturalOffer> found = culturalOfferRepository.findByActive(pageable, true);
        assertEquals(DB_SIZE, found.getSize());
    }

    @Test
    public void testFindByIdAndActive() {
        Optional<CulturalOffer> found = culturalOfferRepository.findByIdAndActive(DB_CO_ID, true);
        CulturalOffer r = found.orElse(null);
        assertEquals(r.getName(), DB_NAME);
    }
    
    @Test
    public void testFindByIdAndActive_GivenFalseId() {
        Optional<CulturalOffer> found = culturalOfferRepository.findByIdAndActive(DB_FALSE_CO_ID, true);
        CulturalOffer co = found.orElse(null);
        assertNull(co);
    }
    
    @Test
    public void testFindByCity() {
        List<CulturalOffer> found = culturalOfferRepository.findByCity(DB_CITY);
        assertEquals(DB_SIZE_BY_CITY, found.size());
    }
    
    @Test
    public void testFindByContent() {
        List<CulturalOffer> found = culturalOfferRepository.findByContent(DB_CONTENT);
        assertEquals(DB_SIZE_BY_CONTENT, found.size());
    }
}

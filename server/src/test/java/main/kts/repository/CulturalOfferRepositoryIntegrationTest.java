package main.kts.repository;

import static main.kts.constants.CulturalOfferConstants.DB_CITY;
import static main.kts.constants.CulturalOfferConstants.DB_CONTENT;
import static main.kts.constants.CulturalOfferConstants.DB_CO_ID;
import static main.kts.constants.CulturalOfferConstants.DB_FALSE_CO_ID;
import static main.kts.constants.CulturalOfferConstants.DB_NAME;
import static main.kts.constants.CulturalOfferConstants.DB_SIZE;
import static main.kts.constants.CulturalOfferConstants.DB_SIZE_BY_CITY;
import static main.kts.constants.CulturalOfferConstants.DB_SIZE_BY_CONTENT;
import static main.kts.constants.CulturalOfferConstants.PAGEABLE_PAGE;
import static main.kts.constants.CulturalOfferConstants.PAGEABLE_SIZE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import main.kts.model.CulturalOffer;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:test.properties")
public class CulturalOfferRepositoryIntegrationTest {

	@Autowired
	private CulturalOfferRepository culturalOfferRepository;
	
	@Test
    public void testFindByActive() {
        List<CulturalOffer> found = culturalOfferRepository.findByActive(true);
        assertEquals(DB_SIZE, found.size());
    }
	/* pageable
	@Test
    public void testFindByCombinedSearch() {
        List<CulturalOffer> found = culturalOfferRepository.findByCombinedSearch(DB_CONTENT, DB_CITY);
        assertEquals(DB_SIZE_BY_CONTENT, found.size());
    }
	*/
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
    
    /*
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
    */
}

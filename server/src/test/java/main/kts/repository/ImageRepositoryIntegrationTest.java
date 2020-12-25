package main.kts.repository;

import static main.kts.constants.ImageConstants.DB_CULTURAL_OFFER_ID;
import static main.kts.constants.ImageConstants.DB_FALSE_IMAGE_ID;
import static main.kts.constants.ImageConstants.DB_IMAGE_ID;
import static main.kts.constants.ImageConstants.DB_IMAGE_RELATIVE_PATH;
import static main.kts.constants.ImageConstants.DB_PAGE_SIZE;
import static main.kts.constants.ImageConstants.DB_SIZE;
import static main.kts.constants.ImageConstants.DB_SIZE_BY_CO;
import static main.kts.constants.ImageConstants.PAGEABLE_PAGE;
import static main.kts.constants.ImageConstants.PAGEABLE_SIZE;
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

import main.kts.model.Image;


@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:test.properties")
public class ImageRepositoryIntegrationTest {

	@Autowired
	private ImageRepository imageRepository;
	
	@Test
    public void testFindByActive() {
        List<Image> found = imageRepository.findByActive(true);
        assertEquals(DB_SIZE, found.size());
    }

	@Test
    public void testFindByActivePage() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        Page<Image> found = imageRepository.findByActive(pageable, true);
        assertEquals(DB_PAGE_SIZE, found.getSize());
    }

    @Test
    public void testFindByIdAndActive() {
        Optional<Image> found = imageRepository.findByIdAndActive(DB_IMAGE_ID, true);
        Image image = found.orElse(null);
        assertEquals(image.getRelativePath(), DB_IMAGE_RELATIVE_PATH);
    }
    
    @Test
    public void testFindByIdAndActive_GivenFalseId() {
        Optional<Image> found = imageRepository.findByIdAndActive(DB_FALSE_IMAGE_ID, true);
        Image image = found.orElse(null);
        assertNull(image);
    }
    
    @Test
    public void testFindAllByCulturalOfferId() {
        List<Image> found = imageRepository.findAllByCulturalOfferId(DB_CULTURAL_OFFER_ID);
        assertEquals(DB_SIZE_BY_CO, found.size());
    }
	
}

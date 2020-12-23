package main.kts.repository;

import static main.kts.constants.CategoryConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import main.kts.model.Category;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:test.properties")
public class CategoryRepositoryIntegrationTest {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Test
    public void testFindByActive() {
        List<Category> found = categoryRepository.findByActive(true);
        assertEquals(DB_SIZE, found.size());
    }
	
	@Test
    public void testFindByActivePage() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        Page<Category> found = categoryRepository.findByActive(pageable, true);
        assertEquals(DB_SIZE, found.getSize());
	}
	
	
	@Test
    public void testFindByIdAndActive() {
		Optional<Category> found = categoryRepository.findByIdAndActive(DB_CATEGORY_ID, true);
		Category category = found.orElse(null);
		assertEquals(category.getId(), DB_CATEGORY_ID);;
	}
	
	
	@Test
    public void testFindByIdAndActive_GivenFalseId() {
		Optional<Category> found = categoryRepository.findByIdAndActive(FALSE_CATEGORY_ID, true);
		Category category = found.orElse(null);
		assertNull(category);;
	}
}

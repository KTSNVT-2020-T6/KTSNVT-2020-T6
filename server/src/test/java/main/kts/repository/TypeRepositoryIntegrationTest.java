package main.kts.repository;

import static main.kts.constants.TypeConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import main.kts.model.Category;
import main.kts.model.Type;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class TypeRepositoryIntegrationTest {

	
	@Autowired
	private TypeRepository typeRepository;
	
	@Test
    public void testFindByActive() {
        List<Type> found = typeRepository.findByActive(true);
        assertEquals(DB_SIZE, found.size());
    }
	
	@Test
    public void testFindByActivePage() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE,DB_PAGEABLE_SIZE);
        Page<Type> found = typeRepository.findByActive(pageable, true);
        assertEquals(DB_PAGEABLE_SIZE, found.getSize());
	}
	
	@Test
    public void testFindByIdAndActive() {
		Optional<Type> found = typeRepository.findByIdAndActive(DB_TYPE_ID, true);
		Type type = found.orElse(null);
		assertEquals(DB_TYPE_ID, type.getId());;
	}
	
	@Test
    public void testFindTypesOfCategory() {
		List<Type> found = typeRepository.findTypesOfCategory(CATEGORY_ID);
		assertEquals(DB_NUMBER_OF_TYPES, found.size());;
	}
	
	@Test
    public void testFindByIdAndActive_GivenFalseId() {
		Optional<Type> found = typeRepository.findByIdAndActive(FALSE_TYPE_ID, true);
		Type type = found.orElse(null);
		assertNull(type);;
	}
	
	
}

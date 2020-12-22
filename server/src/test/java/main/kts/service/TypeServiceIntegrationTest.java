package main.kts.service;

import static main.kts.constants.TypeConstants.*;
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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import main.kts.model.Category;
import main.kts.model.Type;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class TypeServiceIntegrationTest {

	@Autowired
	private TypeService typeService;
	
	@Test
	public void  testFindAll() {
		List<Type> found = typeService.findAll();
		assertEquals(DB_SIZE, found.size());
	}
	
	@Test
	public void testFindAllPageable() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, DB_PAGEABLE_SIZE);
		Page<Type> found = typeService.findAll(pageable);
		assertEquals(DB_PAGEABLE_SIZE, found.getNumberOfElements());
	
	}
	
	@Test
	public void testFindTypesOfCategory() {
		List<Type> found = typeService.findTypesOfCategory(CATEGORY_ID); // 1L
		assertEquals(DB_NUMBER_OF_TYPES, found.size());
		
	}
	@Test
	public void testFindById() {
		Type found = typeService.findOne(TYPE_ID);
		assertEquals(TYPE_ID, found.getId());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testCreate() throws Exception {
		Category cat = new Category(NEW_CATEGORY_ID);
		Type type = new Type(NEW_TYPE_NAME, NEW_TYPE_DESCRIPTION, cat);
		Type created = typeService.create(type);
		assertEquals(NEW_TYPE_NAME, created.getName());
		assertTrue(created.getActive());
		
	}
	@Test
	public void testCreate_NonexistentCategory() throws Exception {
		Category cat = new Category(DB_FALSE_CATEGORY_ID);
		Type type = new Type(NEW_TYPE_NAME, NEW_TYPE_DESCRIPTION, cat);
		Throwable exception = assertThrows(Exception.class, () ->{
			typeService.create(type);
		});
		
		assertEquals("Category does not exist", exception.getMessage());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testUpdate() throws Exception {
		Category cat = new Category(CATEGORY_ID);
		Type type = new Type(UPDATE_TYPE_NAME, DB_TYPE_DESCRIPTION, cat);
		Type updated = typeService.update(type, DB_TYPE_ID);
		assertEquals(UPDATE_TYPE_NAME, updated.getName());
		
	}
	
	@Test
	public void testUpdate_GivenIdDoesntExist() throws Exception{
		Category cat = new Category(CATEGORY_ID);
		Type type = new Type(UPDATE_TYPE_NAME, DB_TYPE_DESCRIPTION, cat);
		Throwable exception = assertThrows(Exception.class, () -> {
			typeService.update(type, FALSE_TYPE_ID);
		});
		
		assertEquals("Type with given id doesn't exist", exception.getMessage());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testDelete() throws Exception {
		typeService.delete(DB_TYPE_ID);
		Type type = typeService.findOne(DB_TYPE_ID);
		assertNull(type);
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testDelete_GivenIdDoesntExist() throws Exception {		
		Throwable exception = assertThrows(Exception.class, () -> {
			typeService.delete(FALSE_TYPE_ID);
		});
		assertEquals("Type with given id doesn't exist", exception.getMessage());
	}
}

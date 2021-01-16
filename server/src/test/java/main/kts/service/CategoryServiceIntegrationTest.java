package main.kts.service;

import static main.kts.constants.CategoryConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CategoryServiceIntegrationTest {
	
	@Autowired
	private CategoryService categoryService;

	@Test
	public void testFindAll() {
		List<Category> found = categoryService.findAll();
		assertEquals(DB_SIZE, found.size());
	}
	@Test
	public void testFindAllPageable() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<Category> found = categoryService.findAll(pageable);
		assertEquals(DB_SIZE, found.getNumberOfElements());
	}
	@Test
	public void testFindById() {
		Category found = categoryService.findOne(CATEGORY_ID);
		assertEquals(CATEGORY_ID, found.getId());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testCreate() throws Exception{
		Category category = new Category(NEW_CATEGORY_NAME, NEW_CATEGORY_DESCRIPTION);
		Category created = categoryService.create(category);
		assertEquals(NEW_CATEGORY_NAME, created.getName());
		assertTrue(created.getActive());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testUpdate() throws Exception {
		Category category = new Category(UPDATE_CATEGORY_NAME, UPDATE_CATEGORY_DESCRIPTION);
		Category updated = categoryService.update(category, CATEGORY_ID);
		
		assertEquals(UPDATE_CATEGORY_NAME, updated.getName());
	}
	
	@Test
	public void testUpdate_GivenIdDoesntExist() throws Exception {
		Category category = new Category(UPDATE_CATEGORY_NAME, UPDATE_CATEGORY_DESCRIPTION);
		Throwable exception = assertThrows(Exception.class, () -> {
			Category updated = categoryService.update(category, FALSE_CATEGORY_ID);
		});
		assertEquals("Category with given id doesn't exist", exception.getMessage());			
	}
	@Test
	@Rollback(true)
	@Transactional
	public void testDelete() throws Exception {
		categoryService.delete(DELETE_CATEGORY_ID);  // id 3
		Category category = categoryService.findOne(DELETE_CATEGORY_ID);
		assertNull(category);
	}
	
	@Test
	public void testDelete_GivenIdDoesntExist() throws Exception {
		Throwable exception = assertThrows(Exception.class, () -> {
			categoryService.delete(FALSE_CATEGORY_ID);
		});
		assertEquals("Category with given id doesn't exist", exception.getMessage());			
	}
	
}

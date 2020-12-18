package main.kts.service;

import static main.kts.constants.CategoryConstants.DB_CATEGORY_DESCIPTION;
import static main.kts.constants.CategoryConstants.DB_CATEGORY_DESCIPTION2;
import static main.kts.constants.CategoryConstants.DB_CATEGORY_ID;
import static main.kts.constants.CategoryConstants.DB_CATEGORY_NAME;
import static main.kts.constants.CategoryConstants.DB_CATEGORY_NAME2;
import static main.kts.constants.CategoryConstants.FALSE_CATEGORY_ID;
import static main.kts.constants.CategoryConstants.FIND_ALL_NUMBER_OF_ITEMS;
import static main.kts.constants.CategoryConstants.NEW_CATEGORY_DESCRIPTION;
import static main.kts.constants.CategoryConstants.NEW_CATEGORY_ID;
import static main.kts.constants.CategoryConstants.NEW_CATEGORY_NAME;
import static main.kts.constants.CategoryConstants.PAGEABLE_PAGE;
import static main.kts.constants.CategoryConstants.PAGEABLE_SIZE;
import static main.kts.constants.CategoryConstants.PAGEABLE_TOTAL_ELEMENTS;
import static main.kts.constants.CategoryConstants.UPDATE_CATEGORY_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

import main.kts.model.Category;
import main.kts.repository.CategoryRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CategoryServiceUnitTest {
	
	@Autowired
	private CategoryService categoryService;
	
	@MockBean
	private CategoryRepository categoryRepository;
	
	
	@Before 
	public void setup() {
		List<Category> categories = new ArrayList<Category>();
		Category existingCategory = new Category(DB_CATEGORY_NAME, DB_CATEGORY_DESCIPTION);
		categories.add(existingCategory);
		Category existingCategory2 = new Category(DB_CATEGORY_NAME2, DB_CATEGORY_DESCIPTION2);
		categories.add(existingCategory2);
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<Category> categoryPage = new PageImpl<>(categories, pageable, PAGEABLE_TOTAL_ELEMENTS);
 		
		given(categoryRepository.findByActive(true)).willReturn(categories);
		given(categoryRepository.findByActive(pageable, true)).willReturn(categoryPage);
		
		Category category = new Category(NEW_CATEGORY_NAME, NEW_CATEGORY_DESCRIPTION);
		Category savedCategory = new Category(NEW_CATEGORY_NAME, NEW_CATEGORY_DESCRIPTION);
		savedCategory.setId(NEW_CATEGORY_ID);
		given(categoryRepository.save(category)).willReturn(savedCategory);
		
		
		Category updateCategory = new Category(UPDATE_CATEGORY_NAME, DB_CATEGORY_DESCIPTION);
		Category updatedCategory = new Category(UPDATE_CATEGORY_NAME, DB_CATEGORY_DESCIPTION);
		updatedCategory.setId(DB_CATEGORY_ID);
		given(categoryRepository.save(updateCategory)).willReturn(updatedCategory);
		
		given(categoryRepository.findById(FALSE_CATEGORY_ID)).willReturn(null);
		given(categoryRepository.findById(DB_CATEGORY_ID)).willReturn(Optional.of(existingCategory));
	
		doNothing().when(categoryRepository).delete(existingCategory);
	}
	
	@Test
	public void testFindAll() {
		List<Category> found = categoryService.findAll();

		verify(categoryRepository, times(1)).findByActive(true);
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.size());
	}
	
	@Test
	public void testFindAllPageable() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<Category> found = categoryService.findAll(pageable);

		verify(categoryRepository, times(1)).findByActive(pageable, true);
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.getNumberOfElements());
	}

	@Test
	public void testCreate() throws Exception {
		Category category = new Category(NEW_CATEGORY_NAME, NEW_CATEGORY_DESCRIPTION);
		Category created = categoryService.create(category);
		verify(categoryRepository, times(1)).save(category);
		
		assertEquals(NEW_CATEGORY_NAME, created.getName());
	}
	
	
	@Test
	public void testUpdate() throws Exception {
		Category category = new Category(UPDATE_CATEGORY_NAME, DB_CATEGORY_DESCIPTION);
		Category updated = categoryService.update(category, DB_CATEGORY_ID);
		
		verify(categoryRepository, times(1)).findById(DB_CATEGORY_ID);
		verify(categoryRepository, times(1)).save(category);
	
		assertEquals(UPDATE_CATEGORY_NAME, updated.getName());
	}

	@Test
	public void testDelete() throws Exception {
		Category category = new Category(DB_CATEGORY_NAME, DB_CATEGORY_DESCIPTION);
		categoryService.delete(DB_CATEGORY_ID);
		
		verify(categoryRepository, times(1)).findById(DB_CATEGORY_ID);
		verify(categoryRepository, times(1)).save(category);
		
	}
	
	@Test
	public void testDelete_GivenFalseId() throws Exception {
		Throwable exception = assertThrows(Exception.class, () -> {
			categoryService.delete(FALSE_CATEGORY_ID);
		});
		verify(categoryRepository, times(1)).findById(FALSE_CATEGORY_ID);
		assertEquals("Category with given id doesn't exist", exception.getMessage());
	}
	
}

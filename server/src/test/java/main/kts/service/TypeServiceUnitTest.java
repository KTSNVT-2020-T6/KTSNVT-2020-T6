package main.kts.service;



import static main.kts.constants.TypeConstants.DB_CATEGORY_ID;
import static main.kts.constants.TypeConstants.DB_CATEGORY_ID2;
import static main.kts.constants.TypeConstants.DB_TYPE_DESCRIPTION;
import static main.kts.constants.TypeConstants.DB_TYPE_DESCRIPTION2;
import static main.kts.constants.TypeConstants.DB_TYPE_ID;
import static main.kts.constants.TypeConstants.DB_TYPE_ID2;
import static main.kts.constants.TypeConstants.DB_TYPE_NAME;
import static main.kts.constants.TypeConstants.DB_TYPE_NAME2;
import static main.kts.constants.TypeConstants.FALSE_TYPE_ID;
import static main.kts.constants.TypeConstants.FIND_ALL_NUMBER_OF_ITEMS;
import static main.kts.constants.TypeConstants.NEW_CATEGORY_ID;
import static main.kts.constants.TypeConstants.FIND_ALL_NUMBER_OF_TYPES;
import static main.kts.constants.TypeConstants.NEW_TYPE_DESCRIPTION;
import static main.kts.constants.TypeConstants.NEW_TYPE_ID;
import static main.kts.constants.TypeConstants.NEW_TYPE_NAME;
import static main.kts.constants.TypeConstants.PAGEABLE_PAGE;
import static main.kts.constants.TypeConstants.PAGEABLE_SIZE;
import static main.kts.constants.TypeConstants.PAGEABLE_TOTAL_ELEMENTS;
import static main.kts.constants.TypeConstants.UPDATE_TYPE_NAME;
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
import main.kts.model.Type;
import main.kts.repository.TypeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class TypeServiceUnitTest {
	
	@Autowired
	private TypeService typeService;
	
	@MockBean
	private TypeRepository typeRepository;
	
	@Before
	public void setup() {
		List<Type> types = new ArrayList<Type>();
		
		Category category = new Category(DB_CATEGORY_ID);
		Type existingType = new Type(DB_TYPE_ID, DB_TYPE_NAME, DB_TYPE_DESCRIPTION, category);
		
		Category category2 = new Category(DB_CATEGORY_ID2);
		Type existingType2 = new Type(DB_TYPE_ID2, DB_TYPE_NAME2, DB_TYPE_DESCRIPTION2, category2);

		types.add(existingType);
		types.add(existingType2);
		given(typeRepository.findByActive(true)).willReturn(types);
		
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<Type> typePage = new PageImpl<>(types, pageable, PAGEABLE_TOTAL_ELEMENTS);
		given(typeRepository.findByActive(pageable, true)).willReturn(typePage);
		
		Category existingCategory = new Category(NEW_CATEGORY_ID);
		Type newType = new Type(NEW_TYPE_ID, NEW_TYPE_NAME, NEW_TYPE_DESCRIPTION, existingCategory);
		Type savedType =  new Type(NEW_TYPE_ID, NEW_TYPE_NAME, NEW_TYPE_DESCRIPTION, existingCategory);
		given(typeRepository.save(newType)).willReturn(savedType);
		
		Type updateType = new Type(UPDATE_TYPE_NAME, DB_TYPE_DESCRIPTION);
		Type updatedType = new Type(UPDATE_TYPE_NAME, DB_TYPE_DESCRIPTION);
		updatedType.setId(DB_TYPE_ID);
		given(typeRepository.save(updateType)).willReturn(updatedType);
		
		given(typeRepository.findById(DB_TYPE_ID)).willReturn(Optional.of(existingType));
		given(typeRepository.findById(FALSE_TYPE_ID)).willReturn(null);
		given(typeRepository.findTypesOfCategory(DB_CATEGORY_ID)).willReturn(types);
		
		doNothing().when(typeRepository).delete(existingType);

	}
	
	@Test
	public void testFindAll() {
		List<Type> found = typeService.findAll();
		verify(typeRepository, times(1)).findByActive(true);
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.size());
	}
	
	@Test
	public void testFindAllPageable() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<Type> found = typeService.findAll(pageable);

		verify(typeRepository, times(1)).findByActive(pageable, true);
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.getNumberOfElements());
	
	}
	
	@Test
	public void testCreate() throws Exception {
		Type type = new Type(NEW_TYPE_NAME, NEW_TYPE_DESCRIPTION);
		Category cat = new Category(NEW_CATEGORY_ID);
		type.setCategory(cat);
		Type createdType = typeService.create(type);
		verify(typeRepository,times(1)).save(type);
		
		assertEquals(NEW_TYPE_NAME, createdType.getName());
	}
	
	@Test
	public void testUpdate() throws Exception {
		Type type = new Type(UPDATE_TYPE_NAME, DB_TYPE_DESCRIPTION);
		Category cat = new Category(DB_CATEGORY_ID);
		type.setCategory(cat);
		Type updatedType = typeService.update(type, DB_TYPE_ID);
		
		verify(typeRepository, times(1)).findById(DB_TYPE_ID);
		verify(typeRepository, times(1)).save(type);
		
		assertEquals(UPDATE_TYPE_NAME, updatedType.getName());
	}
	
	@Test
	public void testDelete() throws Exception{
		Type type = new Type(DB_TYPE_NAME, DB_TYPE_DESCRIPTION);
		Category cat = new Category(DB_CATEGORY_ID);
		type.setCategory(cat);
		typeService.delete(DB_TYPE_ID);
		
		verify(typeRepository, times(1)).findById(DB_TYPE_ID);
		verify(typeRepository, times(1)).save(type);
	}
	
	@Test 
	public void testDelete_GivenFalseId() throws Exception{
		Throwable exception = assertThrows(Exception.class, () ->{
			typeService.delete(FALSE_TYPE_ID);
		});
		
		verify(typeRepository, times(1)).findById(FALSE_TYPE_ID);
		assertEquals("Type with given id doesn't exist", exception.getMessage());
	}
	
	@Test
	public void testFindTypesOfCategory() {
		List<Type> found = typeService.findTypesOfCategory(DB_CATEGORY_ID);
		verify(typeRepository, times(1)).findTypesOfCategory(DB_CATEGORY_ID);
		assertEquals(FIND_ALL_NUMBER_OF_TYPES, found.size());
	}
	

}

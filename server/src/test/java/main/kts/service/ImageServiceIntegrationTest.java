package main.kts.service;

import static main.kts.constants.ImageConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

import main.kts.model.Image;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class ImageServiceIntegrationTest {

	@Autowired
	private ImageService imageService;
	
	@Test
	public void testFindAll() {
		List<Image> found = imageService.findAll();
		assertEquals(DB_SIZE, found.size());
	}
	
	@Test
	public void testFindAllPageable() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<Image> found = imageService.findAll(pageable);
		assertEquals(DB_PAGE_SIZE, found.getNumberOfElements());
	}
	
	@Test
	public void testFindById() {
		Image found = imageService.findOne(IMAGE_ID);
		assertEquals(IMAGE_ID, found.getId());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testCreate() throws Exception {
		Image image = new Image(NEW_IMAGE_NAME, NEW_IMAGE_RELATIVE_PATH);
		Image created = imageService.create(image);

		assertEquals(NEW_IMAGE_RELATIVE_PATH, created.getrelativePath());
		assertTrue(created.getActive());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testUpdate() throws Exception {
		Image image = new Image(DB_IMAGE_NAME, UPDATED_IMAGE_RELATIVE_PATH);
		Image created = imageService.update(image, IMAGE_ID); // 1
		assertEquals(UPDATED_IMAGE_RELATIVE_PATH, created.getrelativePath());
	}
	
	@Test
	public void testUpdate_GivenIdDoesntExist() throws Exception {
		Image image = new Image(DB_IMAGE_NAME, UPDATED_IMAGE_RELATIVE_PATH);
		
		Throwable exception = assertThrows(Exception.class, () -> {
			Image created = imageService.update(image, DB_FALSE_IMAGE_ID);
		});
		assertEquals("Image with given id doesn't exist", exception.getMessage());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testDelete() throws Exception {
		imageService.delete(IMAGE_ID); // 1
		Image image = imageService.findOne(IMAGE_ID);
		assertNull(image);
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testDelete_GivenIdDoesntExist() throws Exception {		
		Throwable exception = assertThrows(Exception.class, () -> {
			imageService.delete(DB_FALSE_IMAGE_ID); // 3
		});
		assertEquals("Image with given id doesn't exist", exception.getMessage());
		Image image = imageService.findOne(IMAGE_ID);
		assertNotNull(image);
	}
}

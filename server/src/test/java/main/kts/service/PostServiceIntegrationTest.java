package main.kts.service;

import static main.kts.constants.PostConstants.*;
import static main.kts.constants.RateConstants.DB_CULTURAL_OFFER_ID;
import static main.kts.constants.RateConstants.DB_FALSE_RATE_ID;
import static main.kts.constants.RateConstants.DB_USER_ID;
import static main.kts.constants.RateConstants.RATE_ID;
import static main.kts.constants.RateConstants.UPDATED_RATE_NUMBER;
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

import main.kts.model.CulturalOffer;
import main.kts.model.Image;
import main.kts.model.Post;
import main.kts.model.Rate;
import main.kts.model.RegisteredUser;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class PostServiceIntegrationTest {
	
	@Autowired
	private PostService postService;
	
	@Test
	public void testFindAll() {
		List<Post> found = postService.findAll();
		assertEquals(DB_SIZE, found.size());
	}
	
	@Test
	public void testFindAllPageable() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<Post> found = postService.findAll(pageable);
		assertEquals(DB_SIZE, found.getNumberOfElements());
	}
	
	@Test
	public void testFindById() {
		Post found = postService.findOne(POST_ID);
		assertEquals(POST_ID, found.getId());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testCreate() throws Exception {
		Image image = new Image(DB_IMAGE_NAME, DB_IMAGE_RELATIVE_PATH);
		image.setId(DB_IMAGE_ID);
		Post post = new Post(NEW_POST_TEXT, NEW_POST_DATE, image);
		Post created = postService.create(post);

		assertEquals(NEW_POST_TEXT, created.getText());
		assertTrue(created.getActive());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testUpdate() throws Exception {
		Image image = new Image(DB_IMAGE_NAME, DB_IMAGE_RELATIVE_PATH);
		image.setId(IMAGE_ID);
		Post post = new Post(UPDATED_POST_TEXT, UPDATED_POST_DATE, image);
		Post created = postService.update(post, POST_ID); // 1

		assertEquals(UPDATED_POST_TEXT, created.getText());
	}
	
	@Test
	public void testUpdate_GivenIdDoesntExist() throws Exception {
		Image image = new Image(DB_IMAGE_NAME, DB_IMAGE_RELATIVE_PATH);
		image.setId(IMAGE_ID);
		Post post = new Post(UPDATED_POST_TEXT, UPDATED_POST_DATE, image);
		
		Throwable exception = assertThrows(Exception.class, () -> {
			Post created = postService.update(post, DB_FALSE_POST_ID);
		});
		assertEquals("Post with given id doesn't exist", exception.getMessage());
	}
	@Test
	@Rollback(true)
	@Transactional
	public void testDelete() throws Exception {
		postService.delete(POST_ID); // 1
		Post post= postService.findOne(POST_ID);
		assertNull(post);
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testDelete_GivenIdDoesntExist() throws Exception {		
		Throwable exception = assertThrows(Exception.class, () -> {
			postService.delete(DB_FALSE_POST_ID); // 3
		});
		assertEquals("Post with given id doesn't exist", exception.getMessage());
		Post post = postService.findOne(POST_ID);
		assertNotNull(post);
	}

}

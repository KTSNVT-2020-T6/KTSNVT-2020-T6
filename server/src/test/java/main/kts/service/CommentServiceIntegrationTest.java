package main.kts.service;

import static main.kts.constants.CommentConstants.*;
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

import main.kts.model.Comment;
import main.kts.model.CulturalOffer;
import main.kts.model.Rate;
import main.kts.model.RegisteredUser;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CommentServiceIntegrationTest {

	@Autowired 
	private CommentService commentService;
	
	@Test
	public void testFindAll() {
		List<Comment> found = commentService.findAll();
		assertEquals(DB_SIZE, found.size());
	}
	
	@Test
	public void testFindAllPageable() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<Comment> found = commentService.findAll(pageable);
		assertEquals(DB_SIZE, found.getNumberOfElements());
	}
	
	@Test
	public void testFindById() {
		Comment found = commentService.findOne(COMMENT_ID);
		assertEquals(COMMENT_ID, found.getId());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testCreate() throws Exception {
		RegisteredUser ru = new RegisteredUser();
		ru.setId(DB_USER_ID); // 4
		CulturalOffer co = new CulturalOffer();
		co.setId(DB_CULTURAL_OFFER_ID2); // 2
		Comment comment = new Comment(NEW_COMMENT_TEXT, NEW_COMMENT_DATE, ru, co);
		Comment created = commentService.create(comment);

		assertEquals(NEW_COMMENT_TEXT, created.getText());
		assertTrue(created.getActive());
	}
	
	@Test
	public void testCreate_NonexistentUser() throws Exception {
		RegisteredUser ru = new RegisteredUser();
		ru.setId(DB_FALSE_USER_ID); // 10
		CulturalOffer co = new CulturalOffer();
		co.setId(DB_CULTURAL_OFFER_ID); // 1
		Comment comment = new Comment(NEW_COMMENT_TEXT, NEW_COMMENT_DATE, ru, co);
		Throwable exception = assertThrows(Exception.class, () -> {
			Comment created = commentService.create(comment);
		});

		assertEquals("User does not exist", exception.getMessage());
	}
	
	@Test
	public void testCreate_NonexistentCulturalOffer() throws Exception {
		RegisteredUser ru = new RegisteredUser();
		ru.setId(DB_USER_ID); // 4
		CulturalOffer co = new CulturalOffer();
		co.setId(DB_FALSE_CULTURAL_OFFER_ID); // 5
		Comment comment = new Comment(NEW_COMMENT_TEXT, NEW_COMMENT_DATE, ru, co);
		Throwable exception = assertThrows(Exception.class, () -> {
			Comment created = commentService.create(comment);
		});

		assertEquals("Cultural offer does not exist", exception.getMessage());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testUpdate() throws Exception {
		RegisteredUser ru = new RegisteredUser();
		ru.setId(DB_USER_ID); // 4
		CulturalOffer co = new CulturalOffer();
		co.setId(DB_CULTURAL_OFFER_ID); // 1
		Comment comment = new Comment(UPDATED_COMMENT_TEXT, UPDATED_COMMENT_DATE, ru, co);
		Comment created = commentService.update(comment, COMMENT_ID); // 1

		assertEquals(UPDATED_COMMENT_TEXT, created.getText());
	}
	
	@Test
	public void testUpdate_GivenIdDoesntExist() throws Exception {
		RegisteredUser ru = new RegisteredUser();
		ru.setId(DB_USER_ID); // 4
		CulturalOffer co = new CulturalOffer();
		co.setId(DB_CULTURAL_OFFER_ID); // 1
		Comment comment = new Comment(UPDATED_COMMENT_TEXT, UPDATED_COMMENT_DATE, ru, co);
		
		Throwable exception = assertThrows(Exception.class, () -> {
			Comment created = commentService.update(comment, DB_FALSE_COMMENT_ID);
		});
		assertEquals("Comment with given id doesn't exist", exception.getMessage());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testDelete() throws Exception {
		commentService.delete(COMMENT_ID); // 1
		Comment comment= commentService.findOne(COMMENT_ID);
		assertNull(comment);
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testDelete_GivenIdDoesntExist() throws Exception {		
		Throwable exception = assertThrows(Exception.class, () -> {
			commentService.delete(DB_FALSE_COMMENT_ID); 
		});
		assertEquals("Comment with given id doesn't exist", exception.getMessage());
		Comment comment = commentService.findOne(COMMENT_ID);
		assertNotNull(comment);
	}
}

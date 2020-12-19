package main.kts.service;

import static main.kts.constants.CommentConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import main.kts.model.Comment;
import main.kts.model.CulturalOffer;
import main.kts.model.RegisteredUser;
import main.kts.repository.CommentRepository;
import main.kts.repository.CulturalOfferRepository;
import main.kts.repository.RegisteredUserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CommentServiceUnitTest {

	@Autowired
	private CommentService commentService;

	@MockBean
	private CommentRepository commentRepository;

	@MockBean
	private RegisteredUserRepository registeredUserRepository;

	@MockBean
	private CulturalOfferRepository culturalOfferRepository;

	@Before
	public void setup() {
		List<Comment> comments = new ArrayList<Comment>();
		RegisteredUser existingRegisteredUser = new RegisteredUser(DB_REGISTERED_USER_ID);
		CulturalOffer existingCulturalOffer1 = new CulturalOffer(DB_CULTURAL_OFFER_ID);
		CulturalOffer existingCulturalOffer2 = new CulturalOffer(DB_CULTURAL_OFFER_ID2);
		comments.add(new Comment(DB_COMMENT_TEXT, DB_COMMENT_DATE, existingRegisteredUser, existingCulturalOffer1));

		given(commentRepository.findByActive(true)).willReturn(comments);

		Comment comment = new Comment(NEW_COMMENT_TEXT, NEW_COMMENT_DATE, existingRegisteredUser, existingCulturalOffer2);
		Comment updateComment = new Comment(UPDATED_COMMENT_TEXT, UPDATED_COMMENT_DATE, existingRegisteredUser, existingCulturalOffer1);

		Comment savedComment = new Comment(NEW_COMMENT_TEXT, NEW_COMMENT_DATE, existingRegisteredUser, existingCulturalOffer2);
		savedComment.setId(COMMENT_ID);
		Comment updatedComment= new Comment(UPDATED_COMMENT_TEXT, UPDATED_COMMENT_DATE, existingRegisteredUser, existingCulturalOffer1);
		updatedComment.setId(COMMENT_ID);

		CulturalOffer savedCO = new CulturalOffer(DB_CULTURAL_OFFER_ID2);

		Comment commentFound = new Comment(DB_COMMENT_ID, DB_COMMENT_TEXT, DB_COMMENT_DATE, existingRegisteredUser, existingCulturalOffer1);
		given(registeredUserRepository.findById(DB_REGISTERED_USER_ID)).willReturn(Optional.of(existingRegisteredUser));
		given(registeredUserRepository.findById(FALSE_USER_ID)).willReturn(null);

		given(culturalOfferRepository.findById(DB_CULTURAL_OFFER_ID2)).willReturn(Optional.of(existingCulturalOffer2));
		given(culturalOfferRepository.findById(DB_CULTURAL_OFFER_ID)).willReturn(Optional.of(existingCulturalOffer1));
		given(culturalOfferRepository.findById(FALSE_CULTURAL_OFFER_ID)).willReturn(null);
		given(culturalOfferRepository.save(existingCulturalOffer2)).willReturn(savedCO);

		//imalo bi smisla i ovo da se trazi preko korisnika i kulturne ponude?
		given(commentRepository.findAllByCulturalOfferId(DB_CULTURAL_OFFER_ID)).willReturn(comments);
		given(commentRepository.findAllByCulturalOfferId(DB_CULTURAL_OFFER_ID2)).willReturn(new ArrayList<Comment>());
		given(commentRepository.save(comment)).willReturn(savedComment);
		given(commentRepository.save(updateComment)).willReturn(updatedComment);
		given(commentRepository.findById(COMMENT_ID)).willReturn(Optional.of(comment));
		given(commentRepository.findById(FALSE_COMMENT_ID)).willReturn(null);

		doNothing().when(commentRepository).delete(savedComment);

	}

	@Test
	public void testFindAll() {
		List<Comment> found = commentService.findAll();

		verify(commentRepository, times(1)).findByActive(true);
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.size());
	}


	@Test
	public void testCreate() throws Exception {
		RegisteredUser existingRegisteredUser = new RegisteredUser(DB_REGISTERED_USER_ID);
		CulturalOffer existingCulturalOffer2 = new CulturalOffer(DB_CULTURAL_OFFER_ID2);
		Comment comment = new Comment(NEW_COMMENT_TEXT, NEW_COMMENT_DATE, existingRegisteredUser, existingCulturalOffer2);
		Comment created = commentService.create(comment);
		
		verify(commentRepository, times(1)).save(comment);
		verify(registeredUserRepository, times(1)).findById(DB_REGISTERED_USER_ID);
		verify(culturalOfferRepository, times(1)).findById(DB_CULTURAL_OFFER_ID2);

		assertEquals(NEW_COMMENT_TEXT, created.getText());
	}


	@Test
	/**
	 * Given user id doesn't exists
	 * 
	 */
	public void testCreate_NonexistentUser() throws Exception {
		RegisteredUser nonexistingRegisteredUser = new RegisteredUser(FALSE_USER_ID);
		CulturalOffer existingCulturalOffer1 = new CulturalOffer(DB_CULTURAL_OFFER_ID);
		Comment comment = new Comment(NEW_COMMENT_TEXT, NEW_COMMENT_DATE, nonexistingRegisteredUser, existingCulturalOffer1);

		Throwable exception = assertThrows(Exception.class, () -> {
			Comment created = commentService.create(comment);
		});

		verify(registeredUserRepository, times(1)).findById(FALSE_USER_ID);
		assertEquals("User does not exist", exception.getMessage());
	}

	@Test
	/**
	 * Given cultural offer id doesn't exists
	 * 
	 */
	public void testCreate_NonexistentCulturalOffer() throws Exception {

		RegisteredUser existingRegisteredUser = new RegisteredUser(DB_REGISTERED_USER_ID);
		CulturalOffer nonexistingCulturalOffer1 = new CulturalOffer(FALSE_CULTURAL_OFFER_ID);
		Comment comment = new Comment(NEW_COMMENT_TEXT,NEW_COMMENT_DATE, existingRegisteredUser, nonexistingCulturalOffer1);

		Throwable exception = assertThrows(Exception.class, () -> {
			Comment created = commentService.create(comment);
		});

		verify(culturalOfferRepository, times(1)).findById(FALSE_CULTURAL_OFFER_ID);
		assertEquals("Cultural offer does not exist", exception.getMessage());
	}

	@Test
	public void testUpdate() throws Exception {
		RegisteredUser existingRegisteredUser = new RegisteredUser(DB_REGISTERED_USER_ID);
		CulturalOffer existingCulturalOffer1 = new CulturalOffer(DB_CULTURAL_OFFER_ID);
		Comment comment= new Comment(UPDATED_COMMENT_TEXT, UPDATED_COMMENT_DATE, existingRegisteredUser, existingCulturalOffer1);
		Comment created = commentService.update(comment, COMMENT_ID);

		verify(commentRepository, times(1)).findById(COMMENT_ID);
		verify(commentRepository, times(1)).save(comment);

		assertEquals(UPDATED_COMMENT_TEXT, created.getText());
	}

	@Test
	/***
	 * Given comment id doesn't exist
	 */
	public void testUpdate_GivenIdDoesntExist() throws Exception {
		RegisteredUser existingRegisteredUser = new RegisteredUser(DB_REGISTERED_USER_ID);
		CulturalOffer existingCulturalOffer1 = new CulturalOffer(DB_CULTURAL_OFFER_ID);
		Comment comment = new Comment(UPDATED_COMMENT_TEXT, UPDATED_COMMENT_DATE, existingRegisteredUser, existingCulturalOffer1);

		Throwable exception = assertThrows(Exception.class, () -> {
			Comment created = commentService.update(comment, FALSE_COMMENT_ID);
		});
		verify(commentRepository, times(1)).findById(FALSE_COMMENT_ID);
		assertEquals("Comment with given id doesn't exist", exception.getMessage());
	}

	@Test
	public void testDelete() throws Exception {
		RegisteredUser existingRegisteredUser = new RegisteredUser(DB_REGISTERED_USER_ID);
		CulturalOffer existingCulturalOffer1 = new CulturalOffer(DB_CULTURAL_OFFER_ID);
		Comment comment = new Comment(NEW_COMMENT_TEXT, NEW_COMMENT_DATE, existingRegisteredUser, existingCulturalOffer1);
		commentService.delete(COMMENT_ID);

		verify(commentRepository, times(1)).findById(COMMENT_ID);
		verify(commentRepository, times(1)).save(comment);

	}

	@Test
	public void testDelete_GivenFalseId() throws Exception {
		Throwable exception = assertThrows(Exception.class, () -> {
			commentService.delete(FALSE_COMMENT_ID);
		});
		verify(commentRepository, times(1)).findById(FALSE_COMMENT_ID);
		assertEquals("Comment with given id doesn't exist", exception.getMessage());
	}

}

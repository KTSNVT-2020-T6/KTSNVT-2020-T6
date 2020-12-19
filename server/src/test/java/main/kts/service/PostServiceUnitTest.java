package main.kts.service;

import static main.kts.constants.PostConstants.*;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import main.kts.model.CulturalOffer;
import main.kts.model.Image;
import main.kts.model.Post;
import main.kts.model.Rate;
import main.kts.model.RegisteredUser;
import main.kts.repository.ImageRepository;
import main.kts.repository.PostRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class PostServiceUnitTest {

	@Autowired
	private PostService postService;

	@MockBean
	private PostRepository postRepository;

	@MockBean
	private ImageRepository imageRepository;

	

	@Before
	public void setup() {
		List<Post> posts = new ArrayList<Post>();
		Image existingImage = new Image(DB_IMAGE_ID,DB_IMAGE_NAME, DB_IMAGE_RELATIVE_PATH);
		Image existingImage1 = new Image(DB_IMAGE_ID1,DB_IMAGE_NAME1, DB_IMAGE_RELATIVE_PATH1);
		posts.add(new Post(DB_POST_TEXT, DB_POST_DATE, existingImage));

		given(postRepository.findByActive(true)).willReturn(posts);
		
		Post post = new Post(NEW_POST_TEXT, NEW_POST_DATE, existingImage1);
		Post updatePost = new Post(UPDATED_POST_TEXT, UPDATED_POST_DATE, existingImage);

		Post savedPost = new Post(NEW_POST_TEXT, NEW_POST_DATE, existingImage1);
		savedPost.setId(POST_ID);
		Post updatedPost= new Post(UPDATED_POST_TEXT, UPDATED_POST_DATE, existingImage);
		updatedPost.setId(POST_ID);

		Post postFound = new Post(DB_POST_TEXT, DB_POST_DATE, existingImage);
		
		given(postRepository.findOneByImageId(DB_IMAGE_ID)).willReturn(postFound);
		given(postRepository.findOneByImageId(DB_IMAGE_ID1)).willReturn(null);
		given(postRepository.save(post)).willReturn(savedPost);
		given(postRepository.save(updatePost)).willReturn(updatedPost);
		given(postRepository.findById(POST_ID)).willReturn(Optional.of(post));
		given(postRepository.findById(FALSE_POST_ID)).willReturn(null);

		doNothing().when(postRepository).delete(savedPost);

	}

	@Test
	public void testFindAll() {
		List<Post> found = postService.findAll();

		verify(postRepository, times(1)).findByActive(true);
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.size());
	}


	@Test
	public void testCreate() throws Exception {
		Image existingImage1 = new Image(DB_IMAGE_ID1,DB_IMAGE_NAME1, DB_IMAGE_RELATIVE_PATH1);
		Post post = new Post(NEW_POST_TEXT, NEW_POST_DATE, existingImage1);
		Post created = postService.create(post);

		//verify(postRepository, times(1)).findOneByImageId(DB_IMAGE_ID1);
		verify(postRepository, times(1)).save(post);
		//verify(imageRepository, times(1)).findById(DB_IMAGE_ID1);

		assertEquals(NEW_POST_TEXT, created.getText());
	}

	
	@Test
	public void testUpdate() throws Exception {
		Image existingImage = new Image(DB_IMAGE_ID,DB_IMAGE_NAME, DB_IMAGE_RELATIVE_PATH);
		Post post = new Post(UPDATED_POST_TEXT, UPDATED_POST_DATE, existingImage);
		Post created = postService.update(post, POST_ID);

		verify(postRepository, times(1)).findById(POST_ID);
		verify(postRepository, times(1)).save(post);
		//verify(postRepository, times(1)).findId(DB_CULTURAL_OFFER_ID);
		//verify(imageRepository, times(1)).findById(DB_IMAGE_ID);
		//verify(culturalOfferRepository, times(1)).save(existingCulturalOffer1);

		assertEquals(UPDATED_POST_TEXT, created.getText());
	}

	@Test
	/***
	 * Given post id doesn't exist
	 */
	public void testUpdate_GivenIdDoesntExist() throws Exception {
		Image existingImage = new Image(DB_IMAGE_ID,DB_IMAGE_NAME, DB_IMAGE_RELATIVE_PATH);
		Post post = new Post(UPDATED_POST_TEXT, UPDATED_POST_DATE, existingImage);
		
		Throwable exception = assertThrows(Exception.class, () -> {
			Post created = postService.update(post, FALSE_POST_ID);
		});
		verify(postRepository, times(1)).findById(FALSE_POST_ID);
		assertEquals("Post with given id doesn't exist", exception.getMessage());
	}

	@Test
	public void testDelete() throws Exception {
		Image existingImage = new Image(DB_IMAGE_ID,DB_IMAGE_NAME, DB_IMAGE_RELATIVE_PATH);
		Post post = new Post(NEW_POST_TEXT, NEW_POST_DATE, existingImage);
		postService.delete(POST_ID);

		verify(postRepository, times(1)).findById(POST_ID);
		verify(postRepository, times(1)).save(post);

	}

	@Test
	public void testDelete_GivenFalseId() throws Exception {
		Throwable exception = assertThrows(Exception.class, () -> {
			postService.delete(FALSE_POST_ID);
		});
		verify(postRepository, times(1)).findById(FALSE_POST_ID);
		assertEquals("Post with given id doesn't exist", exception.getMessage());
	}

}

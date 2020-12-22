package main.kts.repository;

import static main.kts.constants.PostConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import main.kts.model.Post;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class PostRepositoryIntegrationTest {

	@Autowired 
	private PostRepository postRepository;
	
	@Test
    public void testFindByActive() {
        List<Post> found = postRepository.findByActive(true);
        assertEquals(DB_SIZE, found.size());
    }
	
	@Test
    public void testFindByActivePage() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        Page<Post> found = postRepository.findByActive(pageable, true);
        assertEquals(DB_SIZE, found.getSize());
    }

    @Test
    public void testFindByIdAndActive() {
        Optional<Post> found = postRepository.findByIdAndActive(DB_POST_ID, true);
        Post post = found.orElse(null);
        assertEquals(post.getText(), DB_POST_TEXT);
    }
    
    @Test
    public void testFindByIdAndActive_GivenFalseId() {
        Optional<Post> found = postRepository.findByIdAndActive(DB_FALSE_POST_ID, true);
        Post post = found.orElse(null);
        assertNull(post);
    }
    
    @Test
    public void testFindOneByImageId() {
        Post found = postRepository.findOneByImageId(DB_IMAGE_ID);
        assertEquals(DB_POST_ID, found.getId());
    }
    
}

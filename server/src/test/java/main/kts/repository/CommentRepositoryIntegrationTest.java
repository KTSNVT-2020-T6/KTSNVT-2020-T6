package main.kts.repository;

import static main.kts.constants.CommentConstants.DB_COMMENT_ID;
import static main.kts.constants.CommentConstants.DB_COMMENT_TEXT;
import static main.kts.constants.CommentConstants.DB_CULTURAL_OFFER_ID;
import static main.kts.constants.CommentConstants.DB_FALSE_COMMENT_ID;
import static main.kts.constants.CommentConstants.DB_SIZE;
import static main.kts.constants.CommentConstants.DB_SIZE_BY_CO;
import static main.kts.constants.CommentConstants.PAGEABLE_PAGE;
import static main.kts.constants.CommentConstants.PAGEABLE_SIZE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import main.kts.model.Comment;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:test.properties")
public class CommentRepositoryIntegrationTest {

	@Autowired
	private CommentRepository commentRepository;
	
	@Test
    public void testFindByActive() {
        List<Comment> found = commentRepository.findByActive(true);
        assertEquals(DB_SIZE, found.size());
    }
	
	@Test
    public void testFindByActivePage() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        Page<Comment> found = commentRepository.findByActive(pageable, true);
        assertEquals(DB_SIZE, found.getSize());
    }

    @Test
    public void testFindByIdAndActive() {
        Optional<Comment> found = commentRepository.findByIdAndActive(DB_COMMENT_ID, true);
        Comment comment = found.orElse(null);
        assertEquals(comment.getText(), DB_COMMENT_TEXT);
    }
    
    @Test
    public void testFindByIdAndActive_GivenFalseId() {
        Optional<Comment> found = commentRepository.findByIdAndActive(DB_FALSE_COMMENT_ID, true);
        Comment comment= found.orElse(null);
        assertNull(comment);
    }
    
    @Test
    public void testFindAllByCulturalOfferId() {
        List<Comment> found = commentRepository.findAllByCulturalOfferId(DB_CULTURAL_OFFER_ID);
        assertEquals(DB_SIZE_BY_CO, found.size());
    }

}

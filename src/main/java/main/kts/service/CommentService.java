package main.kts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.kts.model.Comment;
import main.kts.repository.CommentRepository;
import main.kts.repository.CulturalOfferRepository;
import main.kts.repository.ImageRepository;
import main.kts.repository.RegisteredUserRepository;

@Service
public class CommentService implements ServiceInterface<Comment> {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private RegisteredUserRepository registeredUserRepository;

	@Autowired
	private CulturalOfferRepository culturalOfferRepository;

	@Autowired
	ImageRepository imageRepository;

	@Override
	public List<Comment> findAll() {
		return commentRepository.findAll();
	}

	@Override
	public Comment findOne(Long id) {
		return commentRepository.findById(id).orElse(null);
	}

	@Override
	public Comment create(Comment entity) throws Exception {
		// if user or offer or image don't exist 
		if (registeredUserRepository.findById(entity.getRegistredUser().getId()).orElse(null) == null)
			throw new Exception("User doesn't exist");
		if (culturalOfferRepository.findById(entity.getCulturalOffer().getId()).orElse(null) == null)
			throw new Exception("Cultural offer doesn't exist");
		if (imageRepository.findById(entity.getImage().getId()).orElse(null) == null)
			throw new Exception("Image doesn't exist");
		
		// make new comment instance
		Comment c = new Comment();
		c.setCulturalOffer(entity.getCulturalOffer());
		c.setDate(entity.getDate());
		c.setImage(entity.getImage());
		c.setRegistredUser(entity.getRegistredUser());
		c.setText(entity.getText());
		c.setActive(true);
		c = commentRepository.save(c);
		return c;
	}

	@Override
	public Comment update(Comment entity, Long id) throws Exception {
		Comment existingComment = commentRepository.findById(id).orElse(null);
		if(existingComment == null) {
			throw new Exception("Comment with given id doesn't exist");
		}
		
		existingComment.setText(entity.getText());
		existingComment.setImage(entity.getImage());
		existingComment.setDate(entity.getDate());
		return commentRepository.save(existingComment);
	}

	@Override
	public void delete(Long id) throws Exception {
		Comment existingComment = commentRepository.findById(id).orElse(null);
		if(existingComment == null) {
			throw new Exception("Comment with given id doesn't exist");
		}
		existingComment.setActive(false);
		commentRepository.save(existingComment);	
	}

	public Page<Comment> findAll(Pageable pageable) {
		return commentRepository.findAll(pageable);
	}

	public List<Comment> findAllByCulturalOfferId(Long id) {
		return commentRepository.findAllByCulturalOfferId(id);
	}

}

package main.kts.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.kts.model.Comment;
import main.kts.model.CulturalOffer;
import main.kts.model.RegisteredUser;
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
		return commentRepository.findByActive(true);
	}

	@Override
	public Comment findOne(Long id) {
		return commentRepository.findByIdAndActive(id, true).orElse(null);
	}

	@Override
	public Comment create(Comment entity) throws Exception {
		Optional<RegisteredUser> ru = registeredUserRepository.findById(entity.getRegistredUser().getId());
		if(!ru.isPresent()) {
			throw new Exception("User does not exist");	
		}
		Optional<CulturalOffer> offer = culturalOfferRepository.findById(entity.getCulturalOffer().getId());
		if(!offer.isPresent()) {
			throw new Exception("Cultural offer does not exist");
		}

		// make new comment instance
		Comment c = new Comment();
		c.setCulturalOffer(offer.orElse(null));
		c.setDate(entity.getDate());
		c.setImage(entity.getImage());
		c.setRegistredUser(ru.orElse(null));
		c.setText(entity.getText());
		c.setActive(true);
		c = commentRepository.save(c);
		return c;
	}

	@Override
	public Comment update(Comment entity, Long id) throws Exception {
		Optional<Comment> optComment = commentRepository.findById(id);
		if(!optComment.isPresent()) {
			throw new Exception("Comment with given id doesn't exist");
		}
		
		Optional<CulturalOffer> offer = culturalOfferRepository.findById(entity.getCulturalOffer().getId());
		if(!offer.isPresent()) {
			throw new Exception("Cultural offer does not exist");
		}
		Comment existingComment = optComment.orElse(null);
		
		existingComment.setText(entity.getText());
		existingComment.setImage(entity.getImage());
		existingComment.setDate(entity.getDate());
		return commentRepository.save(existingComment);
	}

	@Override
	public void delete(Long id) throws Exception {
		Optional<Comment> optComment = commentRepository.findById(id);
		if(!optComment.isPresent()) {
			throw new Exception("Comment with given id doesn't exist");
		}
		Comment existingComment = optComment.orElse(null);
		existingComment.setActive(false);
		commentRepository.save(existingComment);		
	}

	public Page<Comment> findAll(Pageable pageable) {
		return commentRepository.findByActive(pageable, true);
	}

	public List<Comment> findAllByCulturalOfferId(Long id) {
		return commentRepository.findAllByCulturalOfferId(id);
	}

}

package main.kts.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.kts.dto.CommentDTO;
import main.kts.helper.CommentMapper;
import main.kts.model.Comment;
import main.kts.model.CulturalOffer;
import main.kts.model.Image;
import main.kts.model.RegisteredUser;
import main.kts.service.CommentService;
import main.kts.service.CulturalOfferService;
import main.kts.service.ImageService;
import main.kts.service.RegisteredUserService;

@RestController
@RequestMapping(value = "/api/comment", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {
	
	@Autowired
	private CommentService commentService;

	private CommentMapper commentMapper;

	@Autowired
	private ImageService imageService;
	@Autowired
	private CulturalOfferService culturalOfferService;
	@Autowired
	private RegisteredUserService registeredUserService;
	
	public CommentController() {
		commentMapper = new CommentMapper();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CommentDTO>> getAllComments() {
		List<Comment> comments = commentService.findAll();

		return new ResponseEntity<>(toCommentDTOList(comments), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<CommentDTO> getComment(@PathVariable Long id) {
		Comment comment = commentService.findOne(id);
		if (comment == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(commentMapper.toDto(comment), HttpStatus.OK);
	}

//    @RequestMapping(value = "/page", method=RequestMethod.GET)
//    public ResponseEntity<Page<CommentDTO>> loadCharactersPage(Pageable pageable) {
//    	Page<Comment> comments = commentService.findAllPage(pageable);
//    	if(comments == null){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    	return new ResponseEntity<>(toCommentDTOPage(comments), HttpStatus.OK);
//    }

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO) {
		Comment comment;
		Image image;
		CulturalOffer culturalOffer;
		RegisteredUser registeredUser;
		if (!this.validateCommentDTO(commentDTO))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		try {
			image = imageService.findOne(commentDTO.getImageDTO().getId());
			culturalOffer = culturalOfferService.findOne(commentDTO.getCulturalOfferDTO().getId());
			registeredUser = registeredUserService.findOne(commentDTO.getRegisteredUserDTO().getId());
			comment = commentMapper.toEntity(commentDTO);
			comment.setImage(image);
			comment.setCulturalOffer(culturalOffer);
			comment.setRegistredUser(registeredUser);
			comment = commentService.create(comment);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(commentMapper.toDto(comment), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CommentDTO> updateComment(@RequestBody CommentDTO commentDTO, @PathVariable Long id) {
		Comment comment;
		Image image;
		CulturalOffer culturalOffer;
		RegisteredUser registeredUser;
		try {
			image = imageService.findOne(commentDTO.getImageDTO().getId());
			culturalOffer = culturalOfferService.findOne(commentDTO.getCulturalOfferDTO().getId());
			registeredUser = registeredUserService.findOne(commentDTO.getRegisteredUserDTO().getId());
			comment = commentMapper.toEntity(commentDTO);
			comment.setImage(image);
			comment.setCulturalOffer(culturalOffer);
			comment.setRegistredUser(registeredUser);
			comment = commentService.update(comment, id);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(commentMapper.toDto(comment), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteComment(@PathVariable Long id) {
		try {
			commentService.delete(id);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>("OK", HttpStatus.OK);
	}

	private List<CommentDTO> toCommentDTOList(List<Comment> comments) {
		List<CommentDTO> commentDTOS = new ArrayList<>();
		for (Comment comment : comments) {
			commentDTOS.add(commentMapper.toDto(comment));
		}
		return commentDTOS;
	}

//	private Object toCommentDTOPage(Page<Comment> comments) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	private boolean validateCommentDTO(CommentDTO commentDTO) {
		if (commentDTO.getRegistredUserDTO() == null)
			return false;
		if (commentDTO.getCulturalOfferDTO() == null)
			return false;
		if(commentDTO.getDate() == null) 
			return false;
		if(commentDTO.getText() == null) 
			return false;
		if(commentDTO.getDate().before(new Date()))
			return false;
		return true;
	}
}

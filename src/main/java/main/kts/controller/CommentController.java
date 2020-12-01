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
import main.kts.service.CommentService;

@RestController
@RequestMapping(value = "/api/comment", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {
	
	@Autowired
	private CommentService commentService;

	private CommentMapper commentMapper;

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

		if (!this.validateCommentDTO(commentDTO))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		try {
			comment = commentService.create(commentMapper.toEntity(commentDTO));
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(commentMapper.toDto(comment), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CommentDTO> updateComment(@RequestBody CommentDTO commentDTO, @PathVariable Long id) {
		Comment comment;
		try {
			comment = commentService.update(commentMapper.toEntity(commentDTO), id);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(commentMapper.toDto(comment), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
		try {
			commentService.delete(id);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(HttpStatus.OK);
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

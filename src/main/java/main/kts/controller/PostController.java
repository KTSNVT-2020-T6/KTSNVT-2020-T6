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

import main.kts.dto.PostDTO;
import main.kts.helper.PostMapper;
import main.kts.model.Post;
import main.kts.service.PostService;

@RestController
@RequestMapping(value = "/api/post", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostController {
	
	@Autowired
	private PostService postService;

	private PostMapper postMapper;

	public PostController() {
		postMapper = new PostMapper();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<PostDTO>> getAllPosts() {
		List<Post> posts = postService.findAll();

		return new ResponseEntity<>(toPostDTOList(posts), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<PostDTO> getPost(@PathVariable Long id) {
		Post post = postService.findOne(id);
		if (post == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(postMapper.toDto(post), HttpStatus.OK);
	}

//    @RequestMapping(value = "/page", method=RequestMethod.GET)
//    public ResponseEntity<Page<PostDTO>> loadCharactersPage(Pageable pageable) {
//    	Page<Post> posts = postService.findAllPage(pageable);
//    	if(posts == null){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    	return new ResponseEntity<>(toPostDTOPage(posts), HttpStatus.OK);
//    }

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
		Post post;

		if (!this.validatePostDTO(postDTO))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		try {
			post = postService.create(postMapper.toEntity(postDTO));
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(postMapper.toDto(post), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable Long id) {
		Post post;
		try {
			post = postService.update(postMapper.toEntity(postDTO), id);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(postMapper.toDto(post), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletePost(@PathVariable Long id) {
		try {
			postService.delete(id);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	private List<PostDTO> toPostDTOList(List<Post> posts) {
		List<PostDTO> postDTOS = new ArrayList<>();
		for (Post post : posts) {
			postDTOS.add(postMapper.toDto(post));
		}
		return postDTOS;
	}

	
//	private Object toPostDTOPage(Page<Post> posts) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	private boolean validatePostDTO(PostDTO postDTO) {
		if(postDTO.getDate() == null) 
			return false;
		if(postDTO.getText() == null) 
			return false;
		if(postDTO.getDate().before(new Date()))
			return false;
		return true;
		
	}
}

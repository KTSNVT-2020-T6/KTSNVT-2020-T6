package main.kts.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.kts.dto.PostDTO;
import main.kts.helper.PostMapper;
import main.kts.model.CulturalOffer;
import main.kts.model.Image;
import main.kts.model.Post;
import main.kts.service.CulturalOfferService;
import main.kts.service.ImageService;
import main.kts.service.PostService;

@RestController
@RequestMapping(value = "/api/post", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostController {
	
	@Autowired
	private PostService postService;

	private PostMapper postMapper;

	@Autowired
	private ImageService imageService;
	
	@Autowired
	private CulturalOfferService culturalOfferService;
	
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

    @RequestMapping(value="/",method=RequestMethod.GET)
    public ResponseEntity<Page<PostDTO>> loadPostPage(Pageable pageable) {
    	Page<Post> posts = postService.findAll(pageable);
    	if(posts == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    	Page<PostDTO> postsDTO = toPostDTOPage(posts);
    	return new ResponseEntity<>(postsDTO, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
		Post post;
		Image image;
		CulturalOffer culturalOffer;
		
		if (!this.validatePostDTO(postDTO)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		try {
			culturalOffer = culturalOfferService.findOne(postDTO.getCulturalOfferId());
			image = imageService.findOne(postDTO.getImageDTO().getId());
			post = postMapper.toEntity(postDTO);
			post.setImage(image);
			post = postService.create(post);
			culturalOffer.getPost().add(post);
			culturalOfferService.saveAndSendMail(culturalOffer);
			
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(postMapper.toDto(post), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable Long id) {
		Post post;
		Image image;
		if (!this.validatePostDTO(postDTO)) {
			System.out.println("EVOMENE");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		try {
			image = imageService.findOne(postDTO.getImageDTO().getId());
			post = postMapper.toEntity(postDTO);
			post.setImage(image);
			post = postService.update(post, id);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(postMapper.toDto(post), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deletePost(@PathVariable Long id) {
		try {
			postService.delete(id);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>("OK", HttpStatus.OK);
	}

	private List<PostDTO> toPostDTOList(List<Post> posts) {
		List<PostDTO> postDTOS = new ArrayList<>();
		for (Post post : posts) {
			postDTOS.add(postMapper.toDto(post));
		}
		return postDTOS;
	}

	private Page<PostDTO> toPostDTOPage(Page<Post> posts) {
		Page<PostDTO> dtoPage = posts.map(new Function<Post, PostDTO>() {
		    @Override
		    public PostDTO apply(Post entity) {
		    	// dobaviti id kulturne ponude za dto objekat
		    	Long coId = postService.findCulturalOfferIdByPostId(entity.getId());
		    	PostDTO dto = postMapper.toDto(entity);
		    	dto.setCulturalOfferId(coId);
		        return dto;
		    }
		});
		return dtoPage;
	}

	private boolean validatePostDTO(PostDTO postDTO) {
		if(postDTO.getDate() == null) 
			return false;
		if(postDTO.getText() == null) 
			return false;
		if(postDTO.getDate().before(new Date())) 
			return false;
		if(postDTO.getCulturalOfferId() == null) 
			return false;
		

		return true;
		
	}
}

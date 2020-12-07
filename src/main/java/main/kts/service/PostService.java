package main.kts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.kts.model.Post;
import main.kts.model.RegisteredUser;
import main.kts.repository.ImageRepository;
import main.kts.repository.PostRepository;

@Service
public class PostService implements ServiceInterface<Post> {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private ImageRepository imageRepository;

	@Override
	public List<Post> findAll() {
		return postRepository.findByActive(true);
	}

	@Override
	public Post findOne(Long id) {
		return postRepository.findByIdAndActive(id, true).orElse(null);
	}

	@Override
	public Post create(Post entity) throws Exception {
		// if image doesn't exist
		if (imageRepository.findById(entity.getImage().getId()).orElse(null) == null)
			throw new Exception("Image doesn't exist");

		// make new post instance
		Post p= new Post();
		p.setDate(entity.getDate());
		p.setImage(entity.getImage());
		p.setText(entity.getText());
		p.setActive(true);
		
		p = postRepository.save(p);
		return p;
	}

	@Override
	public Post update(Post entity, Long id) throws Exception {
		Post existingPost = postRepository.findById(id).orElse(null);
		if(existingPost == null) {
			throw new Exception("Post with given id doesn't exist");
		}
		
		existingPost.setImage(entity.getImage());
		existingPost.setDate(entity.getDate());
		existingPost.setText(entity.getText());
		return postRepository.save(existingPost);
	}

	@Override
	public void delete(Long id) throws Exception {
		Post existingPost = postRepository.findById(id).orElse(null);
		if(existingPost == null) {
			throw new Exception("Post with given id doesn't exist");
		}
		existingPost.setActive(false);
		postRepository.save(existingPost);
	}

	public Page<Post> findAll(Pageable pageable) {
		return postRepository.findByActive(pageable, true);
	}

}

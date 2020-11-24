package main.kts.service;

import java.util.List;

import org.springframework.stereotype.Service;

import main.kts.model.Post;

@Service
public class PostService implements ServiceInterface<Post>{

	@Override
	public List<Post> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Post findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Post create(Post entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Post update(Post entity, Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}

}

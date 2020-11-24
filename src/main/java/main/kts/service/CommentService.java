package main.kts.service;

import java.util.List;

import org.springframework.stereotype.Service;

import main.kts.model.Comment;

@Service
public class CommentService implements ServiceInterface<Comment>{

	@Override
	public List<Comment> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comment findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comment create(Comment entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comment update(Comment entity, Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}

}

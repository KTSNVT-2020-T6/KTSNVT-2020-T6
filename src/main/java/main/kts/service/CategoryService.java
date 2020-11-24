package main.kts.service;

import java.util.List;

import org.springframework.stereotype.Service;

import main.kts.model.Category;

@Service
public class CategoryService implements ServiceInterface<Category>{

	@Override
	public List<Category> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category create(Category entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category update(Category entity, Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}

}

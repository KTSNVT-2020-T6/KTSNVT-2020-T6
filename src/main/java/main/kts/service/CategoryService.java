package main.kts.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.kts.model.Category;
import main.kts.model.Type;
import main.kts.repository.CategoryRepository;

@Service
public class CategoryService implements ServiceInterface<Category>{

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	@Override
	public Category findOne(Long id) {
		return categoryRepository.findById(id).orElse(null);
	}

	@Override
	public Category create(Category entity) throws Exception {
		Category c = new Category();
		c.setName(entity.getName());
		c.setDescription(entity.getDescription());
		c.setType(new HashSet<Type>());
		return categoryRepository.save(c);
	}

	@Override
	public Category update(Category entity, Long id) throws Exception {
		Category existingCat = categoryRepository.findById(id).orElse(null);
		if(existingCat == null) {
			throw new Exception("Category with given id doesn't exist");
		}
		existingCat.setName(entity.getName());
		existingCat.setDescription(entity.getDescription());
		existingCat.setType(entity.getType());
		
		return categoryRepository.save(existingCat);
	}

	@Override
	public void delete(Long id) throws Exception {
		Category existingCat = categoryRepository.findById(id).orElse(null);
		if(existingCat == null) {
			throw new Exception("Category with given id doesn't exist");
		}
		categoryRepository.delete(existingCat);
		
	}

}

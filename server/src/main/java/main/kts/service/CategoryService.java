package main.kts.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.kts.model.Category;
import main.kts.model.Type;
import main.kts.repository.CategoryRepository;
import main.kts.repository.TypeRepository;

@Service
public class CategoryService implements ServiceInterface<Category>{

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private TypeRepository typeRepository;
	
	@Override
	public List<Category> findAll() {
		return categoryRepository.findByActive(true);
	}

	@Override
	public Category findOne(Long id) {
		return categoryRepository.findByIdAndActive(id, true).orElse(null);
	}

	@Override
	public Category create(Category entity) throws Exception {
		Category c = new Category();
		c.setName(entity.getName());
		c.setDescription(entity.getDescription());
		c.setType(new HashSet<Type>());
		c.setActive(true);
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
		
		for (Type type : existingCat.getType()) {
			type.setActive(false);
			typeRepository.save(type);
		}
		existingCat.setActive(false);
		categoryRepository.save(existingCat);
		
	}

	public Page<Category> findAll(Pageable pageable) {
		return categoryRepository.findByActive(pageable, true);
	}

}
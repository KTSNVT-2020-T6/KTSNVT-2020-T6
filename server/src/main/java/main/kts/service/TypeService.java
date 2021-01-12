package main.kts.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.kts.model.Category;
import main.kts.model.CulturalOffer;
import main.kts.model.Type;
import main.kts.repository.CategoryRepository;
import main.kts.repository.CulturalOfferRepository;
import main.kts.repository.TypeRepository;

@Service
public class TypeService implements ServiceInterface<Type>{
	
	@Autowired
	private TypeRepository typeRepository;

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CulturalOfferRepository culturalOfferRepository;

	@Override
	public List<Type> findAll() {
		return typeRepository.findByActive(true);
	}

	@Override
	public Type findOne(Long id) {
		return typeRepository.findByIdAndActive(id, true).orElse(null);
	}

	@Override
	public Type create(Type entity) throws Exception {
		Optional<Category> cat = categoryRepository.findById(entity.getCategory().getId());
		if(!cat.isPresent())
		{
			throw new Exception("Category does not exist");	
		}
		Category category = cat.orElse(null);
		Type t = new Type();
		// name, descripton, category
		t.setName(entity.getName());
		t.setDescription(entity.getDescription());
		t.setCategory(category);
		t.setActive(true);
		return typeRepository.save(t);
	}
	

	@Override
	public Type update(Type entity, Long id) throws Exception {
		Type existingT = typeRepository.findById(id).orElse(null);
		if(existingT == null) {
			throw new Exception("Type with given id doesn't exist");
		}
		existingT.setName(entity.getName());
		existingT.setDescription(entity.getDescription());
		existingT.setCategory(entity.getCategory());
		
		return typeRepository.save(existingT);
	}

	@Override
	public void delete(Long id) throws Exception {
		Optional<Type> optType = typeRepository.findById(id);
		if(!optType.isPresent()) {
			throw new Exception("Type with given id doesn't exist");
		}
		Type existingT = optType.orElse(null);
		
		List<CulturalOffer> co = culturalOfferRepository.findByTypeId(existingT.getId());
		if(co.size() > 0) {
			throw new Exception("Cultural offers with this type exist.");
		}
		existingT.setActive(false);
		typeRepository.save(existingT);
	}

	public Page<Type> findAll(Pageable pageable) {
		return typeRepository.findByActive(pageable, true);
	}

	public List<Type> findTypesOfCategory(Long id) {
		
		return typeRepository.findTypesOfCategory(id);
	}

	
	
}

package main.kts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.kts.model.Type;
import main.kts.repository.TypeRepository;

@Service
public class TypeService implements ServiceInterface<Type>{
	
	@Autowired
	private TypeRepository typeRepository;

	@Override
	public List<Type> findAll() {
		return typeRepository.findAll();
	}

	@Override
	public Type findOne(Long id) {
		return typeRepository.findById(id).orElse(null);
	}

	@Override
	public Type create(Type entity) throws Exception {
		Type t = new Type();
		// name, descripton, category
		t.setName(entity.getName());
		t.setDescription(entity.getDescription());
		t.setCategory(entity.getCategory());
		return t;
	}
	

	@Override
	public Type update(Type entity, Long id) throws Exception {
		Type existingT = typeRepository.findById(id).orElse(null);
		if(existingT == null) {
			throw new Exception("Type eith given id doesn't exist");
		}
		existingT.setName(entity.getName());
		existingT.setDescription(entity.getDescription());
		existingT.setCategory(entity.getCategory());
		
		return typeRepository.save(existingT);
	}

	@Override
	public void delete(Long id) throws Exception {
		Type existingT = typeRepository.findById(id).orElse(null);
		if(existingT == null) {
			throw new Exception("Type eith given id doesn't exist");
		}
		typeRepository.delete(existingT);
	}

	
	
}

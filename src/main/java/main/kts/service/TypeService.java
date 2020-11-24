package main.kts.service;

import java.util.List;

import org.springframework.stereotype.Service;

import main.kts.model.Type;

@Service
public class TypeService implements ServiceInterface<Type>{

	@Override
	public List<Type> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type create(Type entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type update(Type entity, Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}

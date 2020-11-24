package main.kts.service;

import java.util.List;

import org.springframework.stereotype.Service;

import main.kts.model.Authority;

@Service
public class AuthorityService implements ServiceInterface<Authority>{

	@Override
	public List<Authority> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Authority findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Authority create(Authority entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Authority update(Authority entity, Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}

}

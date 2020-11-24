package main.kts.service;

import java.util.List;

import org.springframework.stereotype.Service;

import main.kts.model.Admin;

@Service
public class AdminService implements ServiceInterface<Admin>{

	@Override
	public List<Admin> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Admin findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Admin create(Admin entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Admin update(Admin entity, Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}

}

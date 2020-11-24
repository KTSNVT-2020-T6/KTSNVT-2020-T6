package main.kts.service;

import java.util.List;

import org.springframework.stereotype.Service;

import main.kts.model.RegisteredUser;

@Service
public class RegisteredUserService implements ServiceInterface<RegisteredUser>{

	@Override
	public List<RegisteredUser> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RegisteredUser findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RegisteredUser create(RegisteredUser entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RegisteredUser update(RegisteredUser entity, Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}

}

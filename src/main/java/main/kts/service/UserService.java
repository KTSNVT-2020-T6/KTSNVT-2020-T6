package main.kts.service;

import java.util.List;

import org.springframework.stereotype.Service;

import main.kts.model.User;

@Service
public class UserService implements ServiceInterface<User>{

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User create(User entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User update(User entity, Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}

}

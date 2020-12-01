package main.kts.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.kts.model.User;
import main.kts.repository.UserRepository;

@Service
public class UserService implements ServiceInterface<User>{

	@Autowired
	private UserRepository repository;
	
	@Override
	public List<User> findAll() {
		return repository.findAll();
	}

	@Override
	public User findOne(Long id) {
		return repository.findById(id).orElse(null);
	}
	public User findByEmail(String email) {
		return repository.findByEmail(email);
	}

	@Override
	public User create(User entity) throws Exception {
		return null;
	}

	@Override
	public User update(User entity, Long id) throws Exception {
		return null;
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}

}

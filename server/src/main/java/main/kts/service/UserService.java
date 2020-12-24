package main.kts.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import main.kts.model.Admin;
import main.kts.model.Authority;
import main.kts.model.RegisteredUser;
import main.kts.model.User;
import main.kts.repository.UserRepository;

@Service
public class UserService implements ServiceInterface<User> {

	@Autowired
	private UserRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthorityService authService;

	@Override
	public List<User> findAll() {
		return repository.findAllByActive(true);
	}

	@Override
	public User findOne(Long id) {
		return repository.findByIdAndActive(id, true).orElse(null);
	}

	public User findByEmail(String email) {
		return repository.findByEmailAndActive(email, true);
	}

	@Override
	public User create(User entity) throws Exception {
		//Ovo se ne radi ovde
//		if (repository.findByEmail(entity.getEmail()) != null) {
//			throw new Exception("User with given email address already exists");
//		}
//		RegisteredUser u = new RegisteredUser();
//		// u.setUsername(entity.getUsername());
//		// pre nego sto postavimo lozinku u atribut hesiramo je
//		u.setPassword(passwordEncoder.encode(entity.getPassword()));
//		u.setFirstName(entity.getFirstName());
//		u.setLastName(entity.getLastName());
//		u.setEmail(entity.getEmail());
//		u.setActive(true);
//		u.setVerified(false);
//
//		Set<Authority> auth = authService.findByName("ROLE_REGISTERED_USER");
//		// u primeru se registruju samo obicni korisnici i u skladu sa tim im se i
//		// dodeljuje samo rola USER
//		u.setAuthority(auth);
//
//		u = this.repository.save(u);
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

	public Page<User> findAll(Pageable pageable) {
		return repository.findByActive(pageable, true);
	}

//	public void save(User user) {
//		// TODO Auto-generated method stub
//		
//	}

}

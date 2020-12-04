package main.kts.service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.kts.model.Authority;
import main.kts.model.CulturalOffer;
import main.kts.model.RegisteredUser;
import main.kts.repository.AuthorityRepository;
import main.kts.repository.RegisteredUserRepository;

@Service
public class RegisteredUserService implements ServiceInterface<RegisteredUser>{

	@Autowired
	private RegisteredUserRepository repository;
	
	@Autowired 
	private AuthorityRepository authorityRepository;
	
	@Override
	public List<RegisteredUser> findAll() {
		return repository.findAll();
	}

	@Override
	public RegisteredUser findOne(Long id) {
		return repository.findById(id).orElse(null);

	}

	@Override
	public RegisteredUser create(RegisteredUser entity) throws Exception {
		//if admin already exists
		RegisteredUser ru = repository.findByEmail(entity.getEmail());
		if (ru  != null)
			throw new Exception("User is already registered");
		
		RegisteredUser ruser = new RegisteredUser();
		ruser.setFirstName(entity.getFirstName());
		ruser.setLastName(entity.getLastName());
		ruser.setEmail(entity.getEmail());
		ruser.setPassword(entity.getPassword());
		ruser.setActive(true);
		ruser.setVerified(false);
		Set<Authority> set = new HashSet<Authority>();
		set.add(authorityRepository.findByRole("REGISTERED_USER"));
		ruser.setAuthority(set);
		if(entity.getImage() != null) {
			ruser.setImage(entity.getImage());
		}
		else
			ruser.setImage(null);
		ruser.setFavoriteCulturalOffers(new HashSet<CulturalOffer>());
        return repository.save(ruser);
	}

	

	@Override
	public RegisteredUser update(RegisteredUser entity, Long id) throws Exception {
		RegisteredUser u = repository.findById(id).orElse(null);
		if(u == null)
			throw new Exception("User doesn't registered");
		String oldEmail = u.getEmail();
		RegisteredUser checkRegisteredUser;
		if(!oldEmail.equals(entity.getEmail())) {
			checkRegisteredUser = repository.findByEmail(entity.getEmail());
		    if(checkRegisteredUser != null) {
		    	throw new Exception("User with given email already exist");
		    }
		    u.setEmail(entity.getEmail());
		}
		else {
			u.setEmail(oldEmail);
		}
		u.setFirstName(entity.getFirstName());
		u.setLastName(entity.getLastName());
		u.setPassword(entity.getPassword());
		if(entity.getImage() != null) {
			u.setImage(entity.getImage());
		}
		return repository.save(u);
	}

	@Override
	public void delete(Long id) throws Exception {
		RegisteredUser a = repository.findById(id).orElse(null);
		if(a == null)
			throw new Exception("Registered user doesn't exist.");
		a.setActive(false);
		a = repository.save(a);
	}
	
	public List<RegisteredUser> findAllRegisteredUser() {
		return repository.findAllRegisteredUser();
	}
	public RegisteredUser findByEmail(String email) {
		return repository.findByEmail(email);
	}


	public List<Long> findByIdCO(Long id) {
		return repository.findByIdCO(id);
	}




}

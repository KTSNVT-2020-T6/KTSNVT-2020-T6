package main.kts.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.kts.model.Admin;
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
		
		//else make new admin instance
		RegisteredUser ruser = new RegisteredUser();
		ruser.setFirstName(entity.getFirstName());
		ruser.setLastName(entity.getLastName());
		ruser.setEmail(entity.getEmail());
		ruser.setPassword(entity.getPassword());
		ruser.setActive(true);
		ruser.setVerified(false);
		ruser.setImage(entity.getImage());
		Set<Authority> set = new HashSet<Authority>();
		set.add(authorityRepository.findByRole("REGISTERED_USER"));
		ruser.setAuthority(set);
		if(entity.getImage() != null)
			ruser.setImage(entity.getImage());
		else // default profile image will be loaded, remove null then
			ruser.setImage(null);
		Set<CulturalOffer> set1 = new HashSet<CulturalOffer>();
		if(entity.getFavoriteCulturalOffers().isEmpty())
			set1 = new HashSet<CulturalOffer>();
		else
			set1 = entity.getFavoriteCulturalOffers();
		ruser.setFavoriteCulturalOffers(set1);
        return ruser;
	}

	@Override
	public RegisteredUser update(RegisteredUser entity, Long id) throws Exception {
		RegisteredUser u = repository.findById(id).orElse(null);
		if(u == null)
			throw new Exception("User doesn't registered");
		//validate new 
		validateAttributes(u);	
		String oldEmail = u.getEmail();
		
		RegisteredUser checkRegisteredUser;
		if(oldEmail != entity.getEmail()) {
			//check in dataBase the new one
			checkRegisteredUser = repository.findByEmail(entity.getEmail());
			//if exist then exception
		    if(checkRegisteredUser != null)
		    	throw new Exception("Email already exist");
		    u.setEmail(entity.getEmail());
		}
		u.setFirstName(entity.getFirstName());
		u.setLastName(entity.getLastName());
		u.setPassword(entity.getPassword());
		u.setVerified(entity.getVerified());
		u.setImage(entity.getImage());
		if(entity.getImage() != null)
			u.setImage(entity.getImage());
		else // default profile image will be loaded, remove null then
			u.setImage(null);
		u.setFavoriteCulturalOffers(entity.getFavoriteCulturalOffers());
		//treba save ovde
		
		return repository.save(u);
	}

	
	@Override
	public void delete(Long id) throws Exception {
		RegisteredUser a = repository.findById(id).orElse(null);
		if(a == null)
			throw new Exception("Registered user doesn't exist.");
		//when it is the last one set it cann't be done
		a.setActive(false);
		
	}
	private void validateAttributes(RegisteredUser u) throws Exception {
		if(u.getFirstName() == null)
			throw new Exception("Registered user's first name is empty.");
		if(u.getLastName() == null)
			throw new Exception("Registered user's last name is empty.");
		if(u.getEmail() == null)
			throw new Exception("Registered user's email is empty.");
		if(u.getPassword() == null)
			throw new Exception("Registered user's password is empty.");
		
	}


	public List<RegisteredUser> findAllRegisteredUser() {
		return repository.findAllRegisteredUser();
	}
	public RegisteredUser findByEmail(String email) {
		return repository.findByEmail(email);
	}


}

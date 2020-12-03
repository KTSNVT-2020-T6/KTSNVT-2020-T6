package main.kts.service;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.kts.model.Authority;
import main.kts.model.CulturalOffer;
import main.kts.model.Image;
import main.kts.model.RegisteredUser;
import main.kts.repository.AuthorityRepository;
import main.kts.repository.ImageRepository;
import main.kts.repository.RegisteredUserRepository;

@Service
public class RegisteredUserService implements ServiceInterface<RegisteredUser>{

	@Autowired
	private RegisteredUserRepository repository;
	
	@Autowired 
	private ImageRepository imageRepository;
	
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
		ruser.setImage(entity.getImage());
		Set<Authority> set = new HashSet<Authority>();
		
		set.add(authorityRepository.findByRole("REGISTERED_USER"));
		ruser.setAuthority(set);
		if(entity.getImage() != null) {
			if(validateImage(entity.getImage())) 
				imageRepository.save(entity.getImage());
			
			else
			{
				throw new Exception("Relative path isn't valid.");
			}
			
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
		//validate new 
		validateAttributes(u);	
		String oldEmail = u.getEmail();
		
		RegisteredUser checkRegisteredUser;
		if(!oldEmail.equals(entity.getEmail())) {
			checkRegisteredUser = repository.findByEmail(entity.getEmail());
		    if(checkRegisteredUser != null)
		    	throw new Exception("User with given email already exist");
		    u.setEmail(entity.getEmail());
		}
		else {
			u.setEmail(oldEmail);
		}
		u.setFirstName(entity.getFirstName());
		u.setLastName(entity.getLastName());
		u.setPassword(entity.getPassword());
		u.setVerified(entity.getVerified());
		Image oldImage = u.getImage();
		if(entity.getImage() != null && ( !oldImage.getName().equals(entity.getImage().getName())
				|| !oldImage.getrelativePath().equals(entity.getImage().getrelativePath()))) {
			
			if(validateImage(entity.getImage())) {
				Image image = imageRepository.save(entity.getImage());
				u.setImage(image);
			}
			else
				throw new Exception("Relative path isn't valid.");	
		}
		else {
			
			u.setImage(oldImage); 
		}
		
		u.setFavoriteCulturalOffers(entity.getFavoriteCulturalOffers());	
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
	private boolean validateImage(Image image) {
		if(image.getRelativePath() == null) 
			return false;
		if(image.getName() == null) 
			return false;
		try {
	        Paths.get(image.getRelativePath());
	    } catch (InvalidPathException | NullPointerException ex) {
	        return false;
	    }
		return true;
	}



}

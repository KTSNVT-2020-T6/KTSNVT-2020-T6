package main.kts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.kts.model.Authority;
import main.kts.repository.AuthorityRepository;

@Service
public class AuthorityService implements ServiceInterface<Authority>{

	@Autowired 
	private AuthorityRepository authorityRepository;
	
	@Override
	public List<Authority> findAll() {
		
		return authorityRepository.findAll();
	}

	@Override
	public Authority findOne(Long id) {
		return authorityRepository.findById(id).orElse(null);
	}

	@Override
	public Authority create(Authority entity) throws Exception {
		Authority a = new Authority();
		a.setRole(entity.getRole());
		return authorityRepository.save(a);
	}

	@Override
	public Authority update(Authority entity, Long id) throws Exception {
		Authority existingA = authorityRepository.findById(id).orElse(null);
		if(existingA == null) {
			throw new Exception("Authority with given id doesn't exist");
		}
		existingA.setRole(entity.getRole());
		return authorityRepository.save(existingA);
	}

	@Override
	public void delete(Long id) throws Exception {
		Authority existingA = authorityRepository.findById(id).orElse(null);
		if(existingA == null) {
			throw new Exception("Authority with given id doesn't exist");
		}
		authorityRepository.delete(existingA);
	
		
	}
	public Authority findByRole(String role) throws Exception {
		return  authorityRepository.findByRole(role);
		
	}

}

package main.kts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.kts.model.User;
import main.kts.model.VerificationToken;
import main.kts.repository.UserRepository;
import main.kts.repository.VerificationTokenRepository;

@Service
public class VerificationTokenService {
	
	@Autowired 
	private VerificationTokenRepository verificationTokenRepository;
	@Autowired 
	private UserRepository userRepository;
	
	public VerificationToken save(VerificationToken token) {
		return verificationTokenRepository.save(token);
	}
	
	public VerificationToken findByToken(String token) {
		return verificationTokenRepository.findByToken(token);
	}
	public VerificationToken findByUser(User user) {
		return verificationTokenRepository.findByUser(user);
	}

	public void save(User user) {
		userRepository.save(user);		
	}	

}

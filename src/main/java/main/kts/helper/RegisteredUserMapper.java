package main.kts.helper;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import main.kts.dto.AuthorityDTO;
import main.kts.dto.RegisteredUserDTO;
import main.kts.model.Authority;
import main.kts.model.RegisteredUser;

@Component
public class RegisteredUserMapper implements MapperInterface<RegisteredUser, RegisteredUserDTO> {

	@Autowired
	ImageMapper imageMapper;
	
	@Override
	public RegisteredUser toEntity(RegisteredUserDTO dto) {
		Set<Authority> auth = new HashSet<Authority>();
		auth.add(new Authority("REGISTERED_USER"));
		return new RegisteredUser(dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getPassword(),
				dto.getActive(), dto.getVerified(), imageMapper.toEntity(dto.getImageDTO()), auth);
	}

	@Override
	public RegisteredUserDTO toDto(RegisteredUser entity) {
		Set<AuthorityDTO> auth = new HashSet<AuthorityDTO>();
		auth.add(new AuthorityDTO("REGISTERED_USER"));
		RegisteredUserDTO rudto = new RegisteredUserDTO(entity.getFirstName(), entity.getLastName(), entity.getEmail(),
				entity.getPassword(), entity.getActive(), entity.getVerified(), imageMapper.toDto(entity.getImage()),
				auth);
		return rudto;
	}

}

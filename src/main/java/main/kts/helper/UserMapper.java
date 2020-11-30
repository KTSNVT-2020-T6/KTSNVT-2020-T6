package main.kts.helper;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import main.kts.dto.AuthorityDTO;
import main.kts.dto.UserDTO;
import main.kts.model.Authority;
import main.kts.model.Image;
import main.kts.model.User;

@Component
public class UserMapper implements MapperInterface<User, UserDTO>{

	@Autowired
	ImageMapper imageMapper;
	@Autowired
	AuthorityMapper authorityMapper;
	
	@Override
	public User toEntity(UserDTO dto) {
		Image image = imageMapper.toEntity(dto.getImageDTO());
		Set<Authority> authorities = new HashSet<Authority>();
		for (AuthorityDTO authorityDTO : dto.getAuthorityDTO()) {
			authorities.add(authorityMapper.toEntity(authorityDTO));
		}
		//return new User(dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getPassword(), dto.getActive(), dto.getVerified(), image, authorities);
		return null;
	}

	@Override
	public UserDTO toDto(User entity) {
		// TODO Auto-generated method stub
		return null;
	}

}

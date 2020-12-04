package main.kts.helper;

import java.util.HashSet;
import java.util.Set;

import main.kts.dto.AuthorityDTO;
import main.kts.dto.ImageDTO;
import main.kts.dto.UserDTO;
import main.kts.model.Authority;
import main.kts.model.User;

public class UserMapper implements MapperInterface<User, UserDTO>{

	
	ImageMapper imageMapper = new ImageMapper();
	
	AuthorityMapper authorityMapper = new AuthorityMapper();
	
	public UserMapper() {}
	
	@Override
	public User toEntity(UserDTO dto) {
		return null;
	}

	@Override
	public UserDTO toDto(User entity) {
		ImageDTO imageDTO = imageMapper.toDto(entity.getImage());
		Set<AuthorityDTO> authoritiesDTO = new HashSet<AuthorityDTO>();
		for (Authority authority : entity.getAuthority()) {
			authoritiesDTO.add(authorityMapper.toDto(authority));
		}
		return new UserDTO(entity.getId(),entity.getFirstName(), entity.getLastName(), entity.getEmail(), entity.getPassword(), entity.getActive(), entity.getVerified(), imageDTO.getId());
	}
	
	

}

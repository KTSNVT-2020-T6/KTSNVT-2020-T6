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
        return new User(dto.getEmail(),dto.getPassword(), dto.getFirstName(), dto.getLastName());
    }

    @Override
    public UserDTO toDto(User entity) {
       return new UserDTO(entity.getId(), entity.getFirstName(), entity.getLastName(),
    		   entity.getEmail(), entity.getPassword(), entity.getActive(),
    		   entity.getVerified(), entity.getImage().getId());
    	// return new UserDTO(entity.getId(), entity.getEmail(),entity.getPassword());
    }

}

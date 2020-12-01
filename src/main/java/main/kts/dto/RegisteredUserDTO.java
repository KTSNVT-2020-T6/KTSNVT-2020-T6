package main.kts.dto;

import java.util.Set;

public class RegisteredUserDTO extends UserDTO{

	public RegisteredUserDTO() {
		super();
	}
	
	public RegisteredUserDTO(String firstName, String lastName, String email, String password, Boolean active, Boolean verified,
			ImageDTO imageDTO, Set<AuthorityDTO> authorityDTO) {
		super(firstName, lastName, email, password, active, verified, imageDTO, authorityDTO);
	}
}

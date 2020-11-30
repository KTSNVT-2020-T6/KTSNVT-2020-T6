package main.kts.helper;

import main.kts.dto.RegisteredUserDTO;
import main.kts.model.RegisteredUser;

public class RegisteredUserMapper implements MapperInterface<RegisteredUser, RegisteredUserDTO>{

	@Override
	public RegisteredUser toEntity(RegisteredUserDTO dto) {
		return new RegisteredUser();
	}

	@Override
	public RegisteredUserDTO toDto(RegisteredUser entity) {
		return new RegisteredUserDTO();
	}

}

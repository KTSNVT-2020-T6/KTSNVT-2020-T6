package main.kts.helper;
import main.kts.dto.RegisteredUserDTO;
import main.kts.model.RegisteredUser;


public class RegisteredUserMapper implements MapperInterface<RegisteredUser, RegisteredUserDTO>{

	
	ImageMapper imageMapper = new ImageMapper();
	public RegisteredUserMapper() {}

	@Override
	public RegisteredUserDTO toDto(RegisteredUser entity) {

		RegisteredUserDTO rudto = new RegisteredUserDTO(entity.getId(),entity.getFirstName(),entity.getLastName(), entity.getEmail(), entity.getPassword(), entity.getActive(), entity.getVerified()
				, entity.getImage().getId());
		return rudto;
	}
	@Override
	public RegisteredUser toEntity(RegisteredUserDTO dto) {
		return new RegisteredUser(dto.getFirstName(), dto.getLastName(),dto.getEmail(), dto.getPassword());
	}

}

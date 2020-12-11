package main.kts.dto;


public class RegisteredUserDTO extends UserDTO{

	
	public RegisteredUserDTO() {
		super();
	}
	public RegisteredUserDTO(String firstName, String lastName, String email, String password, Long idImageDTO) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.idImageDTO = idImageDTO;
	
	}

	public RegisteredUserDTO(Long id, String firstName, String lastName, String email, String password, Boolean active,
			Boolean verified, Long imageDTO) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.active = active;
		this.verified = verified;
		this.idImageDTO = imageDTO;
		
	}
}

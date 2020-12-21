package main.kts.dto;

public class AdminDTO extends UserDTO{
	
	
	public AdminDTO() {
		super();
	}
	public AdminDTO(String firstName, String lastName, String email, String password, Long imageDTO) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.idImageDTO = imageDTO;
	
	}
		
	public AdminDTO(Long id, String firstName, String lastName, String email, String password, Boolean active,
			Boolean verified, Long id2) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.active = active;
		this.verified = verified;
		this.idImageDTO = id2;

	}
	
	

}

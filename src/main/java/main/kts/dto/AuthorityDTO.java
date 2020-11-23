package main.kts.dto;

public class AuthorityDTO {
	
	private String role;
	
	public AuthorityDTO() {
		super();
	}
	
	public AuthorityDTO(String role) {
		super();
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}

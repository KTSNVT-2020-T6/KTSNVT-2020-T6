package main.kts.dto;

public class AuthorityDTO {
	
	private Long id;
	private String role;
	
	public AuthorityDTO() {
		super();
	}
	
	public AuthorityDTO(Long id, String role) {
		super();
		this.id = id;
		this.role = role;
	}
	public AuthorityDTO(String role) {
		super();
		
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}

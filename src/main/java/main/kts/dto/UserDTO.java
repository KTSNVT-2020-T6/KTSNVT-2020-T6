package main.kts.dto;

import java.util.Set;

public class UserDTO {
	
	protected Long id;
	protected String firstName;
	protected String lastName;
	protected String email;
	protected String password;
	protected Boolean active;
	protected Boolean verified;
	   
	protected Long idImageDTO;
	//protected Set<AuthorityDTO> authorityDTO;
	
	public UserDTO() {
		super();
	}
	public UserDTO(String firstName, String lastName, String email, String password, Long imageDTO) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.idImageDTO = imageDTO;
	}
	public UserDTO(Long id, String firstName, String lastName, String email, String password, Boolean active, Boolean verified,
			Long imageDTO) {
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
//	public UserDTO(Long id, String firstName, String lastName, String email, String password, Boolean active, Boolean verified,
//			Long imageDTO, Set<AuthorityDTO> authorityDTO) {
//		super();
//		this.id = id;
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.email = email;
//		this.password = password;
//		this.active = active;
//		this.verified = verified;
//		this.idImageDTO = imageDTO;
//		this.authorityDTO = authorityDTO;
//	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}
//	public Set<AuthorityDTO> getAuthorityDTO() {
//		return authorityDTO;
//	}
//
//	public void setAuthorityDTO(Set<AuthorityDTO> authorityDTO) {
//		this.authorityDTO = authorityDTO;
//	}

	public Long getIdImageDTO() {
		return idImageDTO;
	}

	public void setIdImageDTO(Long idImageDTO) {
		this.idImageDTO = idImageDTO;
	}
	
	
}

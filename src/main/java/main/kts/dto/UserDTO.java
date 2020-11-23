package main.kts.dto;

import java.util.ArrayList;

public class UserDTO {
	
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Boolean active;
	private Boolean verified;
	   
	public ImageDTO imageDTO;
	public ArrayList<AuthorityDTO> authorityDTO;
	
	public UserDTO() {
		super();
	}
	
	public UserDTO(String firstName, String lastName, String email, String password, Boolean active, Boolean verified,
			ImageDTO imageDTO, ArrayList<AuthorityDTO> authorityDTO) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.active = active;
		this.verified = verified;
		this.imageDTO = imageDTO;
		this.authorityDTO = authorityDTO;
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

	public ImageDTO getImageDTO() {
		return imageDTO;
	}

	public void setImageDTO(ImageDTO imageDTO) {
		this.imageDTO = imageDTO;
	}

	public ArrayList<AuthorityDTO> getAuthorityDTO() {
		return authorityDTO;
	}

	public void setAuthorityDTO(ArrayList<AuthorityDTO> authorityDTO) {
		this.authorityDTO = authorityDTO;
	}
	
	
}

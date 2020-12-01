package main.kts.dto;

import java.util.Set;



public class AdminDTO extends UserDTO{
	
	public Set<CulturalOfferDTO> culturalOfferDTO;
	
	public AdminDTO() {
		super();
	}
	
	
	public AdminDTO(String firstName, String lastName, String email, String password, Boolean active, Boolean verified,
			ImageDTO imageDTO, Set<AuthorityDTO> authorityDTO, Set<CulturalOfferDTO> culturalOfferDTO) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.active = active;
		this.verified = verified;
		this.imageDTO = imageDTO;
		this.authorityDTO = authorityDTO;
		this.culturalOfferDTO = culturalOfferDTO;
	}
	
	public AdminDTO(Set<CulturalOfferDTO> culturalOfferDTO) {
		super();
		this.culturalOfferDTO = culturalOfferDTO;
	}

	public Set<CulturalOfferDTO> getCulturalOfferDTO() {
		return culturalOfferDTO;
	}

	public void setCulturalOfferDTO(Set<CulturalOfferDTO> culturalOfferDTO) {
		this.culturalOfferDTO = culturalOfferDTO;
	}
	

}

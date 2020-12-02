package main.kts.dto;

import java.util.Set;

public class RegisteredUserDTO extends UserDTO{

	private Set<CulturalOfferDTO> favoriteCulturalOffersDTO;
	public RegisteredUserDTO() {
		super();
	}
	
//	public RegisteredUserDTO(Long id, String firstName, String lastName, String email, String password, Boolean active, Boolean verified,
//			ImageDTO imageDTO, Set<AuthorityDTO> authorityDTO) {
//		super(id, firstName, lastName, email, password, active, verified, imageDTO, authorityDTO);
//	}
	public RegisteredUserDTO(Long id, String firstName, String lastName, String email, String password, Boolean active, Boolean verified,
			ImageDTO imageDTO, Set<AuthorityDTO> authorityDTO, Set<CulturalOfferDTO> favoriteCulturalOffersDTO) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.active = active;
		this.verified = verified;
		this.imageDTO = imageDTO;
		this.authorityDTO = authorityDTO;
		this.favoriteCulturalOffersDTO = favoriteCulturalOffersDTO;
	}

	public Set<CulturalOfferDTO> getFavoriteCulturalOffersDTO() {
		return favoriteCulturalOffersDTO;
	}

	public void setFavoriteCulturalOffersDTO(Set<CulturalOfferDTO> favoriteCulturalOffersDTO) {
		this.favoriteCulturalOffersDTO = favoriteCulturalOffersDTO;
	}
}

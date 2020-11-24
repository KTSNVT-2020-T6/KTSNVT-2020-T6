package main.kts.dto;

import java.util.Set;



public class AdminDTO extends UserDTO{
	
	public Set<CulturalOfferDTO> culturalOfferDTO;
	
	public AdminDTO() {
		super();
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

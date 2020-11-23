package main.kts.dto;

import java.util.ArrayList;



public class AdminDTO extends UserDTO{
	
	public ArrayList<CulturalOfferDTO> culturalOfferDTO;
	
	public AdminDTO() {
		super();
	}

	public AdminDTO(ArrayList<CulturalOfferDTO> culturalOfferDTO) {
		super();
		this.culturalOfferDTO = culturalOfferDTO;
	}

	public ArrayList<CulturalOfferDTO> getCulturalOfferDTO() {
		return culturalOfferDTO;
	}

	public void setCulturalOfferDTO(ArrayList<CulturalOfferDTO> culturalOfferDTO) {
		this.culturalOfferDTO = culturalOfferDTO;
	}
	

}

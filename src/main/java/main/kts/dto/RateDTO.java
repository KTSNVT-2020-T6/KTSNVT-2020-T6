package main.kts.dto;


public class RateDTO {
	
	private int number;   
	public RegisteredUserDTO registredUserDTO;
	public CulturalOfferDTO culturalOfferDTO;
	
	public RateDTO() {
		super();
	}
	
	public RateDTO(int number, RegisteredUserDTO registredUserDTO, CulturalOfferDTO culturalOfferDTO) {
		super();
		this.number = number;
		this.registredUserDTO = registredUserDTO;
		this.culturalOfferDTO = culturalOfferDTO;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public RegisteredUserDTO getRegistredUserDTO() {
		return registredUserDTO;
	}

	public void setRegistredUserDTO(RegisteredUserDTO registredUserDTO) {
		this.registredUserDTO = registredUserDTO;
	}

	public CulturalOfferDTO getCulturalOfferDTO() {
		return culturalOfferDTO;
	}

	public void setCulturalOfferDTO(CulturalOfferDTO culturalOfferDTO) {
		this.culturalOfferDTO = culturalOfferDTO;
	}
	

	
	
}

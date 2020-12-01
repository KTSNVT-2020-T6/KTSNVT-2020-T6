package main.kts.dto;


public class RateDTO {
	
	private Long id;
	private int number;   
	public RegisteredUserDTO registredUserDTO;
	public CulturalOfferDTO culturalOfferDTO;
	
	public RateDTO() {
		super();
	}
	
	@Override
	public String toString() {
		return "RateDTO [number=" + number + ", registredUserDTO=" + registredUserDTO + ", culturalOfferDTO="
				+ culturalOfferDTO + "]";
	}

	public RateDTO(Long id, int number, RegisteredUserDTO registredUserDTO, CulturalOfferDTO culturalOfferDTO) {
		super();
		this.id = id;
		this.number = number;
		this.registredUserDTO = registredUserDTO;
		this.culturalOfferDTO = culturalOfferDTO;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

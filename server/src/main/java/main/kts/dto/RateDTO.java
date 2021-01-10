package main.kts.dto;


public class RateDTO {
	
	private Long id;
	private Integer number;   
	public Long registredUserId;
	public Long culturalOfferId;
	
	public RateDTO() {
		super();
	}

	public RateDTO(Long id, int number, Long registredUserId, Long culturalOfferId) {
		super();
		this.id = id;
		this.number = number;
		this.registredUserId = registredUserId;
		this.culturalOfferId = culturalOfferId;
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

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Long getRegistredUserId() {
		return registredUserId;
	}

	public void setRegistredUserId(Long registredUserId) {
		this.registredUserId = registredUserId;
	}

	public Long getCulturalOfferId() {
		return culturalOfferId;
	}

	public void setCulturalOfferId(Long culturalOfferId) {
		this.culturalOfferId = culturalOfferId;
	}
	
}

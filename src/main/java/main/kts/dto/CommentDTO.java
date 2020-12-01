package main.kts.dto;

import java.util.Date;

public class CommentDTO {
	
	private Long id;
	private String text;
	private Date date;
	   
	public RegisteredUserDTO registeredUserDTO;
	public ImageDTO imageDTO;
	public CulturalOfferDTO culturalOfferDTO;
	
	public CommentDTO() {
		super();
	}
			
	public CommentDTO(Long id, String text, Date date, RegisteredUserDTO registeredUserDTO, ImageDTO imageDTO, CulturalOfferDTO culturalOfferDTO) {
		super();
		this.id = id;
		this.text = text;
		this.date = date;
		this.registeredUserDTO = registeredUserDTO;
		this.imageDTO = imageDTO;
		this.culturalOfferDTO = culturalOfferDTO;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public RegisteredUserDTO getRegistredUserDTO() {
		return registeredUserDTO;
	}

	public void setRegisteredUserDTO(RegisteredUserDTO registeredUserDTO) {
		this.registeredUserDTO = registeredUserDTO;
	}

	public ImageDTO getImageDTO() {
		return imageDTO;
	}

	public void setImageDTO(ImageDTO imageDTO) {
		this.imageDTO = imageDTO;
	}

	public CulturalOfferDTO getCulturalOfferDTO() {
		return culturalOfferDTO;
	}

	public void setCulturalOfferDTO(CulturalOfferDTO culturalOfferDTO) {
		this.culturalOfferDTO = culturalOfferDTO;
	}

	public RegisteredUserDTO getRegisteredUserDTO() {
		return registeredUserDTO;
	}
	
	
	
}

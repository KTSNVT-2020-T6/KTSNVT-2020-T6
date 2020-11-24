package main.kts.dto;

import java.util.Date;

public class CommentDTO {
	
	private String text;
	private Date date;
	   
	public RegisteredUserDTO registeredUserDTO;
	public ImageDTO imageDTO;
	public CulturalOfferDTO culturalOfferDTO;
	
	public CommentDTO() {
		super();
	}
			
	public CommentDTO(String text, Date date, RegisteredUserDTO registeredUserDTO, ImageDTO imageDTO, CulturalOfferDTO culturalOfferDTO) {
		super();
		this.text = text;
		this.date = date;
		this.registeredUserDTO = registeredUserDTO;
		this.imageDTO = imageDTO;
		this.culturalOfferDTO = culturalOfferDTO;
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

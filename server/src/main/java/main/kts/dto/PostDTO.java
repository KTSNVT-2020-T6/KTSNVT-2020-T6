package main.kts.dto;

import java.util.Date;

public class PostDTO {
	
	private Long id;
	private String text;
	private Date date;
	   
	public ImageDTO imageDTO;
	public Long culturalOfferId;

	public PostDTO() {
		super();
	}
	
	public PostDTO(Long id, String text, Date date, ImageDTO imageDTO) {
		super();
		this.id = id;
		this.text = text;
		this.date = date;
		this.imageDTO = imageDTO;
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

	public ImageDTO getImageDTO() {
		return imageDTO;
	}

	public void setImageDTO(ImageDTO imageDTO) {
		this.imageDTO = imageDTO;
	}

	public Long getCulturalOfferId() {
		return culturalOfferId;
	}

	public void setCulturalOfferId(Long culturalOfferId) {
		this.culturalOfferId = culturalOfferId;
	}
	
	
	
}

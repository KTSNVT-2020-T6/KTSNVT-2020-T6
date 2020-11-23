package main.kts.dto;

import java.util.Date;

public class PostDTO {
	
	private String text;
	private Date date;
	   
	public ImageDTO imageDTO;

	public PostDTO() {
		super();
	}
	
	public PostDTO(String text, Date date, ImageDTO imageDTO) {
		super();
		this.text = text;
		this.date = date;
		this.imageDTO = imageDTO;
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
	
	
	
}

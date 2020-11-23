package main.kts.dto;

import java.util.Date;

public class CommentDTO {
	
	private String text;
	private Date date;
	   
	public RegisteredUserDTO registeredUserDTO;
	public ImageDTO imageDTO;
	
	public CommentDTO() {
		super();
	}
			
	public CommentDTO(String text, Date date, RegisteredUserDTO registeredUserDTO, ImageDTO imageDTO) {
		super();
		this.text = text;
		this.date = date;
		this.registeredUserDTO = registeredUserDTO;
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
	
	
	
}

package main.kts.dto;

import java.util.Date;

public class CommentDTO {
	
	private Long id;
	private String text;
	private Date date;
	   
	public String nameSurname;
	public Long userId;
	public ImageDTO userImage;
	public ImageDTO imageDTO;
	public Long culturalOfferId;
	
	public CommentDTO() {
		super();
	}
	
	public CommentDTO(Long id, String text, Date date, String nameSurname, Long userId, ImageDTO userImage,
			ImageDTO imageDTO, Long culturalOfferId) {
		super();
		this.id = id;
		this.text = text;
		this.date = date;
		this.nameSurname = nameSurname;
		this.userId = userId;
		this.userImage = userImage;
		this.imageDTO = imageDTO;
		this.culturalOfferId = culturalOfferId;
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


	public String getNameSurname() {
		return nameSurname;
	}


	public void setNameSurname(String nameSurname) {
		this.nameSurname = nameSurname;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public ImageDTO getUserImage() {
		return userImage;
	}


	public void setUserImage(ImageDTO userImage) {
		this.userImage = userImage;
	}


	public Long getCulturalOfferId() {
		return culturalOfferId;
	}


	public void setCulturalOfferId(Long culturalOfferId) {
		this.culturalOfferId = culturalOfferId;
	}

	
	
}

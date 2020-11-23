package main.kts.model;

import java.util.*;



//@Entity
public class Comment {
	private String text;
	private Date date;

	public RegisteredUser registredUser;
//	public Image image;
//
//	public Comment(String text, Date date, RegisteredUser registredUser, Image image) {
//		super();
//		this.text = text;
//		this.date = date;
//		this.registredUser = registredUser;
//		this.image = image;
//	}

	public Comment() {
		super();
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

	public RegisteredUser getRegistredUser() {
		return registredUser;
	}

	public void setRegistredUser(RegisteredUser registredUser) {
		this.registredUser = registredUser;
	}

//	public Image getImage() {
//		return image;
//	}
//
//	public void setImage(Image image) {
//		this.image = image;
//	}

}
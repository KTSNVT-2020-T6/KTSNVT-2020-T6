package main.kts.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = false, nullable = false)
	private String text;

	@Column(unique = false, nullable = false)
	private Date date;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public RegisteredUser registredUser;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "image_id", nullable = true)
	public Image image;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "cultural_offer_id", nullable = false)
	public CulturalOffer culturalOffer;

	public Comment(Long id, String text, Date date, RegisteredUser registredUser, Image image, CulturalOffer culturalOffer) {
		this.id = id;
		this.text = text;
		this.date = date;
		this.registredUser = registredUser;
		this.image = image;
		this.culturalOffer = culturalOffer;
	}

	public Comment() {
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

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CulturalOffer getCulturalOffer() {
		return culturalOffer;
	}

	public void setCulturalOffer(CulturalOffer culturalOffer) {
		this.culturalOffer = culturalOffer;
	}

}
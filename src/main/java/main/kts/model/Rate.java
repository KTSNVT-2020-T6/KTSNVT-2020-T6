package main.kts.model;

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
public class Rate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = false, nullable = true)
	private int number;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	public RegisteredUser registredUser;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "cultural_offer_id", nullable = true)
	public CulturalOffer culturalOffer;

	public Rate(Long id, int number, RegisteredUser registredUser, CulturalOffer culturalOffer) {
		super();
		this.id = id;
		this.number = number;
		this.registredUser = registredUser;
		this.culturalOffer = culturalOffer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Rate() {
		super();
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public RegisteredUser getRegistredUser() {
		return registredUser;
	}

	public void setRegistredUser(RegisteredUser registredUser) {
		this.registredUser = registredUser;
	}

	public CulturalOffer getCulturalOffer() {
		return culturalOffer;
	}

	public void setCulturalOffer(CulturalOffer culturalOffer) {
		this.culturalOffer = culturalOffer;
	}

}
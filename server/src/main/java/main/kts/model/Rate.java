package main.kts.model;

import java.util.Objects;

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

	@Column(nullable = false)
	private Boolean active;
	
	public Rate(Long id, int number, RegisteredUser registredUser, CulturalOffer culturalOffer) {
		super();
		this.id = id;
		this.number = number;
		this.registredUser = registredUser;
		this.culturalOffer = culturalOffer;
		this.active = true;
	}
	
	public Rate(int number, RegisteredUser registredUser, CulturalOffer culturalOffer) {
		super();
		this.number = number;
		this.registredUser = registredUser;
		this.culturalOffer = culturalOffer;
		this.active = true;
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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Rate rate = (Rate) o;
        if (rate.getId() == null || id == null) {
            if(rate.getNumber() == getNumber()){
                return true;
            }
            return false;
        }
        return Objects.equals(id, rate.getId());
    }
	
}
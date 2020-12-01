package main.kts.model;

import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("admin")

public class Admin extends User {

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "admin_id", nullable = true)
	public Set<CulturalOffer> culturalOffer;

	public Admin() {
		super();
	}
	
	public Admin(String firstName, String lastName, String email, String password, Boolean active, Boolean verified,
			Image image, Set<Authority> authority, Set<CulturalOffer> culturalOffers) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.active = active;
		this.verified = verified;
		this.image = image;
		this.authority = authority;
		this.culturalOffer = culturalOffers;
	}

	public Admin(Set<CulturalOffer> culturalOffer) {
		super();
		this.culturalOffer = culturalOffer;
	}

	public Set<CulturalOffer> getCulturalOffer() {
		return culturalOffer;
	}

	public void setCulturalOffer(Set<CulturalOffer> culturalOffer) {
		this.culturalOffer = culturalOffer;
	}

}
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
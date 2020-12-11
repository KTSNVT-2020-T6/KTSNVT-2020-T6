package main.kts.model;

import java.util.Collection;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;

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

	public Admin(Long id, String firstName, String lastName, String email, String password, Boolean active,
			Boolean verified, Image image, Set<Authority> auth, Set<CulturalOffer> culturalOffers) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.active = active;
		this.verified = verified;
		this.image = image;
		this.authority = auth;
		this.culturalOffer = culturalOffers;
	}

	public Admin(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}


	public Set<CulturalOffer> getCulturalOffer() {
		return culturalOffer;
	}

	public void setCulturalOffer(Set<CulturalOffer> culturalOffer) {
		this.culturalOffer = culturalOffer;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authority;
	}


	@Override
	public String getUsername() {
		return this.email;
	}

}
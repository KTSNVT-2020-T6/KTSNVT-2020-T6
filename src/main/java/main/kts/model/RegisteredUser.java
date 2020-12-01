package main.kts.model;

import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("registered_user")

public class RegisteredUser extends User {


	@ManyToMany(fetch = FetchType.LAZY)
    private Set<CulturalOffer> favoriteCulturalOffers;
    
	public RegisteredUser() {
		super();
	}
	
	public RegisteredUser(Long id, String firstName, String lastName, String email, String password, Boolean active, Boolean verified,
			Image image, Set<Authority> authority) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.active = active;
		this.verified = verified;
		this.image = image;
		this.authority = authority;
	}
	public RegisteredUser(Long id, String firstName, String lastName, String email, String password, Boolean active, Boolean verified,
			Image image, Set<Authority> authority, Set<CulturalOffer>favoriteCulturalOffers ) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.active = active;
		this.verified = verified;
		this.image = image;
		this.authority = authority;
		this.setFavoriteCulturalOffers(favoriteCulturalOffers);
	}

	public Set<CulturalOffer> getFavoriteCulturalOffers() {
		return favoriteCulturalOffers;
	}

	public void setFavoriteCulturalOffers(Set<CulturalOffer> favoriteCulturalOffers) {
		this.favoriteCulturalOffers = favoriteCulturalOffers;
	}

}
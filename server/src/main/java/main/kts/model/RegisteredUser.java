package main.kts.model;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;

@Entity
@DiscriminatorValue("registered_user")

public class RegisteredUser extends User {


	@ManyToMany(fetch = FetchType.EAGER)
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

	public RegisteredUser(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public RegisteredUser(Long id, String firstName, String lastName,
			String email, String password, boolean active,
			boolean verified) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.active = active;
		this.verified = verified;
		
	}

	public RegisteredUser(String firstName, String lastName,
			String email, String password, boolean active,
			boolean verified) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.active = active;
		this.verified = verified;
	}

	public RegisteredUser(Long registeredUserId) {
		this.id = registeredUserId;
		this.active = true;
		this.verified = true;
	}

	public Set<CulturalOffer> getFavoriteCulturalOffers() {
		return favoriteCulturalOffers;
	}

	public void setFavoriteCulturalOffers(Set<CulturalOffer> favoriteCulturalOffers) {
		this.favoriteCulturalOffers = favoriteCulturalOffers;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authority;
	}

	@Override
	public String getUsername() {
		return this.email;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RegisteredUser ru = (RegisteredUser) o;
        if (ru.getId() == null || id == null) {
            if(ru.getEmail().equals(getEmail())){
                return true;
            }
            return false;
        }
        return Objects.equals(id, ru.getId());
    }


}
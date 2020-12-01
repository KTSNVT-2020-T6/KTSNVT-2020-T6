package main.kts.model;

import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("registered_user")

public class RegisteredUser extends User {

	public RegisteredUser() {
		super();
	}
	
	public RegisteredUser(String firstName, String lastName, String email, String password, Boolean active, Boolean verified,
			Image image, Set<Authority> authority) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.active = active;
		this.verified = verified;
		this.image = image;
		this.authority = authority;
	}

}
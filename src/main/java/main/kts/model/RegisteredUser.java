package main.kts.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("registered_user")

public class RegisteredUser extends User {

	public RegisteredUser() {
		super();
	}

}
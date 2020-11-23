package main.kts.model;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

//@Entity
//@Table(name = "users")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)

public abstract class User {

//	@Id
//	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

//	@Column(nullable = false)
	private String firstName;

//	@Column(nullable = false)
	private String lastName;
	
//	@Column(unique = true)
	private String email;
	
//	@Column(nullable = false)
	private String password;
	
//	@Column(nullable = false)
	private Boolean active;
	
//	@Column(nullable = false)
	private Boolean verified;

//	@Column(nullable = false)
//	public Image image;
	
//	@OneToMany(mappedBy="user", fetch = FetchType.EAGER)
	public ArrayList<Authority> authority;

	public User() {
		super();
	}

//	public User(String firstName, String lastName, String email, String password, Boolean active, Boolean verified,
//			Image image, ArrayList<Authority> authority) {
//		super();
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.email = email;
//		this.password = password;
//		this.active = active;
//		this.verified = verified;
//		this.image = image;
//		this.authority = authority;
//	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

//	public Image getImage() {
//		return image;
//	}

//	public void setImage(Image image) {
//		this.image = image;
//	}

	public ArrayList<Authority> getAuthority() {
		return authority;
	}

	public void setAuthority(ArrayList<Authority> authority) {
		this.authority = authority;
	}

}
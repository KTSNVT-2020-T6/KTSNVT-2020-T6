package main.kts.model;

import java.util.ArrayList;



//@Entity
//@DiscriminatorValue("admin")

public class Admin extends User {

//	@OneToMany(mappedBy="admin", fetch = FetchType.EAGER)
	public ArrayList<CulturalOffer> culturalOffer;

	public Admin() {
		super();
	}

	public Admin(ArrayList<CulturalOffer> culturalOffer) {
		super();
		this.culturalOffer = culturalOffer;
	}

	public ArrayList<CulturalOffer> getCulturalOffer() {
		return culturalOffer;
	}

	public void setCulturalOffer(ArrayList<CulturalOffer> culturalOffer) {
		this.culturalOffer = culturalOffer;
	}

}
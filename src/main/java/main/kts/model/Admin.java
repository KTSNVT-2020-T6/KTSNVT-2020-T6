package main.kts.model; 
import java.util.*;

public class Admin extends User {
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
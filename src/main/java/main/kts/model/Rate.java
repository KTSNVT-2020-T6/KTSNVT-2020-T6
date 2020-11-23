package main.kts.model;


//@Entity
public class Rate {
   private int number;
   
   public RegisteredUser registredUser;
   public CulturalOffer culturalOffer;
   public Rate(int number, RegisteredUser registredUser, CulturalOffer culturalOffer) {
	super();
	this.number = number;
	this.registredUser = registredUser;
	this.culturalOffer = culturalOffer;
}
public Rate() {
	super();
}
public int getNumber() {
	return number;
}
public void setNumber(int number) {
	this.number = number;
}
public RegisteredUser getRegistredUser() {
	return registredUser;
}
public void setRegistredUser(RegisteredUser registredUser) {
	this.registredUser = registredUser;
}
public CulturalOffer getCulturalOffer() {
	return culturalOffer;
}
public void setCulturalOffer(CulturalOffer culturalOffer) {
	this.culturalOffer = culturalOffer;
}
   
   

}
package main.kts.model; 
import java.util.*;

public class Rate {
   private int number;
   
   public RegistredUser registredUser;
   public CulturalOffer culturalOffer;
public Rate(int number, RegistredUser registredUser, CulturalOffer culturalOffer) {
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
public RegistredUser getRegistredUser() {
	return registredUser;
}
public void setRegistredUser(RegistredUser registredUser) {
	this.registredUser = registredUser;
}
public CulturalOffer getCulturalOffer() {
	return culturalOffer;
}
public void setCulturalOffer(CulturalOffer culturalOffer) {
	this.culturalOffer = culturalOffer;
}
   
   

}
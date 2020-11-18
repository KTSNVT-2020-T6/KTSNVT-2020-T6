package main.kts.model; 
import java.util.*;

public class Comment {
   private String text;
   private Date date;
   
   public RegistredUser registredUser;
   public Image image;
public Comment(String text, Date date, RegistredUser registredUser, Image image) {
	super();
	this.text = text;
	this.date = date;
	this.registredUser = registredUser;
	this.image = image;
}
public Comment() {
	super();
}
public String getText() {
	return text;
}
public void setText(String text) {
	this.text = text;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}
public RegistredUser getRegistredUser() {
	return registredUser;
}
public void setRegistredUser(RegistredUser registredUser) {
	this.registredUser = registredUser;
}
public Image getImage() {
	return image;
}
public void setImage(Image image) {
	this.image = image;
}
   
   

}
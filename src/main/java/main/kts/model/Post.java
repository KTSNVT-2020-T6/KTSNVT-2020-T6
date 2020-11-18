package main.kts.model; 
import java.util.*;

public class Post {
   private String text;
   private Date date;
   
   public Image image;

public Post() {
	super();
}

public Post(String text, Date date, Image image) {
	super();
	this.text = text;
	this.date = date;
	this.image = image;
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

public Image getImage() {
	return image;
}

public void setImage(Image image) {
	this.image = image;
}
   
   

}
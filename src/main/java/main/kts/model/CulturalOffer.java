package main.kts.model; 
import java.util.*;


//@Entity
public class CulturalOffer {
   private double averageRate;
   private String description;
   private String name;
   private Date date;
   private double lat;
   private double lon;
   
   public ArrayList<Post> post;
   public Type type;
//   public ArrayList<Image> image;
   
public CulturalOffer() {
	super();
}

//public CulturalOffer(double averageRate, String description, String name, Date date, double lat, double lon,
//		ArrayList<Post> post, Type type, ArrayList<Image> image) {
//	super();
//	this.averageRate = averageRate;
//	this.description = description;
//	this.name = name;
//	this.date = date;
//	this.lat = lat;
//	this.lon = lon;
//	this.post = post;
//	this.type = type;
//	this.image = image;
//}

public double getAverageRate() {
	return averageRate;
}

public void setAverageRate(double averageRate) {
	this.averageRate = averageRate;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public Date getDate() {
	return date;
}

public void setDate(Date date) {
	this.date = date;
}

public double getLat() {
	return lat;
}

public void setLat(double lat) {
	this.lat = lat;
}

public double getLon() {
	return lon;
}

public void setLon(double lon) {
	this.lon = lon;
}

public ArrayList<Post> getPost() {
	return post;
}

public void setPost(ArrayList<Post> post) {
	this.post = post;
}

public Type getType() {
	return type;
}

public void setType(Type type) {
	this.type = type;
}

//public ArrayList<Image> getImage() {
//	return image;
//}
//
//public void setImage(ArrayList<Image> image) {
//	this.image = image;
//}
   
   
   

   
  
}
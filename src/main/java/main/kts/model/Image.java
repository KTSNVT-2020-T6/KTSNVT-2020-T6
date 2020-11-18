package main.kts.model; 
import java.util.*;

public class Image {
   private String name;
   private String url;
public Image() {
	super();
}
public Image(String name, String url) {
	super();
	this.name = name;
	this.url = url;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
   
   

}
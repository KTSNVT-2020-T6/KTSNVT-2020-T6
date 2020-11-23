package main.kts.model; 
import java.util.*;



//@Entity
public class Category {
   private String name;
   private String description;
   
   public ArrayList<Type> type;

public Category(String name, String description, ArrayList<Type> type) {
	super();
	this.name = name;
	this.description = description;
	this.type = type;
}

public Category() {
	super();
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public ArrayList<Type> getType() {
	return type;
}

public void setType(ArrayList<Type> type) {
	this.type = type;
}
   
   
   

}
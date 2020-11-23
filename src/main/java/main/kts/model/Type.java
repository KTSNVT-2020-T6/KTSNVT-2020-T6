package main.kts.model;


//@Entity
public class Type {
   private String name;
   private String description;
   
   public Category category;

public Type() {
	super();
}

public Type(String name, String description, Category category) {
	super();
	this.name = name;
	this.description = description;
	this.category = category;
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

public Category getCategory() {
	return category;
}

public void setCategory(Category category) {
	this.category = category;
}
   
   

   

}
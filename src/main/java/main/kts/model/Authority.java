package main.kts.model;


//@Entity
public class Authority {
   private String role;

public Authority() {
	super();
}

public Authority(String role) {
	super();
	this.role = role;
}

public String getRole() {
	return role;
}

public void setRole(String role) {
	this.role = role;
}


   

}
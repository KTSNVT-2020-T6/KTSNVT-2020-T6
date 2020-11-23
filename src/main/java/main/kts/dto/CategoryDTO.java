package main.kts.dto;

import java.util.ArrayList;


public class CategoryDTO {
	
	private String name;
	private String description;
	   
	public ArrayList<TypeDTO> typeDTO;

	public CategoryDTO() {
		super();
	}
	
	public CategoryDTO(String name, String description, ArrayList<TypeDTO> typeDTO) {
		super();
		this.name = name;
		this.description = description;
		this.typeDTO = typeDTO;
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

	public ArrayList<TypeDTO> getTypeDTO() {
		return typeDTO;
	}

	public void setTypeDTO(ArrayList<TypeDTO> typeDTO) {
		this.typeDTO = typeDTO;
	}
	
	
	
}

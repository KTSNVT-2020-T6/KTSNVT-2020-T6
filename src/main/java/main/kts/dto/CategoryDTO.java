package main.kts.dto;

import java.util.Set;


public class CategoryDTO {
	
	private Long id;
	private String name;
	private String description;
	   
	public Set<TypeDTO> typeDTO;

	public CategoryDTO() {
		super();
	}
	
	public CategoryDTO(Long id, String name, String description, Set<TypeDTO> typeDTO) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.typeDTO = typeDTO;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Set<TypeDTO> getTypeDTO() {
		return typeDTO;
	}

	public void setTypeDTO(Set<TypeDTO> typeDTO) {
		this.typeDTO = typeDTO;
	}
	
	
	
}

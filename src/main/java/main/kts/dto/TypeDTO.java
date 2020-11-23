package main.kts.dto;


public class TypeDTO {

	private String name;
	private String description;   
	public CategoryDTO categoryDTO;
	
	public TypeDTO() {
		super();
	}
	
	public TypeDTO(String name, String description, CategoryDTO categoryDTO) {
		super();
		this.name = name;
		this.description = description;
		this.categoryDTO = categoryDTO;
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

	public CategoryDTO getCategoryDTO() {
		return categoryDTO;
	}

	public void setCategoryDTO(CategoryDTO categoryDTO) {
		this.categoryDTO = categoryDTO;
	}

	
}

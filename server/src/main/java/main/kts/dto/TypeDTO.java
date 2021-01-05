package main.kts.dto;


public class TypeDTO {

	private Long id;
	private String name;
	private String description;   
	public CategoryDTO categoryDTO;
	
	public TypeDTO() {
		super();
	}
	
	public TypeDTO(Long id, String name, String description, CategoryDTO categoryDTO) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.categoryDTO = categoryDTO;
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


	public CategoryDTO getCategoryDTO() {
		return categoryDTO;
	}

	public void setCategoryDTO(CategoryDTO categoryDTO) {
		this.categoryDTO = categoryDTO;
	}

	@Override
	public String toString() {
		return "TypeDTO [id=" + id + ", name=" + name + ", description=" + description + ", categoryDTO=" + categoryDTO
				+ "]";
	}

	
}

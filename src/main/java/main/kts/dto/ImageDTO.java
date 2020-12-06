package main.kts.dto;

public class ImageDTO {

	private Long id;
	private String name;
	private String relativePath;
	
	public ImageDTO() {
		super();
	}
	
	public ImageDTO(Long id, String name, String relativePath) {
		super();
		this.id = id;
		this.name = name;
		this.relativePath = relativePath;
	}

	public ImageDTO(String name2, String originalFilename) {
		this.name = name2;
		this.relativePath = originalFilename;
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

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}
	
}

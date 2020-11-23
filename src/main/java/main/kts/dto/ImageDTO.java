package main.kts.dto;

public class ImageDTO {

	private String name;
	private String relativePath;
	
	public ImageDTO() {
		super();
	}
	
	public ImageDTO(String name, String relativePath) {
		super();
		this.name = name;
		this.relativePath = relativePath;
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

package main.kts.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Image {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String relativePath;

	public Image() {
		super();
	}

	public Image(String name, String relativePath) {
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

	public String getrelativePath() {
		return relativePath;
	}

	public void setrelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

}
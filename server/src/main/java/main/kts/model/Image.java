package main.kts.model;

import java.util.Objects;

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

	@Column(nullable = false, unique = false)
	private String name;
	@Column(nullable = false, unique = false)
	private String relativePath;

	@Column(nullable = false)
	private Boolean active;
	
	public Image() {
		super();
	}

	public Image(Long id, String name, String relativePath) {
		super();
		this.id = id;
		this.name = name;
		this.relativePath = relativePath;
		this.active = true;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Image image = (Image) o;
        if (image.getId() == null || id == null) {
            if(image.relativePath == getRelativePath()){
                return true;
            }
            return false;
        }
        return Objects.equals(id, image.getId());
    }

}
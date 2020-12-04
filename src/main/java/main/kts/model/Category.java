package main.kts.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String name;

	@Column(unique = false, nullable = false)
	private String description;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
	public Set<Type> type;

	@Column(nullable = false)
	private Boolean active;
	
	public Category(Long id, String name, String description, Set<Type> type) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.type = type;
		this.active = true;
	}

	public Category() {
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

	public Set<Type> getType() {
		return type;
	}

	public void setType(Set<Type> type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	
}
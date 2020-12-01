package main.kts.dto;

import java.util.Set;
import java.util.Date;


public class CulturalOfferDTO {
	
	private Long id;
	private double averageRate;
	private String description;
	private String name;
	private Date date;
	private double lat;
	private double lon;
	   
	public Set<PostDTO> postDTO;
	public TypeDTO typeDTO;
	public Set<ImageDTO> imageDTO;
	public Set<CommentDTO> commentDTO;
	
	public CulturalOfferDTO() {
		super();
	}
	
	public CulturalOfferDTO(Long id, double averageRate, String description, String name, Date date, double lat, double lon,
			Set<PostDTO> postDTO, TypeDTO typeDTO, Set<ImageDTO> imageDTO, Set<CommentDTO> commentDTO) {
		super();
		this.id = id;
		this.averageRate = averageRate;
		this.description = description;
		this.name = name;
		this.date = date;
		this.lat = lat;
		this.lon = lon;
		this.postDTO = postDTO;
		this.typeDTO = typeDTO;
		this.imageDTO = imageDTO;
		this.commentDTO = commentDTO;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getAverageRate() {
		return averageRate;
	}

	public void setAverageRate(double averageRate) {
		this.averageRate = averageRate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public Set<PostDTO> getPostDTO() {
		return postDTO;
	}

	public void setPostDTO(Set<PostDTO> postDTO) {
		this.postDTO = postDTO;
	}

	public TypeDTO getTypeDTO() {
		return typeDTO;
	}

	public void setTypeDTO(TypeDTO typeDTO) {
		this.typeDTO = typeDTO;
	}

	public Set<ImageDTO> getImage() {
		return imageDTO;
	}

	public void setImage(Set<ImageDTO> imageDTO) {
		this.imageDTO = imageDTO;
	}

	public Set<ImageDTO> getImageDTO() {
		return imageDTO;
	}

	public void setImageDTO(Set<ImageDTO> imageDTO) {
		this.imageDTO = imageDTO;
	}

	public Set<CommentDTO> getCommentDTO() {
		return commentDTO;
	}

	public void setCommentDTO(Set<CommentDTO> commentDTO) {
		this.commentDTO = commentDTO;
	}

	
	
}

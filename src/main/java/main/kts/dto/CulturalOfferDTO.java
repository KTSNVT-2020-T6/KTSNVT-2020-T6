package main.kts.dto;

import java.util.ArrayList;
import java.util.Date;


public class CulturalOfferDTO {
	
	private double averageRate;
	private String description;
	private String name;
	private Date date;
	private double lat;
	private double lon;
	   
	public ArrayList<PostDTO> postDTO;
	public TypeDTO typeDTO;
	public ArrayList<ImageDTO> imageDTO;
	
	public CulturalOfferDTO() {
		super();
	}
	
	public CulturalOfferDTO(double averageRate, String description, String name, Date date, double lat, double lon,
			ArrayList<PostDTO> postDTO, TypeDTO typeDTO, ArrayList<ImageDTO> imageDTO) {
		super();
		this.averageRate = averageRate;
		this.description = description;
		this.name = name;
		this.date = date;
		this.lat = lat;
		this.lon = lon;
		this.postDTO = postDTO;
		this.typeDTO = typeDTO;
		this.imageDTO = imageDTO;
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

	public ArrayList<PostDTO> getPostDTO() {
		return postDTO;
	}

	public void setPostDTO(ArrayList<PostDTO> postDTO) {
		this.postDTO = postDTO;
	}

	public TypeDTO getTypeDTO() {
		return typeDTO;
	}

	public void setTypeDTO(TypeDTO typeDTO) {
		this.typeDTO = typeDTO;
	}

	public ArrayList<ImageDTO> getImage() {
		return imageDTO;
	}

	public void setImage(ArrayList<ImageDTO> imageDTO) {
		this.imageDTO = imageDTO;
	}

	
	
}

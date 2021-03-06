package main.kts.model;

import java.util.Set;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class CulturalOffer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = false, nullable = true)
	private double averageRate;

	@Column(unique = false, nullable = true)
	private String description;

	@Column(unique = true, nullable = false)
	private String name;

	@Column(unique = false, nullable = true)
	private Date date;

	@Column(unique = false, nullable = false)
	private double lat;

	@Column(unique = false, nullable = false)
	private double lon;
	
	@Column(unique = false, nullable = false)
	private String city;


	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "cultural_offer_id", nullable = true)
	public Set<Post> post;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "type_id", nullable = false)
	public Type type;

	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name = "cultural_offer_id", nullable = true)
	public Set<Image> image;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "culturalOffer")
	public Set<Comment> comment;

	@Column(nullable = false)
	private Boolean active;
	
	public CulturalOffer() {
	}

	public CulturalOffer(Long id,double averageRate, String description, String name, Date date, double lat, double lon,
			Set<Post> post, Type type, Set<Image> image, Set<Comment> comment) {
		this.id = id;
		this.averageRate = averageRate;
		this.description = description;
		this.name = name;
		this.date = date;
		this.lat = lat;
		this.lon = lon;
		this.post = post;
		this.type = type;
		this.image = image;
		this.comment = comment;
		this.active = true;
	}

	public CulturalOffer(Long id, double averageRate, String description, String name, Date date, double lat,
			double lon, String city, Set<Post> post, Type type, Set<Image> image, Set<Comment> comment) {
		super();
		this.id = id;
		this.averageRate = averageRate;
		this.description = description;
		this.name = name;
		this.date = date;
		this.lat = lat;
		this.lon = lon;
		this.city = city;
		this.post = post;
		this.type = type;
		this.image = image;
		this.comment = comment;
		this.active = true;
	}

	public CulturalOffer(Long culturalOfferId) {
		this.id = culturalOfferId;
		this.active = true;
		this.averageRate = 5;
	}

	public CulturalOffer(String name, String description, Date date, String city, double lat,
			double lon, double averageRate) {
		this.averageRate = averageRate;
		this.description = description;
		this.name = name;
		this.date = date;
		this.lat = lat;
		this.lon = lon;
		this.city = city;
		this.post = new HashSet<Post>();
		this.type = new Type();
		this.image = new HashSet<Image>();
		this.comment = new HashSet<Comment>();
		this.active = true;
	}

	public CulturalOffer(String name, String description, Date date, String city, double lat,
			double lon, double averageRate, Type t) {
		this.averageRate = averageRate;
		this.description = description;
		this.name = name;
		this.date = date;
		this.lat = lat;
		this.lon = lon;
		this.city = city;
		this.post = new HashSet<Post>();
		this.type =t;
		this.image = new HashSet<Image>();
		this.comment = new HashSet<Comment>();
		this.active = true;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public Set<Post> getPost() {
		return post;
	}

	public void setPost(Set<Post> post) {
		this.post = post;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Set<Image> getImage() {
		return image;
	}

	public void setImage(Set<Image> image) {
		this.image = image;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Comment> getComment() {
		return comment;
	}

	public void setComment(Set<Comment> comment) {
		this.comment = comment;
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
        CulturalOffer co = (CulturalOffer) o;
        if (co.getId() == null || id == null) {
            if(co.getName().equals(getName())){
                return true;
            }
            return false;
        }
        return Objects.equals(id, co.getId());
    }

	@Override
	public String toString() {
		return "CulturalOffer [id=" + id + ", averageRate=" + averageRate + ", description=" + description + ", name="
				+ name + ", date=" + date + ", lat=" + lat + ", lon=" + lon + ", city=" + city + ", post=" + post
				+ ", type=" + type + ", image=" + image + ", comment=" + comment + ", active=" + active + "]";
	}

}
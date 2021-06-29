package com.nw.model;

import java.io.Serializable;
import java.time.Year;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.nw.converter.YearAttributeConverter;

@Entity
public class Movie implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "Enter movie name.")
	private String name;

	@NotNull(message = "Please select movie's released year.")
	@Convert(converter = YearAttributeConverter.class)
	private Year year;
	
	@NotBlank(message = "Please enter movie description.")
	private String description;

	@NotBlank(message = "Please select image file.")
	private String image;

	@NotEmpty(message = "Please selected genre.")
	@ElementCollection(targetClass = Genre.class)
	@CollectionTable(name = "movie_genre")
	@Column(name = "genre")
	@Enumerated(EnumType.STRING)
	private Collection<Genre> genre;
	
	@NotEmpty(message = "Please selected movie language.")
	@ElementCollection(targetClass = Language.class)
	@CollectionTable(name = "movie_language")
	@Column(name = "language")
	@Enumerated(EnumType.STRING)
	private Collection<Language> language;

	@NotEmpty(message = "Please select actors.")
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "movie_actor", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "actor_id"))
	private Set<Actor> actors = new HashSet<>();

	public Movie() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Year getYear() {
		return year;
	}

	public void setYear(Year year) {
		this.year = year;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Set<Actor> getActors() {
		return actors;
	}

	public void setActors(Set<Actor> actors) {
		this.actors = actors;
	}

	public Collection<Genre> getGenre() {
		return genre;
	}

	public void setGenre(Collection<Genre> genre) {
		this.genre = genre;
	}

	public Collection<Language> getLanguage() {
		return language;
	}

	public void setLanguage(Collection<Language> language) {
		this.language = language;
	}

	public String getPhotosPath() {
		if (image == null || id == null) {
			return null;
		}
		return "/images/movie/" + id + "/" + image;
	}

}

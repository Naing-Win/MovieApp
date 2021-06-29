package com.nw.service;

import java.util.List;

import com.nw.model.Movie;

public interface MovieService {
	
	public Movie save(Movie movie);
	public List<Movie> findAll();
	public List<Movie> findByName(String name);
	public Movie findById(int id);

}

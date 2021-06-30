package com.nw.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.nw.model.Movie;

public interface MovieService {
	
	public Movie save(Movie movie);
	public List<Movie> findAll();
	public List<Movie> findByName(String name);
	public Movie findById(int id);
	Page<Movie> getPaginated(int pageNo, int pageSize);

}

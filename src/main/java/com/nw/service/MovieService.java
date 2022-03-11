package com.nw.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nw.model.Movie;

public interface MovieService {
	
	public Movie save(Movie movie);
	public List<Movie> findAll();
	public Page<Movie> findByName(String name, Pageable pageable);
	public Movie findById(int id);
	Page<Movie> getPaginated(int pageNo, int pageSize, String sortField, String sortDir);
	//public List<Movie> searchByName(String keyword);
}

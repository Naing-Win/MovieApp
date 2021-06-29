package com.nw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nw.model.Movie;
import com.nw.repository.MovieRepository;

@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieRepository movieRepository;

	@Override
	public Movie save(Movie movie) {
		// TODO Auto-generated method stub
		return movieRepository.save(movie);
	}

	@Override
	public List<Movie> findAll() {
		// TODO Auto-generated method stub
		return movieRepository.findAll();
	}

	@Override
	public List<Movie> findByName(String name) {
		// TODO Auto-generated method stub
		return movieRepository.findByName(name);
	}

	@Override
	public Movie findById(int id) {
		// TODO Auto-generated method stub
		return movieRepository.getById(id);
	}

}

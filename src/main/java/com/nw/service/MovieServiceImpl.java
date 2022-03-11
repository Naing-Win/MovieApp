package com.nw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	public Page<Movie> findByName(String name, Pageable pageable) {
		// TODO Auto-generated method stub
		return movieRepository.findByName(name, pageable);
	}

	@Override
	public Movie findById(int id) {
		// TODO Auto-generated method stub
		return movieRepository.getById(id);
	}

	@Override
	public Page<Movie> getPaginated(int pageNo, int pageSize, String sortField, String sortDir) {
		// TODO Auto-generated method stub
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return movieRepository.findAll(pageable);
	}

	/*
	@Override
	public List<Movie> searchByName(String keyword) {
		// TODO Auto-generated method stub
		return movieRepository.searchByName(keyword);
	}
	*/
}

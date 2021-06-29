package com.nw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nw.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
	
	@Query(value = "select * from Movie order by id desc", nativeQuery = true)
	public List<Movie> findAll();
	
	@Query(value = "select * from Movie where name like '%:name%' or genre like '%:name%'", nativeQuery = true)
	public List<Movie> findByName(String name);

}

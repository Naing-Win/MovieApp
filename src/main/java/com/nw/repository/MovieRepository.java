package com.nw.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nw.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
	
	@Query(value = "select * from movie order by id desc", nativeQuery = true)
	public List<Movie> findAll();
	
	@Query(value = "select * from movie where name like %?1%", nativeQuery = true)
	public Page<Movie> findByName(String name, Pageable pageable);
	
	@Query(value = "select * from movie where name like %?1%", nativeQuery = true)
	public List<Movie> searchByName(String keyword);
	
}

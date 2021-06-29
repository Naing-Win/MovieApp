package com.nw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nw.model.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {
	
	//@Query("select * from movie_actor where ")
	//public List<Actor> getActorsByMovieName(@Param("name") String name);
}

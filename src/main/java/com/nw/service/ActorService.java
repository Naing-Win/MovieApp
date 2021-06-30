package com.nw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.nw.model.Actor;

public interface ActorService {

	public void save(Actor actor);
	public List<Actor> findAll();
	public Optional<Actor> getActorsByMovieId(int id);
	Page<Actor> getPaginated(int pageNo, int pageSize);
}

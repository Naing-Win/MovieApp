package com.nw.service;

import java.util.List;
import java.util.Optional;

import com.nw.model.Actor;

public interface ActorService {

	public void save(Actor actor);
	public List<Actor> findAll();
	public Optional<Actor> getActorsByMovieId(int id);
}

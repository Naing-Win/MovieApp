package com.nw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nw.model.Actor;
import com.nw.repository.ActorRepository;

@Service
public class ActorServiceImpl implements ActorService {
	
	@Autowired
	private ActorRepository actorRepository;

	@Override
	public void save(Actor actor) {
		// TODO Auto-generated method stub
		actorRepository.save(actor);
	}

	@Override
	public List<Actor> findAll() {
		// TODO Auto-generated method stub
		return actorRepository.findAll();
	}

	@Override
	public Optional<Actor> getActorsByMovieId(int id) {
		// TODO Auto-generated method stub
		return actorRepository.findById(id);
	}

}

package com.nw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nw.model.Actor;
import com.nw.service.ActorServiceImpl;

@Controller
@RequestMapping("/actor")
public class ActorController {

	@Autowired
	private ActorServiceImpl actorService;
	
	@GetMapping("/list")
	public String getAllActors(Model model) {
		model.addAttribute("actors", actorService.findAll());
		return "actor/list";
	}
	
	@GetMapping("/create")
	public String showActorForm(Actor actor) {
		return "actor/create";
	}
	
	@PostMapping("/create")
	public String processActorForm(Actor actor) {
		actorService.save(actor);
		return "redirect:/actor/list";
	}
}

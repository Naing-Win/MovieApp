package com.nw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		//model.addAttribute("actors", actorService.findAll());
		//return "actor/list";
		return getPaginated(1, model);
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
	
	@GetMapping("/page/{pageNo}")
	public String getPaginated(@PathVariable("pageNo") int pageNo, Model model) {
		int pageSize = 9;
		Page<Actor> page = actorService.getPaginated(pageNo, pageSize);
		List<Actor> actors = page.getContent();
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("actors", actors);
		return "actor/list";
	}
}

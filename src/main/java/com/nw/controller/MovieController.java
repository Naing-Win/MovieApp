package com.nw.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.nw.common.FileStorageCommon;
import com.nw.model.Movie;
import com.nw.service.ActorServiceImpl;
import com.nw.service.MovieServiceImpl;

@Controller
@RequestMapping("/movie")
public class MovieController {

	@Autowired
	private MovieServiceImpl movieService;
	@Autowired
	private ActorServiceImpl actorService;

	@GetMapping("/list")
	public String getMainPage(Model model, String keyword) {
		return getPaginated(1, "name", "asc", keyword, model);
	}

	@GetMapping("/create")
	public String showMovieForm(Model model) {
		model.addAttribute("actors", actorService.findAll());
		model.addAttribute("movie", new Movie());
		return "movie/create";
	}

	@PostMapping(value = "/create")
	public String processMovieForm(Model model, @RequestParam("file") MultipartFile file, @Valid Movie movie,
			BindingResult result) throws IOException {
		if (result.hasErrors()) {
			model.addAttribute("actors", actorService.findAll());
			return "movie/create";
		}
		String fileName = file.getOriginalFilename();
		movie.setImage(fileName);
		Movie savedMovie = movieService.save(movie);
		FileStorageCommon.fileStorage(file, savedMovie.getId(), savedMovie);
		return "redirect:/movie/list";
	}

	@GetMapping("/{id}")
	public String getMovieById(@PathVariable int id, Model model) {
		Movie movie = movieService.findById(id);
		model.addAttribute("movie", movie);
		return "movie/detail";
	}

	@GetMapping("/update/{id}")
	public String updateMovieForm(@PathVariable int id, Model model) {
		model.addAttribute("movie", movieService.findById(id));
		model.addAttribute("actors", actorService.findAll());
		return "movie/update";
	}

	@PostMapping(value = "/update/{id}")
	public String processUpdateMovieForm(@PathVariable int id, Movie movie, @RequestParam("file") MultipartFile file,
			BindingResult result) throws IOException {
		Movie tempMovie = movieService.findById(id);
		String fileName = file.getOriginalFilename();
		if (fileName.equals("") || result.hasErrors()) {
			return "redirect:/movie/update/" + id;
		}
		movie.setImage(fileName);
		tempMovie.setDescription(movie.getDescription());
		tempMovie.setGenre(movie.getGenre());
		tempMovie.setId(id);
		tempMovie.setLanguage(movie.getLanguage());
		tempMovie.setName(movie.getName());
		tempMovie.setYear(movie.getYear());
		tempMovie.setActors(movie.getActors());
		Movie savedMovie = movieService.save(tempMovie);
		FileStorageCommon.fileStorage(file, savedMovie.getId(), savedMovie);
		return "redirect:/movie/";
	}

	@GetMapping("/page/{pageNo}")
	public String getPaginated(@PathVariable("pageNo") int pageNo, @RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir, @Param("keyword") String keyword, Model model) {
		int pageSize = 10;
		Page<Movie> page = movieService.getPaginated(pageNo, pageSize, sortField, sortDir);
		List<Movie> movies = page.getContent();
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		if (keyword != null) {
			model.addAttribute("movies", movieService.findByName(keyword, PageRequest.of(pageNo - 1, pageSize)));
		} else {
			model.addAttribute("movies", movies);
		}
		return "index";
	}

	/*
	 * @GetMapping("/search") public String searchByName(Model
	 * model, @Param("keyword") String keyword) { if (keyword != null) {
	 * //List<Movie> movies = movieService.searchByName(keyword);
	 * model.addAttribute("movies", movieService.searchByName(keyword)); } else {
	 * model.addAttribute("movies", movieService.findAll()); } return "index"; }
	 */
}

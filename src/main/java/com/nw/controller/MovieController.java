package com.nw.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

	@GetMapping("/")
	public String getMainPage(Model model, String keyword) {
		/*
		if (keyword != null) {
			model.addAttribute("movies", movieService.findByName(keyword));
		} else {
			List<Movie> movies = movieService.findAll();
			model.addAttribute("movies", movies);
			model.addAttribute("actors", actorService.findAll());
		}
		*/
		return getPaginated(1, model, keyword);
	}

	@GetMapping("/create")
	public String showMovieForm(Model model) {
		model.addAttribute("actors", actorService.findAll());
		model.addAttribute("movie", new Movie());
		return "movie/create";
	}

	@PostMapping(value = "/create")
	public String processMovieForm(Model model, @RequestParam("file") MultipartFile file, @Valid Movie movie, BindingResult result) throws IOException {
		if (result.hasErrors()) {
			model.addAttribute("actors", actorService.findAll());
			return "movie/create";
		}
		String fileName = file.getOriginalFilename();
		movie.setImage(fileName);
		Movie savedMovie = movieService.save(movie);
		FileStorageCommon.fileStorage(file, savedMovie.getId(), savedMovie);
		/*
		String uploadDir = "src\\main\\resources\\static\\images\\movie" + "\\" + savedMovie.getId();
		Path uploadPath = Paths.get(uploadDir);
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ioe) {
			throw new IOException("Could not save image file: " + fileName, ioe);
		}
		*/
		return "redirect:/movie/";
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
	public String processUpdateMovieForm(@PathVariable int id, Movie movie, @RequestParam("file") MultipartFile file, BindingResult result) throws IOException {
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
		/*
		String uploadDir = "src/main/resources/static/images/movie/" + savedMovie.getId();
		Path uploadPath = Paths.get(uploadDir);
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ioe) {
			throw new IOException("Could not save image file: " + fileName, ioe);
		}
		*/
		return "redirect:/movie/";
	}
	
	@GetMapping("/page/{pageNo}")
	public String getPaginated(@PathVariable("pageNo") int pageNo, Model model, String keyword) {
		int pageSize = 10;
		Page<Movie> page = movieService.getPaginated(pageNo, pageSize);
		List<Movie> movies = page.getContent();
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		if (keyword != null) {
			model.addAttribute("movies", movieService.findByName(keyword));
		} else {
			model.addAttribute("movies", movies);
			model.addAttribute("actors", actorService.findAll());
		}
		return "index";
	}

}

package it.uniroma3.lezioniexpert.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.lezioniexpert.repository.ProfessorRepository;


@Controller
public class ProfessorController {
	@Autowired ProfessorRepository professorRepository;
	
	
	/*GET DELLA PAGINA CON LA LISTA DI TUTTI GLI STUDENTI*/
	@GetMapping("/professors")
	public String getMovies(Model model) {		
		model.addAttribute("professors", this.professorRepository.findAll());
		return "professors.html";
	}
	
	
	
	/*GET DELLA PAGINA DESCRITTIVA DEL SINGOLO STUDENTE*/
	@GetMapping("/professor/{id}")
	public String getMovie(@PathVariable Long id, Model model) {
		model.addAttribute("professor", this.professorRepository.findById(id).get());
		return "professor.html";
	}	
	

	
}


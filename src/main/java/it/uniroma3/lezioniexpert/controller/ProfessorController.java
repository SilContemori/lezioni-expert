package it.uniroma3.lezioniexpert.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.lezioniexpert.model.Credentials;
import it.uniroma3.lezioniexpert.repository.ProfessorRepository;
import it.uniroma3.lezioniexpert.service.CredentialsService;


@Controller
public class ProfessorController {
	@Autowired ProfessorRepository professorRepository;
	@Autowired CredentialsService credentialsService;
	
	
	/*GET DELLA PAGINA CON LA LISTA DI TUTTI GLI STUDENTI*/
	@GetMapping("/professors")
	public String getMovies(Model model) {		
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		if(credentials.getProfessor()!=null) {
			model.addAttribute("professor", credentials.getProfessor());
		}
		model.addAttribute("professors", this.professorRepository.findAll());
		return "professors.html";
	}
	
	
	
	/*GET DELLA PAGINA DESCRITTIVA DEL SINGOLO STUDENTE*/
	@GetMapping("/professor/{id}")
	public String getMovie(@PathVariable Long id, Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		if(credentials.getProfessor()!=null) {
			model.addAttribute("currentProfessor", credentials.getProfessor());
		}
		model.addAttribute("professor", this.professorRepository.findById(id).get());
		return "professor.html";
	}	
	

	
}


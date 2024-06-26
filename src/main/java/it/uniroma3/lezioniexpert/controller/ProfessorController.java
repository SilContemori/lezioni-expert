package it.uniroma3.lezioniexpert.controller;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.lezioniexpert.model.Credentials;
import it.uniroma3.lezioniexpert.repository.ProfessorRepository;
import it.uniroma3.lezioniexpert.service.CredentialsService;
import it.uniroma3.lezioniexpert.repository.UserRepository;

@Controller
public class ProfessorController {
	@Autowired ProfessorRepository professorRepository;
	@Autowired UserRepository userRepository;
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
	
	
	
	/*GET DELLA PAGINA DESCRITTIVA DEL SINGOLO Professor*/
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
	
	
	@GetMapping(value = "/profilePage") 
	public String getProfilePage(Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		if (credentials.getRole().equals(Credentials.PROFESSOR_ROLE)) {
			//				return "admin/indexAdmin.html";
			model.addAttribute("professor", this.professorRepository.findById(credentials.getProfessor().getId()).get());
			return "professorProfilePage.html";
		}else if(credentials.getRole().equals(Credentials.DEFAULT_ROLE)){
			model.addAttribute("user", this.userRepository.findById(credentials.getUser().getId()).get());
			return "userProfilePage.html";
		}else {
			model.addAttribute("user", this.userRepository.findById(credentials.getUser().getId()).get());
			return "adminProfilePage.html";
		}
	}
	
}


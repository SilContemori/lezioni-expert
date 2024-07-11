package it.uniroma3.lezioniexpert.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.lezioniexpert.model.Credentials;
import it.uniroma3.lezioniexpert.model.Education;
import it.uniroma3.lezioniexpert.model.Professor;
import it.uniroma3.lezioniexpert.repository.EducationRepository;
import it.uniroma3.lezioniexpert.repository.ProfessorRepository;
import it.uniroma3.lezioniexpert.service.CredentialsService;
import jakarta.validation.Valid;

@Controller
public class EducationController {
	@Autowired EducationRepository educationRepository;
	@Autowired ProfessorRepository professorRepository;
	@Autowired CredentialsService credentialsService;
	
	/*GET E POST PER AGGIUNGERE UNA NUOVA EDUCAZIONE A UN PROFESSORE*/
	@GetMapping(value="/formNewEducation/{idProfessor}")
	public String formNewEducation(@PathVariable Long idProfessor,Model model) {
		model.addAttribute("education", new Education());
		model.addAttribute("professor", this.professorRepository.findById(idProfessor).orElse(null));
		return "formNewEducation.html";
	}
	
	@PostMapping("/newEducation/{idProfessor}")
	public String newEducation(@PathVariable Long idProfessor,@Valid @ModelAttribute Education education, BindingResult bindingResult, Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		if(credentials.getRole().equals(Credentials.PROFESSOR_ROLE)) {
			Professor p=professorRepository.findById(idProfessor).orElse(null);
			boolean b=false;
			for(Education e:p.getEducations()) {
				if(e.equals(education)) {
					b=true;
				}
			}
			if(!b) {
				education.setProfessor(p);
				p.getEducations().add(education);
				this.educationRepository.save(education);
				this.professorRepository.save(p);
				return "redirect:/profilePage";
			}else {
				return "redirect:/profilePage";
			}
		}else {
			Professor p=professorRepository.findById(idProfessor).orElse(null);
			boolean b=false;
			for(Education e:p.getEducations()) {
				if(e.equals(education)) {
					b=true;
				}
			}
			if(!b) {
				education.setProfessor(p);
				p.getEducations().add(education);
				this.educationRepository.save(education);
				this.professorRepository.save(p);
				return "redirect:/professor/"+p.getId();
			}else {
				return "redirect:/professor/"+p.getId();
			}
			
		}
	}
	
	/*GET PER RIMUOVI EDUCAZIONE DI UN PROFESSORE*/
	@GetMapping("/removeEducation/{idProfessor}/{idEducation}")
	public String removeEducation(@PathVariable Long idProfessor,@PathVariable Long idEducation,Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		Education e=educationRepository.findById(idEducation).orElse(null);
		Professor p=professorRepository.findById(idProfessor).orElse(null);
		e.setProfessor(null);
		p.getEducations().remove(e);
		this.educationRepository.delete(e);
		this.professorRepository.save(p);
		if(credentials.getRole().equals(Credentials.PROFESSOR_ROLE)) {
			return "redirect:/profilePage";
		}
		return "redirect:/professor/"+p.getId();
	}
}

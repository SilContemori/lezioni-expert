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
import it.uniroma3.lezioniexpert.model.Professor;
import it.uniroma3.lezioniexpert.model.Review;
import it.uniroma3.lezioniexpert.repository.ProfessorRepository;
import it.uniroma3.lezioniexpert.repository.ReviewRepository;
import it.uniroma3.lezioniexpert.repository.UserRepository;
import it.uniroma3.lezioniexpert.service.CredentialsService;
import jakarta.validation.Valid;

@Controller
public class ReviewController {

	@Autowired ReviewRepository reviewRepository;
	@Autowired ProfessorRepository professorRepository;
	@Autowired CredentialsService credentialsService;
	@Autowired UserRepository userRepository;

	/*GET E POST PER AGGIUNGERE UNA RECENSIONE*/
	@GetMapping(value="/formNewReview/{idProfessor}")
	public String formNewReview(@PathVariable Long idProfessor,Model model) {
		model.addAttribute("review", new Review());
		model.addAttribute("professor", this.professorRepository.findById(idProfessor).orElse(null));
		return "formNewReview.html";
	}

	@PostMapping("/newReview/{idProfessor}")
	public String newReview(@PathVariable Long idProfessor,@Valid @ModelAttribute Review review, BindingResult bindingResult, Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		Professor p=professorRepository.findById(idProfessor).orElse(null);
		boolean b=false;
		for(Review r:p.getReviews()) {
			if(r.equals(review)) {
				b=true;
			}
		}
		if(!b) {
			review.setProfessor(p);
			review.setAuthor(credentials.getUser());
			p.getReviews().add(review);
			credentials.getUser().getReviewsDone().add(review);
			this.reviewRepository.save(review);
			this.userRepository.save(credentials.getUser());
			this.professorRepository.save(p);
			return "redirect:/professor/"+p.getId();
		}else {
			return "redirect:/professor/"+p.getId();
		}
	}
	
	/*GET PER RIMUOVERE UNA RECENSIONE DI UN PROFESSORE*/
	@GetMapping("/removeReview/{idProfessor}/{idReview}")
	public String removeReview(@PathVariable Long idProfessor,@PathVariable Long idReview,Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		Review r=reviewRepository.findById(idReview).orElse(null);
		Professor p=professorRepository.findById(idProfessor).orElse(null);
		r.setProfessor(null);
		r.setAuthor(null);
		p.getEducations().remove(r);
		credentials.getUser().getReviewsDone().remove(r);
		this.reviewRepository.delete(r);
		this.professorRepository.save(p);
		this.userRepository.save(credentials.getUser());
		if(credentials.getRole().equals(Credentials.PROFESSOR_ROLE)) {
			return "redirect:/profilePage";
		}
		return "redirect:/professor/"+p.getId();
	}

}

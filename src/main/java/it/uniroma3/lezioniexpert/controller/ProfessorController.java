package it.uniroma3.lezioniexpert.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.lezioniexpert.model.Announcement;
import it.uniroma3.lezioniexpert.model.Credentials;
import it.uniroma3.lezioniexpert.model.Images;
import it.uniroma3.lezioniexpert.model.Professor;
import it.uniroma3.lezioniexpert.repository.AnnouncementRepository;
import it.uniroma3.lezioniexpert.repository.CredentialsRepository;
import it.uniroma3.lezioniexpert.repository.ImagesRepository;
import it.uniroma3.lezioniexpert.repository.ProfessorRepository;
import it.uniroma3.lezioniexpert.service.CredentialsService;
import jakarta.validation.Valid;
import it.uniroma3.lezioniexpert.repository.UserRepository;

@Controller
public class ProfessorController {
	@Autowired ProfessorRepository professorRepository;
	@Autowired UserRepository userRepository;
	@Autowired CredentialsService credentialsService;
	@Autowired ImagesRepository imageRepository;
	@Autowired CredentialsRepository credentialsRepository;
	@Autowired AnnouncementRepository announcementRepository;
	
	
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
		if(credentials.getRole().equals(Credentials.PROFESSOR_ROLE)) {
			model.addAttribute("professorRole", credentials.getRole());
			return "professor.html";
		}else {
			if(credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
				model.addAttribute("adminRole", credentials.getRole());
				return "professorEditable.html";
			}
		}
		model.addAttribute("userRole", credentials.getRole());
		return "professorEditable.html";
	}	
	
	
	@GetMapping(value = "/profilePage") 
	public String getProfilePage(Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		if (credentials.getRole().equals(Credentials.PROFESSOR_ROLE)) {
			//				return "admin/indexAdmin.html";
			if(credentials.getProfessor().getAnnouncements()!=null) {
				List<String> subjects=new ArrayList<>();
				for(Announcement a:credentials.getProfessor().getAnnouncements()) {
					if(a.getSubject()!=null) {
						subjects.add(a.getSubject().getName());
					}
				}
				if(subjects.size()!=0) {
					model.addAttribute("subjects", subjects);
				}
			}
			Professor p= this.professorRepository.findById(credentials.getProfessor().getId()).get();
			model.addAttribute("announcements", p.getAnnouncements());
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
	
	/*GET E POST PER AGGIUNGERE UN NUOVO PROFESSORE*/
	@GetMapping(value="/formNewProfessor")
	public String formNewProfessor(Model model) {
		model.addAttribute("professor", new Professor());
		return "formNewProfessor.html";
	}

	@PostMapping(value={"/professor"},consumes = "multipart/form-data")
	public String newProfessor(@Valid @ModelAttribute Professor professor,@RequestPart("file") MultipartFile file, BindingResult bindingResult, Model model) {
		//		this.movieValidator.validate(movie, bindingResult);
		if (!bindingResult.hasErrors()) {
			try {
				Images i=new Images();
				i.setImageData(file.getBytes());
				professor.setProfileImage(i);
				this.imageRepository.save(i);
			} catch (Exception e) {
				System.out.println("erroreeee");
			}
			this.professorRepository.save(professor);
			return "redirect:/professor/"+professor.getId();
		} else {
			return "formNewProfessor.html"; 
		}
	}
	
	/*GET PER RIMUOVERE UN PROFESSORE E TUTTI I SUOI ANNUNCI*/
	@GetMapping("/removeProfessor/{idProfessor}")
	public String removeProfessor(@PathVariable("idProfessor") Long idProfessor, Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		Professor professor= this.professorRepository.findById(idProfessor).get();
		List<Announcement> announcements = professor.getAnnouncements();
		for(Announcement a: announcements) {
			a.setProfessor(null);
		}
		professor.setAnnouncements(null);
		this.announcementRepository.deleteAll(announcements);
	    this.professorRepository.save(professor);
		Iterable<Credentials> allCredentials = this.credentialsRepository.findAll();
		for(Credentials i: allCredentials) {
			if(i.getProfessor() != null) {
				if(i.getProfessor().getId() == idProfessor) {
					if(!i.getRole().equals(Credentials.ADMIN_ROLE)) {
		                i.setProfessor(null);
		                this.credentialsRepository.delete(i);
		            }
				}
			}
		}
		this.professorRepository.delete(professor);
		model.addAttribute("user", this.userRepository.findById(credentials.getUser().getId()).get());
		return "adminProfilePage.html";
	}
	
	/*GET PER SETTARE UN PROFESSORE A UN ANNUNCIO ESISTENTE*/
	@GetMapping(value="/setProfessor/{idAnnouncement}")
	public String setProfessor(@PathVariable Long idAnnouncement,Model model) {
		Announcement a=announcementRepository.findById(idAnnouncement).orElse(null);
		model.addAttribute("professors",this.professorRepository.findAll());
		model.addAttribute("announcement",a);
		return "setProfessor.html";
	}
	
	@GetMapping(value="/setProfessor/{idAnnouncement}/{idProfessor}")
	public String setProfessorAnnouncement(@PathVariable Long idAnnouncement,@PathVariable Long idProfessor,Model model) {
		Announcement a=announcementRepository.findById(idAnnouncement).orElse(null);
		Professor p=professorRepository.findById(idProfessor).orElse(null);
		a.setProfessor(p);
		p.getAnnouncements().add(a);
		announcementRepository.save(a);
		professorRepository.save(p);
		return "redirect:/announcement/"+a.getId();
		
	}
	
}


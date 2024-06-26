package it.uniroma3.lezioniexpert.controller;

import static it.uniroma3.lezioniexpert.model.Credentials.PROFESSOR_ROLE;

import java.util.ArrayList;
import java.util.List;

import static it.uniroma3.lezioniexpert.model.Credentials.ADMIN_ROLE;
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

import it.uniroma3.lezioniexpert.model.Announcement;
import it.uniroma3.lezioniexpert.model.Credentials;
import it.uniroma3.lezioniexpert.model.Professor;
import it.uniroma3.lezioniexpert.model.Subject;
import it.uniroma3.lezioniexpert.repository.AnnouncementRepository;
import it.uniroma3.lezioniexpert.repository.ProfessorRepository;
import it.uniroma3.lezioniexpert.repository.SubjectRepository;
import it.uniroma3.lezioniexpert.service.CredentialsService;
import jakarta.validation.Valid;

@Controller
public class AnnouncementController {
	@Autowired AnnouncementRepository announcementRepository;
	@Autowired ProfessorRepository professorRepository;
	@Autowired CredentialsService credentialsService;
	@Autowired SubjectRepository subjectRepository;
	
	/*GET DELLA PAGINA DEGLI ANNUNCI*/
	@GetMapping("/announcements")
	public String getAnnouncements(Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		model.addAttribute("announcements", this.announcementRepository.findAll());
		if(credentials.getProfessor()!=null) {
			model.addAttribute("professor", credentials.getProfessor());
		}
		return "announcements.html";
		
	}
	
	/*GET DI TUTTI I TIPI DI ANNUNCI PER MATERIA*/
	@GetMapping("/annunciPerMateria")
	public String getAnnouncementForSubject(Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		model.addAttribute("announcements", this.announcementRepository.findAll());
		if(credentials.getProfessor()!=null) {
			model.addAttribute("professor", credentials.getProfessor());
		}
		List<String> subjectsNames =new ArrayList<>();
		
		
		for(Subject s:this.subjectRepository.findAll()) {
			System.out.println("subject----------------------------"+s.getName());
//			if(subjects!=null) {
			if(!subjectsNames.contains(s.getName()) ) {
				subjectsNames.add(s.getName());
			}
//				for(Subject s1: subjects) {
//					System.out.println("s-------------------------------"+s.getName());
//					System.out.println("s1-------------------------------"+s1.getName());
//					if(!s.getName().equals(s1.getName())) {
//						System.out.println("adding....");
//						subjects.add(s);
//					}
//				}
//			}
		}
//		System.out.println("size---------------------------"+subjects.size());
		
//		for(int i=0;i<subjects.size();i++) {
//			System.out.println("elem---------------------------"+subjects.get(i));
//		}
		
		model.addAttribute("subjects", subjectsNames);
		return "announcementsBySubject.html";
		
	}
	
	/*GET DELLA PAGINA CON IL SINGOLO ANNUNCIO*/
	@GetMapping("/announcement/{id}")
	public String getAnnouncement(@PathVariable Long id,Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		Announcement a=this.announcementRepository.findById(id).get();
//		if(a.getProfessor()!=null) {
//			if(credentials.getProfessor()!=null) {
//				Long idProfessor=credentials.getProfessor().getId();
//				if(a.getProfessor().getId()==idProfessor) {
//					model.addAttribute("announcement", this.announcementRepository.findById(id).get());
//					model.addAttribute("credentials", credentials);
//					return "announcementProfessor.html";
//				}
//			}
//		}
//		if(credentials.getRole().equals(ADMIN_ROLE)) {
//			model.addAttribute("announcement", this.announcementRepository.findById(id).get());
//			model.addAttribute("credentials", credentials);
//			return "announcementProfessor.html";
//		}
		model.addAttribute("ricetta", this.announcementRepository.findById(id).get());
		model.addAttribute("credentials", credentials);
		return "announcement.html";
	}
	
	
	/*GET E POST PER INSERIRE UN NUOVO ANNUNCIO*/
	@GetMapping(value="/formNewAnnouncement")
	public String formNewRicetta(Model model) {
		Announcement announcement=new Announcement();
		Subject s=new Subject();
		model.addAttribute("announcement", announcement);
		model.addAttribute("subject", s);
		return "formNewAnnouncement.html";
	}
	

	@PostMapping(value={"/announcement"})
	public String newAnnouncement(@Valid @ModelAttribute Announcement announcement,@ModelAttribute Subject subject,BindingResult bindingResult, Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		if(credentials.getRole().equals(PROFESSOR_ROLE)) {
			Professor p=credentials.getProfessor();
			//		this.movieValidator.validate(movie, bindingResult);
			if (!bindingResult.hasErrors()) {
				announcement.setProfessor(p);
				subject.setName(subject.getName().toLowerCase());
				subject.setLevel(subject.getLevel().toLowerCase());
				this.announcementRepository.save(announcement);
				announcement.setSubjects(subject);
				this.subjectRepository.save(subject);
				p.getAnnouncements().add(announcement);
				this.professorRepository.save(p); 
				return "redirect:/announcement/"+announcement.getId();
			} else {
				return "formNewAnnouncement.html"; 
			}
		}else {
			if (!bindingResult.hasErrors()) {
				this.announcementRepository.save(announcement); 
				this.subjectRepository.save(announcement.getSubjects());
				return "redirect:/announcement/"+announcement.getId();
			}else {
				return "formNewAnnouncement.html"; 
			}
		}
	}
}

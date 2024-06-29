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
	@GetMapping("/subjects")
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
			if(!subjectsNames.contains(s.getName()) ) {
				subjectsNames.add(s.getName());
			}	
		}
		model.addAttribute("subjects", subjectsNames);
		return "subjects.html";
	}

	/*GET DELLA PAGINA CON IL SINGOLO ANNUNCIO*/
	@GetMapping("/announcement/{id}")
	public String getAnnouncement(@PathVariable Long id,Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		Announcement a=this.announcementRepository.findById(id).get();
		if(a.getProfessor()!=null) {
			if(credentials.getProfessor()!=null) {
				Long idProfessor=credentials.getProfessor().getId();
				if(a.getProfessor().getId()==idProfessor) {
					model.addAttribute("announcement", this.announcementRepository.findById(id).get());
					model.addAttribute("credentials", credentials);
					return "announcementEditable.html";
				}
			}
		}
		if(credentials.getRole().equals(ADMIN_ROLE)) {
			model.addAttribute("announcement", this.announcementRepository.findById(id).get());
			model.addAttribute("credentials", credentials);
			return "announcementEditable.html";
		}
		model.addAttribute("announcement", this.announcementRepository.findById(id).get());
		model.addAttribute("credentials", credentials);
		return "announcement.html";
	}

	/*GET DELLA PAGINA DEGLI ANNUNCI RELATIVI AD UNA MATERIA*/
	@GetMapping("/announcementsOfSubject/{subjectName}")
	public String getAnnouncementOfSubject(Model model,@PathVariable String subjectName) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		model.addAttribute("announcements", this.announcementRepository.findAll());
		if(credentials.getProfessor()!=null) {
			model.addAttribute("professor", credentials.getProfessor());
		}
		List<Announcement> announcementsOfSubject=new ArrayList<>();
		for(Announcement a:this.announcementRepository.findAll()) {
			if(a.getSubject()!=null) {
				if(a.getSubject().getName().equals(subjectName)) {
					announcementsOfSubject.add(a);
				}
			}
		}
		model.addAttribute("subjectName", subjectName);
		model.addAttribute("announcements", announcementsOfSubject);
		return "announcementsOfSubject.html";

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
				announcement.setSubject(subject);
				this.subjectRepository.save(subject);
				p.getAnnouncements().add(announcement);
				this.professorRepository.save(p);this.professorRepository.save(p); 
				return "redirect:/announcement/"+announcement.getId();
			} else {
				return "formNewAnnouncement.html"; 
			}
		}else {
			if (!bindingResult.hasErrors()) {
				subject.setName(subject.getName().toLowerCase());
				subject.setLevel(subject.getLevel().toLowerCase());
				this.announcementRepository.save(announcement);
				announcement.setSubject(subject);
				this.subjectRepository.save(subject);

				return "redirect:/announcement/"+announcement.getId();
			}else {
				return "formNewAnnouncement.html"; 
			}
		}
	}

	/*GET PER RIMUOVERE UN ANNUNCIO*/
	@GetMapping("/removeAnnouncement/{idAnnouncement}")
	public String removeAnnouncement(@PathVariable Long idAnnouncement,Model model) {
		Announcement a=this.announcementRepository.findById(idAnnouncement).orElse(null);
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		if(credentials.getRole().equals(PROFESSOR_ROLE)) {
			if(a.getProfessor()!=null) {
				Professor p=a.getProfessor();
				Subject s=a.getSubject();
				a.setSubject(null);
				s.setAnnouncement(null);
				p.getAnnouncements().remove(a);
				a.setProfessor(null);
				this.professorRepository.save(p);
				this.announcementRepository.delete(a);
			}else {
				if(a.getProfessor()!=null) {
					Professor p=a.getProfessor();
					p.getAnnouncements().remove(a);
					a.setProfessor(null);
					this.professorRepository.save(p);
				}
				Subject s=a.getSubject();
				a.setProfessor(null);
				a.setSubject(null);
				s.setAnnouncement(null);
				this.announcementRepository.delete(a);
			}
			return "redirect:/profilePage";
		}else {
			if(a.getProfessor()!=null) {
				Professor p=a.getProfessor();
				Subject s=a.getSubject();
				a.setSubject(null);
				s.setAnnouncement(null);
				p.getAnnouncements().remove(a);
				a.setProfessor(null);
				this.professorRepository.save(p);
				this.announcementRepository.delete(a);
			}else {
				if(a.getProfessor()!=null) {
					Professor p=a.getProfessor();
					p.getAnnouncements().remove(a);
					a.setProfessor(null);
					this.professorRepository.save(p);
				}
				Subject s=a.getSubject();
				a.setProfessor(null);
				a.setSubject(null);
				s.setAnnouncement(null);
				this.announcementRepository.delete(a);
			}
			return "redirect:/announcements";
		}	
	}
}

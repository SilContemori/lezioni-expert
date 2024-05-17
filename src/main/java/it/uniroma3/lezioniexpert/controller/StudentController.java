package it.uniroma3.lezioniexpert.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.lezioniexpert.repository.StudentRepository;


@Controller
public class StudentController {
	@Autowired StudentRepository studentRepository;
	
	
	/*GET DELLA PAGINA CON LA LISTA DI TUTTI GLI STUDENTI*/
	@GetMapping("/student")
	public String getMovies(Model model) {		
		model.addAttribute("students", this.studentRepository.findAll());
		return "students.html";
	}
	
	
	
	/*GET DELLA PAGINA DESCRITTIVA DEL SINGOLO STUDENTE*/
	@GetMapping("/student/{id}")
	public String getMovie(@PathVariable Long id, Model model) {
		model.addAttribute("student", this.studentRepository.findById(id).get());
		return "student.html";
	}	
}
















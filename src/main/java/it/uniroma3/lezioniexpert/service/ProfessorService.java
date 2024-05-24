
package it.uniroma3.lezioniexpert.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.lezioniexpert.model.Professor;
import it.uniroma3.lezioniexpert.repository.ProfessorRepository;


@Service
public class ProfessorService {
	@Autowired
	private ProfessorRepository professorRepository;
	
	public Professor findById(Long id) {
		return professorRepository.findById(id).get();
	}
	
	public Iterable<Professor> findAll() {
		return professorRepository.findAll();
	}
	
//	public List<Student> findByYear(Integer i) {
//		return studentRepository.findByYear(i);
//	}
//	
//	public Student save(Student m) {
//		return studentRepository.save(m);
//	}

}



package it.uniroma3.lezioniexpert.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.lezioniexpert.model.Student;
import it.uniroma3.lezioniexpert.repository.StudentRepository;


@Service
public class StudentService {
	@Autowired
	private StudentRepository studentRepository;
	
	public Student findById(Long id) {
		return studentRepository.findById(id).get();
	}
	
	public Iterable<Student> findAll() {
		return studentRepository.findAll();
	}
	
//	public List<Student> findByYear(Integer i) {
//		return studentRepository.findByYear(i);
//	}
//	
//	public Student save(Student m) {
//		return studentRepository.save(m);
//	}

}



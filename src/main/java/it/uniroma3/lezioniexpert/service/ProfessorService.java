
package it.uniroma3.lezioniexpert.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Professor getProfessor(Long id) {
        Optional<Professor> result = this.professorRepository.findById(id);
        return result.orElse(null);
    }

    @Transactional
    public Professor saveProfessor(Professor professor) {
        return this.professorRepository.save(professor);
    }

    @Transactional
    public List<Professor> getAllProfessors() {
        List<Professor> result = new ArrayList<>();
        Iterable<Professor> iterable = this.professorRepository.findAll();
        for(Professor professor : iterable)
            result.add(professor);
        return result;
    }

}



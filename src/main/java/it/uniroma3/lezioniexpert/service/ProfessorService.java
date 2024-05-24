
package it.uniroma3.lezioniexpert.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.lezioniexpert.model.Professor;
import it.uniroma3.lezioniexpert.model.User;
import it.uniroma3.lezioniexpert.repository.ProfessorRepository;
import it.uniroma3.lezioniexpert.repository.UserRepository;


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

    /**
     * This method retrieves a User from the DB based on its ID.
     * @param id the id of the User to retrieve from the DB
     * @return the retrieved User, or null if no User with the passed ID could be found in the DB
     */
    @Transactional
    public Professor getProfessor(Long id) {
        Optional<Professor> result = this.professorRepository.findById(id);
        return result.orElse(null);
    }

    /**
     * This method saves a User in the DB.
     * @param user the User to save into the DB
     * @return the saved User
     * @throws DataIntegrityViolationException if a User with the same username
     *                              as the passed User already exists in the DB
     */
    @Transactional
    public Professor saveProfessor(Professor professor) {
        return this.professorRepository.save(professor);
    }

    /**
     * This method retrieves all Users from the DB.
     * @return a List with all the retrieved Users
     */
    @Transactional
    public List<Professor> getAllProfessors() {
        List<Professor> result = new ArrayList<>();
        Iterable<Professor> iterable = this.professorRepository.findAll();
        for(Professor professor : iterable)
            result.add(professor);
        return result;
    }

}




package it.uniroma3.lezioniexpert.repository;



import org.springframework.data.repository.CrudRepository;

import it.uniroma3.lezioniexpert.model.Professor;

public interface ProfessorRepository extends CrudRepository<Professor,Long>{
//	public List<Student> findByYear(Integer year);

//	public boolean existsByNameAndYear(String title, Integer year);
}

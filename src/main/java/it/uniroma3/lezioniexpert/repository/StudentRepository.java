package it.uniroma3.lezioniexpert.repository;



import org.springframework.data.repository.CrudRepository;

import it.uniroma3.lezioniexpert.model.Student;

public interface StudentRepository extends CrudRepository<Student,Long>{
//	public List<Student> findByYear(Integer year);

//	public boolean existsByNameAndYear(String title, Integer year);
}

package it.uniroma3.lezioniexpert.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.lezioniexpert.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

}

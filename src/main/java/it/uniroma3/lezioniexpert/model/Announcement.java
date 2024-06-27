package it.uniroma3.lezioniexpert.model;

import java.util.Objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
@Entity
public class Announcement {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@NotNull
	private String location;
	private Integer hourlyRate;//budget se per studente
	@ManyToOne
	private Professor professor;
	@OneToOne
	private Subject subjects;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Professor getProfessor() {
		return professor;
	}
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	public Integer getHourlyRate() {
		return hourlyRate;
	}
	public void setHourlyRate(Integer hourlyRate) {
		this.hourlyRate = hourlyRate;
	}
	
	public Subject getSubjects() {
		return subjects;
	}
	public void setSubjects(Subject subjects) {
		this.subjects = subjects;
	}
	@Override
	public int hashCode() {
		return Objects.hash(hourlyRate, location);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Announcement other = (Announcement) obj;
		return Objects.equals(hourlyRate, other.hourlyRate) && Objects.equals(location, other.location);
	}
	
}

package it.uniroma3.lezioniexpert.model;

import java.util.List;
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
	@ManyToOne
	private Student student;
	@ManyToOne
	private Professor professor;
	private Integer HourlyRate;//budget se per studente
	@ManyToMany
	private List<Subject> subjects;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Student getStudet() {
		return student;
	}
	public void setStudet(Student studet) {
		this.student = studet;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	@Override
	public int hashCode() {
		return Objects.hash(HourlyRate, location, professor, student, subjects);
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
		return Objects.equals(HourlyRate, other.HourlyRate) && Objects.equals(location, other.location)
				&& Objects.equals(professor, other.professor) && Objects.equals(student, other.student)
				&& Objects.equals(subjects, other.subjects);
	}
	public Professor getProfessor() {
		return professor;
	}
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	public Integer getHourlyRate() {
		return HourlyRate;
	}
	public void setHourlyRate(Integer hourlyRate) {
		HourlyRate = hourlyRate;
	}
	public List<Subject> getSubjects() {
		return subjects;
	}
	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

}

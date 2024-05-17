package it.uniroma3.lezioniexpert.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Objects;

@Entity
public class Student {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@NotNull
	private String name;
	@NotNull
	private String surname;
	@NotNull
	private Integer age;
	private String description;
	@ManyToMany
	private List<Subject> subjects;
	@ManyToMany
	private List<Professor> teachers;
	@OneToMany
	private List<Announcement> announcements;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public List<Subject> getSubjects() {
		return subjects;
	}
	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}
	public List<Professor> getProfessors() {
		return teachers;
	}
	public void setProfessors(List<Professor> professors) {
		this.teachers = professors;
	}
	
	public List<Professor> getTeachers() {
		return teachers;
	}
	public void setTeachers(List<Professor> teachers) {
		this.teachers = teachers;
	}
	public List<Announcement> getAnnouncements() {
		return announcements;
	}
	public void setAnnouncements(List<Announcement> announcements) {
		this.announcements = announcements;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(age, announcements, description, name, subjects, surname, teachers);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return Objects.equals(age, other.age) && Objects.equals(announcements, other.announcements)
				&& Objects.equals(description, other.description) && Objects.equals(name, other.name)
				&& Objects.equals(subjects, other.subjects) && Objects.equals(surname, other.surname)
				&& Objects.equals(teachers, other.teachers);
	}	
	
}

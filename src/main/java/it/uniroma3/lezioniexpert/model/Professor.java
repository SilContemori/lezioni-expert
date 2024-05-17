package it.uniroma3.lezioniexpert.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Objects;
@Entity
public class Professor {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@NotNull
	private String name;
	@NotNull
	private String surname;
	private String description;
	private Integer age;
	@ManyToMany(mappedBy="teachers")
	private List<Student> students;
	@ManyToMany
	private List<Subject> teachings;
	@ManyToMany
	private List<Education> educations;
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
	public List<Student> getStudents() {
		return students;
	}
	public void setStudents(List<Student> students) {
		this.students = students;
	}
	public List<Subject> getSubjects() {
		return teachings;
	}
	public void setSubjects(List<Subject> subjects) {
		this.teachings = subjects;
	}
	public List<Education> getEducations() {
		return educations;
	}
	public void setEducations(List<Education> educations) {
		this.educations = educations;
	}
	
	
	public List<Subject> getTeachings() {
		return teachings;
	}
	public void setTeachings(List<Subject> teachings) {
		this.teachings = teachings;
	}
	public List<Announcement> getAnnouncements() {
		return announcements;
	}
	public void setAnnouncements(List<Announcement> announcements) {
		this.announcements = announcements;
	}
	@Override
	public int hashCode() {
		return Objects.hash(age, announcements, description, educations, name, students, surname, teachings);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Professor other = (Professor) obj;
		return Objects.equals(age, other.age) && Objects.equals(announcements, other.announcements)
				&& Objects.equals(description, other.description) && Objects.equals(educations, other.educations)
				&& Objects.equals(name, other.name) && Objects.equals(students, other.students)
				&& Objects.equals(surname, other.surname) && Objects.equals(teachings, other.teachings);
	}
	


}

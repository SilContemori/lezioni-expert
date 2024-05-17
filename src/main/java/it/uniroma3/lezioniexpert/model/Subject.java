package it.uniroma3.lezioniexpert.model;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
@Entity
public class Subject {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@NotNull
	private String livello;
	@NotNull
	private String nome;
	@ManyToMany(mappedBy="subjects")
	private List<Student> students;
	@ManyToMany(mappedBy="teachings")
	private List<Professor> professor;
	@ManyToMany(mappedBy="subjects")
	private List<Announcement> announcements;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLivello() {
		return livello;
	}
	public void setLivello(String livello) {
		this.livello = livello;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<Student> getStudents() {
		return students;
	}
	public void setStudents(List<Student> students) {
		this.students = students;
	}
	public List<Professor> getProfessor() {
		return professor;
	}
	public void setProfessor(List<Professor> professor) {
		this.professor = professor;
	}
	public List<Announcement> getAnnouncements() {
		return announcements;
	}
	public void setAnnouncements(List<Announcement> announcements) {
		this.announcements = announcements;
	}
	@Override
	public int hashCode() {
		return Objects.hash(announcements, livello, nome, professor, students);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Subject other = (Subject) obj;
		return Objects.equals(announcements, other.announcements) && Objects.equals(livello, other.livello)
				&& Objects.equals(nome, other.nome) && Objects.equals(professor, other.professor)
				&& Objects.equals(students, other.students);
	}
	
	

}

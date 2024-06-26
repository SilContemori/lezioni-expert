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
	private String level;
	@NotNull
	private String name;	
	@ManyToMany(mappedBy="teachings")
	private List<Professor> professor;
	@OneToOne(mappedBy="subjects")
	private Announcement announcement;
	
	@Override
	public boolean equals(Object obj) {
		Subject sub = (Subject ) obj;
		return (this.name.toLowerCase().equals(sub.name.toLowerCase()) && this.level.toLowerCase().equals(sub.level));
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(level, name);
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String livello) {
		this.level = livello;
	}
	public String getName() {
		return name;
	}
	public void setName(String nome) {
		this.name = nome;
	}
	public List<Professor> getProfessor() {
		return professor;
	}
	public void setProfessor(List<Professor> professor) {
		this.professor = professor;
	}

	public Announcement getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(Announcement announcement) {
		this.announcement = announcement;
	}

}

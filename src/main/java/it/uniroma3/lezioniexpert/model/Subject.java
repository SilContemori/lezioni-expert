package it.uniroma3.lezioniexpert.model;
import java.util.List;

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
	@ManyToMany(mappedBy="teachings")
	private List<Professor> professor;
	@ManyToMany(mappedBy="subjects")
	private List<Announcement> announcements;
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((livello == null) ? 0 : livello.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((professor == null) ? 0 : professor.hashCode());
		result = prime * result + ((announcements == null) ? 0 : announcements.hashCode());
		return result;
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (livello == null) {
			if (other.livello != null)
				return false;
		} else if (!livello.equals(other.livello))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (professor == null) {
			if (other.professor != null)
				return false;
		} else if (!professor.equals(other.professor))
			return false;
		if (announcements == null) {
			if (other.announcements != null)
				return false;
		} else if (!announcements.equals(other.announcements))
			return false;
		return true;
	}
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
	

}

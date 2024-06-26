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
	private String level;
	@NotNull
	private String name;
	@OneToMany(mappedBy="subjects")
	private List<Announcement> announcemenys;
	
	@ManyToMany(mappedBy="teachings")
	private List<Professor> professor;
	@ManyToMany(mappedBy="subjects")
	private List<Announcement> announcements;
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((professor == null) ? 0 : professor.hashCode());
		result = prime * result + ((announcements == null) ? 0 : announcements.hashCode());
		return result;
	}
	
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Subject other = (Subject) obj;
//		if (id == null) {
//			if (other.id != null)
//				return false;
//		} else if (!id.equals(other.id))
//			return false;
//		if (level == null) {
//			if (other.level != null)
//				return false;
//		} else if (!level.equals(other.level))
//			return false;
//		if (name == null) {
//			if (other.name != null)
//				return false;
//		} else if (!name.equals(other.name))
//			return false;
//		if (professor == null) {
//			if (other.professor != null)
//				return false;
//		} else if (!professor.equals(other.professor))
//			return false;
//		if (announcements == null) {
//			if (other.announcements != null)
//				return false;
//		} else if (!announcements.equals(other.announcements))
//			return false;
//		return true;
//	}
	
	@Override
	public boolean equals(Object obj) {
		Subject sub = (Subject ) obj;
//		if(this.name.toLowerCase().equals(sub.name.toLowerCase()) ) {
//			return this.level.toLowerCase().equals(sub.level.toLowerCase());
//		}
		return (this.name.toLowerCase().equals(sub.name.toLowerCase()) && this.level.toLowerCase().equals(sub.level));
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
	public List<Announcement> getAnnouncements() {
		return announcements;
	}
	public void setAnnouncements(List<Announcement> announcements) {
		this.announcements = announcements;
	}
	

}

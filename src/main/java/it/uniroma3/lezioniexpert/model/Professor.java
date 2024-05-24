package it.uniroma3.lezioniexpert.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
public class Professor {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@NotBlank
	private String name;
	@NotBlank
	private String surname;
	private String description;
	private Integer age;
	@NotBlank
	private String email;
	@ManyToMany
	private List<Subject> teachings;
	@ManyToMany
	private List<Education> educations;
	@OneToMany
	private List<Announcement> announcements;


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((age == null) ? 0 : age.hashCode());
		result = prime * result + ((teachings == null) ? 0 : teachings.hashCode());
		result = prime * result + ((educations == null) ? 0 : educations.hashCode());
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
		Professor other = (Professor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (age == null) {
			if (other.age != null)
				return false;
		} else if (!age.equals(other.age))
			return false;
		if (teachings == null) {
			if (other.teachings != null)
				return false;
		} else if (!teachings.equals(other.teachings))
			return false;
		if (educations == null) {
			if (other.educations != null)
				return false;
		} else if (!educations.equals(other.educations))
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
	public List<Subject> getTeachings() {
		return teachings;
	}
	public void setTeachings(List<Subject> teachings) {
		this.teachings = teachings;
	}
	public List<Education> getEducations() {
		return educations;
	}
	public void setEducations(List<Education> educations) {
		this.educations = educations;
	}
	public List<Announcement> getAnnouncements() {
		return announcements;
	}
	public void setAnnouncements(List<Announcement> announcements) {
		this.announcements = announcements;
	}

}

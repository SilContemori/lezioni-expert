package it.uniroma3.lezioniexpert.model;

import java.util.Objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
@Entity
public class Education {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@NotNull
	private String nome;
	@NotNull
	private String schoolName;
	@NotNull
	private Integer durationInMonth;
	@NotNull
	private Long startYear;
	@NotNull
	private Long endYear;
	@NotNull
	private Integer vote;
	
	@ManyToOne
	private Professor professor;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public Integer getDurationInMonth() {
		return durationInMonth;
	}
	public void setDurationInMonth(Integer durationInMonth) {
		this.durationInMonth = durationInMonth;
	}
	public Long getStartYear() {
		return startYear;
	}
	public void setStartYear(Long startYear) {
		this.startYear = startYear;
	}
	public Long getEndYear() {
		return endYear;
	}
	public void setEndYear(Long endYear) {
		this.endYear = endYear;
	}
	public Integer getVote() {
		return vote;
	}
	public void setVote(Integer vote) {
		this.vote = vote;
	}
	
	public Professor getProfessor() {
		return professor;
	}
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	@Override
	public int hashCode() {
		return Objects.hash(durationInMonth, endYear, nome, professor, schoolName, startYear, vote);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Education other = (Education) obj;
		return Objects.equals(durationInMonth, other.durationInMonth) && Objects.equals(endYear, other.endYear)
				&& Objects.equals(nome, other.nome) && Objects.equals(professor, other.professor)
				&& Objects.equals(schoolName, other.schoolName) && Objects.equals(startYear, other.startYear)
				&& Objects.equals(vote, other.vote);
	}
}

package br.pucrs.politecnica.assignments.classes;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ClassesDTO {
	
	@NotNull
	private Long disciplineID;
	
	@NotNull
	private List<String> studentList;
	
	@NotNull
	private Long professorID;
	
	@NotNull
	@Size(min=1, max = 5)
	private String code;
	
	@Size(max = 2048)
	private String observation;
	
	public Long getDiscipline() {
		return disciplineID;
	}

	public void setDiscipline(Long discipline) {
		this.disciplineID = discipline;
	}

	public Long getProfessor() {
		return professorID;
	}

	public void setProfessor(Long professor) {
		this.professorID = professor;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public List<String> getStudentList() {
		if (this.studentList == null) {
			this.studentList = new ArrayList<>();
		}
		return this.studentList;
	}

	public void setStudentList(List<String> studentList) {
		this.studentList = studentList;
	}
	
}

package br.pucrs.politecnica.assignments.discipline;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DisciplineDTO {

	@NotNull
	@Size(min = 4, max = 16)
	private String code;
	
	@NotNull
	@Size(min = 4, max = 64)
	private String name;
	
	@Size(max = 2048)
	private String observation;
	
	public Discipline transformToDiscipline(String name) {
		Discipline discipline = new Discipline();
		discipline.setCode(this.code);
		discipline.setCreatedBy(name);
		discipline.setCreatedTime(LocalDateTime.now());
		discipline.setName(this.name);
		discipline.setObservation(this.observation);
		return discipline;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}
	
	
}

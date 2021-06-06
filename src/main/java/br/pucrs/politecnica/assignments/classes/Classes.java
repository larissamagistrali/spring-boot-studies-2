package br.pucrs.politecnica.assignments.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import br.pucrs.politecnica.assignments.discipline.Discipline;
import br.pucrs.politecnica.assignments.professor.Professor;
import br.pucrs.politecnica.assignments.student.Student;
import br.pucrs.politecnica.assignments.taskProfessor.TaskProfessor;

@Entity
@Table(name = "classes")
public class Classes implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 5)
	private String code;

	@ManyToOne
	@JoinColumn(name = "professor_id")
	private Professor professor;

	@ManyToOne
	@JoinColumn(name = "discipline_id")
	private Discipline discipline;

	@ManyToMany(mappedBy = "classes", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<TaskProfessor> tasks;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(name = "classes_student", joinColumns = {
			@JoinColumn(name = "classes_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "student_login_id", referencedColumnName = "login_id") })
	private List<Student> students;

	@Size(max = 2048)
	private String observation;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Discipline getDiscipline() {
		return discipline;
	}

	public void setDiscipline(Discipline discipline) {
		this.discipline = discipline;
	}

	public List<TaskProfessor> getTasks() {
		List<TaskProfessor> sortedTasks = new ArrayList<>(getTasksProfessorInternal());
		 
		sortedTasks.sort((TaskProfessor e1, TaskProfessor e2) -> e1.getTaskTime().compareTo(e2.getTaskTime()));
		return Collections.unmodifiableList(sortedTasks);
	}

	public List<Student> getStudents() {
		if (this.students == null) {
			this.students = new ArrayList<>();
		}
		List<Student> sortedStudent = new ArrayList<Student>(this.students);
		sortedStudent.sort((Student e1, Student e2) -> e1.getLogin().getName().compareTo(e2.getLogin().getName()));
		return Collections.unmodifiableList(sortedStudent);
	}

	public void setStudents(ArrayList<Student> students) {
		this.students = students;
	}

	protected List<TaskProfessor> getTasksProfessorInternal() {
		if (this.tasks == null) {
			this.tasks = new ArrayList<>();
		}
		return this.tasks;
	}

	public int getStudentSize() {
		return getStudents().size();
	}

	public void setTasks(List<TaskProfessor> tasks) {
		 
		this.tasks = tasks;
	}

	public String toString() {
		return code;
	}
}

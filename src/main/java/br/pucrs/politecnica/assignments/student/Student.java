package br.pucrs.politecnica.assignments.student;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.pucrs.politecnica.assignments.classes.Classes;
import br.pucrs.politecnica.assignments.login.Login;
import br.pucrs.politecnica.assignments.taskStudent.TaskStudent;

@Entity
@Table(name = "student") 
public class Student implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	@NotNull
	@Size(max = 12)
	private String registration;
	
	@ManyToMany(mappedBy = "students", fetch = FetchType.LAZY)
	private Set<Classes> classes;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn
	@MapsId
	private Login login;
	
	@OneToMany(mappedBy = "student")
	private Set<TaskStudent> taskStudent; 
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Set<Classes> getClasses() {
		if (this.classes == null) {
			this.classes = new HashSet<>();
		}
		return this.classes;
	}

	public void setClasses(Set<Classes> classes) {
		this.classes = classes;
	}

	public Set<TaskStudent> getTaskStudent() {
		if(this.taskStudent == null) {
			this.taskStudent = new HashSet<>();
		}
		return this.taskStudent;
	}

	public void setTaskStudent(Set<TaskStudent> taskStudent) {
		this.taskStudent = taskStudent;
	}
	
	

}

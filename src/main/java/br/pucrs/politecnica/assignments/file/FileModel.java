package br.pucrs.politecnica.assignments.file;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.pucrs.politecnica.assignments.View;
import br.pucrs.politecnica.assignments.taskProfessor.TaskProfessor;
import br.pucrs.politecnica.assignments.taskStudent.TaskStudent;

@Entity
@Table(name="file_model")
public class FileModel implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "id")
	@JsonView(View.FileInfo.class)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @Column(name = "name")
    @JsonView(View.FileInfo.class)
	private String name;
    
    @Column(name = "mimetype")
	private String mimetype;
	
    @Column(name="data")
    private byte[] data;
	
	@OneToOne(mappedBy = "fileModel", fetch = FetchType.LAZY)
    private TaskStudent taskStudent;
	
	@OneToOne(mappedBy = "fileModel", fetch = FetchType.LAZY)
    private TaskProfessor taskProfessor;
	
	public FileModel(){}
	
	public FileModel(String name, String mimetype, byte[] data){
		this.name = name;
		this.mimetype = mimetype;
		this.data = data;
	}
	
	public Long getId(){
		return this.id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getMimetype(){
		return this.mimetype;
	}
	
	public void setMimetype(String mimetype){
		this.mimetype = mimetype;
	}
	
	public byte[] getData(){
		return this.data;
	}
	
	public void setData(byte[] data){
		this.data = data;
	}

	public TaskStudent getTaskStudent() {
		return taskStudent;
	}

	public void setTaskStudent(TaskStudent taskStudent) {
		this.taskStudent = taskStudent;
	}

	public TaskProfessor getTaskProfessor() {
		return taskProfessor;
	}

	public void setTaskProfessor(TaskProfessor taskProfessor) {
		this.taskProfessor = taskProfessor;
	}

}
package br.pucrs.politecnica.assignments.taskStudent;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.pucrs.politecnica.assignments.feedback.Feedback;
import br.pucrs.politecnica.assignments.file.FileModel;
import br.pucrs.politecnica.assignments.student.Student;
import br.pucrs.politecnica.assignments.taskProfessor.TaskProfessor;

@Entity
@Table(name = "task_student")
public class TaskStudent implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@NotNull
	private TaskProfessor taskProfessor;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@NotNull
	private Student student;

	@JoinColumn(name = "file_model_id")
	@OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private FileModel fileModel;

	@NotNull
	private boolean status;

	@Size(max = 2048)
	private String comment;
	
	@NotNull
	private LocalDateTime postedTime;
    
	@OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "feedback_id", referencedColumnName = "id", nullable = false)
    private Feedback feedback;

	public TaskProfessor getTaskProfessor() {
		return taskProfessor;
	}

	public void setTaskProfessor(TaskProfessor taskProfessor) {
		this.taskProfessor = taskProfessor;
	}

	public TaskProfessor getTask() {
		return taskProfessor;
	}

	public void setTask(TaskProfessor taskProfessor) {
		this.taskProfessor = taskProfessor;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public LocalDateTime getPostedTime() {
		return this.postedTime;
	}

	public void setPostedTime(LocalDateTime postedTime) {
		this.postedTime = postedTime;
	}

	public Feedback getFeedback() {
		return feedback;
	}

	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}

	public FileModel getFileModel() {
		return fileModel;
	}

	public void setFileModel(FileModel fileModel) {
		this.fileModel = fileModel;
	}
	
}

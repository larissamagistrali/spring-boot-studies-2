package br.pucrs.politecnica.assignments.feedback;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.pucrs.politecnica.assignments.file.FileModel;
import br.pucrs.politecnica.assignments.taskStudent.TaskStudent;

@Entity
@Table(name="feedback")
public class Feedback {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name = "status")
	private Boolean status;
	
	private String comment;
	
	@JoinColumn(name = "file_model_id")
	@OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private FileModel fileModel;
	
	@Column(name = "sent")
	private boolean sent;
	
	@OneToOne(mappedBy = "feedback")
    private TaskStudent taskStudent;
	
	public boolean getSent() {
		return sent;
	}

	public void setSent(boolean sent) {
		this.sent = sent;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public TaskStudent getTaskStudent() {
		return taskStudent;
	}

	public void setTaskStudent(TaskStudent taskStudent) {
		this.taskStudent = taskStudent;
	}


	public FileModel getFileModel() {
		return this.fileModel;
	}

	public void setFileModel(FileModel fileModel) {
		this.fileModel = fileModel;
	}

}

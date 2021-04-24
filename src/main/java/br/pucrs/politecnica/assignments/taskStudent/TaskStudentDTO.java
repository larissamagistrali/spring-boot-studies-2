package br.pucrs.politecnica.assignments.taskStudent;

import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

public class TaskStudentDTO {
	
	private MultipartFile files;
	
	@Size(max = 2048)
	private String comment;
	
	private Boolean sendTask;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Boolean getSendTask() {
		return sendTask;
	}

	public void setSendTask(Boolean sendTask) {
		this.sendTask = sendTask;
	}

	public MultipartFile getFiles() {
		return files;
	}

	public void setFiles(MultipartFile files) {
		this.files = files;
	}
	
}

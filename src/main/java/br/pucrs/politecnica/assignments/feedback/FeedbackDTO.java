package br.pucrs.politecnica.assignments.feedback;

import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;


public class FeedbackDTO {
	
	
	private MultipartFile files;
	
	@Size(max = 2048)
	private String comment;
	
	private Boolean sent;

	private Boolean finalizada;
	
	public MultipartFile getFiles() {
		return files;
	}

	public void setFiles(MultipartFile files) {
		this.files = files;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Boolean getSent() {
		return sent;
	}

	public void setSent(Boolean finalizada) {
		this.sent = finalizada;
	}


	public Boolean isFinalizada() {
		return this.finalizada;
	}

	public Boolean getFinalizada() {
		return this.finalizada;
	}

	public void setFinalizada(Boolean finalizada) {
		this.finalizada = finalizada;
	}

	
	
	
}

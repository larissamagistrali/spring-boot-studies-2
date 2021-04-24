package br.pucrs.politecnica.assignments.taskProfessor;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import br.pucrs.politecnica.assignments.classes.ClassesService;

public class TaskProfessorDTO {
	
	@NotNull
	private List<String> classes;
	
	@NotNull
    @Size(max = 64)
	private String title;
	
	@NotNull
    @Size(max = 2048)
	private String details;
	
	private MultipartFile files;
	
	@NotNull
	private boolean status;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@Future
    @NotNull
	private LocalDateTime taskTime;
	
	@Autowired
	private ClassesService classesService;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public LocalDateTime getTaskTime() {
		return taskTime;
	}

	public void setTaskTime(LocalDateTime taskTime) {
		this.taskTime = taskTime;
	}

	public List<String> getClasses() {
		return classes;
	}

	public void setClasses(List<String> classes) {
		this.classes = classes;
	}

	public MultipartFile getFiles() {
		return files;
	}

	public void setFiles(MultipartFile files) {
		this.files = files;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public ClassesService getClassesService() {
		return classesService;
	}

	public void setClassesService(ClassesService classesService) {
		this.classesService = classesService;
	}
	
	

}

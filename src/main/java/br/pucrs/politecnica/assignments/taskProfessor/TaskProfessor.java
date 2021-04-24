package br.pucrs.politecnica.assignments.taskProfessor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import br.pucrs.politecnica.assignments.classes.Classes;
import br.pucrs.politecnica.assignments.file.FileModel;
import br.pucrs.politecnica.assignments.student.Student;
import br.pucrs.politecnica.assignments.taskStudent.TaskStudent;

@Entity 
@Table(name = "task_professor")
public class TaskProfessor implements Serializable{ 

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(name = "classes_task_professor",
    joinColumns = {
            @JoinColumn(name = "task_professor_id", referencedColumnName = "id")},
    inverseJoinColumns = {
            @JoinColumn(name = "classes_id", referencedColumnName = "id")})
	private Set<Classes> classes;

	@OneToMany(mappedBy = "taskProfessor")
    private Set<TaskStudent> tasksStudent;
	
	@JoinColumn(name = "file_model_id")
	@OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private FileModel fileModel;

	@NotNull
	@Size(max = 64)
	private String title;

	@NotNull
	@Size(max = 2048)
	private String details;
	
	@NotNull
	private boolean canceled;

	private boolean status;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@Future
	@NotNull
	private LocalDateTime taskTime;

	@DateTimeFormat(pattern = "dd-MMM-YYYY HH:mm")
	public LocalDateTime getTaskTime() {
		return taskTime;
	}

	public void setTaskTime(LocalDateTime taskTime) {
		this.taskTime = taskTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Classes> getClasses() {
		if(this.classes == null) {
			return new HashSet<Classes>();
		}
		return this.classes;
	}

	public void setClasses(Set<Classes> classes) {
		this.classes = classes;
	}

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

	public Set<TaskStudent> getTasksStudent() {
		if(this.tasksStudent == null) {
			return new HashSet<TaskStudent>();
		}
		return this.getTasksStudentInternal().stream().sorted((TaskStudent e1, TaskStudent e2) -> e1.getStudent().getLogin().getName().compareTo(e2.getStudent().getLogin().getName())).collect(Collectors.toSet());
	}

	public void setTasksStudent(Set<TaskStudent> tasksStudent) {
		this.tasksStudent = tasksStudent;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	public boolean getCanceled() {
		return canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	public FileModel getFileModel() {
		return fileModel;
	}

	public void setFileModel(FileModel fileModel) {
		this.fileModel = fileModel;
	}
	
	protected Set<TaskStudent> getTasksStudentInternal(){
		if(this.tasksStudent == null) {
			return new HashSet<TaskStudent>();
		}
		return this.tasksStudent;
	}
	
	public int getTasksStudentSize() {
		return tasksStudent.size() ;
	}

	public int getStudentSize(TaskProfessor taskProfessor) {
		for (Classes classe : classes) {
			for (TaskProfessor task : classe.getTasks()) {
				if (task.getId() == taskProfessor.getId()) {
					return classe.getStudentSize();
				}

			}

		}
		return 0;

	}
	
	public String getClassId(TaskProfessor taskProfessor) {
		for (Classes classe : classes) {
			for (TaskProfessor task : classe.getTasks()) {
				if (task.getId() == taskProfessor.getId()) {
					return  "" + classe.getId();
				}

			}

		}
		return "-1";

	}

	public String toString() {
		return title;
	}


	public boolean HasAnyTaskStudentFromStudent(Student student){
		return this.tasksStudent.stream().anyMatch(student.getTaskStudent()::contains);
	}

}

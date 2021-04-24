package br.pucrs.politecnica.assignments.taskStudent;

import java.security.Principal;
import java.util.LinkedHashMap;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.pucrs.politecnica.assignments.classes.Classes;
import br.pucrs.politecnica.assignments.discipline.Discipline;
import br.pucrs.politecnica.assignments.student.Student;
import br.pucrs.politecnica.assignments.student.StudentService;
import br.pucrs.politecnica.assignments.taskProfessor.TaskProfessor;
import br.pucrs.politecnica.assignments.taskProfessor.TaskProfessorService;

@Controller
public class TaskStudentController {

	private static final String VIEWS_TASKSTUDENT_CREATE_OR_UPDATE_TASK_FORM = "student/taskStudentDetail";

	private TaskStudentService taskStudentService;

	private TaskProfessorService taskProfessorService;

	private StudentService studentService;

	private Student studentLogged;

	private TaskProfessor taskProfessor;
 
	private Discipline taskDiscipline;

	private Classes taskClass;

	private TaskStudentDTO taskStudentDTO;
	
	public TaskStudentController(TaskStudentService taskStudentService, TaskProfessorService taskProfessorService,
			StudentService studentService) {
		this.taskStudentService = taskStudentService;
		this.taskProfessorService = taskProfessorService;
		this.studentService = studentService;
	}

	@GetMapping("/taskStudent/{taskProfessorId}/new")
	public String initCreationForm(@PathVariable("taskProfessorId") Long taskProfessorId, Model model,
			Principal principal) {
		try {
			studentLogged = studentService.getStudentLogged(principal);
			taskProfessor = taskProfessorService.getTaskProfessor(taskProfessorId);
			taskStudentDTO = taskStudentService.getTaskStudentDTOByStudent(studentLogged, taskProfessor);
			taskDiscipline = taskProfessorService.getDisciplineByStudent(taskProfessor, studentLogged);
			taskClass = taskProfessorService.getClassByStudent(taskProfessor, studentLogged);
			model.addAttribute("professor", taskProfessorService.getProfessor(taskProfessor));
			model.addAttribute("task", taskProfessor);
			model.addAttribute("discipline", taskDiscipline);
			model.addAttribute("class", taskClass);
			model.addAttribute("taskStudent", taskStudentDTO);
			model.addAttribute("fileModel", taskStudentService.getTaskStudentByStudent(studentLogged, taskProfessor).getFileModel());
			return VIEWS_TASKSTUDENT_CREATE_OR_UPDATE_TASK_FORM;
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/home";
		}
	}

	@PostMapping("/taskStudent/{taskProfessorId}/new")
	public String processCreationForm(@PathVariable("taskProfessorId") Long taskProfessorId,
			@Valid TaskStudentDTO taskStudentDTO, BindingResult result, Model model, Principal principal) {
		try {
			if (result.hasErrors()) {
				model.addAttribute("professor", taskProfessorService.getProfessor(taskProfessor));
				model.addAttribute("task", taskProfessorService.getTaskProfessor(taskProfessorId));
				model.addAttribute("discipline", taskProfessorService.getDisciplineByStudent(taskProfessor, studentLogged));
				model.addAttribute("class", taskProfessorService.getClassByStudent(taskProfessor, studentLogged));
				model.addAttribute("taskStudent", taskStudentDTO);
				model.addAttribute("taskProfessorId", taskProfessorId);
				return VIEWS_TASKSTUDENT_CREATE_OR_UPDATE_TASK_FORM;
			} else {
				taskStudentService.saveTaskStudent(taskStudentDTO, taskProfessorId, principal);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return VIEWS_TASKSTUDENT_CREATE_OR_UPDATE_TASK_FORM;
		}
		return "redirect:/taskStudent";
	}
	
	@GetMapping("/taskStudent")
	public String showTaskList(Principal principal, Model model){
		try {
			Student student = this.studentService.getStudentLogged(principal);
			LinkedHashMap<TaskProfessor, Classes> tasks =  this.studentService.getTasksToDoList(student);
			model.addAttribute("taskList", tasks);
			model.addAttribute("statusList", this.studentService.getStatusByMapTasks(tasks, student));
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/homeAdm";
		}
		return "/student/tasksStudent";
	}

}
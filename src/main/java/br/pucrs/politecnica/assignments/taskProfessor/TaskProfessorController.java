package br.pucrs.politecnica.assignments.taskProfessor;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.pucrs.politecnica.assignments.classes.Classes;
import br.pucrs.politecnica.assignments.classes.ClassesService;
import br.pucrs.politecnica.assignments.discipline.Discipline;
import br.pucrs.politecnica.assignments.discipline.DisciplineService;
import br.pucrs.politecnica.assignments.feedback.FeedbackService;
import br.pucrs.politecnica.assignments.professor.Professor;
import br.pucrs.politecnica.assignments.professor.ProfessorService;

@Controller
public class TaskProfessorController {

	private static final String VIEWS_TASK_CREATE_OR_UPDATE_FORM = "taskProfessor/createOrUpdateTaskForm";

	private TaskProfessorService taskProfessorService;

	private ClassesService classesService;

	private DisciplineService disciplineService;

	private ProfessorService professorService;

	private FeedbackService feedbackService;

	public TaskProfessorController(TaskProfessorService taskProfessorService, ClassesService classesService,
			DisciplineService disciplineService, ProfessorService professorService, FeedbackService feedbackService) {
		this.taskProfessorService = taskProfessorService;
		this.classesService = classesService;
		this.disciplineService = disciplineService;
		this.professorService = professorService;
		this.feedbackService = feedbackService;
	}

	@GetMapping("/taskProfessor/new")
	public String initCreationForm(Model model, Principal principal) throws Exception {
		TaskProfessorDTO taskProfessorDTO = new TaskProfessorDTO();
		List<Discipline> disciplines = disciplineService
				.getDisciplinesByClasses(classesService.getClassesByUser(principal));

		List<Classes> classesDiscipline = classesService.getClassesByUser(principal);

		model.addAttribute("disciplines", disciplines);
		model.addAttribute("classesDiscipline", classesDiscipline);
		model.addAttribute("taskProfessorDTO", taskProfessorDTO);
		return VIEWS_TASK_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/taskProfessor/new")
	public String processCreationForm(@Valid TaskProfessorDTO taskProfessorDTO, BindingResult result, Model model,
			Principal principal) throws Exception {
		if (result.hasErrors()) {
			List<Discipline> disciplines = disciplineService
					.getDisciplinesByClasses(classesService.getClassesByUser(principal));
			List<Classes> classesDiscipline = classesService.getClassesByUser(principal);
			model.addAttribute("disciplines", disciplines);
			model.addAttribute("classesDiscipline", classesDiscipline);
			model.addAttribute("taskProfessorDTO", taskProfessorDTO);
			return VIEWS_TASK_CREATE_OR_UPDATE_FORM;
		} else {
			taskProfessorService.saveTaskProfessor(taskProfessorDTO);
			return "redirect:/home";
		}
	}

	@GetMapping("/taskProfessor/{taskProfessorId}/edit")
	public String initUpdateTaskProfessorForm(@PathVariable("taskProfessorId") Long taskProfessorId, Model model,
			Principal principal) throws IOException, Exception {
		TaskProfessorDTO taskProfessorDTO = this.taskProfessorService
				.transformToTaskProfessorDTO(this.taskProfessorService.getTaskProfessor(taskProfessorId));
		TaskProfessor taskProfessor = this.taskProfessorService.getTaskProfessor(taskProfessorId);
		Discipline discipline = taskProfessorService
				.getTaskProfessorsDiscipline(this.taskProfessorService.getTaskProfessor(taskProfessorId));
		List<Discipline> disciplines = disciplineService
				.getDisciplinesByClasses(classesService.getClassesByUser(principal));
	 
		disciplines.remove(discipline);
		model.addAttribute("discipline", discipline);
		model.addAttribute("disciplines", disciplines);
		model.addAttribute("classesDiscipline", classesService.getClassesByUser(principal));
		model.addAttribute(taskProfessorDTO);
		model.addAttribute("taskProfessor", taskProfessor);
		return VIEWS_TASK_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/taskProfessor/{taskProfessorId}/edit")
	public String processUpdateTaskProfessorForm(@PathVariable("taskProfessorId") Long taskProfessorId,
			@Valid TaskProfessorDTO taskProfessorDTO, BindingResult result, Model model, Principal principal)
			throws Exception {
		if (result.hasErrors()) {
			List<Discipline> disciplines = disciplineService
					.getDisciplinesByClasses(classesService.getClassesByUser(principal));
			List<Classes> classesDiscipline = classesService.getClassesByUser(principal);
			Discipline discipline = taskProfessorService
					.getTaskProfessorsDiscipline(this.taskProfessorService.getTaskProfessor(taskProfessorId));
			model.addAttribute("discipline", discipline);
			model.addAttribute("disciplines", disciplines);
			model.addAttribute("classesDiscipline", classesDiscipline);
			model.addAttribute("taskProfessorDTO", taskProfessorDTO);
			return VIEWS_TASK_CREATE_OR_UPDATE_FORM;
		} else {
			taskProfessorService.saveTaskProfessor(taskProfessorDTO, taskProfessorId);
			return "redirect:/taskProfessor/listTaskProfessor";
		}
	}
	 
	@GetMapping("/taskProfessor/{taskProfessorId}/delete")
	public String cancelTaskProfessor(@PathVariable("taskProfessorId") Long taskProfessorId) throws Exception {
		TaskProfessor taskProfessor = taskProfessorService.getTaskProfessor(taskProfessorId);
		if(taskProfessor.getCanceled()) {
			taskProfessor.setCanceled(false);
		}
		else {
			taskProfessor.setCanceled(true);
		}
		taskProfessorService.saveTaskProfessor(taskProfessor);
		return "redirect:/taskProfessor/listTaskProfessor";
	}

	@GetMapping("/taskProfessor/viewDelivery/{classesId}/{taskProfessorId}")
	public String showTaskStudentList(@PathVariable("taskProfessorId") String taskProfessorId,
			@PathVariable("classesId") String classesId, Model model) {
		try {
			TaskProfessor task = this.taskProfessorService.getTaskProfessor(Long.valueOf(taskProfessorId));
			Classes classes = this.classesService.getClassesById(Long.valueOf(classesId));
			model.addAttribute("classes", classes);
			model.addAttribute("taskProfessor", task);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "viewDelivery/delivery";
	}

	@GetMapping("/taskProfessor/viewDelivery")
	public String showClassesTask(@RequestParam(value = "classId", required = false, defaultValue = "0") int classId,
			Principal principal, Model model) throws Exception {
		try {
			List<Classes> classesList = new ArrayList<>();
			classesList.addAll(this.professorService.getUserProfessor(principal).getClasses());
			model.addAttribute("classesList", classesList);
			System.out.println("aaaaaaaaaaaaaaaaa");
			System.out.println("aaaaaaaaaaaaaaaaa");
			System.out.println("aaaaaaaaaaaaaaaaa");
			System.out.println("aaaaaaaaaaaaaaaaa");
			List<TaskProfessor> tasksProf = this.taskProfessorService.listClassesTaskProfessor(classesList,
					classesList.get(0).getId());
			model.addAttribute("tasks", tasksProf);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			model.addAttribute("feedbacks", this.feedbackService
					.listFeedbacksAvailable(this.professorService.getUserProfessor(principal).getClasses()));
		} catch (Exception e) {
			model.addAttribute("feedbacks", this.feedbackService
					.listFeedbacksNull(this.professorService.getUserProfessor(principal).getClasses()));
			e.printStackTrace();
		}
		return "viewDelivery/listDelivery";
	}

	@GetMapping("/taskProfessor/listTaskProfessor")
	public String listTask(@RequestParam(value="disciplineId", required = false) Long disciplineId, Model model, Principal principal) {
		try {
			Professor professor = this.professorService.getUserProfessor(principal);
			List<Discipline> disciplineList = this.professorService.getDisciplinesByProfessor(professor);
			if(disciplineId != null) {
				Discipline discipline = this.disciplineService.findById(disciplineId);
				System.out.println(discipline);
				Map<TaskProfessor, Classes> classesTaskProfessor = this.disciplineService.getClassesAndTaskProfessorByDiscipline(discipline);
				System.out.println(classesTaskProfessor);
				model.addAttribute("classesTasks", classesTaskProfessor);
				model.addAttribute("disciplineName", discipline.getName());
			}
			model.addAttribute("disciplines", disciplineList);
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/home";
		}
		return "taskProfessor/taskList";
	}

	@GetMapping("/taskProfessor/viewDelivery/{classesId}/{taskProfessorId}/sent")
	public String setSentAllFeedbacks(@PathVariable("taskProfessorId") String taskProfessorId,
			@PathVariable("classesId") String classesId) {
		try {
			this.feedbackService.setSentAll(this.taskProfessorService.getTaskProfessor(Long.valueOf(taskProfessorId)).getTasksStudent());
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/home";
		}
		return "redirect:/taskProfessor/viewDelivery/{classesId}/{taskProfessorId}";
	}

}

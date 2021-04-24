package br.pucrs.politecnica.assignments.feedback;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.pucrs.politecnica.assignments.classes.ClassesService;
import br.pucrs.politecnica.assignments.student.Student;
import br.pucrs.politecnica.assignments.student.StudentService;
import br.pucrs.politecnica.assignments.taskProfessor.TaskProfessorService;
import br.pucrs.politecnica.assignments.taskStudent.TaskStudentService;


@Controller
public class FeedbackController {

	private static final String VIEWS_FEEDBACK_CREATE_OR_UPDATE_TASK_FORM = "feedback/createOrUpdateFeedbackForm";
	
	private TaskStudentService taskStudentService;
	
	private FeedbackService feedbackService;

	private ClassesService classesService;

	private TaskProfessorService taskProfessorService;

	private StudentService studentService;

	public FeedbackController(TaskStudentService taskStudentService, FeedbackService feedbackService, ClassesService classesService, TaskProfessorService taskProfessorService, StudentService studentService) {
		this.taskStudentService = taskStudentService;
		this.feedbackService = feedbackService;
		this.classesService = classesService;
		this.taskProfessorService = taskProfessorService;
		this.studentService = studentService;
	}
	
	@GetMapping("/taskProfessor/viewDelivery/{classesId}/{taskProfessorId}/{taskStudentId}")
	public String initSetFeedbackCreationForm(@PathVariable("taskProfessorId") Long taskProfessorId, @PathVariable("classesId") Long classesId, @PathVariable("taskStudentId") Long taskStudentId, Model model) {
		try {
			model.addAttribute("taskStudent", taskStudentService.getTaskStudent(taskStudentId));
			model.addAttribute("taskProfessor", this.taskProfessorService.getTaskProfessor(taskProfessorId));
			model.addAttribute("classes", this.classesService.getClassesById(classesId));
			model.addAttribute("feedback", new FeedbackDTO());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return VIEWS_FEEDBACK_CREATE_OR_UPDATE_TASK_FORM;
	}

	@PostMapping("/taskProfessor/viewDelivery/{classesId}/{taskProfessorId}/{taskStudentId}")
	public String processSetFeedbackCreationForm(@PathVariable("taskProfessorId") Long taskProfessorId, @PathVariable("classesId") Long classesId, @PathVariable("taskStudentId") Long taskStudentId, Model model,
		@Valid FeedbackDTO feedbackDTO, BindingResult result, Principal principal) {
			try {
				if (result.hasErrors()) {
					model.addAttribute("taskStudent", taskStudentService.getTaskStudent(taskStudentId));
					model.addAttribute("taskProfessor", this.taskProfessorService.getTaskProfessor(taskProfessorId));
					model.addAttribute("classes", this.classesService.getClassesById(classesId));
					return VIEWS_FEEDBACK_CREATE_OR_UPDATE_TASK_FORM;
				} else {
					feedbackService.saveFeedback(feedbackDTO, taskStudentService.getTaskStudent(taskStudentId));
				}
			} catch (Exception e) {
				e.printStackTrace();
				return VIEWS_FEEDBACK_CREATE_OR_UPDATE_TASK_FORM;
			}
			return "redirect:/taskProfessor/viewDelivery/{classesId}/{taskProfessorId}";
		}

	@GetMapping("/feedbackList")
	public String getFeedbackList(Principal principal, Model model) {
		try{
			Student student = this.studentService.getStudentLogged(principal);
			model.addAttribute("student", student);
			model.addAttribute("tasksProfessorList", this.taskProfessorService.getTaskProfessorListByStudent(student));
		}
		catch(Exception e){
			e.printStackTrace();
			return "redirect:/home";
		}
		return "feedback/feedbacksList";
	}

	@GetMapping("/feedbackList/viewFeedback/{classesId}/{feedbackId}")
	public String getFeedbackDetails(@PathVariable("classesId") Long classesId, @PathVariable("feedbackId") Long feedbackId, Model model) {
		try{
			model.addAttribute("classes", this.classesService.getClassesById(classesId));
			model.addAttribute("feedback", this.feedbackService.getFeedbackById(feedbackId));
		}
		catch(Exception e){
			e.printStackTrace();
			return "redirect:/home";
		}
		return "feedback/feedbackDetails";
	}
	
}


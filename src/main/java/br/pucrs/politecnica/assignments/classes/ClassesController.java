package br.pucrs.politecnica.assignments.classes;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.pucrs.politecnica.assignments.discipline.DisciplineService;
import br.pucrs.politecnica.assignments.professor.ProfessorService;
import br.pucrs.politecnica.assignments.student.StudentService;

@Controller
public class ClassesController {

	private ClassesService classesService;

	private ProfessorService professorService;

	private StudentService studentService;

	private DisciplineService disciplineService;

	private static final String VIEWS_CLASSES_CREATE_OR_UPDATE_FORM = "classes/createOrUpdateClassesForm";

	public ClassesController(ClassesService classesService, ProfessorService professorService,
			StudentService studentService, DisciplineService disciplineService) {
		this.classesService = classesService;
		this.professorService = professorService;
		this.studentService = studentService;
		this.disciplineService = disciplineService;
	}

	@GetMapping("/classes/new")
	public String initCreationClassesForm(Model model) {	
		try {
			ClassesDTO classesDTO = new ClassesDTO();
			ArrayList<String> studentList = new ArrayList<String>();
			model.addAttribute("classesDTO", classesDTO);
			model.addAttribute("disciplineList", disciplineService.getDisciplineList());
			model.addAttribute("professorList", professorService.getProfessorList());
			model.addAttribute("studentList", studentService.getStudentList());
			model.addAttribute("selectedStudentsList", studentList);
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/home";
		}
		return VIEWS_CLASSES_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/classes/new")
	public String processCreationClassesForm(@Valid ClassesDTO classesDTO, BindingResult result, Model model) {
		if (result.hasErrors()) {
			try {
				model.addAttribute("disciplineList", disciplineService.getDisciplineList());
				model.addAttribute("professorList", professorService.getProfessorList());
				model.addAttribute("studentList", studentService.getStudentList());	
				return VIEWS_CLASSES_CREATE_OR_UPDATE_FORM;
			} catch (Exception e) {
				e.printStackTrace();
				return "redirect:/home";
			}
		} else {
			try {
				classesService.registrateClass(classesDTO);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "redirect:/home";
		}
	}
	
	@GetMapping("/classes/{classId}/edit")
	public String initUpdateClassesForm(@PathVariable("classId") Long classId, Model model) {
		try {
			ClassesDTO classesDTO = this.classesService.transformToClassesDTO(classId);
			List<String> studentList = classesDTO.getStudentList();
			model.addAttribute("classesDTO",classesDTO);
			model.addAttribute("disciplineList", disciplineService.getDisciplineList());
			model.addAttribute("professorList", professorService.getProfessorList());
			model.addAttribute("studentList", studentService.getStudentList());
			model.addAttribute("selectedStudentsList", studentList);
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/home";
		}
		return VIEWS_CLASSES_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping("/classes/{classId}/edit")
	public String processUpdateClassesForm(@Valid ClassesDTO classesDTO, BindingResult result, @PathVariable("classId") Long classId, Model model) {
		if(result.hasErrors()) {
			try {
				model.addAttribute("disciplineList", disciplineService.getDisciplineList());
				model.addAttribute("professorList", professorService.getProfessorList());
				model.addAttribute("studentList", studentService.getStudentList());	
				return VIEWS_CLASSES_CREATE_OR_UPDATE_FORM;
			} catch (Exception e) {
				e.printStackTrace();
				return "redirect:/home";
			}
		}
		else {
			try {
				classesService.editClass(classesDTO, classId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "redirect:/home";
		}
	}
}

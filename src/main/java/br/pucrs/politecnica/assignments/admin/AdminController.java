package br.pucrs.politecnica.assignments.admin;

import java.security.Principal;
import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.pucrs.politecnica.assignments.classes.Classes;
import br.pucrs.politecnica.assignments.classes.ClassesService;
import br.pucrs.politecnica.assignments.discipline.Discipline;
import br.pucrs.politecnica.assignments.discipline.DisciplineService;
import br.pucrs.politecnica.assignments.login.LoginDTO;
import br.pucrs.politecnica.assignments.professor.Professor;
import br.pucrs.politecnica.assignments.professor.ProfessorService;

@Controller
public class AdminController {

	private static final String VIEWS_PERSON_CREATE_OR_UPDATE_FORM = "person/createOrUpdatePersonRegistrationForm";

	private AdminService adminService;

	private ClassesService classesService;

	private DisciplineService disciplineService;

	private ProfessorService professorService;

	public AdminController(AdminService adminService, ClassesService classesService,
			DisciplineService disciplineService, ProfessorService professorService) {
		super();
		this.adminService = adminService;
		this.classesService = classesService;
		this.disciplineService = disciplineService;
		this.professorService = professorService;
	}
 
	@GetMapping("/person/new")
	public String initCreationForm(Model model) {
		LoginDTO loginDTO = new LoginDTO();
		model.addAttribute("loginDTO", loginDTO);
		return VIEWS_PERSON_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/person/new")
	public String processCreationForm(@Valid LoginDTO personDTO, BindingResult result, Principal principal) {
		if (result.hasErrors()) {
			return VIEWS_PERSON_CREATE_OR_UPDATE_FORM;
		}
		try {
			adminService.preparePersonForRegistration(personDTO, principal);
		} catch (Exception e) {
			e.printStackTrace();
			return VIEWS_PERSON_CREATE_OR_UPDATE_FORM;
		}
		return "redirect:/home";
	}

	@GetMapping("/classesList")
	public String showClassesList(@RequestParam(value = "fieldDiscipline", required = false) Long fieldDiscipline,
			@RequestParam(value = "fieldProfessor", required = false) Long fieldProfessor, Model model)
			throws Exception {
		model.addAttribute("disciplineList", this.disciplineService.getAllDisciplines());
		model.addAttribute("professorList", this.professorService.getAllProfessors());

		if (fieldProfessor == null && fieldDiscipline == null) {
			model.addAttribute("classesList", new ArrayList<Classes>());
		} else {
			if (fieldProfessor == 0 && fieldDiscipline == 0) {
				model.addAttribute("classesList", this.classesService.getAllClasses());
			} else {
				if (fieldProfessor != 0 && fieldDiscipline != 0) {
					Professor professor = this.professorService.findById(fieldProfessor);
					Discipline discipline = this.disciplineService.findById(fieldDiscipline);
					model.addAttribute("classesList",
							this.classesService.getListClassesProfessorAndDiscipline(professor, discipline));
				} else {
					if (fieldProfessor != 0) {
						Professor professor = this.professorService.findById(fieldProfessor);
						model.addAttribute("classesList", this.classesService.getListClassesProfessor(professor));
					}
					if (fieldDiscipline != 0) {
						Discipline discipline = this.disciplineService.findById(fieldDiscipline);
						model.addAttribute("classesList", this.classesService.getListClassesDiscipline(discipline));
					}
				}
			}
		}
		return "admin/adminClassesList";
	}

}

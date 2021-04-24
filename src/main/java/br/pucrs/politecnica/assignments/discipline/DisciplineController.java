package br.pucrs.politecnica.assignments.discipline;

import java.security.Principal;
import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import br.pucrs.politecnica.assignments.login.LoginService;

@Controller
public class DisciplineController {

	@Autowired
	private LoginService loginService;

	@Autowired
	private DisciplineService disciplineService;

	private static final String VIEWS_DISCIPLINE_CREATE_OR_UPDATE_FORM = "discipline/createOrUpdateDisciplineRegistrationForm";

	@GetMapping("/discipline")
	public String showDisciplineList(Model model) {
		try {
			model.addAttribute("disciplineList", disciplineService.getDisciplineList());
		} catch (Exception e) {
			ArrayList<Discipline> aux = new ArrayList<Discipline>();
			model.addAttribute("disciplineList", aux);
		}
		return "/discipline/disciplineList";
	}

	@GetMapping("/discipline/new")
	public String initCreationDisciplineForm(Model model) {
		DisciplineDTO disciplineDTO = new DisciplineDTO();
		model.addAttribute("disciplineDTO", disciplineDTO);
		return VIEWS_DISCIPLINE_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/discipline/new")
	public String processCreationDisciplineForm(@Valid DisciplineDTO disciplineDTO, BindingResult result, Principal principal){
		if (result.hasErrors()) {
            return VIEWS_DISCIPLINE_CREATE_OR_UPDATE_FORM;
        }
		try {
			disciplineService.saveDiscipline(disciplineDTO, loginService.getLogin(principal));
			return "redirect:/discipline";
		} catch (Exception e) {
			return VIEWS_DISCIPLINE_CREATE_OR_UPDATE_FORM;
		}
	}
	
}

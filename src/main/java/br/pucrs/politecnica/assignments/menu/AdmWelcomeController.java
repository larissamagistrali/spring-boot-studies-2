package br.pucrs.politecnica.assignments.menu;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import br.pucrs.politecnica.assignments.login.Login;

@Controller
public class AdmWelcomeController {
	
	@GetMapping("/home")
	public String admWelcome(@AuthenticationPrincipal Login loginDetails) {
		if (loginDetails.isFirstTime()) {

			return "redirect:/firstAccessForm";

		}
 
		switch (loginDetails.getUserRole()){
			case STUDENT:
				return "/welcomeStudent";

			case PROFESSOR:
				return "/welcomeProfessor";

			case ADMIN:
				return "/welcomeAdm";
		}

		return "/errorLogin";
	}
}

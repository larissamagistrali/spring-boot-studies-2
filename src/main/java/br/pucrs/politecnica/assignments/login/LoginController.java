package br.pucrs.politecnica.assignments.login;


import java.security.Principal;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

	private LoginService loginService;
	
	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}

	// Login
	@GetMapping("/login")
	public String login() {
		return "login/loginForm";
	}

	// --------------------------------------------
	// firstAcess
	@GetMapping("/firstAccessForm")
	public String firstAccessForm(Model model, Principal loginDetails) {
		if (!loginService.firstAcessForm(loginDetails)) {
			return "redirect:/home";
		}
		FirstAccessDTO firstAccessDTO = new FirstAccessDTO();
		model.addAttribute("firstAccessDTO", firstAccessDTO);
		model.addAttribute("email", loginService.getLogin(loginDetails).getEmail());
		return "login/firstAccessForm";
	}

	@PostMapping("/firstAccessForm")
	public String changePassword(@Valid FirstAccessDTO firstAccessDTO, BindingResult result, Principal p) throws Exception {
		if (result.hasErrors()) {
            return "login/firstAccessForm";
        }else {
    		loginService.changePassword(firstAccessDTO.getPassword(), firstAccessDTO.getConfirmation(), p);
    		return "welcome";
        }
	}

	// --------------------------------------------
	// changePassword
	@GetMapping("/changePassword")
	public String changePasswordGet(Model model) {
		model.addAttribute(new Login());
		return "login/changePasswordForm";
	}

	@PostMapping("/changePassword")
	public String changePasswordPost() {
		return "redirect:/login";
	}

	// --------------------------------------------
	// redefinePassword
	@GetMapping("/redefinePassword")
	public String redefinePasswordGet() {
		return "login/redefinePasswordForm";
	}

	@PostMapping("/redefinePassword")
	public String redefinePasswordPost() {
		return "redirect:/login";
	}

}

package br.pucrs.politecnica.assignments.login;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginDTO {

	@NotNull
	@Size(min = 1, max = 12)
	private String registration;
	
	@NotNull
	@Size(min = 2, max = 64)
	private String name;
	
	@NotNull
	@Size(min = 8, max = 32)
	private String email;
	
	@NotNull
	private String role;

	public Login transformToLogin(String name, String password) {
		Login login = new Login();
		login.setCreatedBy(name);
		login.setCreatedTime(LocalDateTime.now());
		login.setFirstTime(true);
		login.setEmail(this.email);
		login.setName(this.name);
		login.setPassword(password);
		login.setUserRole(enumSelect(this.role));
		return login;
	}
	
	public UserRole enumSelect(String role) {
		if(role.equals("Professor")) {
			return UserRole.PROFESSOR;
		}
		else if(role.equals("Admin")) {
			return UserRole.ADMIN;
		}
		return UserRole.STUDENT;
	}
	
	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

		
	
}

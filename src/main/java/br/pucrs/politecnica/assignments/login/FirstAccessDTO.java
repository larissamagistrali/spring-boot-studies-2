package br.pucrs.politecnica.assignments.login;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FirstAccessDTO {
	
	@NotNull
	@Size(min = 6)
	private String password;
	
	@NotNull
	@Size(min = 6)
	private String confirmation;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmation() {
		return confirmation;
	}

	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}

}

package br.pucrs.politecnica.assignments.admin;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.pucrs.politecnica.assignments.login.Login;

@Entity
@Table(name = "admin")
public class Admin implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	
	@NotNull
	@Size(max = 12)
	private String registration;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "login_id")
	@MapsId
	private Login login;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

package br.pucrs.politecnica.assignments.admin;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.pucrs.politecnica.assignments.log.LogService;
import br.pucrs.politecnica.assignments.login.Login;
import br.pucrs.politecnica.assignments.login.LoginDTO;
import br.pucrs.politecnica.assignments.login.LoginRepository;
import br.pucrs.politecnica.assignments.login.UserRole;
import br.pucrs.politecnica.assignments.professor.ProfessorService;
import br.pucrs.politecnica.assignments.student.StudentService;

@Service
public class AdminService {
	
	@Autowired
	private LoginRepository loginRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private LogService logService;
	
	@Autowired
	private ProfessorService professorService;
	
	@Autowired
	private StudentService studentService;

	public List<Admin> getAdminList() throws Exception {
		Optional<List<Admin>> optList = adminRepository.findAllOrderByName();
		return optList.orElseThrow(() -> new Exception());
	}

	public Admin saveAdmin(Login login, String registration) {
		Admin admin = new Admin();
		login.setUserRole(UserRole.ADMIN);
		admin.setRegistration(registration);
		admin.setLogin(login);
		admin.setId(login.getId());
		return adminRepository.save(admin);
	}

	public void preparePersonForRegistration(LoginDTO person, Principal principal) throws Exception {
		Optional<Login> loginOpt = this.loginRepository.findByEmail(principal.getName());
		if (loginOpt.isPresent()) {
			Login login = person.transformToLogin(loginOpt.get().getName(), bCryptPasswordEncoder.encode(person.getRegistration()));
			switch (login.getUserRole()) {
			case PROFESSOR:
				professorService.saveProfessor(login, person.getRegistration());
				break;
			case STUDENT:
				studentService.saveStudent(login, person.getRegistration());
				break;
			case ADMIN:				
				this.saveAdmin(login, person.getRegistration());
				break;
			}
			logService.createLog(login);
		}
		else {
			throw new Exception();
		}
	}
}

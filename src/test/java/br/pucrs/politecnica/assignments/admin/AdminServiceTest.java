package br.pucrs.politecnica.assignments.admin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import br.pucrs.politecnica.assignments.login.Login;
import br.pucrs.politecnica.assignments.login.LoginRepository;
import br.pucrs.politecnica.assignments.professor.Professor;
import br.pucrs.politecnica.assignments.professor.ProfessorRepository;
import br.pucrs.politecnica.assignments.professor.ProfessorService;
import br.pucrs.politecnica.assignments.student.Student;
import br.pucrs.politecnica.assignments.student.StudentRepository;
import br.pucrs.politecnica.assignments.student.StudentService;

@SpringBootTest
@ActiveProfiles("test")
public class AdminServiceTest {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private ProfessorService professorService;
	
	@Autowired
	private StudentService studentService;
	
	@MockBean
	private StudentRepository studentRepository;

	@MockBean
	private ProfessorRepository professorRepository;

	@MockBean
	private LoginRepository loginRepository;

	@MockBean
	private AdminRepository adminRepository;
	
	@Test
	public void getAdminListTest() throws Exception {
		Admin admin1 = new Admin();
		Admin admin2 = new Admin();
		List<Admin> listAdmins = new ArrayList<Admin>();
		listAdmins.add(admin1);
		listAdmins.add(admin2);
		Optional<List<Admin>> optList = Optional.of(listAdmins);
		
		given(this.adminRepository.findAllOrderByName()).willReturn(optList);
		assertEquals(admin1,this.adminService.getAdminList().get(0));
		assertEquals(admin2,this.adminService.getAdminList().get(1));
	}
	
	@Test
	public void saveProfessorTest() throws Exception {
		Login login = new Login();
		String registration = "abadaba";
		Professor professor = new Professor();
		professor.setRegistration(registration);	
		professor.setId(login.getId());
		given(this.professorRepository.save(Mockito.any())).willReturn(professor);
		assertEquals(professor, this.professorService.saveProfessor(login,registration));
		assertEquals(login.getId(),this.professorService.saveProfessor(login, registration).getId());

	}
	
	@Test
	public void saveAdminTest() throws Exception {
		Login login = new Login();
		String registration = "abadaba";
		Admin admin = new Admin();
		admin.setRegistration(registration);	
		admin.setId(login.getId());
		given(this.adminRepository.save(Mockito.any())).willReturn(admin);
		assertEquals(admin, this.adminService.saveAdmin(login,registration));
		assertEquals(login.getId(),this.adminService.saveAdmin(login, registration).getId());
	}
	
	@Test
	public void saveStudentTest() throws Exception {
		Login login = new Login();
		String registration = "abadaba";
		Student student = new Student();
		student.setRegistration(registration);	
		student.setId(login.getId());
		given(this.studentRepository.save(Mockito.any())).willReturn(student);
		assertEquals(student, this.studentService.saveStudent(login,registration));
		assertEquals(login.getId(),this.studentService.saveStudent(login, registration).getId());
	}
}

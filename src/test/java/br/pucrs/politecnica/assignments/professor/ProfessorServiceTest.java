package br.pucrs.politecnica.assignments.professor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import br.pucrs.politecnica.assignments.admin.AdminRepository;
import br.pucrs.politecnica.assignments.classes.Classes;
import br.pucrs.politecnica.assignments.discipline.Discipline;
import br.pucrs.politecnica.assignments.login.Login;
import br.pucrs.politecnica.assignments.login.LoginRepository;
import br.pucrs.politecnica.assignments.student.StudentRepository;

@SpringBootTest
@ActiveProfiles("test")
public class ProfessorServiceTest {
	
	@Autowired
	private ProfessorService professorService;
	
	@MockBean
	private StudentRepository studentRepository;

	@MockBean
	private ProfessorRepository professorRepository;

	@MockBean
	private LoginRepository loginRepository;

	@MockBean
	private AdminRepository adminRepository;
	
	@Test
	public void shouldGetProfessorList() throws Exception {
		Professor professor1 = new Professor();
		Professor professor2 = new Professor();
		List<Professor> listProfessors = new ArrayList<Professor>();
		listProfessors.add(professor1);
		listProfessors.add(professor2);
		Optional<List<Professor>> optList = Optional.of(listProfessors);
		
		given(this.professorRepository.findAllOrderByName()).willReturn(optList);
		assertEquals(professor1,this.professorService.getProfessorList().get(0));
		assertEquals(professor2,this.professorService.getProfessorList().get(1));
	}
	
	@Test
	public void shouldSaveProfessor() throws Exception {
		Login login = new Login();
		String registration = "Teste123";
		Professor professor = new Professor();
		professor.setRegistration(registration);	
		professor.setId(login.getId());
		professor.setLogin(login);
		given(this.professorRepository.save(Mockito.any())).willReturn(professor);
		assertEquals(professor, this.professorService.saveProfessor(login,registration));
		assertEquals(login.getId(),this.professorService.saveProfessor(login, registration).getId());
		assertEquals(login, this.professorService.saveProfessor(login, registration).getLogin());
	}
	
	@Test
	public void shouldGetUserProfessor() throws Exception {
		 Principal mockPrincipal = Mockito.mock(Principal.class);
		 Mockito.when(mockPrincipal.getName()).thenReturn("professor2@teste.com");
		 Professor professor = new Professor();
		 given(this.loginRepository.findByEmail(Mockito.any())).willReturn(Optional.of(new Login()));
		 given(this.professorRepository.findById(Mockito.any())).willReturn(Optional.of(professor));
		 assertEquals(professor, professorService.getUserProfessor(mockPrincipal));
	}
	
	@Test
	public void shouldFindById() throws Exception {
		Professor professor = new Professor();
		given(this.professorRepository.findById(Mockito.any())).willReturn(Optional.of(professor));
		assertEquals(professor, professorService.findById(Long.valueOf("1")));
	}
	
	@Test
	public void shouldGetDisciplinesByProfessor() throws Exception{
		Professor professor = new Professor();
		Classes class1 = new Classes();
		Classes class2 = new Classes();
		Discipline discipline1 = new Discipline();
		Discipline discipline2 = new Discipline();
		class1.setDiscipline(discipline1);
		class2.setDiscipline(discipline2);
		
		List<Classes> classesSet = new ArrayList<>();
		classesSet.add(class1);
		classesSet.add(class2);
		professor.setClasses(classesSet);
		List<Discipline> disciplineList = new ArrayList<>();
		disciplineList.add(discipline1);
		disciplineList.add(discipline2);
		assertEquals(disciplineList, professorService.getDisciplinesByProfessor(professor));
	}
}

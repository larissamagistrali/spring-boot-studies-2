package br.pucrs.politecnica.assignments.classes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import br.pucrs.politecnica.assignments.discipline.Discipline;
import br.pucrs.politecnica.assignments.discipline.DisciplineService;
import br.pucrs.politecnica.assignments.login.Login;
import br.pucrs.politecnica.assignments.login.LoginRepository;
import br.pucrs.politecnica.assignments.professor.Professor;
import br.pucrs.politecnica.assignments.professor.ProfessorService;
import br.pucrs.politecnica.assignments.student.Student;
import br.pucrs.politecnica.assignments.student.StudentService;

@SpringBootTest
@ActiveProfiles("test")
public class ClassesServiceTest {

	@MockBean
	private ClassesRepository classesRepository;

	@MockBean
	private LoginRepository loginRepository;

	@Autowired
	private ClassesService classesService;

	@MockBean
	private DisciplineService disciplineService;

	@MockBean
	private ProfessorService professorService;

	@MockBean
	private StudentService studentService;

	@Test
	void shouldSaveClasses() throws Exception {
		ClassesDTO classesDTO = new ClassesDTO();
		classesDTO.setCode("abcde");
		Discipline discipline = new Discipline();
		Professor professor = new Professor();
		given(this.disciplineService.findById((long) 2)).willReturn(discipline);
		classesDTO.setDiscipline(discipline.getId());
		given(this.professorService.findById((long) 2)).willReturn(professor);
		classesDTO.setProfessor(professor.getId());
		Classes classes = classesService.transformToClasses(classesDTO);
		given(this.classesRepository.save(Mockito.any())).willReturn(classes);
		assertEquals(classes, this.classesService.registrateClass(classesDTO));
	}

	@Test
	void shouldTransformClass() throws Exception {
		ClassesDTO classesDTO = new ClassesDTO();
		classesDTO.setCode("1254");
		classesDTO.setDiscipline(1L);
		classesDTO.setObservation("obs");
		classesDTO.setProfessor(1L);
		List<String> studentList = new ArrayList<String>();
		studentList.add("estudante");
		classesDTO.setStudentList(studentList);
		given(this.disciplineService.findById(1L)).willReturn(new Discipline());
		given(this.professorService.findById(1L)).willReturn(new Professor());
		given(this.studentService.findByName("estudante")).willReturn(new Student());
		Classes classes = this.classesService.transformToClasses(classesDTO);
		assertThat(classes.getCode()).isEqualTo(classesDTO.getCode());
	}

	@Test
	void shouldGetClassesByUser() throws Exception {
		Principal mockPrincipal = Mockito.mock(Principal.class);
		Mockito.when(mockPrincipal.getName()).thenReturn("professor@teste.com");
		List<Classes> listClass = new ArrayList<Classes>();
		Classes classes1 = new Classes();
		Classes classes2 = new Classes();
		listClass.add(classes1);
		listClass.add(classes2);
		given(this.loginRepository.findByEmail(Mockito.any())).willReturn(Optional.of(new Login()));
		given(this.classesRepository.findByProfessorId(Mockito.any())).willReturn(Optional.of(listClass));
		assertEquals(listClass, classesService.getClassesByUser(mockPrincipal));

	}

	@Test
	void shouldGetClassesByLongList() {
		List<Long> longList = new ArrayList<Long>();
		longList.add(1L);
		longList.add(2L);
		Classes class1 = new Classes();
		Classes class2 = new Classes();
		Set<Classes> classSet = new HashSet<Classes>();
		classSet.add(class2);
		classSet.add(class1);
		given(this.classesRepository.findById(1L)).willReturn(Optional.of(class1));
		given(this.classesRepository.findById(2L)).willReturn(Optional.of(class2));
		assertEquals(classSet.contains(class1), this.classesService.getClassesByLongList(longList).contains(class1));
	}

	@Test
	void shouldTransformClassDTO() throws Exception {
		Classes c = new Classes();
		c.setCode("aaaaa");
		c.setId((long)2);
		System.out.println(c.getCode());
		Discipline discipline = new Discipline();
		Professor professor = new Professor();
		given(this.disciplineService.findById((long) 2)).willReturn(discipline);
		c.setDiscipline(discipline);
		given(this.professorService.findById((long) 2)).willReturn(professor);
		c.setProfessor(professor);
		c.setObservation("obs");
		ClassesDTO cDTO = this.classesService.transformToClassesDTO(c.getId());
		assertEquals(cDTO.getCode(), this.classesService.transformToClassesDTO(c.getId()).getCode());

	}

	/*
	 * ClassesDTO classesDTO = new ClassesDTO(); classesDTO.setCode("1254");
	 * classesDTO.setDiscipline(1L); classesDTO.setObservation("obs");
	 * classesDTO.setProfessor(1L); List<String> studentList = new
	 * ArrayList<String>(); studentList.add("estudante");
	 * classesDTO.setStudentList(studentList);
	 * given(this.disciplineService.findById(1L)).willReturn(new Discipline());
	 * given(this.professorService.findById(1L)).willReturn(new Professor());
	 * given(this.studentService.findByName("estudante")).willReturn(new Student());
	 * Classes classes = classesService.transformToClasses(classesDTO);
	 * given(this.classesRepository.save(classes)).willReturn(classes);
	 * assertThat(classes.getCode()).isEqualTo("1254");
	 * assertThat(classesService.transformToClasses(classesDTO).getCode()).isEqualTo
	 * ("1254");
	 */
}

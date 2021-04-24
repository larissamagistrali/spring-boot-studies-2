package br.pucrs.politecnica.assignments.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import br.pucrs.politecnica.assignments.classes.Classes;
import br.pucrs.politecnica.assignments.login.Login;
import br.pucrs.politecnica.assignments.login.LoginRepository;
import br.pucrs.politecnica.assignments.professor.ProfessorRepository;
import br.pucrs.politecnica.assignments.taskProfessor.TaskProfessor;
import br.pucrs.politecnica.assignments.taskProfessor.TaskProfessorRepository;
import br.pucrs.politecnica.assignments.taskStudent.TaskStudent;

@SpringBootTest
@ActiveProfiles("test")
public class StudentServiceTest {

	@Autowired
	private StudentService studentService;

	@MockBean
	private StudentRepository studentRepository;

	@MockBean
	private ProfessorRepository professorRepository;

	@MockBean
	private LoginRepository loginRepository;

	@MockBean
	private TaskProfessorRepository taskProfessorRepository;

	@Test
	public void shouldGetStudentList() throws Exception {
		Student student1 = new Student();
		Student student2 = new Student();
		List<Student> listStudents = new ArrayList<Student>();
		listStudents.add(student1);
		listStudents.add(student2);
		Optional<List<Student>> optList = Optional.of(listStudents);

		given(this.studentRepository.findAllOrderByName()).willReturn(optList);
		assertEquals(student1, this.studentService.getStudentList().get(0));
		assertEquals(student2, this.studentService.getStudentList().get(1));
	}
	
	@Test
	public void shouldGetStudentLogged() throws Exception {
		Principal mockPrincipal = Mockito.mock(Principal.class);
		Mockito.when(mockPrincipal.getName()).thenReturn("student@teste.com");
		Student student = new Student();
		given(this.loginRepository.findByEmail(Mockito.any())).willReturn(Optional.of(new Login()));
		given(this.studentRepository.findByLoginId(Mockito.any())).willReturn(Optional.of(student));
		assertEquals(student, studentService.getStudentLogged(mockPrincipal));
	}
	
	@Test
	public void shouldSaveStudent() throws Exception {
		Login login = new Login();
		String registration = "AGBEDT";
		Student student = new Student();
		student.setRegistration(registration);
		student.setId(login.getId());
		student.setLogin(login);
		given(this.studentRepository.save(Mockito.any())).willReturn(student);
		assertEquals(student, this.studentService.saveStudent(login, registration));
		assertEquals(login.getId(), this.studentService.saveStudent(login, registration).getId());
		assertEquals(login, this.studentService.saveStudent(login, registration).getLogin());
	}

	@Test
	public void shouldFindByName() throws Exception {
		Student student = new Student();
		Login login = new Login();
		student.setLogin(login);
		given(this.studentRepository.findByName(Mockito.any())).willReturn(Optional.of(student));
		assertEquals(student, studentService.findByName(student.getLogin().getName()));
	}

	@Test
	public void shouldGetTasksToDoList() throws Exception {
		HashMap<TaskProfessor, Classes> map = new LinkedHashMap<>();
		Student student = new Student();
		Classes classes1 = new Classes();
		Classes classes2 = new Classes();
		TaskProfessor taskProfessor1 = new TaskProfessor();
		TaskProfessor taskProfessor2 = new TaskProfessor();
		TaskProfessor taskProfessor3 = new TaskProfessor();
		TaskProfessor taskProfessor4 = new TaskProfessor();
		taskProfessor1.setTaskTime(LocalDateTime.of(2100, 11, 11, 11, 11, 11));
		taskProfessor2.setTaskTime(LocalDateTime.of(2200, 11, 11, 11, 11, 11));
		taskProfessor3.setTaskTime(LocalDateTime.of(2300, 11, 11, 11, 11, 11));
		taskProfessor4.setTaskTime(LocalDateTime.of(2400, 11, 11, 11, 11, 11));
		taskProfessor1.setDetails("aaaaa");
		taskProfessor2.setDetails("bbbbb");
		taskProfessor3.setDetails("ccccc");
		taskProfessor4.setDetails("ddddd");
		//TaskProfessor alterado de SET para List
		List<TaskProfessor> taskSet1 = new ArrayList<TaskProfessor>();
		taskSet1.add(taskProfessor1);
		taskSet1.add(taskProfessor2);
		classes1.setTasks(taskSet1);
		//TaskProfessor alterado de SET para List
		List<TaskProfessor> taskSet2 = new ArrayList<TaskProfessor>();
		taskSet2.add(taskProfessor3);
		taskSet2.add(taskProfessor4);
		classes2.setTasks(taskSet2);
		Set<Classes> classesSet = new HashSet<Classes>();
		classesSet.add(classes1);
		classesSet.add(classes2);
		student.setClasses(classesSet);
		for (Classes c : student.getClasses()) {
			for (TaskProfessor t : c.getTasks()) {
				given(this.taskProfessorRepository.findById(t.getId())).willReturn(Optional.of(t));
				Optional<TaskProfessor> task = taskProfessorRepository.findById(t.getId());
				if (task.isPresent()) {
					if (task.get().getStatus()) {
						map.put(task.get(), c);
					}
				}
			}
		}
		LinkedHashMap<TaskProfessor, Classes> result = map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey((TaskProfessor e1, TaskProfessor e2) -> e1.getTaskTime().compareTo(e2.getTaskTime())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
		assertEquals(result, studentService.getTasksToDoList(student));
	}
	
	@Test
	public void shouldGetStatusByMapTasks () throws Exception{
		Student student = new Student();
		student.setRegistration("Teste123");
		Student falseStudent = new Student();
		student.setRegistration("");
		
		LinkedHashMap<TaskProfessor, Classes> map = new LinkedHashMap<TaskProfessor, Classes>();
		
		Set<TaskStudent> aux = new HashSet<TaskStudent>();
		TaskStudent st1 = new TaskStudent();
		st1.setStudent(student);
		aux.add(st1);
		
		Set<TaskStudent> aux2 = new HashSet<TaskStudent>();
		TaskStudent st2 = new TaskStudent();
		st2.setStudent(falseStudent);
		aux2.add(st2);
		
		TaskProfessor taskProfessorTrue = new TaskProfessor();
		taskProfessorTrue.setTasksStudent(aux);
		TaskProfessor taskProfessorFalse = new TaskProfessor();
		taskProfessorFalse.setTasksStudent(aux2);
		
		map.put(taskProfessorTrue, new Classes());
		map.put(taskProfessorFalse, new Classes());
		
		List<Boolean> result = this.studentService.getStatusByMapTasks(map, student);
		
		assertEquals(result.get(0),true);
		assertEquals(result.get(1),false);
	}


}

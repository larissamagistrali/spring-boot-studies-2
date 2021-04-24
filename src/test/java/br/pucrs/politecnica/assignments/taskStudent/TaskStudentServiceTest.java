package br.pucrs.politecnica.assignments.taskStudent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.security.Principal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import br.pucrs.politecnica.assignments.login.Login;
import br.pucrs.politecnica.assignments.login.LoginRepository;
import br.pucrs.politecnica.assignments.student.Student;
import br.pucrs.politecnica.assignments.student.StudentRepository;
import br.pucrs.politecnica.assignments.student.StudentService;
import br.pucrs.politecnica.assignments.taskProfessor.TaskProfessor;
import br.pucrs.politecnica.assignments.taskProfessor.TaskProfessorService;



@SpringBootTest
@ActiveProfiles("test")
public class TaskStudentServiceTest {
	
	@MockBean
	TaskProfessorService taskProfessorService;
	
	@MockBean
	TaskStudentRepository taskStudentRepository;
	
	@MockBean
	LoginRepository loginRepository;
	
	@MockBean
	StudentRepository studentRepository;
	
	@MockBean
	StudentService studentService;
	
	@Autowired
	TaskStudentService taskStudentService;
	
	@Test
	void shouldSaveTaskStudentTest() throws Exception {
		TaskStudentDTO taskDTO = new TaskStudentDTO();
		taskDTO.setComment("Comment test");
		taskDTO.setSendTask(true);
		byte[] a = new byte[20]; 
		taskDTO.setFiles(new MockMultipartFile("file name","file path", ".rar", a));
		
		TaskProfessor taskProfessor = new TaskProfessor();
		
		Student student = new Student();
		
		Principal principal = Mockito.mock(Principal.class);
		Mockito.when(principal.getName()).thenReturn("teste@teste.com");
		
		given(this.taskProfessorService.getTaskProfessor(Mockito.any())).willReturn(taskProfessor);
		given(this.studentService.getStudentLogged(Mockito.any())).willReturn(student);
		
		Login login = new Login();
		Optional<Login> loginOpt = Optional.of(login);
		given(this.loginRepository.findByEmail(Mockito.any())).willReturn(loginOpt);
		
		Optional<Student> studentOpt = Optional.of(student);
		given(this.studentRepository.findByLoginId(Mockito.any())).willReturn(studentOpt);
		
		TaskStudent taskStudent = taskStudentService.transformToTaskStudent(taskDTO, student, taskProfessor);
		
		given(this.taskStudentRepository.save(Mockito.any())).willReturn(taskStudent);
		assertEquals(taskStudent, this.taskStudentService.saveTaskStudent(taskDTO, 1L, principal));
	}
	
}

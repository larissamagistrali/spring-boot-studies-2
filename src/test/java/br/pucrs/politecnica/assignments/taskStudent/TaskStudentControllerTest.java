package br.pucrs.politecnica.assignments.taskStudent;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.pucrs.politecnica.assignments.classes.Classes;
import br.pucrs.politecnica.assignments.discipline.Discipline;
import br.pucrs.politecnica.assignments.professor.Professor;
import br.pucrs.politecnica.assignments.professor.ProfessorRepository;
import br.pucrs.politecnica.assignments.student.Student;
import br.pucrs.politecnica.assignments.student.StudentService;
import br.pucrs.politecnica.assignments.taskProfessor.TaskProfessor;
import br.pucrs.politecnica.assignments.taskProfessor.TaskProfessorService;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TaskStudentControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private TaskStudentService taskStudentService;
	@MockBean
	private TaskProfessorService taskProfessorService;
	@MockBean
	private StudentService studentService;
	@MockBean
	private Student studentLogged;
	@MockBean
	private TaskProfessor taskProfessor;
	@MockBean
	private Discipline taskDiscipline;
	@MockBean
	private Classes taskClass;
	
	@Autowired
	private ProfessorRepository professorRepository;
	
	@MockBean
	private TaskStudentDTO taskStudentDTO;
	
	@Test
	public void shouldInitCreationForm() throws Exception {
		Student student = new Student();
		given(this.studentService.getStudentLogged(Mockito.any())).willReturn(student);
		
		TaskProfessor taskProfessor = new TaskProfessor();

		given(this.taskProfessorService.getTaskProfessor(Mockito.any())).willReturn(taskProfessor);
		
		TaskStudentDTO taskStudent = new TaskStudentDTO();
		given(this.taskStudentService.getTaskStudentDTOByStudent(student, taskProfessor)).willReturn(taskStudent);
		
		Discipline discipline = new Discipline();
		given(this.taskProfessorService.getDisciplineByStudent(taskProfessor, student)).willReturn(discipline);
		
		Classes classes = new Classes();
		given(this.taskProfessorService.getClassByStudent(taskProfessor, student)).willReturn(classes);
		
		TaskStudent ts = new TaskStudent();
		given(this.taskStudentService.getTaskStudentByStudent(student, taskProfessor)).willReturn(ts);
		
		Professor p = this.professorRepository.findById(2L).get();
		
		given(this.taskProfessorService.getProfessor(taskProfessor)).willReturn(p);
		
		this.mockMvc.perform(get("/taskStudent/2/new").with(user("student@teste.com").password("teste123").roles("ADMIN")))
		.andExpect(status().isOk());
	}
	
	@Test 
	void shouldShowTaskStudentListTest() throws Exception{	
		this.mockMvc.perform(get("/taskStudent").with(user("teste@teste.com").password("teste123").roles("USER","STUDENT")))
		.andExpect(model().attributeExists("taskList"))
		.andExpect(model().attributeExists("statusList"))
		.andExpect(status().isOk());
	}
	
	void shouldInitCreationTaskStudentForm() throws Exception {
		this.mockMvc.perform(post("/taskStudent/{taskProfessorId}/new", 1).with(user("teste@teste.com").password("teste123").roles("USER","STUDENT"))
		.param("id", "1").param("professor", "1").param("task", "1").param("discipline", "1").param("class", "1").param("taskStudent", "1").param("taskProfessorId", "1"))
		.andExpect(status().is3xxRedirection());
	}
	
	@Test 
	void shouldReturnExceptionInTaskStudentForm() throws Exception{
		given(this.taskProfessorService.getTaskProfessor(Mockito.any())).willThrow(new Exception());
		this.mockMvc.perform(post("/taskStudent/{taskProfessorId}/new", 1).with(user("teste@teste.com").password("teste123").roles("USER","STUDENT")))
		.andExpect(status().is3xxRedirection());
	}
	 
	
}

package br.pucrs.politecnica.assignments.classes;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.pucrs.politecnica.assignments.discipline.DisciplineService;
import br.pucrs.politecnica.assignments.professor.ProfessorService;
import br.pucrs.politecnica.assignments.student.StudentService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ClassesControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProfessorService professorService;

	@MockBean
	private StudentService studentService;

	@MockBean
	private DisciplineService disciplineService;
	
	@MockBean
	private ClassesService classesService;
	
	@Test
	void shouldInitCreationClassesForm() throws Exception {
		this.mockMvc.perform(get("/classes/new").with(user("teste@teste.com").password("teste123").roles("USER","ADMIN")))
		.andExpect(model().attributeExists("classesDTO"))
		.andExpect(model().attributeExists("disciplineList"))
		.andExpect(model().attributeExists("professorList"))
		.andExpect(model().attributeExists("selectedStudentsList"))
		.andExpect(status().isOk());
	}
	
	@Test
	void shouldAddNewClasses() throws Exception {
		this.mockMvc.perform(post("/classes/new").with(user("teste@teste.com").password("teste123").roles("USER","ADMIN"))
				.param("code", "1").param("discipline", "1").param("professor", "1").param("observation", "obs").param("studentList", "estudante"))
				.andExpect(status().is3xxRedirection());
	}
	 
	@Test
	void shouldReturnExceptionInInitCreationClassesForm() throws Exception{
		given(this.disciplineService.getDisciplineList()).willThrow(new Exception());
		this.mockMvc.perform(get("/classes/new").with(user("teste@teste.com").password("teste123").roles("USER","ADMIN")))
		.andExpect(status().is3xxRedirection());
	}
	
	@Test
	void shouldReturnExceptionInAddNewClasses() throws Exception{
		given(this.disciplineService.getDisciplineList()).willThrow(new Exception());
		this.mockMvc.perform(post("/classes/new").with(user("teste@teste.com").password("teste123").roles("USER","ADMIN")))
		.andExpect(status().is3xxRedirection());
	}
	
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(
				post("/classes/new")
				.param("discipline", "1").param("professor", "1").param("observation", "obs").param("studentList", "estudante"))
				.andExpect(view().name("classes/createOrUpdateClassesForm"));
	}
	
	@Test
	void shouldInitUpdateClassesForm() throws Exception{
		
		ClassesDTO cDTO = new ClassesDTO();
		List<String> studentList = new ArrayList<>();
		cDTO.setStudentList(studentList);
		
		given(this.classesService.transformToClassesDTO(Mockito.any())).willReturn(cDTO);
		
		this.mockMvc.perform(get("/classes/{classId}/edit",1).with(user("teste@teste.com").password("teste123").roles("USER","ADMIN")))
		.andExpect(model().attributeExists("classesDTO"))
		.andExpect(model().attributeExists("disciplineList"))
		.andExpect(model().attributeExists("professorList"))
		.andExpect(model().attributeExists("studentList"))
		.andExpect(model().attributeExists("selectedStudentsList"))
		.andExpect(status().isOk());
	}
	
	@Test
	void shouldShowUpdateTaskException() throws Exception{
		given(this.disciplineService.getDisciplineList()).willThrow(new Exception());
		
		this.mockMvc.perform(get("/classes/{classId}/edit",1)
				.with(user("teste@teste.com").password("teste123").roles("USER","ADMIN")))
			.andExpect(status().is3xxRedirection());
	}
	
	
	@Test
	void testProcessUpdateClassesForm() throws Exception{
		
		
		this.mockMvc.perform(
				post("/classes/{classId}/edit",1).with(user("teste@teste.com").password("teste123").roles("USER","ADMIN"))
				.param("discipline", "1").param("professor", "1").param("observation", "obs").param("studentList", "estudante"))
				.andExpect(view().name("classes/createOrUpdateClassesForm"));
	}
	
	@Test
	void shouldShowProcessUpdateClassesForm() throws Exception{
		given(this.disciplineService.getDisciplineList()).willThrow(new Exception());
		
		this.mockMvc.perform(post("/classes/{classId}/edit",1)
				.with(user("teste@teste.com").password("teste123").roles("USER","ADMIN")))
			.andExpect(status().is3xxRedirection());
		
	}
	
}

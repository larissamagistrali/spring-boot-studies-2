package br.pucrs.politecnica.assignments.admin;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.pucrs.politecnica.assignments.classes.Classes;
import br.pucrs.politecnica.assignments.classes.ClassesService;
import br.pucrs.politecnica.assignments.discipline.Discipline;
import br.pucrs.politecnica.assignments.discipline.DisciplineService;
import br.pucrs.politecnica.assignments.log.LogService;
import br.pucrs.politecnica.assignments.professor.Professor;
import br.pucrs.politecnica.assignments.professor.ProfessorService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AdminControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AdminService adminService;
	
	@MockBean
	private LogService logService;
	
	@MockBean
	private ProfessorService professorService;
	
	@MockBean
	private DisciplineService disciplineService;
	
	@MockBean
	private ClassesService classesService;
	
	@Test
	void shouldNewAdminGet() throws Exception {
		this.mockMvc.perform(get("/person/new").with(user("teste@teste.com").password("teste123").roles("USER","ADMIN")))
		.andExpect(model().attributeExists("loginDTO"))
		.andExpect(status().isOk());
	}
	
	@Test
	void shouldAddNewProfessorPost() throws Exception {
		this.mockMvc.perform(post("/person/new").with(user("teste@teste.com").password("teste123").roles("USER","ADMIN"))
				.param("role", "1").param("email", "teste@teste.com").param("registration", "16200441").param("name", "Jose"))
				.andExpect(status().is3xxRedirection());
	}
	
	@Test
	void shouldAddNewStudentPost() throws Exception {
		this.mockMvc.perform(post("/person/new").with(user("teste@teste.com").password("teste123").roles("USER","ADMIN"))
				.param("role", "2").param("email", "teste@teste.com").param("registration", "16200441").param("name", "Jose"))
				.andExpect(status().is3xxRedirection());
	}
	
	@Test
	void shouldAddNewAdminPost() throws Exception {
		this.mockMvc.perform(post("/person/new").with(user("teste@teste.com").password("teste123").roles("USER","ADMIN"))
				.param("role", "3").param("email", "teste@teste.com").param("registration", "16200441").param("name", "Jose"))
				.andExpect(status().is3xxRedirection());
	}
	
	@Test
	void shouldShowClassesList() throws Exception {
		this.mockMvc.perform(get("/classesList").with(user("teste@teste.com").password("teste123").roles("USER", "ADMIN")))
				.andExpect(model().attributeExists("disciplineList"))
				.andExpect(model().attributeExists("professorList"))
				.andExpect(model().attributeExists("classesList"))
				.andExpect(status().isOk());
	}
	
	@Test
	void shouldShowClassesList1() throws Exception {
		given(this.classesService.getAllClasses()).willReturn(new ArrayList<Classes>());
		
		this.mockMvc.perform(get("/classesList").with(user("teste@teste.com").password("teste123").roles("USER", "ADMIN"))
		.param("fieldProfessor", "0").param("fieldDiscipline", "0"))
		.andExpect(model().attributeExists("classesList"))
		.andExpect(status().isOk());
	}
	
	@Test
	void shouldShowClassesList2() throws Exception {
		Discipline discipline = new Discipline();
		given(this.disciplineService.findById(Mockito.anyLong())).willReturn(discipline);
		Professor professor = new Professor();
		given(this.professorService.findById(Mockito.any())).willReturn(professor);
		
		given(this.classesService.getListClassesProfessorAndDiscipline(Mockito.any(), Mockito.any())).willReturn(new ArrayList<Classes>());
		
		this.mockMvc.perform(get("/classesList").with(user("teste@teste.com").password("teste123").roles("USER", "ADMIN"))
				.param("fieldProfessor", "1").param("fieldDiscipline", "1"))
				.andExpect(model().attributeExists("classesList"))
				.andExpect(status().isOk());
	}
	
	@Test
	void shouldShowClassesList3() throws Exception {
		Professor professor = new Professor();
		given(this.professorService.findById(Mockito.any())).willReturn(professor);
		
		given(this.classesService.getListClassesProfessor(Mockito.any())).willReturn(new ArrayList<Classes>());
		
		this.mockMvc.perform(get("/classesList").with(user("teste@teste.com").password("teste123").roles("USER", "ADMIN"))
				.param("fieldProfessor", "1").param("fieldDiscipline", "0"))
				.andExpect(model().attributeExists("classesList"))
				.andExpect(status().isOk());
	}
	
	@Test
	void shouldShowClassesList4() throws Exception {
		Discipline discipline = new Discipline();
		given(this.disciplineService.findById(Mockito.anyLong())).willReturn(discipline);
		
		given(this.classesService.getListClassesDiscipline(Mockito.any())).willReturn(new ArrayList<Classes>());
		
		this.mockMvc.perform(get("/classesList").with(user("teste@teste.com").password("teste123").roles("USER", "ADMIN"))
				.param("fieldProfessor", "0").param("fieldDiscipline", "1"))
				.andExpect(model().attributeExists("classesList"))
				.andExpect(status().isOk());
	}

	
}

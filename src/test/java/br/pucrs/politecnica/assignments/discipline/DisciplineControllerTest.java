package br.pucrs.politecnica.assignments.discipline;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DisciplineControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private DisciplineService disciplineService;
	
	
	@Test 
	void showDisciplineListTest() throws Exception{	
		this.mockMvc.perform(get("/discipline").with(user("teste@teste.com").password("teste123").roles("USER","ADMIN")))
		.andExpect(model().attributeExists("disciplineList"))
		.andExpect(status().isOk());
	}
	
	@Test
	void shoudlReturnExceptionInShowDisciplineList() throws Exception{
		given(this.disciplineService.getDisciplineList()).willThrow(new Exception());
		this.mockMvc.perform(get("/discipline").with(user("teste@teste.com").password("teste123").roles("USER","ADMIN")))
		.andExpect(model().attributeExists("disciplineList"))
		.andExpect(status().isOk());
	}
	
	@Test
	void shouldNewDisciplineGet() throws Exception {
		this.mockMvc.perform(get("/discipline/new").with(user("teste@teste.com").password("teste123").roles("USER","ADMIN")))
		.andExpect(model().attributeExists("disciplineDTO"))
		.andExpect(status().isOk());
	}
	
	@Test
	void shouldAddNewDisciplinePost() throws Exception 	{
		this.mockMvc.perform(post("/discipline/new").with(user("teste@teste.com").password("teste123").roles("USER","ADMIN"))
		.param("code", "abc1234").param("name", "Testes").param("observation", "Testes iniciante"))
		.andExpect(status().is3xxRedirection());
	}
	
}

package br.pucrs.politecnica.assignments.student;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.pucrs.politecnica.assignments.log.LogService;
import br.pucrs.politecnica.assignments.login.LoginService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class StudentControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private StudentService studentService;
	
	@MockBean
	private LogService logService;

	@MockBean
	private LoginService loginService;

	@Test 
	void showDisciplineListTest() throws Exception{	
		this.mockMvc.perform(get("/taskStudent").with(user("admin@teste.com").password("teste123").roles("ADMIN")))
		.andExpect(model().attributeExists("taskList"))
		.andExpect(model().attributeExists("statusList"))
		.andExpect(status().isOk());
	}
	
	
}

package br.pucrs.politecnica.assignments.login;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import br.pucrs.politecnica.assignments.login.UserRole;
import br.pucrs.politecnica.assignments.admin.AdminService;
import br.pucrs.politecnica.assignments.log.LogService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class LoginControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AdminService adminService;

	@MockBean
	private LogService logService;

	@MockBean
	private LoginService loginService;

	@Test
	void shouldLogin() throws Exception {
		this.mockMvc.perform(get("/login")).andExpect(status().isOk());
	}
	
	@Test
	void shouldFirstAcessFormGetFalse() throws Exception {
		given(this.loginService.firstAcessForm(Mockito.any())).willReturn(false);
		this.mockMvc.perform(get("/firstAccessForm").with(user("teste@teste.com").password("teste123").roles("ADMIN")))
			.andExpect(status().is3xxRedirection());
	}
	
	@Test
	void shouldFirstAcessFormGetTrue() throws Exception {
		given(this.loginService.firstAcessForm(Mockito.any())).willReturn(true);
		given(this.loginService.getLogin(Mockito.any())).willReturn(new Login());
		this.mockMvc.perform(get("/firstAccessForm").with(user("teste@teste.com").password("teste123").roles("ADMIN")))
			.andExpect(status().isOk());
		
	}
	
	@Test
	void shouldFirstAcessFormPost() throws Exception {
		this.mockMvc.perform(post("/firstAccessForm").with(user("teste@teste.com").password("teste123").roles("USER","ADMIN"))
			.param("password", "123").param("confirmation", "123"))
			.andExpect(status().isOk());
	}

	@Test
	void shouldChangePasswordGet() throws Exception {
		this.mockMvc.perform(get("/changePassword").with(user("teste@teste.com").password("teste123").roles("USER","ADMIN")))
			.andExpect(status().isOk());
	}
	
	@Test
	void shouldChangePasswordPost() throws Exception {
		this.mockMvc.perform(post("/changePassword").with(user("teste@teste.com").password("teste123").roles("USER","ADMIN")))
			.andExpect(status().is3xxRedirection());
	}
	
	@Test
	void shouldRedefinePasswordGet() throws Exception {
		this.mockMvc.perform(get("/redefinePassword").with(user("teste@teste.com").password("teste123").roles("USER","ADMIN")))
			.andExpect(status().isOk())
			.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	void shouldRedefinePasswordPost() throws Exception {
		this.mockMvc.perform(post("/redefinePassword").with(user("teste@teste.com").password("teste123").roles("USER","ADMIN")))
			.andExpect(status().is3xxRedirection());
	}


}

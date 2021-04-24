package br.pucrs.politecnica.assignments.login;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class LoginRepositoryTest {

	@Autowired
	private LoginRepository loginRepository;
	
	// ----------------------------------------------------------------------------------
	// Todas os metodos abaixo sao transactional, ou seja, nao altera no banco de dados
	// Metodos find e update podem falhar se nao encontrarem os dados de email.
	
	@Test
	@Transactional
	void shouldInsert() {
		Login login = new Login();
		login.setId((long) 0);
		login.setEmail("teste@teste.com");
		login.setName("Teste");
		login.setPassword("admin123");
		login.setUserRole(UserRole.ADMIN);
		login.setCreatedBy("teste");
		login.setCreatedTime(LocalDateTime.now());
		login.setFirstTime(true);
		loginRepository.save(login);
		assertThat(login.getEmail()).isEqualTo("teste@teste.com");
		this.loginRepository.save(login);
		List<Login> logins = new ArrayList<Login>();
		for (Login login2 : this.loginRepository.findAll()) {
			logins.add(login2);
		}
		Login aux = null;
		for (Login login3 : logins) {
			if(login3.getEmail().equals("teste@teste.com")) {
				aux = login3;
			}
		}
		assertThat(login.getEmail()).isEqualTo(aux.getEmail());
		
	}
	
	@Test
	@Transactional
	void assertThatFindByEmail() {
		Optional<Login> login = this.loginRepository.findByEmail("admin@teste.com");
		assertThat(login.get().getEmail()).isEqualTo("admin@teste.com");
		
	}
	

	@Test
	@Transactional
	void shouldUpdate() {
		
		Optional<Login> findLogin = this.loginRepository.findByEmail("admin@teste.com");
		findLogin.get().setEmail("jose@gmail.com");
		this.loginRepository.save(findLogin.get());
		
		Optional<Login> findLoginUpdate = this.loginRepository.findByEmail("jose@gmail.com");
		assertThat(findLoginUpdate.get().getEmail()).isEqualTo("jose@gmail.com");
		
		Optional<Login> findLoginOld = this.loginRepository.findByEmail("teste@teste.com");
		assertThat(findLoginOld.isPresent()).isEqualTo(false);
	}
	
	
		


	
}

package br.pucrs.politecnica.assignments.admin;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.pucrs.politecnica.assignments.login.Login;
import br.pucrs.politecnica.assignments.login.LoginRepository;

@SpringBootTest
@ActiveProfiles("test")
class AdminRepositoryTest {

	@Autowired
	private LoginRepository loginRepository;

	@Autowired
	private AdminRepository adminRepository;

	// ----------------------------------------------------------------------------------
	// Todas os metodos abaixo sao transactional, ou seja, nao altera no banco de dados
	// Metodos abaixo podem falhar se nao encontrarem os dados de login ja cadastrados no BD.


	@Test
	@Transactional
	void shouldFindByRegistration() {
		Optional<Login> login = loginRepository.findByEmail("admin@teste.com");
		assertThat(login.isPresent()).isEqualTo(true);

		Admin adm = new Admin();
		adm.setLogin(login.get());
		adm.setRegistration("16206699-1");

		this.adminRepository.save(adm);
		Optional<Admin> findAdmin = adminRepository.findByRegistration("16206699-1");
		assertThat(findAdmin.isPresent()).isEqualTo(true);

	}

	@Test
	@Transactional
	void shouldInsert() {
		Optional<Login> login = loginRepository.findByEmail("admin@teste.com");
		assertThat(login.isPresent()).isEqualTo(true);

		Admin adm = new Admin();
		adm.setLogin(login.get());
		adm.setRegistration("16206699-1");

		this.adminRepository.save(adm);
		Optional<Admin> findAdmin = adminRepository.findByRegistration("16206699-1");
		assertThat(findAdmin.get().getRegistration()).isEqualTo("16206699-1");

	}

	@Test
	@Transactional
	void shouldUpdate() {
		Optional<Login> login = loginRepository.findByEmail("admin@teste.com");
		assertThat(login.isPresent()).isEqualTo(true);

		Admin adm = new Admin();
		adm.setLogin(login.get());
		adm.setRegistration("16206699-1");
		this.adminRepository.save(adm);

		Optional<Admin> findAdmin = adminRepository.findByRegistration("16206699-1");
		findAdmin.get().setRegistration("16206698-1");
		this.adminRepository.save(findAdmin.get());

		// Verifica encontra a alteracao
		findAdmin = adminRepository.findByRegistration("16206698-1");
		assertThat(findAdmin.get().getRegistration()).isEqualTo("16206698-1");

		// Verifica se nao duplicou o objeto
		findAdmin = adminRepository.findByRegistration("16206699-1");
		assertThat(findAdmin.isPresent()).isEqualTo(false);

	}

}

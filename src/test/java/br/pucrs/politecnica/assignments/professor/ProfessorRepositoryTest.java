package br.pucrs.politecnica.assignments.professor;

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
class ProfessorRepositoryTest {

	@Autowired
	private LoginRepository loginRepository;

	@Autowired
	private ProfessorRepository professorRepository;

	// ----------------------------------------------------------------------------------
	// Todas os metodos abaixo sao transactional, ou seja, nao altera no banco de dados
	// Metodos abaixo podem falhar se nao encontrarem os dados de login ja cadastrados no BD.

	@Test
	@Transactional
	void shouldFindByRegistration() {
		Optional<Professor> findProfessor = professorRepository.findByRegistration("248188121452");
		assertThat(findProfessor.isPresent()).isEqualTo(true);

	}

	@Test
	@Transactional
	void shouldInsert() {
		Optional<Login> login = loginRepository.findByEmail("professorteste@teste.com");
		assertThat(login.isPresent()).isEqualTo(true);

		Professor professor = new Professor();
		professor.setLogin(login.get());
		professor.setRegistration("16206699-1");

		this.professorRepository.save(professor);
		Optional<Professor> findProfessor = professorRepository.findByRegistration("16206699-1");
		assertThat(findProfessor.get().getRegistration()).isEqualTo("16206699-1");

	}

	@Test
	@Transactional
	void shouldUpdate() {
		Optional<Login> login = loginRepository.findByEmail("professorteste@teste.com");
		assertThat(login.isPresent()).isEqualTo(true);

		Professor professor = new Professor();
		professor.setLogin(login.get());
		professor.setRegistration("16206699-1");
		this.professorRepository.save(professor);

		Optional<Professor> findProfessor = professorRepository.findByRegistration("16206699-1");
		findProfessor.get().setRegistration("16206698-1");
		this.professorRepository.save(findProfessor.get());

		// Verifica encontra a alteracao
		findProfessor = professorRepository.findByRegistration("16206698-1");
		assertThat(findProfessor.get().getRegistration()).isEqualTo("16206698-1");

		// Verifica se nao duplicou o objeto
		findProfessor = professorRepository.findByRegistration("16206699-1");
		assertThat(findProfessor.isPresent()).isEqualTo(false);

	}

}

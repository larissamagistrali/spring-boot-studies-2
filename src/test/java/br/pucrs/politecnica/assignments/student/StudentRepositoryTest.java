package br.pucrs.politecnica.assignments.student;

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
class StudentRepositoryTest {

	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	

	// ----------------------------------------------------------------------------------
	// Todas os metodos abaixo sao transactional, ou seja, nao altera no banco de dados
	// Metodos abaixo podem falhar se nao encontrarem os dados de login ja cadastrados no BD.

	
	@Test
	@Transactional
	void shouldFindByRegistration() {
		
		Optional<Student> findStudent = studentRepository.findByRegistration("201754121224");
		assertThat(findStudent.isPresent()).isEqualTo(true);
		
	}
	
	
	@Test
	@Transactional
	void shouldInsert() {
		Optional<Login> login = loginRepository.findByEmail("studentteste@teste.com");
		assertThat(login.isPresent()).isEqualTo(true);
		
		Student Student = new Student();
		Student.setLogin(login.get());
		Student.setRegistration("16206699-1");
		
		this.studentRepository.save(Student);
		Optional<Student> findStudent = studentRepository.findByRegistration("16206699-1");
		assertThat(findStudent.get().getRegistration()).isEqualTo("16206699-1");
		
	}
	
	@Test
	@Transactional
	void shouldUpdate() {
		Optional<Login> login = loginRepository.findByEmail("studentteste@teste.com");
		assertThat(login.isPresent()).isEqualTo(true);
		
		Student Student = new Student();
		Student.setLogin(login.get());
		Student.setRegistration("16206699-1");
		this.studentRepository.save(Student);
		
		Optional<Student> findStudent = studentRepository.findByRegistration("16206699-1");
		findStudent.get().setRegistration("16206698-1");
		this.studentRepository.save(findStudent.get());
		
		//Verifica encontra a alteracao
		findStudent = studentRepository.findByRegistration("16206698-1");
		assertThat(findStudent.get().getRegistration()).isEqualTo("16206698-1");
		
		//Verifica se nao duplicou o objeto
		findStudent = studentRepository.findByRegistration("16206699-1");
		assertThat(findStudent.isPresent()).isEqualTo(false);
		
		
	}

}

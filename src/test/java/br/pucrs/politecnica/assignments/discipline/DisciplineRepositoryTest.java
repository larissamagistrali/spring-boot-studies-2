package br.pucrs.politecnica.assignments.discipline;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.List;
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
public class DisciplineRepositoryTest {
	
	@Autowired
	private DisciplineRepository disciplineRepository;
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Test
	@Transactional
	void shouldInsert() {
		Optional<Login> admin = loginRepository.findByEmail("admin@teste.com");
		assertThat(admin.isPresent()).isEqualTo(true);
		
	}
	
	@Test
	@Transactional
	void findAllOrderByNameTest() {
		
		Optional<Login> admin = loginRepository.findByEmail("admin@teste.com");
				
		Discipline discipline1 = new Discipline();
		discipline1.setCode("abcd1");
		discipline1.setName("SQLServer");
		discipline1.setObservation("Criando, consultando e manipulando dados básicos");
		discipline1.setCreatedBy(admin.get().getName());
		discipline1.setCreatedTime(LocalDateTime.now());
		
		Discipline discipline2 = new Discipline();
		discipline2.setCode("abcd2");
		discipline2.setName("Annotation");
		discipline2.setObservation("Anotações em java");
		discipline2.setCreatedBy(admin.get().getName());
		discipline2.setCreatedTime(LocalDateTime.now());
		
		this.disciplineRepository.save(discipline1);
		this.disciplineRepository.save(discipline2);
		
		Optional<List<Discipline>> listOpt = disciplineRepository.findAllOrderByName();
		List<Discipline> list = listOpt.get();
		assertEquals(5,list.size());	
		assertEquals(discipline2, list.get(0));
		assertNotNull(list.size());
		assertThat(list.lastIndexOf(discipline1));
	}

}

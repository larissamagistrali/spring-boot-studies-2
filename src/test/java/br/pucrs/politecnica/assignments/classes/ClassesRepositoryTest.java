package br.pucrs.politecnica.assignments.classes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.pucrs.politecnica.assignments.discipline.Discipline;
import br.pucrs.politecnica.assignments.discipline.DisciplineRepository;
import br.pucrs.politecnica.assignments.professor.Professor;
import br.pucrs.politecnica.assignments.professor.ProfessorRepository;

@SpringBootTest
@ActiveProfiles("test")
public class ClassesRepositoryTest {
	
	@Autowired
	private ClassesRepository classesRepository;
	
	@Autowired
	private ProfessorRepository professorRepository;
	
	@Autowired
	private DisciplineRepository disciplineRepository;
	
	@Test 
	@Transactional
	void shouldFindAllOrderByCode() {
		Professor professor = professorRepository.findById((long) 2).get();
		Discipline discipline = disciplineRepository.findByCode("142151");
		Classes classes1 = new Classes();
		classes1.setCode("aaaaa");
		classes1.setProfessor(professor);
		classes1.setDiscipline(discipline);
		Classes classes2 = new Classes();
		classes2.setCode("bbbbb");
		classes2.setProfessor(professor);
		classes2.setDiscipline(discipline);
		this.classesRepository.save(classes1);
		this.classesRepository.save(classes2);
		Optional<List<Classes>> listOpt = classesRepository.findAllOrderByCode();
		List<Classes> list = listOpt.get();
		assertEquals(5,list.size());	
		assertEquals(classes1, list.get(3));
		assertNotNull(list.size());
		assertThat(list.lastIndexOf(classes2));
	}

	@Test 
	@Transactional
	void shouldFindByProfessorId() {
		Professor professor = professorRepository.findById((long) 2).get();
		List<Classes> classList = classesRepository.findByProfessorId(professor.getId()).get();
		assertThat(classList.contains(classesRepository.findByCode("12343").get())).isEqualTo(true);
	}
	

}

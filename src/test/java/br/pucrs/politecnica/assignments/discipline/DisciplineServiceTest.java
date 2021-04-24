package br.pucrs.politecnica.assignments.discipline;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import br.pucrs.politecnica.assignments.classes.Classes;
import br.pucrs.politecnica.assignments.login.Login;
import br.pucrs.politecnica.assignments.taskProfessor.TaskProfessor;

@SpringBootTest
@ActiveProfiles("test")
public class DisciplineServiceTest {
	
	@Autowired
	private DisciplineService disciplineService;
	
	@MockBean
	private DisciplineRepository disciplineRepository;
	
	@Test
	void saveDisciplineTest() {
		Login login = new Login();
		login.setName("teste");
		DisciplineDTO disciplineDTO = new DisciplineDTO();
		Discipline discipline = disciplineDTO.transformToDiscipline("teste");
		
		given(this.disciplineRepository.save(Mockito.any())).willReturn(discipline);
		assertEquals(discipline, this.disciplineService.saveDiscipline(disciplineDTO, login));
		assertEquals(login.getName(),this.disciplineService.saveDiscipline(disciplineDTO, login).getCreatedBy());
	}
	
	@Test
	void getDisciplineListEmptyTest() throws Exception {
		Assertions.assertThrows( new Exception().getClass() , ()->{disciplineService.getDisciplineList();});
	}
	
	@Test
	void getDisciplineListTest() throws Exception {
		Discipline discipline1 = new Discipline();
		Discipline discipline2 = new Discipline();
		List<Discipline> listDisciplines = new ArrayList<Discipline>();
		listDisciplines.add(discipline1);
		listDisciplines.add(discipline2);
		Optional<List<Discipline>> optList = Optional.of(listDisciplines);
		
		given(this.disciplineRepository.findAllOrderByName()).willReturn(optList);
		assertEquals(discipline1,this.disciplineService.getDisciplineList().get(0));
		assertEquals(discipline2,this.disciplineService.getDisciplineList().get(1));
	}
	
	@Test
	void shouldGetDisciplinesByClasses() throws Exception{
		Classes classes1 = new Classes();
		Classes classes2 = new Classes();
		Discipline discipline1 = new Discipline();
		Discipline discipline2 = new Discipline();
		classes1.setDiscipline(discipline1);
		classes2.setDiscipline(discipline2);
		List<Discipline> disciplineList = new ArrayList<Discipline>();
		disciplineList.add(discipline1);
		disciplineList.add(discipline2);
		List<Classes> classList = new ArrayList<Classes>();
		classList.add(classes1);
		classList.add(classes2);
		assertEquals(disciplineList, disciplineService.getDisciplinesByClasses(classList));
	}
	
	@Test
	public void shouldFindById() throws Exception {
		Discipline discipline = new Discipline();
		discipline.setId((long) 10);
		given(this.disciplineRepository.findById(Mockito.any())).willReturn(Optional.of(discipline));
		assertEquals(discipline, disciplineService.findById(discipline.getId()));
	}
	
	@Test
	public void shouldGetClassesAndTaskProfessorByDiscipline() throws Exception {
		Discipline discipline = new Discipline();
		Classes class1 = new Classes();
		Classes class2 = new Classes();
		TaskProfessor task1 = new TaskProfessor();
		TaskProfessor task2 = new TaskProfessor();
		List<TaskProfessor> taskList1 = new ArrayList<>();
		List<TaskProfessor> taskList2 = new ArrayList<>();
		List<Classes> classesList = new ArrayList<>();
		
		taskList1.add(task1);
		taskList2.add(task2);
		class1.setTasks(taskList1);
		class2.setTasks(taskList2);
		classesList.add(class1);
		classesList.add(class2);
		discipline.setClasses(classesList);
		
		Map<TaskProfessor, Classes> classesTasksMap = new HashMap<>();
		classesTasksMap.put(task1, class1);
		classesTasksMap.put(task2, class2);
		
		assertEquals(classesTasksMap, disciplineService.getClassesAndTaskProfessorByDiscipline(discipline));
	}

}

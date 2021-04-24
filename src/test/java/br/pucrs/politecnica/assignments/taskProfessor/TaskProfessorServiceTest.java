package br.pucrs.politecnica.assignments.taskProfessor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import br.pucrs.politecnica.assignments.classes.ClassesRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ActiveProfiles("test")
public class TaskProfessorServiceTest {

	@Autowired
	private TaskProfessorService taskProfessorService;
	
	@MockBean
	private TaskProfessorRepository taskProfessorRepository;
	 
	@MockBean
	private ClassesRepository classesRepository;
	
	@Test
	void saveTaskProfessorTest() throws Exception {
		TaskProfessorDTO taskProfessorDTO = new TaskProfessorDTO();
		taskProfessorDTO.setTaskTime(LocalDateTime.now());
		taskProfessorDTO.setDetails("Teste Teste");
		taskProfessorDTO.setTitle("TESTE");
		byte[] bytes = new byte[4];
		taskProfessorDTO.setFiles(new MockMultipartFile("file", bytes));
		
		ArrayList<String> list = new ArrayList<String>(); 
		taskProfessorDTO.setClasses(list);
		
		TaskProfessor taskProfessor = taskProfessorService.transformToTaskProfessor(taskProfessorDTO);
		
		given(this.taskProfessorRepository.save(Mockito.any())).willReturn(taskProfessor);
		assertEquals(taskProfessor, this.taskProfessorService.saveTaskProfessor(taskProfessorDTO)); 
	}
	
	@Test
	void getTaskProfessorEmptyTest() {
		Long a = Long.getLong("123");
		Assertions.assertThrows( new Exception().getClass() , ()->{taskProfessorService.getTaskProfessor(a);});
	}
	
	@Test
	void getTaskProfessorTest() throws Exception {
		Optional<TaskProfessor> taskProfessor = Optional.of(new TaskProfessor());
		Long a = Long.getLong("123");
		given(this.taskProfessorRepository.findById(Mockito.any())).willReturn(taskProfessor);
		assertEquals(taskProfessor.get(), this.taskProfessorService.getTaskProfessor(a));
	}
}

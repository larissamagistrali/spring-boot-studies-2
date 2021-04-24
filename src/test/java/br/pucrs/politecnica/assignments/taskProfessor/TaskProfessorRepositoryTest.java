 	package br.pucrs.politecnica.assignments.taskProfessor;

	import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Optional;

	import javax.transaction.Transactional;

	import org.junit.jupiter.api.Test;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.boot.test.context.SpringBootTest;
	import org.springframework.test.context.ActiveProfiles;

	@SpringBootTest
	@ActiveProfiles("test")
public class TaskProfessorRepositoryTest {

		@Autowired
		private TaskProfessorRepository taskProfessorRepository;
		
		@Test
		@Transactional
		void assertThatFindById() {
			TaskProfessor taskProfessor1 = new TaskProfessor();
			taskProfessor1.setStatus(false);
			taskProfessor1.setTitle("Teste");
			taskProfessor1.setDetails("Teste 123");
			taskProfessor1.setTaskTime(LocalDateTime.of(2100, 11, 11, 11, 11, 11));
			this.taskProfessorRepository.save(taskProfessor1);
			Optional<TaskProfessor> taskProfessor2 = this.taskProfessorRepository.findById(taskProfessor1.getId());
			assertEquals(taskProfessor1.getDetails(), taskProfessor2.get().getDetails());
			
		}
}

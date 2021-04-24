package br.pucrs.politecnica.assignments.taskStudent;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import br.pucrs.politecnica.assignments.feedback.Feedback;
import br.pucrs.politecnica.assignments.file.FileModel;
import br.pucrs.politecnica.assignments.student.Student;
import br.pucrs.politecnica.assignments.student.StudentRepository;
import br.pucrs.politecnica.assignments.taskProfessor.TaskProfessor;
import br.pucrs.politecnica.assignments.taskProfessor.TaskProfessorRepository;

@SpringBootTest
@ActiveProfiles("test")
class TaskStudentRepositoryTest {

	@Autowired
	private TaskStudentRepository taskStudentRepository;
	
	@Autowired
	private TaskProfessorRepository taskProfessorRepository;
	
	@Autowired
	private StudentRepository studentRepository;

	@Test
	@Transactional
	void shouldInsert() {
		TaskStudent taskStudent = new TaskStudent();
		taskStudent.setComment("Teste");
		//taskStudent.setFiles("files");
		taskStudent.setComment("Teste do teste");
		//taskStudent.setFiles("files");
		taskStudent.setFileModel(new FileModel());
		taskStudent.setStatus(false);
		taskStudent.setId(31L);
		taskStudent.setPostedTime(LocalDateTime.now());
		
		//new Student
		Optional<Student> opStudent = this.studentRepository.findByRegistration("181478121212");
		assertThat(opStudent.isPresent()).isEqualTo(true);
					
		//new taskProfessor
		Optional<TaskProfessor> opTaskProfessor = this.taskProfessorRepository.findByTitle("Tarefa de Teste 1");
		taskStudent.setStudent(opStudent.get());
		taskStudent.setTaskProfessor(opTaskProfessor.get());
		Feedback feedback = new Feedback();
		feedback.setStatus(false);
		feedback.setId(1L);
		taskStudent.setFeedback(feedback);
		long oldTam  = this.taskStudentRepository.count();
		this.taskStudentRepository.save(taskStudent);
		
		long newTam = this.taskStudentRepository.count();

		assertThat(oldTam+1).isEqualTo(newTam);
	}
}

package br.pucrs.politecnica.assignments.taskStudent;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import br.pucrs.politecnica.assignments.feedback.Feedback;
import br.pucrs.politecnica.assignments.file.FileModel;
import br.pucrs.politecnica.assignments.login.Login;
import br.pucrs.politecnica.assignments.login.LoginRepository;
import br.pucrs.politecnica.assignments.student.Student;
import br.pucrs.politecnica.assignments.student.StudentRepository;
import br.pucrs.politecnica.assignments.taskProfessor.TaskProfessor;
import br.pucrs.politecnica.assignments.taskProfessor.TaskProfessorService;

@Service
public class TaskStudentService {

	private TaskStudentRepository taskStudentRepository;

	private LoginRepository loginRepository;

	private StudentRepository studentRepository;

	private TaskProfessorService taskProfessorService;

	public TaskStudentService(TaskStudentRepository taskStudentRepository, LoginRepository loginRepository,
			StudentRepository studentRepository, TaskProfessorService taskProfessorService) {
		this.taskStudentRepository = taskStudentRepository;
		this.loginRepository = loginRepository;
		this.studentRepository = studentRepository;
		this.taskProfessorService = taskProfessorService;
	}

	public Student getStudentLogged(Principal principal) throws Exception {
		Optional<Login> loginOpt = loginRepository.findByEmail(principal.getName());
		if (loginOpt.isPresent()) {
			Optional<Student> studentOpt = this.studentRepository.findByLoginId(loginOpt.get().getId());
			return studentOpt.orElseThrow(() -> new Exception());
		}
		throw new Exception();
	}

	public TaskStudent saveTaskStudent(TaskStudentDTO taskStudentDTO, Long taskProfessorId, Principal principal)
			throws Exception {
		return taskStudentRepository.save(transformToTaskStudent(taskStudentDTO, getStudentLogged(principal),
				taskProfessorService.getTaskProfessor(taskProfessorId)));
	}

	public TaskStudent transformToTaskStudent(TaskStudentDTO taskStudentDTO, Student student,
			TaskProfessor taskProfessor) throws IOException {
		TaskStudent taskStudent = new TaskStudent();
		FileModel filemodel = new FileModel(taskStudentDTO.getFiles().getOriginalFilename(),
				taskStudentDTO.getFiles().getContentType(), taskStudentDTO.getFiles().getBytes());
		taskStudent.setFileModel(filemodel);
		taskStudent.setStatus(taskStudentDTO.getSendTask());
		taskStudent.setStudent(student);
		taskStudent.setTask(taskProfessor);
		taskStudent.setPostedTime(LocalDateTime.now());
		taskStudent.setComment(taskStudentDTO.getComment());
		Feedback feedBack = new Feedback();
		feedBack.setStatus(false);
		taskStudent.setFeedback(feedBack);
		taskStudent.setTaskProfessor(taskProfessor);
		return taskStudent;
	}
	
	public TaskStudentDTO getTaskStudentDTOByStudent(Student student, TaskProfessor taskProfessor) throws Exception {
        TaskStudent taskStudent = getTaskStudentByStudent(student, taskProfessor);
        TaskStudentDTO taskStudentDTO = new TaskStudentDTO();
        taskStudentDTO.setComment(taskStudent.getComment());
        taskStudentDTO.setSendTask(taskStudent.getStatus());
        return taskStudentDTO;
    }

	public TaskStudent getTaskStudentByStudent(Student student, TaskProfessor taskProfessor) throws Exception {
		Set<TaskStudent> taskStudentList = student.getTaskStudent();
		if (taskStudentList != null) {
			for (TaskStudent t : taskStudentList) {
				if (t.getTaskProfessor().equals(taskProfessor)) {
					return t;
				}
			}
		}
		return new TaskStudent();
	}

	public TaskStudent findTaskStudentById(Long id) throws Exception {
		Optional<TaskStudent> taskStudentOptional = taskStudentRepository.findById(id);
		return taskStudentOptional.orElseThrow(() -> new Exception());
	}
	
	public TaskStudent getTaskStudent(Long taskStudentId) throws Exception {
		Optional<TaskStudent> taskStudent = this.taskStudentRepository.findById(taskStudentId);
		return taskStudent.orElseThrow(() -> new Exception());
	}

}

package br.pucrs.politecnica.assignments.student;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.pucrs.politecnica.assignments.classes.Classes;
import br.pucrs.politecnica.assignments.login.Login;
import br.pucrs.politecnica.assignments.login.LoginRepository;
import br.pucrs.politecnica.assignments.taskProfessor.TaskProfessor;
import br.pucrs.politecnica.assignments.taskProfessor.TaskProfessorRepository;

@Service
public class StudentService {

	private StudentRepository studentRepository;

	private LoginRepository loginRepository;

	private TaskProfessorRepository taskProfessorRepository;

	public StudentService(StudentRepository studentRepository, LoginRepository loginRepository,
			TaskProfessorRepository taskProfessorRepository) {
		this.studentRepository = studentRepository;
		this.loginRepository = loginRepository;
		this.taskProfessorRepository = taskProfessorRepository;
	}

	public List<Student> getStudentList() throws Exception {
		Optional<List<Student>> optList = this.studentRepository.findAllOrderByName();
		return optList.orElseThrow(() -> new Exception());
	}

	public Student getStudentLogged(Principal principal) throws Exception {
		Optional<Student> studentList = this.studentRepository
				.findByLoginId(loginRepository.findByEmail(principal.getName()).get().getId());
		return studentList.orElseThrow(() -> new Exception());
	}

	public LinkedHashMap<TaskProfessor, Classes> getTasksToDoList(Student student) throws Exception {
		HashMap<TaskProfessor, Classes> map = new LinkedHashMap<>();
		for (Classes c : student.getClasses()) {
			for (TaskProfessor t : c.getTasks()) {
				if(!t.getCanceled()) {
					Optional<TaskProfessor> task = taskProfessorRepository.findById(t.getId());
					if (task.isPresent()) {
						if (task.get().getStatus()) {
							map.put(task.get(), c);
						}
					}
				}
			}
		}
		LinkedHashMap<TaskProfessor, Classes> result = map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey((TaskProfessor e1, TaskProfessor e2) -> e1.getTaskTime().compareTo(e2.getTaskTime())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
		return result;
	}
	
	public List<Boolean> getStatusByMapTasks(LinkedHashMap<TaskProfessor, Classes> mapa, Student student) {
		List<Boolean> resp = new ArrayList<>();
		for(TaskProfessor task: mapa.keySet()) {
				resp.add(student.getTaskStudent().stream().filter(p -> p.getTaskProfessor().getId() == task.getId()).findFirst().get().getStatus());
		}
		return resp;
	}

	public Student saveStudent(Login login, String registration) {
		Student student = new Student();
		student.setRegistration(registration);
		student.setLogin(login);
		student.setId(login.getId());
		return studentRepository.save(student);
	}
	
	public Student findByName(String name) {
		return studentRepository.findByName(name).get();
	}
	
}

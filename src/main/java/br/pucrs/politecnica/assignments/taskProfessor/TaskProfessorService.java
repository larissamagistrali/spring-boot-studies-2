package br.pucrs.politecnica.assignments.taskProfessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import br.pucrs.politecnica.assignments.classes.Classes;
import br.pucrs.politecnica.assignments.classes.ClassesService;
import br.pucrs.politecnica.assignments.discipline.Discipline;
import br.pucrs.politecnica.assignments.file.FileModel;
import br.pucrs.politecnica.assignments.professor.Professor;
import br.pucrs.politecnica.assignments.student.Student;
import br.pucrs.politecnica.assignments.taskStudent.TaskStudent;

@Service
public class TaskProfessorService {

	private TaskProfessorRepository taskProfessorRepository;
	
	private ClassesService classesService;
	
	public TaskProfessorService(TaskProfessorRepository taskProfessorRepository, ClassesService classesService) {
		this.taskProfessorRepository = taskProfessorRepository;
		this.classesService = classesService;
	}

	public TaskProfessor getTaskProfessor(Long taskProfessorId) throws Exception {
		Optional<TaskProfessor> taskProfessor = this.taskProfessorRepository.findById(taskProfessorId);
		
		return taskProfessor.orElseThrow(() -> new Exception());
	}

	public List<TaskProfessor> listClassesTaskProfessor(List<Classes> classesList, Long classId) {
		List<TaskProfessor> tasksProf = new ArrayList<TaskProfessor>();
		List<TaskProfessor> aux = new ArrayList<TaskProfessor>();
		for (Classes classes : classesList) {
			if (classes.getId() == classId) {
				aux = classes.getTasks();
			}
		}
		for(TaskProfessor t:aux)
		{
			if(!t.getCanceled()) {
				tasksProf.add(t);
			}
		}
		return tasksProf;
	}
	
	public TaskProfessor transformToTaskProfessor(TaskProfessorDTO taskProfessorDTO) throws IOException {
		TaskProfessor taskProfessor = new TaskProfessor();
		FileModel filemodel = new FileModel(taskProfessorDTO.getFiles().getOriginalFilename(),taskProfessorDTO.getFiles().getContentType(),taskProfessorDTO.getFiles().getBytes());
		try {
			taskProfessor.setClasses(classesService.getClassesByStringList(taskProfessorDTO.getClasses()));
		} catch (Exception e) {
			taskProfessor.setClasses(new HashSet<Classes>());
		}
		taskProfessor.setDetails(taskProfessorDTO.getDetails());
		taskProfessor.setTaskTime(taskProfessorDTO.getTaskTime());
		taskProfessor.setTitle(taskProfessorDTO.getTitle());
		taskProfessor.setStatus(taskProfessorDTO.isStatus());
		taskProfessor.setCanceled(false);
		taskProfessor.setFileModel(filemodel);
		
		return taskProfessor;
	}
	
	public TaskProfessor transformToTaskProfessor(TaskProfessorDTO taskProfessorDTO,Long taskProfessorId) throws IOException {
		TaskProfessor taskProfessor = taskProfessorRepository.findById(taskProfessorId).get();
		FileModel filemodel = new FileModel(taskProfessorDTO.getFiles().getOriginalFilename(),taskProfessorDTO.getFiles().getContentType(),taskProfessorDTO.getFiles().getBytes());
		try {
			taskProfessor.setClasses(classesService.getClassesByStringList(taskProfessorDTO.getClasses()));
		} catch (Exception e) {
			taskProfessor.setClasses(new HashSet<Classes>());
		}
		taskProfessor.setDetails(taskProfessorDTO.getDetails());
		taskProfessor.setTaskTime(taskProfessorDTO.getTaskTime());
		taskProfessor.setTitle(taskProfessorDTO.getTitle());
		taskProfessor.setStatus(taskProfessorDTO.isStatus());
		taskProfessor.setCanceled(false);
		taskProfessor.setFileModel(filemodel);
		return taskProfessor;
	}
	
	public TaskProfessorDTO transformToTaskProfessorDTO(TaskProfessor taskProfessor) throws IOException {
		TaskProfessorDTO taskProfessorDTO = new TaskProfessorDTO();
		try {
			taskProfessorDTO.setClasses(classesService.getStringsByClassesSet(taskProfessor.getClasses()));
		} catch (Exception e) {
			taskProfessorDTO.setClasses(new ArrayList<String>());
		}
		taskProfessorDTO.setDetails(taskProfessor.getDetails());
		taskProfessorDTO.setTaskTime(taskProfessor.getTaskTime()); 
		taskProfessorDTO.setTitle(taskProfessor.getTitle());
		taskProfessorDTO.setStatus(taskProfessor.getStatus());
		return taskProfessorDTO;
	}
	
	public TaskProfessor saveTaskProfessor(TaskProfessorDTO taskProfessorDTO) throws Exception {
		return taskProfessorRepository.save(transformToTaskProfessor(taskProfessorDTO));
	}
	
	public TaskProfessor saveTaskProfessor(TaskProfessorDTO taskProfessorDTO,Long taskProfessorId) throws Exception {
		return taskProfessorRepository.save(transformToTaskProfessor(taskProfessorDTO,taskProfessorId));
	}
	public TaskProfessor saveTaskProfessor(TaskProfessor taskProfessor) throws Exception {
		return taskProfessorRepository.save(taskProfessor);
	}

	public Professor getProfessor(TaskProfessor taskProfessor) throws Exception {
		Set<Classes> classes = taskProfessor.getClasses();
		for (Classes c : classes) {
			return c.getProfessor();
		}
		throw new Exception();
	}
	
	public Discipline getDisciplineByStudent(TaskProfessor taskProfessor, Student student) throws Exception{
		Set<Classes> classes = taskProfessor.getClasses();
		for (Classes c : classes) {
			for (Student s : c.getStudents()) {
				if(s.equals(student)) {
					return c.getDiscipline();
				}
			}	
		}
		throw new Exception();
	}
	
	public Classes getClassByStudent(TaskProfessor taskProfessor, Student student) throws Exception{
		Set<Classes> classes = taskProfessor.getClasses();
		for (Classes c : classes) {
			for (Student s : c.getStudents()) {
				if(s.equals(student)) {
					return c;
				}
			}	
		}
		throw new Exception();
	}
	
	public Discipline getTaskProfessorsDiscipline(TaskProfessor task)
	{
		Discipline discipline = new Discipline();
		for(Classes c:task.getClasses())
		{
			discipline = c.getDiscipline();
			break;
		}
		return discipline;
	}
	

	public LinkedHashMap<TaskProfessor, TaskStudent> getTaskProfessorListByStudent(Student student){
		LinkedHashMap<TaskProfessor, TaskStudent> result = new LinkedHashMap<>();
		student.getClasses() 
				.stream()//Itera sobre a lista de turmas do estudante	
				.map(p -> p.getTasks()) //Mapeia a stream para cada uma ser uma coleção de taskProfessor da turma (Pega todas as taskProfessor de todas as turmas que o aluno está matriculado)
				.forEach(p -> p.stream().filter(a -> a.getStatus()) //Itera sobre as TaskProfessor
					.forEach(q -> result.put(q, //para cada taskProfessor das turmas que o aluno está matriculado, ele insere como key a taskProfessor
												(student.getTaskStudent().stream().filter(r -> r.getTaskProfessor() == q) //itera sobre as taskStudent do student e filtra apenas as que são daquela taskProfessor
												.findFirst().isPresent()? //verifica se está presente
												student.getTaskStudent().stream().filter(r -> r.getTaskProfessor() == q).findFirst().get() //insere a taskStudent como value se presente
												:null)))); //insere null se não presente
		
		List<Map.Entry<TaskProfessor, TaskStudent>> entries = new ArrayList<Map.Entry<TaskProfessor, TaskStudent>>(result.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<TaskProfessor, TaskStudent>>() {
  			public int compare(Map.Entry<TaskProfessor, TaskStudent> a, Map.Entry<TaskProfessor, TaskStudent> b){
    		return a.getKey().getTaskTime().compareTo(b.getKey().getTaskTime());
		  }});
		  
		LinkedHashMap<TaskProfessor, TaskStudent> sortedMap = new LinkedHashMap<TaskProfessor, TaskStudent>();
		for (Map.Entry<TaskProfessor, TaskStudent> entry : entries) {
  			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	} 

}

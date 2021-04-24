package br.pucrs.politecnica.assignments.discipline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.pucrs.politecnica.assignments.classes.Classes;
import br.pucrs.politecnica.assignments.login.Login;
import br.pucrs.politecnica.assignments.taskProfessor.TaskProfessor;

@Service
public class DisciplineService {

	private DisciplineRepository disciplineRepository;

	public DisciplineService(DisciplineRepository disciplineRepository) {
		this.disciplineRepository = disciplineRepository;
	}

	public List<Discipline> getDisciplineList() throws Exception {
		Optional<List<Discipline>> optList = disciplineRepository.findAllOrderByName();
		return optList.orElseThrow(() -> new Exception());
	}

	public Discipline saveDiscipline(DisciplineDTO disciplineDTO, Login person) {
		return disciplineRepository.save(disciplineDTO.transformToDiscipline(person.getName()));
	}

	public List<Discipline> getDisciplinesByClasses(List<Classes> classes) {
		List<Discipline> disciplines = new ArrayList<Discipline>();
		for (Classes c : classes) {
			if (!disciplines.contains(c.getDiscipline())) {
				disciplines.add(c.getDiscipline());
			}
		}
		return disciplines;
	}

	public Discipline findById(Long id) throws Exception {
		Optional<Discipline> discipline = disciplineRepository.findById(id);
		return discipline.orElseThrow(() -> new Exception());
	}

	public Map<TaskProfessor, Classes> getClassesAndTaskProfessorByDiscipline(Discipline discipline) {
		List<Classes> classes = discipline.getClasses();
		Map<TaskProfessor, Classes> classesTaskProfessor = new HashMap<>();
		for (Classes c : classes) {
			for (TaskProfessor t : c.getTasks()) {
				if(!t.getCanceled()) {
					classesTaskProfessor.put(t, c);
				}
				
			}
		}
		return classesTaskProfessor;
	}
	
	public List<Discipline> getAllDisciplines(){
		Iterable<Discipline> discipline = this.disciplineRepository.findAll();
		List<Discipline> disciplineList = new ArrayList<Discipline>();
		discipline.forEach(disciplineList::add);
		return disciplineList;
	}

}

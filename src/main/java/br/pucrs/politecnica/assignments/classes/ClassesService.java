package br.pucrs.politecnica.assignments.classes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import br.pucrs.politecnica.assignments.discipline.Discipline;
import br.pucrs.politecnica.assignments.discipline.DisciplineService;
import br.pucrs.politecnica.assignments.login.Login;
import br.pucrs.politecnica.assignments.login.LoginRepository;
import br.pucrs.politecnica.assignments.professor.Professor;
import br.pucrs.politecnica.assignments.professor.ProfessorService;
import br.pucrs.politecnica.assignments.student.Student;
import br.pucrs.politecnica.assignments.student.StudentService;

@Service
public class ClassesService {

	private ClassesRepository classesRepository;

	private LoginRepository loginRepository;

	private DisciplineService disciplineService;

	private ProfessorService professorService;

	private StudentService studentService;

	public ClassesService(ClassesRepository classesRepository, LoginRepository loginRepository,
			DisciplineService disciplineService, ProfessorService professorService, StudentService studentService) {
		this.classesRepository = classesRepository;
		this.loginRepository = loginRepository;
		this.disciplineService = disciplineService;
		this.professorService = professorService;
		this.studentService = studentService;
	}

	public Classes registrateClass(ClassesDTO classesDTO) throws Exception {
		return classesRepository.save(transformToClasses(classesDTO));
	}
	
	public Classes editClass(ClassesDTO classesDTO, Long id) throws Exception {
		return classesRepository.save(transformToClasses(classesDTO, id));
	}
	public Classes transformToClasses(ClassesDTO classesDTO) throws Exception {
		Classes classes = new Classes();
		classes.setCode(classesDTO.getCode());
		classes.setDiscipline(this.disciplineService.findById(classesDTO.getDiscipline()));
		classes.setProfessor(this.professorService.findById(classesDTO.getProfessor()));
		classes.setObservation(classesDTO.getObservation());
		Set<Student> studentsList = new HashSet<>();
		for (String studentName : classesDTO.getStudentList()) {
			studentsList.add(this.studentService.findByName(studentName));
		}
		return classes;
	}
	
	public Classes transformToClasses(ClassesDTO classesDTO, Long id) throws Exception {
		Classes classes = new Classes();
		classes.setCode(classesDTO.getCode());
		classes.setDiscipline(this.disciplineService.findById(classesDTO.getDiscipline()));
		classes.setProfessor(this.professorService.findById(classesDTO.getProfessor()));
		classes.setObservation(classesDTO.getObservation());
		Set<Student> studentsList = new HashSet<>();
		classes.setId(id);
		for (String studentName : classesDTO.getStudentList()) {
			studentsList.add(this.studentService.findByName(studentName));
		}
		return classes;
	}
	
	public List<Classes> getClassesByUser(Principal principal) throws Exception  {
        Optional<Login> log = loginRepository.findByEmail(principal.getName());
        Optional<List<Classes>> classes = classesRepository.findByProfessorId(log.get().getId());
        return classes.orElseThrow(() -> new Exception());
    }
	
	public Set<Classes> getClassesByLongList(List<Long> longList){
		Set<Classes> classesList = new HashSet<Classes>();
		for (Long l : longList) {
			Optional<Classes> classOpt = classesRepository.findById(l);
			if (classOpt.isPresent()) {
				classesList.add(classOpt.get());
			}
		}
		return classesList;
	}

	public Classes getClassesById(Long id) throws Exception {
		try {
			Classes thisclass = this.classesRepository.findById(id).get();
			return thisclass;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Set<Classes> getClassesByStringList(List<String> classes) {
		Set<Classes> classesList = new HashSet<Classes>();
		for (String l : classes) {
			Optional<Classes> classOpt = classesRepository.findByCode(l);
			if (classOpt.isPresent()) {
				classesList.add(classOpt.get());
			}
		}
		return classesList;
	}

	public List<String> getStringsByClassesSet(Set<Classes> classes) {
		List<String> stringList = new ArrayList<String>();
		for (Classes c : classes) {
			stringList.add(c.getCode());
		}
		return stringList;
	}
	
	public ClassesDTO transformToClassesDTO(Long id) {
			Classes c;
			ClassesDTO cDTO = new ClassesDTO();
			try {
				c = getClassesById(id);
				cDTO = new ClassesDTO();
				cDTO.setCode(c.getCode());
				cDTO.setDiscipline(c.getDiscipline().getId());
				cDTO.setProfessor(c.getProfessor().getId());
				cDTO.setObservation(c.getObservation());
				List<String> studentNameList = new ArrayList<>();
				for(Student s : c.getStudents()) {
					studentNameList.add(s.getLogin().getName());
				}
				cDTO.setStudentList(studentNameList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return cDTO;
	}

	public List<Classes> getAllClasses() {
		Iterable<Classes> classes = this.classesRepository.findAll();
		List<Classes> classesList = new ArrayList<Classes>();
		classes.forEach(classesList::add);
		return classesList;
	}

	public LinkedHashMap<Professor, List<Classes>> getClassesProfessor(List<Professor> profs) {
		LinkedHashMap<Professor, List<Classes>> professorList2 = new LinkedHashMap<>();
		for (Professor professor : profs) {
			professorList2.put(professor, professor.getClasses());
		}

		return professorList2;
	}
	
	public List<Classes> getListClassesProfessor(Professor professor){
		List<Classes> listClasses = new ArrayList<Classes>();
		List<Classes> setProfessor = professor.getClasses();
		for(Classes c: setProfessor){
			listClasses.add(c);
		}
		return listClasses;
	}
	
	public List<Classes> getListClassesDiscipline(Discipline discipline){
		return discipline.getClasses();
	}
	
	public List<Classes> getListClassesProfessorAndDiscipline(Professor professor, Discipline discipline){
		List<Classes> listClasses = new ArrayList<Classes>();
		List<Classes> setProfessor = professor.getClasses();
		for(Classes c: setProfessor){
			if(c.getDiscipline()==discipline) {
				listClasses.add(c);
			}	
		}
		return listClasses;
	}
	
}

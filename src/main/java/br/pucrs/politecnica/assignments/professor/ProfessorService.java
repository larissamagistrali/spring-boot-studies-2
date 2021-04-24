package br.pucrs.politecnica.assignments.professor;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.pucrs.politecnica.assignments.classes.Classes;
import br.pucrs.politecnica.assignments.discipline.Discipline;
import br.pucrs.politecnica.assignments.login.Login;
import br.pucrs.politecnica.assignments.login.LoginRepository;

@Service
public class ProfessorService {
	@Autowired
	private ProfessorRepository professorRepository;
	
	@Autowired
	private LoginRepository loginRepository;

	public List<Professor> getProfessorList() throws Exception {
		Optional<List<Professor>> optList = this.professorRepository.findAllOrderByName();
		return optList.orElseThrow(() -> new Exception());
	}
	
	public Professor saveProfessor(Login login, String registration) {
		Professor professor = new Professor();
		professor.setRegistration(registration);
		professor.setLogin(login);
		professor.setId(login.getId());
		return professorRepository.save(professor);
	}
	
	
	public Professor getUserProfessor(Principal principal) throws Exception {
		Optional<Professor> professor = this.professorRepository.findById(loginRepository.findByEmail(principal.getName()).get().getId());
		return professor.orElseThrow(() -> new Exception());
	}
	
	public Professor findById(Long id) {
		return professorRepository.findById(id).get();
	}

	public List<Professor> getAllProfessors(){
		Iterable<Professor> professor = this.professorRepository.findAll();
		List<Professor> professorList = new ArrayList<Professor>();
		professor.forEach(professorList::add);
		return professorList;
	}

	public List<Discipline> getDisciplinesByProfessor(Professor professor){
		List<Classes> classes = professor.getClasses();
		List<Discipline> disciplineList = new ArrayList<>();
		for (Classes c : classes) {
			disciplineList.add(c.getDiscipline());
		}
		return disciplineList.stream().distinct().collect(Collectors.toList());
	}
	
}

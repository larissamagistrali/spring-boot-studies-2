package br.pucrs.politecnica.assignments.taskProfessor;


import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TaskProfessorRepository extends CrudRepository<TaskProfessor, Long>{
	
	
	Optional<TaskProfessor> findById(Long id);
	
	Optional<TaskProfessor> findByTitle(String title);	
}

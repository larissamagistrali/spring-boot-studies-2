package br.pucrs.politecnica.assignments.professor;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface ProfessorRepository extends CrudRepository<Professor, Long> {
	Optional<Professor> findByRegistration(String registration);
	
	@Query("SELECT professor FROM Professor professor left join fetch professor.login ORDER BY professor.login.name")
	Optional<List<Professor>> findAllOrderByName();
	
	Optional<Professor> findById(Long id);
}
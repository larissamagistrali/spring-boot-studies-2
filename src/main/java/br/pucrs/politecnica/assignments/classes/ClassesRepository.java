package br.pucrs.politecnica.assignments.classes;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ClassesRepository extends CrudRepository<Classes, Long>{
	
	@Query("SELECT classes FROM Classes classes ORDER BY classes.code")
	Optional<List<Classes>> findAllOrderByCode();

	Optional<Classes> findByCode(String code);

	@Query("SELECT DISTINCT classes FROM Classes classes WHERE classes.professor.id =:id")
	Optional<List<Classes>> findByProfessorId(@Param("id") Long id);
	
	Optional<Classes> findById(Long id);	
}

package br.pucrs.politecnica.assignments.discipline;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface DisciplineRepository extends CrudRepository<Discipline, Long> {
	
	@Query("SELECT discipline FROM Discipline discipline ORDER BY discipline.name")	
	Optional<List<Discipline>> findAllOrderByName();

	Discipline findByCode(@Param("code") String code);

}

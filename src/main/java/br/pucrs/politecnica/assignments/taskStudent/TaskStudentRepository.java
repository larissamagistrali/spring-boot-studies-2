package br.pucrs.politecnica.assignments.taskStudent;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TaskStudentRepository extends CrudRepository <TaskStudent, Long>{

	Optional<TaskStudent> findById(Long id);

	@Query("SELECT DISTINCT task_student FROM TaskStudent task_student WHERE task_student.student.id =:id")
	Optional<Set<TaskStudent>> findByStudentId(@Param("id") Long id);
}

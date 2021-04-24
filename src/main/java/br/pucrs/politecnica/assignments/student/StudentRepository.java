package br.pucrs.politecnica.assignments.student;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends CrudRepository<Student, Long> {

	Optional<Student> findByRegistration(String registration);
	
	@Query("SELECT student FROM Student student left join fetch student.login ORDER BY student.login.name")
	Optional<List<Student>> findAllOrderByName();

	Optional<Student> findByLoginId(Long login_id);
	
	@Query("SELECT student FROM Student student left join fetch student.login WHERE student.login.name =:name")
    Optional<Student> findByName(@Param("name") String name);
	
}

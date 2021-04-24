package br.pucrs.politecnica.assignments.feedback;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface FeedbackRepository extends CrudRepository <Feedback, Long>{
	
		@Transactional
		@Query("SELECT count(feedback.status) FROM Feedback feedback WHERE feedback.status =:status AND feedback.taskStudent.id =:task_student_id")
		 Optional <Integer> findByTaskStudent(@Param("task_student_id") Long task_student_id, @Param("status") Boolean status);

}

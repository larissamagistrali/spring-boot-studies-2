package br.pucrs.politecnica.assignments.file;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FileRepository extends JpaRepository<FileModel, Long>{	
	
	Optional<List<FileModel>> findAllByTaskStudentId(Long taskStudentId);
	
	Optional<List<FileModel>> findAllByTaskProfessorId(Long taskProfessorId);
}

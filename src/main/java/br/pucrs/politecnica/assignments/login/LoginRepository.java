package br.pucrs.politecnica.assignments.login;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface LoginRepository extends CrudRepository<Login, Long> {

	Optional<Login> findByEmail(String email);
	
	Optional<Login> findById(Long id);
	
	@Query("SELECT login FROM Login login ORDER BY login.name")
	Optional<List<Login>> findAllOrderByName();
}

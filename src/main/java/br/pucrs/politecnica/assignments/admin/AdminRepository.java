package br.pucrs.politecnica.assignments.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface AdminRepository extends CrudRepository<Admin, Long>{
	
	Optional<Admin> findByRegistration(String registration);

	@Query("SELECT admin FROM Admin admin left join fetch admin.login ORDER BY admin.login.name")
	Optional<List<Admin>> findAllOrderByName();
	
	
} 
 

package vn.edu.fpt.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vn.edu.fpt.capstone.model.RoleModel;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel,Long> {
	@Transactional   
	@Query(value ="SELECT * FROM role WHERE role = ?1", nativeQuery=true)
	RoleModel getRoleByCode(String role);
}

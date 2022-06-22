package vn.edu.fpt.capstone.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vn.edu.fpt.capstone.model.UserModel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserModel, Long>{
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
	Optional<UserModel> findByUsername(String username);
	Optional<UserModel> findByEmail(String email);
	Optional<UserModel> findByVerificationCode(String verificationCode);
	
//	@Query("SELECT u FROM UserModel u WHERE u.firstName LIKE %:key% or u.lastName LIKE %:key%")
//	List<UserModel> findAllUserSearch(String key);
	
	@Query("SELECT COUNT(u) FROM UserModel u WHERE u.isActive = 1")
	int countUserActive();
	
	@Query("SELECT COUNT(u) FROM UserModel u")
	int getTotalUser();
	
	@Query("SELECT COUNT(u) FROM UserModel u WHERE u.codeTransaction = ?1")
	int getTotalUserCode(String code);
	
}

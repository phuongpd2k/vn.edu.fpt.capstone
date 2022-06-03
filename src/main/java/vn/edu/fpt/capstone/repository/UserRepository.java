package vn.edu.fpt.capstone.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vn.edu.fpt.capstone.model.UserModel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserModel, Long>{
//	@Query("SELECT * FROM USER u WHERE u.EMAIL = :email")
//	 UserModel findByEmail(@Param("email") String email);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
	Optional<UserModel> findByUsername(String username);
	Optional<UserModel> findByEmail(String email);
	Optional<UserModel> findByVerificationCode(String verificationCode);
}

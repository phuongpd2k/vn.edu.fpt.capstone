package vn.edu.fpt.capstone.repository;

import org.springframework.stereotype.Repository;
import vn.edu.fpt.capstone.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long>{
//	@Query("SELECT * FROM USER u WHERE u.EMAIL = :email")
//	 UserModel findByEmail(@Param("email") String email);
}

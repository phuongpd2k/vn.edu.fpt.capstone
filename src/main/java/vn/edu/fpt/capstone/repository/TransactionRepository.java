package vn.edu.fpt.capstone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.edu.fpt.capstone.dto.TransactionDto;
import vn.edu.fpt.capstone.model.TransactionModel;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, Long> {
	@Query(value = "select transaction.* from transaction where transaction.user_id= :userId", nativeQuery = true)
	List<TransactionModel> findByUserId(@Param("userId") Long userId);

	List<TransactionModel> findAllByOrderByCreatedDateDesc();

	
	@Query("SELECT COUNT(u) FROM TransactionModel u WHERE u.code = ?1")
	int getTotalCode(String code);

	@Query("SELECT t FROM TransactionModel t WHERE t.postId = ?1")
	TransactionModel findByPostId(Long id);
}

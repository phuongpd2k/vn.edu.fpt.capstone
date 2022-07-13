package vn.edu.fpt.capstone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.edu.fpt.capstone.model.FeedbackModel;

public interface FeedbackRepository extends JpaRepository<FeedbackModel, Long> {
	@Query(value = "SELECT * FROM feedback f where f.postid= :postId", nativeQuery = true)
	List<FeedbackModel> findByPostId(@Param("postId") Long postId);

	@Query(value = "SELECT * FROM feedback f where f.postid= :postId and f.user_id= :userId", nativeQuery = true)
	List<FeedbackModel> findByPostIdAndUserId(@Param("postId") Long postId, @Param("userId") Long userId);

	@Query("SELECT COUNT(f) FROM FeedbackModel f WHERE f.id = ?1")
	int getAmountByPostId(Long id);

	@Query("SELECT SUM(f.rating) FROM FeedbackModel f WHERE f.id = ?1")
	float getTotalRatingByPostId(Long id);
}

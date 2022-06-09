package vn.edu.fpt.capstone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.edu.fpt.capstone.model.PostModel;

@Repository
public interface PostRepository extends JpaRepository<PostModel, Long> {
	@Query("SELECT pm FROM PostModel pm WHERE pm.enable = true AND pm.createdBy = ?1")
	List<PostModel> findAllByUsername(String username);

	@Query("SELECT pm FROM PostModel pm WHERE pm.enable = true")
	List<PostModel> findAllQuery();

}

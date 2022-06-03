package vn.edu.fpt.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.fpt.capstone.model.PostModel;

@Repository
public interface PostRepository extends JpaRepository<PostModel, Long> {

}

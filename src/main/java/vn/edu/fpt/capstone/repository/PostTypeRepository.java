package vn.edu.fpt.capstone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.edu.fpt.capstone.model.PostTypeModel;

@Repository
public interface PostTypeRepository extends JpaRepository<PostTypeModel, Long>{

	@Query("SELECT ptm FROM PostTypeModel ptm WHERE ptm.enable = true")
	List<PostTypeModel> findAllByEnable();

}

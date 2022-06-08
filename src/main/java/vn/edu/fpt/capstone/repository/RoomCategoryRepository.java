package vn.edu.fpt.capstone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.capstone.model.RoomCategoryModel;
@Repository
public interface RoomCategoryRepository extends JpaRepository<RoomCategoryModel,Long> {
	@Query("SELECT r FROM RoomCategoryModel r WHERE r.enable = true")
	public List<RoomCategoryModel> findAllByEnableTrue();
}

package vn.edu.fpt.capstone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.capstone.model.RoomTypeModel;
@Repository
public interface RoomTypeRepository extends JpaRepository<RoomTypeModel,Long> {
	@Query("SELECT r FROM RoomTypeModel r WHERE r.enable = true")
	public List<RoomTypeModel> findAllByEnableTrue();
}

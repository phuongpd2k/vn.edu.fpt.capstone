package vn.edu.fpt.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.capstone.model.RoomAmenitiesModel;
import vn.edu.fpt.capstone.model.RoomCategoryModel;
import vn.edu.fpt.capstone.model.RoomImageModel;
@Repository
public interface RoomImageRepository extends JpaRepository<RoomImageModel,Long> {
}

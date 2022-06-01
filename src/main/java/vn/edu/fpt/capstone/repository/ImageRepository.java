package vn.edu.fpt.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.fpt.capstone.model.ImageModel;
import vn.edu.fpt.capstone.model.RoomTypeModel;
@Repository
public interface ImageRepository extends JpaRepository<ImageModel,Long> {
}

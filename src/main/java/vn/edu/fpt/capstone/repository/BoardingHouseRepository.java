package vn.edu.fpt.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.capstone.model.AmenityModel;
import vn.edu.fpt.capstone.model.BoardingHouseModel;

@Repository
public interface BoardingHouseRepository extends JpaRepository<BoardingHouseModel,Long> {
}

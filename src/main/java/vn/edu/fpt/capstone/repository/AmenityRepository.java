package vn.edu.fpt.capstone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.capstone.model.AmenityModel;
@Repository
public interface AmenityRepository extends JpaRepository<AmenityModel,Long> {
	@Query("SELECT r FROM AmenityModel r WHERE r.enable = true")
	public List<AmenityModel> findAllByEnableTrue();
}

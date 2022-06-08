package vn.edu.fpt.capstone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import vn.edu.fpt.capstone.model.TypeOfRentalModel;

public interface TypeOfRentalRepository extends JpaRepository<TypeOfRentalModel, Long>{
	@Query("SELECT tor FROM TypeOfRentalModel tor WHERE tor.enable = true")
	public List<TypeOfRentalModel> findAllByEnableTrue();
}

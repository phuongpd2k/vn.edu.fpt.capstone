package vn.edu.fpt.capstone.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.capstone.model.RoomModel;

@Repository
public interface RoomRepository extends JpaRepository<RoomModel,Long> {
	@Query("SELECT rm FROM RoomModel rm WHERE rm.house.id = ?1")
	Page<RoomModel> getListPage(Long house_id, Pageable pageable);

	@Query("SELECT MAX(rm.rentalPrice) FROM RoomModel rm WHERE rm.house.id = ?1")
	int getMaxPrice(Long idHouse);
	
	@Query("SELECT MIN(rm.rentalPrice) FROM RoomModel rm WHERE rm.house.id = ?1")
	int getMinPrice(Long idHouse);

	@Query("SELECT MIN(rm.area) FROM RoomModel rm WHERE rm.house.id = ?1")
	int getMinArea(Long idHouse);

	@Query("SELECT MAX(rm.area) FROM RoomModel rm WHERE rm.house.id = ?1")
	int getMaxArea(Long idHouse);

	@Query("SELECT rm FROM RoomModel rm WHERE rm.enable = true AND rm.house.enable = true AND rm.id = ?1")
	RoomModel getByIdAndEnable(Long lId);
}

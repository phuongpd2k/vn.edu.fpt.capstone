package vn.edu.fpt.capstone.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.capstone.model.RoomModel;

@Repository
public interface RoomRepository extends JpaRepository<RoomModel, Long> {
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

	@Query("SELECT rm FROM RoomModel rm WHERE (rm.enable = true AND rm.house.enable = true AND rm.roomType.id = :typeId "
			+ "AND rm.roomCategory.id = :categoryId AND rm.house.id = :houseId)")
	RoomModel getRoomByTypeAndCategory(@Param("typeId") Long roomType, @Param("categoryId") Long roomCategory,
			@Param("houseId") Long houseId);

	@Query("SELECT rm FROM RoomModel rm WHERE rm.enable = true AND rm.house.enable = true AND rm.house.id = ?1")
	List<RoomModel> getAllRoomOfHouse(Long id);

	@Query("SELECT COUNT(u) FROM RoomModel u WHERE u.createdBy = ?1")
	int getTotalRoomHost(String createdBy);

	@Query(value = "SELECT r.* FROM room r JOIN favorite f ON r.id = f.roomid	WHERE f.userid= :userId", nativeQuery = true)
	List<RoomModel> getRoomFavoriteByUserId(@Param("userId") Long userId);

	@Query("SELECT COUNT(r) > 0"
			+ " FROM RoomModel r WHERE r.house.id = ?1 AND r.name = ?2 AND r.enable = true")
	boolean checkExistRoomName(Long id, String name);

}

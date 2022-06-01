package vn.edu.fpt.capstone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.capstone.model.HouseModel;

@Repository
public interface HouseRepository extends JpaRepository<HouseModel, Long> {
	@Query(value = "select house.* from house inner join address on house.address_id = address.id inner join phuong_xa on address.phuong_xa_id = phuong_xa.xaid where phuong_xa.xaid= :xaid", nativeQuery = true)
	List<HouseModel> findByXaId(@Param("xaid") Long xaId);

	@Query(value = "select count(*) from house inner join room on house.id = room.house_id where house.id= :houseId", nativeQuery = true)
	int countRoomByHouseId(@Param("houseId") Long houseId);

	@Query(value = "select house.* from house where house.user_id= :userId", nativeQuery = true)
	List<HouseModel> findByUserId(@Param("userId") Long userId);
}

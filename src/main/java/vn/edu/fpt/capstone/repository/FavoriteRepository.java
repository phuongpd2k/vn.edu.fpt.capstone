package vn.edu.fpt.capstone.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.capstone.model.FavoriteModel;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteModel,Long> {
	@Query(value = "SELECT * FROM favorite where userid= :userId and roomid= :roomId", nativeQuery = true)
	FavoriteModel findByUserIdAndRoomId(@Param("userId") Long userId, @Param("roomId") Long roomId);
}

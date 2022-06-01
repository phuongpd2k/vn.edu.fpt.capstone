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
}

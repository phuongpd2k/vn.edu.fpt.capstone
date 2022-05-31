package vn.edu.fpt.capstone.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.edu.fpt.capstone.dto.RoomDto;
import vn.edu.fpt.capstone.model.RoomModel;

@Repository
public interface RoomRepository extends JpaRepository<RoomModel,Long> {
//	@Query("select new vn.edu.fpt.capstone.dto.RoomDto(ed) from RoomModel ed")
//	Page<RoomDto> getListPage(Pageable pageable);
}

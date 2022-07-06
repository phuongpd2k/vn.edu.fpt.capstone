package vn.edu.fpt.capstone.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.edu.fpt.capstone.dto.FilterRoomDto;
import vn.edu.fpt.capstone.model.PostModel;
import vn.edu.fpt.capstone.response.HouseHistoryResponse;

@Repository
public interface PostRepository extends JpaRepository<PostModel, Long> {
	@Query("SELECT pm FROM PostModel pm WHERE pm.enable = true AND pm.createdBy = ?1")
	List<PostModel> findAllByUsername(String username);

	@Query("SELECT pm FROM PostModel pm WHERE pm.enable = true")
	List<PostModel> findAllQuery();
	
	@Query("select p from  PostModel p where p.enable = true AND p.isActive = true AND p.house.name LIKE %?1%")
	Page<PostModel> getListPage(String houseName, Pageable pageable);

	@Query("SELECT new vn.edu.fpt.capstone.response.HouseHistoryResponse(p.postType.type, p.cost, p.startDate, p.endDate, p.status) FROM PostModel p WHERE p.createdBy = ?1 AND p.house.id = ?2")
	List<HouseHistoryResponse> getListHouseHistory(String username, Long id);

	@Query("SELECT COUNT(u) FROM PostModel u")
	int getTotalAmountPost();

	@Query("SELECT COUNT(u) FROM PostModel u WHERE u.createdBy = ?1")
	int getTotalAmountPostHost(String createdBy);

	@Query("SELECT pm FROM PostModel pm WHERE pm.enable = true AND pm.house.user.id = ?1")
	List<PostModel> findAllPostByUserId(Long id);

	@Query("SELECT p FROM PostModel p WHERE p.enable = true AND p.isActive = true"
			+ " AND (COALESCE(:dto) is null or p.house.typeOfRental.id IN :dto)")
	Page<PostModel> getFilterPage(@Param("dto") List<Long> dto, Pageable pageable);

}

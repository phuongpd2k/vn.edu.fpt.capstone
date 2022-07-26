package vn.edu.fpt.capstone.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.edu.fpt.capstone.dto.DashBoardData;
import vn.edu.fpt.capstone.model.PostModel;
import vn.edu.fpt.capstone.response.HouseHistoryResponse;
import vn.edu.fpt.capstone.response.HouseResponse;

@Repository
public interface PostRepository extends JpaRepository<PostModel, Long> {
	@Query("SELECT pm FROM PostModel pm WHERE pm.enable = true AND pm.createdBy = ?1")
	List<PostModel> findAllByUsername(String username);

	@Query("SELECT pm FROM PostModel pm WHERE pm.enable = true ORDER BY pm.createdDate DESC")
	List<PostModel> findAllQuery();

	@Query("select p from PostModel p where p.enable = true AND p.isActive = true AND p.house.name LIKE %?1%"
			+ " AND p.house.user.isActive = true"
			+ " AND p.house.enable = true"
			+ " AND p.status = 'CENSORED'"
			+ " AND p.endDate >= ?2"
			+ " AND p.startDate <= ?2"
			+ " ORDER BY p.postCost")
	Page<PostModel> getListPage(String houseName, Date dateNow, Pageable pageable);

	@Query("SELECT new vn.edu.fpt.capstone.response.HouseHistoryResponse(p.postType.type, p.cost, p.startDate, p.endDate, p.status) FROM PostModel p WHERE p.createdBy = ?1 AND p.house.id = ?2")
	List<HouseHistoryResponse> getListHouseHistory(String username, Long id);

	@Query("SELECT COUNT(u) FROM PostModel u")
	int getTotalAmountPost();

	@Query("SELECT COUNT(u) FROM PostModel u WHERE u.createdBy = ?1")
	int getTotalAmountPostHost(String createdBy);

	@Query("SELECT pm FROM PostModel pm WHERE pm.enable = true AND pm.house.user.id = ?1 ORDER BY pm.createdDate DESC")
	List<PostModel> findAllPostByUserId(Long id);

	@Query("SELECT p FROM PostModel p WHERE p.enable = true AND p.isActive = true"
			+ " AND (COALESCE(:houseTypeIds) is null or p.house.typeOfRental.id IN :houseTypeIds)"
			+ " AND (p.room.rentalPrice >= :minPrice) AND (p.room.rentalPrice <= :maxPrice)"
			+ " AND (COALESCE(:roomCategoryIds) is null or p.room.roomCategory.id IN :roomCategoryIds)"
			+ " AND (p.room.maximumNumberOfPeople = :maximumNumberOfPeople)"
			// + " AND (COALESCE(:amenityIds) is null or COALESCE(:amenityIds) =
			// COALESCE(p.room.amenities))"
			+ " GROUP BY p.room.id")
	Page<PostModel> getFilterPage(@Param("houseTypeIds") List<Long> houseTypeIds, @Param("minPrice") int minPrice,
			@Param("maxPrice") int maxPrice, @Param("roomCategoryIds") List<Long> roomCategoryIds,
			@Param("maximumNumberOfPeople") int maximumNumberOfPeople, Pageable pageable);

	@Query(value = "SELECT p.* FROM favorite f JOIN post p ON f.postId = p.id WHERE f.userid= :userId", nativeQuery = true)
	List<PostModel> findAllFavoritePostByUserId(@Param("userId") Long userId);

	@Query(value="SELECT * FROM post WHERE post.enable = true AND post.is_active = true"
			+ " AND post.status = 'CENSORED'"
			+ " AND post.end_date >= ?1"
			+ " AND post.start_date <= ?1"
			+ " ORDER BY post.post_cost DESC Limit 0, 8", nativeQuery = true)
	List<PostModel> findTop8(Date dateNow);

	@Query("SELECT new vn.edu.fpt.capstone.dto.DashBoardData(MONTH(p.createdDate), COUNT(p))"
			+ " FROM PostModel p"
			+ " WHERE YEAR(p.createdDate) = ?1"
			+ " GROUP BY MONTH(p.createdDate)"
			+ " ORDER BY MONTH(p.createdDate)")
	List<DashBoardData> getDataPostingDashBoardAdmin(int year);

	@Query("select count(p) > 0"
			+ " from PostModel p where p.post_code = ?1")
	boolean checkExistCode(String code);

	@Query("select new vn.edu.fpt.capstone.response.HouseResponse(p.house.name, p.house.typeOfRental.name, p.verify) from PostModel p where p.enable = true AND p.isActive = true"
			+ " AND p.house.user.isActive = true"
			+ " AND p.house.enable = true"
			+ " AND p.status = 'CENSORED'"
			+ " AND p.endDate >= ?1"
			+ " AND p.startDate <= ?1"
			+ " GROUP BY p.house.name"
			+ " ORDER BY p.postCost")
	List<HouseResponse> getAllHouseNamePosting(Date date);

	@Query("select p from PostModel p where p.enable = true AND p.isActive = true AND p.house.name LIKE %?1%"
			+ " AND p.house.user.isActive = true"
			+ " AND p.house.enable = true"
			+ " AND p.status = 'CENSORED'"
			+ " AND p.endDate >= ?2"
			+ " AND p.startDate <= ?2"
			+ " GROUP BY p.house.id"
			+ " ORDER BY p.postCost")	
	List<PostModel> getAllPostModelContainKey(String key, Date dateNow);

	@Query("select p from PostModel p where p.enable = true AND p.isActive = true"
			+ " AND p.house.user.isActive = true"
			+ " AND p.house.enable = true"
			+ " AND p.status = 'CENSORED'"
			+ " AND p.endDate >= ?1"
			+ " AND p.startDate <= ?1"
			+ " ORDER BY p.postCost")
	Page<PostModel> getFilterPageTop8(Date date, Pageable pageable);

}

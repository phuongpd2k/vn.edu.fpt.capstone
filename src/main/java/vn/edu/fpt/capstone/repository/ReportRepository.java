package vn.edu.fpt.capstone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.capstone.model.ReportModel;

@Repository
public interface ReportRepository extends JpaRepository<ReportModel, Long> {

	@Query(value = "SELECT r.* FROM report r JOIN post p ON r.post_id = p.id "
			+ "LEFT JOIN house h on p.house_id = h.id LEFT JOIN user u ON u.id = h.user_id\r\n"
			+ "where unix_timestamp(r.created_date)*1000 > :startDate "
			+ "AND unix_timestamp(r.created_date)*1000 < :endDate "
			+ "AND (u.full_name like %:fullName% OR u.username = :userName)", nativeQuery = true)
	List<ReportModel> searchReports(@Param("startDate") Long startDate, @Param("endDate") Long endDate,
			@Param("fullName") String fullName, @Param("userName") String userName);
}

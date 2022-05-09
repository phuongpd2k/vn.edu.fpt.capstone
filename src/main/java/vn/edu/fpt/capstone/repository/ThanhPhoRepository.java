package vn.edu.fpt.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.edu.fpt.capstone.dto.ThanhPhoDto;
import vn.edu.fpt.capstone.model.ThanhPhoModel;
import vn.edu.fpt.capstone.model.UserModel;

@Repository
public interface ThanhPhoRepository extends JpaRepository<ThanhPhoModel, Long> {
	@Query(value = "SELECT * FROM vn_tinhthanhpho tp WHERE tp.slug = :slug", nativeQuery = true)
	ThanhPhoDto findBySlug(@Param("slug") String slug);
}

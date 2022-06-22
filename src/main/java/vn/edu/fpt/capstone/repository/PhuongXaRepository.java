package vn.edu.fpt.capstone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.edu.fpt.capstone.model.PhuongXaModel;

@Repository
public interface PhuongXaRepository extends JpaRepository<PhuongXaModel, Long>{
	@Query(value = "select * from phuong_xa where maqh= :maqh", nativeQuery = true)
	List<PhuongXaModel> findAllByMaQh(@Param("maqh") Long maQh);
}

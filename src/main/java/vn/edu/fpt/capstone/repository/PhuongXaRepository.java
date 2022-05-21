package vn.edu.fpt.capstone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.fpt.capstone.model.PhuongXaModel;

@Repository
public interface PhuongXaRepository extends JpaRepository<PhuongXaModel, Long>{
	List<PhuongXaModel> findAllByMaQh(Long maQh);
}

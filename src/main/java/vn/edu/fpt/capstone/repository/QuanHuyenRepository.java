package vn.edu.fpt.capstone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.fpt.capstone.model.QuanHuyenModel;
@Repository
public interface QuanHuyenRepository extends JpaRepository<QuanHuyenModel, Long>{
	List<QuanHuyenModel> findAllByMaTp(Long maTp);
}

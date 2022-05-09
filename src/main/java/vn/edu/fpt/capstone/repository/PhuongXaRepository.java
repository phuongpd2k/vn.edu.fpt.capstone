package vn.edu.fpt.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.fpt.capstone.model.PhuongXaModel;

@Repository
public interface PhuongXaRepository extends JpaRepository<PhuongXaModel, Long>{

}

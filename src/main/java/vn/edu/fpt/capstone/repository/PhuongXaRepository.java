package vn.edu.fpt.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.edu.fpt.capstone.model.PhuongXaModel;
import vn.edu.fpt.capstone.model.UserModel;

@Repository
public interface PhuongXaRepository extends JpaRepository<PhuongXaModel, Long>{
}

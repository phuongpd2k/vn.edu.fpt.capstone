package vn.edu.fpt.capstone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import vn.edu.fpt.capstone.model.BankAccountModel;

public interface BankAccountRepository extends JpaRepository<BankAccountModel, Long>{

	@Query("SELECT ba from BankAccountModel ba WHERE ba.enable = true")
	List<BankAccountModel> getAllByEnable();

}

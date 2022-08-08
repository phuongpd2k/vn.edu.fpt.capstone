package vn.edu.fpt.capstone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.capstone.dto.DashBoardDataFloat;
import vn.edu.fpt.capstone.model.TransactionModel;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, Long> {
	@Query(value = "select transaction.* from transaction where transaction.user_id= :userId", nativeQuery = true)
	List<TransactionModel> findByUserId(@Param("userId") Long userId);

	List<TransactionModel> findAllByOrderByCreatedDateDesc();

	
	@Query("SELECT COUNT(u) FROM TransactionModel u WHERE u.code = ?1")
	int getTotalCode(String code);

	@Query("SELECT t FROM TransactionModel t WHERE t.postId = ?1")
	TransactionModel findByPostId(Long id);

	@Query("SELECT t FROM TransactionModel t WHERE t.user.id = ?1 order by t.createdDate desc")
	List<TransactionModel> findAllById(Long id);

	@Query("SELECT SUM(t.amount) FROM TransactionModel t WHERE (t.transferType = 'POSTING' OR t.transferType = 'POSTING_EXTEND' OR t.transferType = 'VERIFY') AND t.status = 'SUCCESS'")
	Float getTotalAmountMoney();

	
	@Query("SELECT SUM(t.amount) FROM TransactionModel t WHERE (t.transferType = 'POSTING' OR t.transferType = 'POSTING_EXTEND' OR t.transferType = 'VERIFY') AND t.user.id = ?1")
	Float getTotalAmountMoneyHost(Long id);

	@Query("SELECT new vn.edu.fpt.capstone.dto.DashBoardDataFloat(MONTH(t.createdDate), SUM(t.amount))"
			+ " FROM TransactionModel t"
			+ " WHERE YEAR(t.createdDate) = ?1"
			+ " AND (t.transferType = 'POSTING' OR t.transferType = 'POSTING_EXTEND' OR t.transferType = 'VERIFY') AND t.status = 'SUCCESS'"
			+ " GROUP BY MONTH(t.createdDate)"
			+ " ORDER BY MONTH(t.createdDate)")
	List<DashBoardDataFloat> getDataRevenueDashBoardAdmin(int year);

	@Query("SELECT new vn.edu.fpt.capstone.dto.DashBoardDataFloat(MONTH(t.createdDate), SUM(t.actualAmount))"
			+ " FROM TransactionModel t"
			+ " WHERE t.user.id = ?1 AND YEAR(t.createdDate) = ?2"
			+ " AND (t.transferType = 'DEPOSIT') AND t.status = 'SUCCESS'"
			+ " AND t.action = 'PLUS'"
			+ " GROUP BY MONTH(t.createdDate)"
			+ " ORDER BY MONTH(t.createdDate)")
	List<DashBoardDataFloat> getDataDepositDashBoardHost(Long id, int year);

	@Query("SELECT new vn.edu.fpt.capstone.dto.DashBoardDataFloat(MONTH(t.createdDate), SUM(t.amount))"
			+ " FROM TransactionModel t"
			+ " WHERE t.user.id = ?1 AND YEAR(t.createdDate) = ?2"
			+ " AND (t.transferType = 'POSTING' OR t.transferType = 'POSTING_EXTEND' OR t.transferType = 'VERIFY') AND t.status = 'SUCCESS'"
			+ " GROUP BY MONTH(t.createdDate)"
			+ " ORDER BY MONTH(t.createdDate)")
	List<DashBoardDataFloat> getDataPostingExtendDashBoardHost(Long id, int year);

	@Query("SELECT t FROM TransactionModel t WHERE t.postId = ?1 AND t.transferType = 'POSTING'")
	TransactionModel findByPostIdAndTransferTypePosting(Long id);

	@Query("SELECT SUM(t.amount) FROM TransactionModel t WHERE (t.transferType = 'REFUND') AND t.status = 'SUCCESS' AND t.user.id = ?1")
	Float getTotalAmountMoneyRefundHost(Long id);

	@Query("SELECT t FROM TransactionModel t WHERE t.postId = ?1 AND t.transferType = ?2")
	TransactionModel findByPostIdAndTransferType(Long id, String type);
}

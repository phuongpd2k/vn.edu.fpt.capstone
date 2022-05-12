package vn.edu.fpt.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.capstone.model.AmenityModel;
import vn.edu.fpt.capstone.model.FeedbackLandlordModel;

@Repository
public interface FeedbackLandLordRepository extends JpaRepository<FeedbackLandlordModel,Long> {
}

package vn.edu.fpt.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.capstone.model.AddressModel;
@Repository
public interface AddressRepository extends JpaRepository<AddressModel, Long> {
}
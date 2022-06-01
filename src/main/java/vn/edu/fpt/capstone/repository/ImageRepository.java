package vn.edu.fpt.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vn.edu.fpt.capstone.model.ImageModel;
@Repository
public interface ImageRepository extends JpaRepository<ImageModel,Long> {
	@Transactional    
	@Query(value = "SELECT * FROM image i where i.image_url = ?1",nativeQuery=true)
	ImageModel getImageByUrl(String url);
}

package com.service.client.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.service.client.model.*;

@Repository("guideRepository")
public interface GuideRepository extends JpaRepository<Guide, Long> {

	@Query(value = "select * from guides where guideAttractionId = ?1 order by guideComment", nativeQuery = true)
	public List<Guide> findByAttractionIdOrderByCommentDesc(long attractionId);

	public Guide findById(long id);
}

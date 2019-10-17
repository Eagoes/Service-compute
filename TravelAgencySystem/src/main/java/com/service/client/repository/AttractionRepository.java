package com.service.client.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.client.model.*;

import javax.smartcardio.ATR;

@Repository("attractionRepository")
public interface AttractionRepository extends JpaRepository<Attraction, Long> {
	
	public List<Attraction> findByAttractionCityOrderByAttractionStarDesc(String attractionCity);

	public Attraction findById(long id);
}

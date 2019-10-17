package com.service.client.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.client.model.Attraction;
import com.service.client.repository.AttractionRepository;

@Service("attractionService")
public class AttractionService {
	AttractionRepository attractionRepository;
	
	public AttractionService(AttractionRepository attractionRepository) {
		// TODO Auto-generated constructor stub
		this.attractionRepository = attractionRepository;
	}
	
	@Autowired
	public List<Attraction> findall() {
		return attractionRepository.findAll();
	}
	
	public void saveAttraction(Attraction attraction) {
		attractionRepository.save(attraction);
	}
	
	public Attraction findAttractionByCity(String city) {
		List<Attraction> attr_list = attractionRepository.findByAttractionCityOrderByAttractionStarDesc(city);
		if(attr_list.size()==0)
			return null;
		else
			return attr_list.get(0);
	}
	
	public Attraction findBestAttractionByCity(String city) {
		List<Attraction> attr_list = attractionRepository.findByAttractionCityOrderByAttractionStarDesc(city);
		if(attr_list.size()==0)
			return null;
		else
			return attr_list.get(0);
	}

	public List<Attraction> findAllAttractionByOrder(String city) {
		return attractionRepository.findByAttractionCityOrderByAttractionStarDesc(city);
	}

	public Attraction getAttraction(long id) {
		return attractionRepository.findById(id);
	}
}

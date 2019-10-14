package com.service.client.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.client.model.Guide;
import com.service.client.repository.GuideRepository;

@Service("guideService")
public class GuideService {

	GuideRepository guideRepository;
	
	public GuideService(GuideRepository guideRepository) {
		// TODO Auto-generated constructor stub
		this.guideRepository = guideRepository;
	}
	
	@Autowired
	public List<Guide> findAll() {
		return guideRepository.findAll();
	}
	
	public Guide saveGuide(Guide guide) {
		return guideRepository.save(guide);
	}
	
	public Guide findGuideByAttractionId(long attractionId) {
		List<Guide> guidelist = guideRepository.findByAttractionIdOrderByCommentDesc(attractionId);
		if(guidelist.size()==0)
			return null;
		else
			return guidelist.get(0);
	}

	public List<Guide> findGuideByAttractionIdInOrder(long attractionId) {
		return guideRepository.findByAttractionIdOrderByCommentDesc(attractionId);
	}

	public Guide findGuideById(long id) {
		return guideRepository.findById(id);
	}
	
}
